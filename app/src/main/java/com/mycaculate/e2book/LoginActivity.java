package com.mycaculate.e2book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtAccount,edtPassword,edtNickname;
    ImageButton btn_login, btn_nologup;
    String account,password;
    String[] idForNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化介面
        initView();
        //初始化按鈕
        initButton();
    }
    private void initView(){
        edtAccount = findViewById(R.id.edtaccount);
        edtPassword = findViewById(R.id.edtPassword);
        btn_login =findViewById(R.id.btn_login);
        btn_nologup = findViewById(R.id.btn_noLogup);
    }
    private void initButton(){
        btn_login.setOnClickListener(this);
        btn_nologup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                //確認是否有這個帳號，密碼
                confirmID();
                break;
            case R.id.btn_noLogup:
                //沒註冊時，進入註冊畫面
                Intent intent = new Intent();
                intent.setClass(this,LogUpActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void confirmID(){
        account = edtAccount.getText().toString();
        password = edtPassword.getText().toString();

        LoadingLogin loadingLogin = new LoadingLogin(this,account,password);
        try {
            idForNickname = loadingLogin.execute("http://renewforlive11.000webhostapp.com/test/login.php").get();
            Log.i("idForNickname=",idForNickname[1]);
            Toast.makeText(this,"歡迎您回來:"+idForNickname[1],Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            Intent intent = new Intent();
            intent.putExtra("idForNickname",idForNickname);
            intent.setClass(this,MainActivity.class);
            startActivity(intent);
        }
    }
}
