package com.mycaculate.e2book;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import static com.mycaculate.e2book.WebConnect.URI_LOGIN;

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
        if (!TextUtils.isEmpty(edtAccount.getText().toString())){
            account = edtAccount.getText().toString();
            if (!TextUtils.isEmpty(edtPassword.getText().toString())){
                password = edtPassword.getText().toString();
            }
            else{
                Toast.makeText(this,"沒有輸入密碼喔~~",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else{
            Toast.makeText(this,"沒有輸入帳號喔~~",Toast.LENGTH_SHORT).show();
            return;
        }


        LoadingLogin loadingLogin = new LoadingLogin(this,account,password);
        try {
            idForNickname = loadingLogin.execute(URI_LOGIN).get();
            Log.i("idForNickname=",idForNickname[1]);
            if (idForNickname[0].equals("0")){
                if (idForNickname[1].equals("0")){
                    Toast.makeText(this,"帳號錯誤",Toast.LENGTH_SHORT).show();
                }
                else if (idForNickname[1].equals("1")){
                    Toast.makeText(this,"密碼錯誤",Toast.LENGTH_SHORT).show();
                }
                return;
            }
            else{
                Toast.makeText(this,"歡迎您回來:"+idForNickname[1],Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("bData",idForNickname);
                intent.setClass(this,MainActivity.class);
                startActivity(intent);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
