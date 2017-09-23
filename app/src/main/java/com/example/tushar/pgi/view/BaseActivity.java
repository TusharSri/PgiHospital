package com.example.tushar.pgi.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tushar.pgi.R;

public class BaseActivity extends AppCompatActivity {

    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void showDialog(String message){
        if(message==null){
            message ="Please Wait";
        }

        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(message);
        }

        if(!mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    public void dismissDialog(){
        if(mProgressDialog==null){
            return;
        }

        if(mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
}
