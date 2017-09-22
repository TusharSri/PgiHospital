package com.example.tushar.pgi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class LoginActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;
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

        final EditText aadharNumberEditText = (EditText) findViewById(R.id.edittext_aadhar_number);
        aadharNumberEditText.addTextChangedListener(new TextWatcher() {
            private static final char space = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 14){
                    Intent dashboardIntent = new Intent(LoginActivity.this,Dashboard.class);
                    dashboardIntent.putExtra("aadharNumber",s.toString().replaceAll(" ",""));
                    dashboardIntent.putExtra("scannedQR","");
                    startActivity(dashboardIntent);
                }
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                // Insert char where needed.
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
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
                Intent dashboardIntent = new Intent(LoginActivity.this,Dashboard.class);
                dashboardIntent.putExtra("scannedQR",result.getContents());
                dashboardIntent.putExtra("aadharNumber","");
                startActivity(dashboardIntent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
