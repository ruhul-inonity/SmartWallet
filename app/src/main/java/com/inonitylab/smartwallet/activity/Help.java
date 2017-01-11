package com.inonitylab.smartwallet.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.inonitylab.smartwallet.R;

public class Help extends AppCompatActivity {
    private EditText helpEditText;
    private TextView helpTextView;
    private ProgressBar helpProgressBar;
    private Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        helpEditText = (EditText) findViewById(R.id.help_editText);
        helpButton = (Button) findViewById(R.id.help_button);
        helpTextView = (TextView) findViewById(R.id.help_textView);
        helpProgressBar = (ProgressBar) findViewById(R.id.help_progressBar);
        
        helpProgressBar.setVisibility(View.INVISIBLE);
        
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Help.this, "Your feedback is received! We will get back to you soon!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
