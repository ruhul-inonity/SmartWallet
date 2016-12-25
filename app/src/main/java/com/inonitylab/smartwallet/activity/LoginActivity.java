package com.inonitylab.smartwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inonitylab.smartwallet.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login, register;
    private EditText Email, Pass;

    private DBHelper db;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBHelper(this);
        session = new Session(this);

        Email = (EditText) findViewById(R.id.email);
        Pass = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.loginBtn);
        register = (Button)findViewById(R.id.regBtn);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        if(session.loggedin()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }




//        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//
//        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putInt(getString(R.string.saved_high_score), newHighScore);
//        editor.commit();
//
//
//        TextView textView = (TextView) findViewById(R.id.output);
//        textView.setText(usernames);
//



    }

    @Override

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.loginBtn:
                login();
                break;
            case R.id.regBtn:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            default:

        }
    }

    private void login(){
        String email = Email.getText().toString();
        String password = Pass.getText().toString();

        if(db.getUser(email,password)){
            session.setLoggedIn(true);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Wrong email/password!", Toast.LENGTH_SHORT).show();
        }
    }

}
