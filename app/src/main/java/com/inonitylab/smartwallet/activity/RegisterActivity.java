package com.inonitylab.smartwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inonitylab.smartwallet.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button reg;
    private TextView backlogin;
    private EditText email, pass;

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DBHelper(this);   //

        reg = (Button)findViewById(R.id.reg);
        email = (EditText) findViewById(R.id.Email);
        pass = (EditText) findViewById(R.id.Pass);
        backlogin = (TextView) findViewById(R.id.backLogin);

        reg.setOnClickListener(this);
        backlogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.reg:
                register();
                break;
            case R.id.backLogin:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
            default:

        }
    }

    private void register(){
        String mail = email.getText().toString();
        String password = pass.getText().toString();
        if(mail.isEmpty() && password.isEmpty()){
            displayToast("Email/password empty!");
        }
        else {
            db.addUser(mail,password);
            displayToast("user registered!");
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

}
