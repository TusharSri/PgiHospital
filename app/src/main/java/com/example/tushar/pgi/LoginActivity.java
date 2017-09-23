package com.example.tushar.pgi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tushar.pgi.model.LoginModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;

public class LoginActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;
    private EditText aadharNumberEditText;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//        qrScan = new IntentIntegrator(this);
//        Button qrCodeScanButton = (Button) findViewById(R.id.button_scan_qr_code);
//        qrCodeScanButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                qrScan.initiateScan();
//            }
//        });

        aadharNumberEditText = (EditText) findViewById(R.id.edittext_aadhar_number);
        password = (EditText) findViewById(R.id.edittext_password);
        Button loginButton = (Button) findViewById(R.id.button_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("login");

                Query queryRef = myRef.orderByChild("uid").equalTo(String.valueOf(aadharNumberEditText.getText()));
                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.getChildren().iterator().hasNext()) {
                            Toast.makeText(LoginActivity.this, "Login not possible", Toast.LENGTH_SHORT).show();
                        }
//                        else {
//                            for (DataSnapshot child : dataSnapshot.getChildren()) {
//                                //((TextView)findViewById(R.id.ghost)).setText(child.getValue().toString());
//                                if (child != null) {
//                                    for (DataSnapshot chi : child.getChildren()) {
//                                        if (chi.getKey().equalsIgnoreCase("password")) {
//                                            if (password.getText().toString().equalsIgnoreCase(chi.getValue().toString())) {
//                                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                                                startActivity(new Intent());
//                                            } else {
//                                                Toast.makeText(LoginActivity.this, "Login not possible", Toast.LENGTH_SHORT).show();
//                                            }
//                                            //(TextView)findViewById(R.id.ghost)).setText(chi.getValue().toString());
//                                        }
//                                    }
//                                } else {
//                                    Toast.makeText(LoginActivity.this, "Login not possible", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }


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
        });


//        aadharNumberEditText.addTextChangedListener(new TextWatcher() {
//            private static final char space = ' ';
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(s.length() == 14){
//                    Intent dashboardIntent = new Intent(LoginActivity.this,Dashboard.class);
//                    dashboardIntent.putExtra("aadharNumber",s.toString().replaceAll(" ",""));
//                    dashboardIntent.putExtra("scannedQR","");
//                    startActivity(dashboardIntent);
//                }
//                if (s.length() > 0 && (s.length() % 5) == 0) {
//                    final char c = s.charAt(s.length() - 1);
//                    if (space == c) {
//                        s.delete(s.length() - 1, s.length());
//                    }
//                }
//                // Insert char where needed.
//                if (s.length() > 0 && (s.length() % 5) == 0) {
//                    char c = s.charAt(s.length() - 1);
//                    // Only if its a digit where there should be a space we insert a space
//                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
//                        s.insert(s.length() - 1, String.valueOf(space));
//                    }
//                }
//            }
//        });


    }

//    //Getting the scan results
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if (result != null) {
//            if (result.getContents() == null) {
//                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
//            } else {
//                Intent dashboardIntent = new Intent(LoginActivity.this,Dashboard.class);
//                dashboardIntent.putExtra("scannedQR",result.getContents());
//                dashboardIntent.putExtra("aadharNumber","");
//                startActivity(dashboardIntent);
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
}
