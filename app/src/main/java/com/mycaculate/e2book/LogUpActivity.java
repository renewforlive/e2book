package com.mycaculate.e2book;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class LogUpActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    Spinner spinner_location;
    EditText edtusername=null,edtPwd=null,edtRePwd=null,edtNickname=null,edtMail=null;
    ImageButton btn_ok,btn_back;
    String[] data = new String[5];
    String[] idForNickname;
    int area_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);

        initView();
        initButton();

        //spinner的使用
        ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(
                this, R.array.location_array, android.R.layout.simple_spinner_item );
        nAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner_location.setAdapter(nAdapter);
        spinner_location.setOnItemSelectedListener(this);

    }
    private void initView(){

        spinner_location =findViewById(R.id.spinner_location);
        edtusername = findViewById(R.id.edtusername);
        edtPwd = findViewById(R.id.edtPwd);
        edtRePwd =findViewById(R.id.edtRePwd);
        edtNickname = findViewById(R.id.edtNickname);
        edtMail = findViewById(R.id.edtMail);
        btn_ok = findViewById(R.id.btn_ok);
        btn_back = findViewById(R.id.btn_back);
    }
    private void initButton(){
        btn_ok.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                //輸入資料確認
                if (!TextUtils.isEmpty(edtusername.getText().toString())){
                    data[0]=edtusername.getText().toString();
                    if (!TextUtils.isEmpty(edtPwd.getText().toString())){
                        data[1]=edtPwd.getText().toString();
                        if (!TextUtils.isEmpty(edtRePwd.getText().toString())){
                            if (!edtRePwd.getText().toString().equals(data[1])){
                                Toast.makeText(this,"密碼不一致喔~~",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else{
                                if (!TextUtils.isEmpty(edtNickname.getText().toString()))data[2]=edtNickname.getText().toString();
                                else {
                                    Toast.makeText(this,"暱稱可以更靠近彼此~~",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        else {
                            Toast.makeText(this,"再輸入一次密碼喔~~",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else {
                        Toast.makeText(this,"沒有輸入密碼喔~~",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    Toast.makeText(this,"沒有輸入帳號喔~~",Toast.LENGTH_SHORT).show();
                    return;
                }

                data[3] = edtMail.getText().toString();
                data[4] = String.valueOf(area_id);
                //上傳輸入資料
                uploadData();

                break;
            case R.id.btn_back:
                break;
        }
    }
    public void uploadData(){
        LoadingLogup loadingLogup = new LoadingLogup(this,data);
        loadingLogup.execute("http://renewforlive11.000webhostapp.com/test/insertmember.php");
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                area_id =11;
                break;
            case 1:
                area_id =12;
                break;
            case 2:
                area_id =13;
                break;
            case 3:
                area_id =14;
                break;
            case 4:
                area_id =15;
                break;
            case 5:
                area_id =16;
                break;
            case 6:
                area_id =17;
                break;
            case 7:
                area_id =18;
                break;
            case 8:
                area_id =19;
                break;
            case 9:
                area_id =20;
                break;
            case 10:
                area_id =21;
                break;
            case 11:
                area_id =22;
                break;
            case 12:
                area_id =23;
                break;
            case 13:
                area_id =24;
                break;
            case 14:
                area_id =25;
                break;
            case 15:
                area_id =26;
                break;
            case 16:
                area_id =27;
                break;
            case 17:
                area_id =28;
                break;
            case 18:
                area_id =29;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        area_id = 11;
    }
}
