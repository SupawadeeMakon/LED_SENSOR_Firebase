package piw.rmutsv.ac.th.lab_firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Contro_Switch extends AppCompatActivity {


    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    private static final String TAG = "Switch LED";
    private Button Switch1;
    public Integer Value, Value_refer;

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

    }
}
