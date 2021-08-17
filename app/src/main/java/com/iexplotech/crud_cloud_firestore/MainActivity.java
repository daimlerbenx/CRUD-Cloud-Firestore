package com.iexplotech.crud_cloud_firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //declare variable
    private EditText EditText_Name, EditText_Age;
    private Button Button_Create, Button_ShowData;

    private FirebaseFirestore db;

    private String update_id, update_name, update_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connect variable with id from XML
        EditText_Name = findViewById(R.id.EditText_Name_MainActivity);
        EditText_Age = findViewById(R.id.EditText_Age_MainActivity);

        Button_Create = findViewById(R.id.Button_Create_MainActivity);
        Button_ShowData = findViewById(R.id.Button_ShowData_MainActivity);

        //access Cloud Firestore instance
        db = FirebaseFirestore.getInstance();

        //collecting data for update function
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //change the text button create to update
            Button_Create.setText("Update");

            //collect id and name
            update_id = bundle.getString("update_id");
            update_name = bundle.getString("update_name");
            update_age = bundle.getString("update_age");

            //save data to edit field or EditText
            EditText_Name.setText(update_name);
            EditText_Age.setText(update_age);
        } else {
            Button_Create.setText("Create");
        }

        //set clicker for button create
        Button_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = EditText_Name.getText().toString();
                String age = EditText_Age.getText().toString();

                Bundle bundle_auth = getIntent().getExtras();

                if (bundle != null) {
                    String id = update_id;
                    updateToCloudFirestore(id, name, age);
                } else {
                    String id = UUID.randomUUID().toString();
                    //execute function create data
                    saveToCloudFirestore(id, name, age);
                }
            }
        });

        Button_ShowData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReadActivity.class));
            }
        });
    }

    //function create
    private void saveToCloudFirestore(String id, String name, String age) {
        if (!name.isEmpty() && !age.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            map.put("age", age);

            //collection as table, document as unique id, field as data
            db.collection("Employee").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Error, data not inserted", Toast.LENGTH_SHORT).show();
                }
            });

        } else
            Toast.makeText(this, "Error, please insert some data", Toast.LENGTH_SHORT).show();
    }

    //function update
    private void updateToCloudFirestore(String id, String name, String age) {
        db.collection("Employee").document(id).update("name", name, "age", age)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, ReadActivity.class));
                        } else {
                            //concat message after the text Error
                            Toast.makeText(MainActivity.this, "Error, " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}