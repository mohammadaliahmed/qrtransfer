package qrscanner.application.Activites;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import qrscanner.application.R;


public class Results extends Activity {
    String result;
    TextView text;
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_results);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        text=findViewById(R.id.text);
        home=findViewById(R.id.home);
        result = getIntent().getStringExtra("result");
        text.setText(result);

        if (result.contains("http")) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(result)));
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(Results.this,Splash.class));
               finish();
            }
        });
    }


}

