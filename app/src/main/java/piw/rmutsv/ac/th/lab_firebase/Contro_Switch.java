package piw.rmutsv.ac.th.lab_firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Contro_Switch extends AppCompatActivity {


    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference,datagetFirebase;
    private static final String TAG = "Switch LED";
    private Button Switch1;
    public Integer Value, Value_refer;

    private TextView textViewdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contro__switch);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Switch");

        Switch1 = (Button) findViewById(R.id.btnLED);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Value = dataSnapshot.getValue(Integer.class);
                Log.d(TAG, "Value is:" + Value);
                if (Value == 1) {
                    Switch1.setText("LED ON");
                    Value_refer = 0;
                } else {
                    Switch1.setText("LED OFF");
                    Value_refer = 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "Failed to read value", databaseError.toException());

            }
        });

        Switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.setValue(Value_refer);
            }
        });


        //Get data from Sensor

        //ประกาศ id ของ textView
        textViewdata = (TextView) findViewById(R.id.txtFirebase);

        //สร้างตัวรับข้อมูล Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        datagetFirebase = database.getReference();
        datagetFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {//เรียกข้อมูลที่เกิดการเปลี่ยนแปลงทุกครั้งของค่าข้อมูลใน Path ที่อ้างถึง
                Map map = (Map)dataSnapshot.getValue() ; //ดึงค่าจาก Firebase
                String data1 =String.valueOf(map.get("Senser_Y401")); //ดึงค่าที่อยู่ใน Path
                textViewdata.setText(data1);//แสดงค่า

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {//ไม่สามารถอ่านค่าข้อมูลในdatabase ได้

            }
        });

    }
}
