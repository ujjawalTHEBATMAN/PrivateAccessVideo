package com.example.securevideostreamer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.securevideostreamer.specialToast.failederrorloginpagecreating;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class checking extends AppCompatActivity {

    Long time = 60L;
    ProgressBar pb1;

    String verificationCode;
    PhoneAuthProvider.ForceResendingToken token;

    String phoneno;
    Button button;
    EditText editText;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checking);

        phoneno = getIntent().getStringExtra("phoneno").toString();

        button = findViewById(R.id.b1);
        editText = findViewById(R.id.e1);
        pb1 = findViewById(R.id.progressbar);
        sendOtp(phoneno,false);
        progressbarset(false);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonskip=(Button)findViewById(R.id.b22);
        buttonskip.setOnClickListener((v)->{
            startActivity(new Intent(getApplicationContext(), Mainstoredata4.class).putExtra("phone", phoneno));
        });
        button.setOnClickListener(v -> {
            String enteredOTP=editText.getText().toString();
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationCode, enteredOTP);
            SignIn(phoneAuthCredential);

        });

    }
    void sendOtp(String phoneno,boolean isResend){
        progressbarset(true);
        PhoneAuthOptions.Builder builder = new PhoneAuthOptions.Builder(mAuth)
                .setPhoneNumber(phoneno)
                .setTimeout(time, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull
                            PhoneAuthCredential phoneAuthCredential) {
                        verificationCode = phoneAuthCredential.getSmsCode();
                        // Capture code here
                        SignIn(phoneAuthCredential);
                        progressbarset(false);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(checking.this, ""+e, Toast.LENGTH_SHORT).show();

                        progressbarset(false);
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                    {
                        super.onCodeSent(s, forceResendingToken);
                        verificationCode
 = s; // Capture code here (alternative)
                        token = forceResendingToken;
                        failederrorloginpagecreating.showToast(getApplicationContext(), "OTP sent successfully");
                        progressbarset(false);
                    }
                });

        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(token).build());
        }
        else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

    public void progressbarset(boolean inProcess) {
        if (inProcess) {
            pb1.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
        } else {
            pb1.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }
    }

    void SignIn(PhoneAuthCredential phoneAuthCredential) {
        progressbarset(true);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                startActivity(new Intent(getApplicationContext(), Mainstoredata4.class).putExtra("phone", phoneno));
                failederrorloginpagecreating.showToast(getApplicationContext(), "Login Successful");
            } else {

                    failederrorloginpagecreating.showToast(getApplicationContext(), "Login Failed");

            }
        });
    }
}