package com.example.tushar.pgi.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tushar.pgi.R;
import com.example.tushar.pgi.model.LoginModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class LoginActivity extends BaseActivity {

    private IntentIntegrator qrScan;
    private EditText aadharNumberEditText;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        qrScan = new IntentIntegrator(this);
        Button qrCodeScanButton = (Button) findViewById(R.id.button_scan_qr_code);
        qrCodeScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });

        aadharNumberEditText = (EditText) findViewById(R.id.edittext_aadhar_number);
        password = (EditText) findViewById(R.id.edittext_password);
        Button loginButton = (Button) findViewById(R.id.button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(aadharNumberEditText.getText().length()==0 && password.getText().length()==0){
                    Toast.makeText(LoginActivity.this, "Please enter some values", Toast.LENGTH_SHORT).show();
                }else {
                    showDialog(null);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("login");

                    Query queryRef = myRef.orderByChild("uid").equalTo(String.valueOf(aadharNumberEditText.getText()));
                    queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            dismissDialog();
                            if (!dataSnapshot.getChildren().iterator().hasNext()) {
                                Toast.makeText(LoginActivity.this, "Login not possible", Toast.LENGTH_SHORT).show();
                            }
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                LoginModel model = child.getValue(LoginModel.class);
                                if (password.getText().toString().equalsIgnoreCase(model.getPassword())) {
                                    Intent dashboardIntent = new Intent(LoginActivity.this, Dashboard.class);
                                    dashboardIntent.putExtra("type", model.getType());
                                    dashboardIntent.putExtra("uid", model.getUid());
                                    startActivity(dashboardIntent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login not possible", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                aadharNumberEditText.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
