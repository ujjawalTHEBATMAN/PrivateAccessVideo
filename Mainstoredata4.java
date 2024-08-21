package com.example.securevideostreamer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.securevideostreamer.model.UserModel;
import com.example.securevideostreamer.specialToast.firebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class Mainstoredata4 extends AppCompatActivity {

    EditText e1;
    Button b1;
    ProgressBar p1;
    String phoneno;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mainstoredata4);
        e1 = findViewById(R.id.Edittext1);
        b1 = findViewById(R.id.button);
        p1 = findViewById(R.id.progressBar);
        phoneno=getIntent().getExtras().getString("phone");

        getUserName();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserName();
            }
        });

    }

    void setUserName() {


        String username =e1.getText().toString();
        if(username.isEmpty()||username.length()<3){
            e1.setError("Enter valid username");
            return;
        }setInProgress(true);
        if(userModel!=null){
            userModel.setUsername(username);
        }else{
            userModel=new UserModel(phoneno,username, Timestamp.now());
        }
        firebaseUtil.currentUserDetail().set(userModel ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(Mainstoredata4.this, Playlist.class);
                    intent.putExtra("phone",phoneno);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

    }
    private void getUserName() {
        setInProgress(true );
        firebaseUtil.currentUserDetail().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    UserModel userModel=task.getResult().toObject(UserModel.class);
                        if(userModel!=null){
                            e1.setText(userModel.getUsername());
                        }
                }

            }
        });
    }

    void setInProgress(boolean inProgress){

        if(inProgress){

            b1.setVisibility(View.GONE);
            p1.setVisibility(View.VISIBLE);
            }
        else{
            b1.setVisibility(View.VISIBLE);
            p1.setVisibility(View.GONE);
        }
    }
}