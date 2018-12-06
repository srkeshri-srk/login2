package com.example.shreyanshkeshari.login2;

import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;

public class MainActivity extends Activity {

    EditText et1,et2;
    String uname,pass;
    boolean login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ProgressBar progressBar =  findViewById(R.id.progressBar);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            public void run()
            {
                progressBar.setVisibility(View.GONE);

                LinearLayout inner = findViewById(R.id.inner);
                inner.setVisibility(View.VISIBLE);
            }
        }, 2000);


    }

    public void login(View view)
    {

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);

         uname = et1.getText().toString().trim();
         pass = et2.getText().toString().trim();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("username");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    String value = ds.getKey();
                    Log.e("------>",value);


                    if ((uname.equals(value)))
                    {
                        login = true;
                        break;
                    }
                    else
                    {
                        login = false;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        if (login)
        {
            Toast.makeText(this, "Valid", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
            FirebaseDatabase fd = FirebaseDatabase.getInstance();
            DatabaseReference dr = fd.getReference("username").child(uname);

            MyFBHandler myfb = new MyFBHandler(pass);
            dr.setValue(myfb);

        }
        et1.setText("");
        et2.setText("");
        et1.requestFocus();

    }

    // this is test only github
}
