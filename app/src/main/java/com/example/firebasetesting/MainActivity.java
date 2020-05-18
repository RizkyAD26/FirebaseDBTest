package com.example.firebasetesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference firebasedb;
    private FirebaseDatabase firebaseinstance;
    private String testvalue;

    EditText xmlval1,xmlval2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xmlval1 = (EditText)findViewById(R.id.nilai1);
        xmlval2 = (EditText)findViewById(R.id.nilai2);

        firebaseinstance = FirebaseDatabase.getInstance();
        firebasedb = firebaseinstance.getReference("NilaiActivity");
        testvalue = firebasedb.push().getKey();
    }

    public void addvalue(String value1,String value2){
        valueactivity ivalue = new valueactivity(value1, value2);
        firebasedb.child("hasilValue").child(testvalue).setValue(ivalue);
    }

    public void updatevalue(String value1,String value2){
        firebasedb.child("hasilvalue").child(testvalue).child(value1).setValue(value1);
        firebasedb.child("hasilvalue").child(testvalue).child(value2).setValue(value2);
    }

    public void addvalue(View view) {
        addvalue(xmlval1.getText().toString().trim(),xmlval2.getText().toString().trim());
    }

    public void updatevalue(View view) {
        updatevalue(xmlval1.getText().toString().trim(),xmlval2.getText().toString().trim());
    }

    public void readvalue(View view) {
        firebasedb.child("hasilvalue").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String dbval1 = ds.child("nilai1").getValue(String.class);
                    String dbval2 = ds.child("nilai2").getValue(String.class);
                    Log.d("READ",dbval1+"/"+dbval2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
