package com.iexplotech.crud_cloud_firestore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ReadActivity activity;

    private List<Model> EmployeeList;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Adapter(ReadActivity activity, List<Model> EmployeeList) {
        this.activity = activity;
        this.EmployeeList = EmployeeList;
    }

    //function read
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(EmployeeList.get(position).getId());
        holder.name.setText(EmployeeList.get(position).getName());
        holder.age.setText(EmployeeList.get(position).getAge());
    }

    @Override
    public int getItemCount() {
        return EmployeeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView id, name, age;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.TextView_ID_item);
            name = itemView.findViewById(R.id.TextView_Name_item);
            age = itemView.findViewById(R.id.TextView_Age_item);
        }
    }

    //for function update, pass existing data into EditText
    //class TouchHelper is created to invoke this method
    public void updateData (int position) {
        Model item =EmployeeList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("update_id", item.getId());
        bundle.putString("update_name", item.getName());
        bundle.putString("update_age", item.getAge());

        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData (int position) {
        Model item = EmployeeList.get(position);
        db.collection("Employee").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            notifyDeletion(position);
                            Toast.makeText(activity, "Data deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "Error, " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyDeletion (int position) {
        //first remove data from employee list
        EmployeeList.remove(position);
        notifyItemRemoved(position);

        //refresh after data removed
        activity.showData();
    }
}
