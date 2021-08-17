package com.iexplotech.crud_cloud_firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {

    private ImageView ImageView_Back;

    private RecyclerView RecyclerView_Employee;

    private FirebaseFirestore db;

    private Adapter adapter;
    private List<Model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        ImageView_Back = (ImageView) findViewById(R.id.ImageView_Home_ReadActivity);
        RecyclerView_Employee = findViewById(R.id.RecyclerView_Employee_ReadActivity);

        final Context context = this;
        ImageView_Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView_Employee.setHasFixedSize(true);
        RecyclerView_Employee.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        list = new ArrayList<>();
        adapter = new Adapter(this, list);

        RecyclerView_Employee.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelper((adapter)));
        touchHelper.attachToRecyclerView(RecyclerView_Employee);

        showData();
    }

    public void showData() {
        db.collection("Employee").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();

                        for (DocumentSnapshot snapshot : task.getResult()) {

                            Model model = new Model(snapshot.getString("id"), snapshot.getString("name"), snapshot.getString("age"));
                            list.add(model);
                        }

                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReadActivity.this, "Error, something wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}