package com.example.sportlightsales;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NoInternetActivity extends AppCompatActivity {

    public static SqlHelper sqlHelper;
    Button btnretry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_no_internet);
            btnretry = findViewById(R.id.btn_Retry);
            btnretry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sqlHelper.executeUrl(sqlHelper.isShowLoading());
                    finish();
                }
            });
        }catch (Exception e){
//            EmailHelper emailHelper = new EmailHelper(NoInternetActivity.this, EmailHelper.TECH_SUPPORT, "Error: NoInternetActivity", e.getMessage() + "\n" + StringHelper.convertStackTrace(e));
//            emailHelper.sendEmail();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        sqlHelper.executeUrl(sqlHelper.isShowLoading());
        finish();
    }
}
