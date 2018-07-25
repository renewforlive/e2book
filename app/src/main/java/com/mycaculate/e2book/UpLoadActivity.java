package com.mycaculate.e2book;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.mycaculate.e2book.WebConnect.URI_INSERTBOOK;

public class UpLoadActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtBookName,edtAuthor,edtPublisher,edtPrice, edtNotes;
    ImageButton btn_uploadimg, btn_camera, btn_confirm,btn_back;
    ImageView showimg;
    Spinner spinner_catalog;
    int catalog_id;
    //MainActivity傳來的member_id和nickname
    Bundle bData;
    String[] idForNickname;
    //request的標語
    int IMAGE_TAG = 1;
    int CAMERA = 2;
    //抓手機解析度
    private DisplayMetrics mPhone;
    //選擇圖片的路徑
    public String picturePath;
    //
    private File photo;
    //照相圖片的路徑
    public String packagePath;
    private Uri picUri;

    //字串，新的內容
    String newBookName,newCatalog_id,newAuthor,newPublisher,newPrice,newNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        initView();
        initButton();
        //bData
        bData = getIntent().getExtras();
        if (bData != null){
            idForNickname = bData.getStringArray("bData");
        }
        //手機解析度
        mPhone = new DisplayMetrics();
        //spinner的使用
        ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(
                this, R.array.catalog_array, android.R.layout.simple_spinner_item );
        nAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner_catalog.setAdapter(nAdapter);
        spinner_catalog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        catalog_id = 1;
                        break;
                    case 1:
                        catalog_id = 2;
                        break;
                    case 2:
                        catalog_id = 3;
                        break;
                    case 3:
                        catalog_id = 4;
                        break;
                    case 4:
                        catalog_id = 5;
                        break;
                    case 5:
                        catalog_id = 6;
                        break;
                    case 6:
                        catalog_id = 7;
                        break;
                    case 7:
                        catalog_id = 8;
                        break;
                    case 8:
                        catalog_id = 9;
                        break;
                    case 9:
                        catalog_id = 10;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    //初始化元件
    private void initView(){
        edtBookName = findViewById(R.id.edtBookName);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtPublisher = findViewById(R.id.edtPublisher);
        edtPrice = findViewById(R.id.edtPrice);
        edtNotes = findViewById(R.id.edtNotes);
        btn_camera = findViewById(R.id.btn_camera);
        btn_uploadimg = findViewById(R.id.btn_uploadimg);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_back = findViewById(R.id.btn_cancel);
        showimg = findViewById(R.id.showimg);
        spinner_catalog = findViewById(R.id.spinner_catalog);
    }
    //初始化按鈕
    private void initButton(){
        btn_camera.setOnClickListener(this);
        btn_uploadimg.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    @Override
    //按鈕選擇
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_camera:
                open_camera();
                break;
            case R.id.btn_uploadimg:
                uploadingimg();
                break;
            case R.id.btn_confirm:
                start_ok();
                break;
            case R.id.btn_cancel:
                back();
                break;
        }
    }
    //開啟照相機
    public void open_camera(){
        packagePath = Environment.getExternalStorageDirectory() + "/DCIM";
        Log.i("packagePath",packagePath);
        String fileName = new SimpleDateFormat("yyMMddhhmmss").format(new Date(System.currentTimeMillis())) + ".jpg";
        String path = packagePath;
        photo = new File(path,fileName);
        picUri = Uri.fromFile(photo);
        Log.i("picUri= ",String.valueOf(picUri));
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        startActivityForResult(intent,CAMERA);
    }
    //開啟上傳圖片
    public void uploadingimg(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_TAG);
    }

    @Override
    //選擇照片後的結果
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == IMAGE_TAG && resultCode == RESULT_OK && data !=null){
            Uri selectedImage = data.getData();
            Log.i("selectImage",String.valueOf(data.getData()));

            String id = selectedImage.getLastPathSegment().split(":")[1];
            final String[] imageColumns = {MediaStore.Images.Media.DATA };
            final String imageOrderBy = null;
            Log.i("id=",id);
            Uri uri = getUri();
            picturePath = "path";
            Cursor imageCursor = getContentResolver().query(uri, imageColumns,
                    MediaStore.Images.Media._ID + "=" + id, null, imageOrderBy);

            if (imageCursor.moveToFirst()) {
                picturePath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }

            decodeFile(picturePath);
        }
    }
    //取得外部資源
    private Uri getUri(){
        String state = Environment.getExternalStorageState();
        //判斷是否安裝外部儲存裝置
        if(!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
            //如果找不到外部儲存裝置，則讀取內部裝置
            return MediaStore.Images.Media.INTERNAL_CONTENT_URI;
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }
    //剪裁照片
    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        //設定一張圖片格式
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(filePath, o2);

        showimg.setImageBitmap(bitmap);
        Log.i("Success","is success!");
    }
    //選擇確定按鈕的內容
    public void start_ok(){
        if (picturePath == null){
            Toast.makeText(this,"請選擇照片~~",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            if (TextUtils.isEmpty(edtBookName.getText().toString())){
                Toast.makeText(this,"請輸入書名~~",Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                newBookName = edtBookName.getText().toString();
                if(TextUtils.isEmpty(edtAuthor.getText().toString())){
                    Toast.makeText(this,"請輸入作者~~",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    newAuthor = edtAuthor.getText().toString();
                    if(TextUtils.isEmpty(edtPublisher.getText().toString())){
                        Toast.makeText(this,"請輸入出版者~~",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        newPublisher = edtPublisher.getText().toString();
                        if(TextUtils.isEmpty(edtPrice.getText().toString())){
                            Toast.makeText(this,"請輸入原始價格~~",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{
                            newPrice = edtPrice.getText().toString();
                            newNotes = edtNotes.getText().toString();
                            newCatalog_id = String.valueOf(catalog_id);
                            String[] new_data = new String[]{newBookName,newCatalog_id,newAuthor,newPublisher,newPrice,newNotes};
                            InsertBookTask insertBookTask = new InsertBookTask(this,new_data,picturePath,idForNickname);
                            insertBookTask.execute(URI_INSERTBOOK);
                        }
                    }
                }
            }
        }

    }
    public void back(){
        Intent intent = new Intent();
        intent.putExtra("idForNickname",idForNickname);
        intent.setClass(UpLoadActivity.this,MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.backtomenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backtomenu:
                Intent intent = new Intent();
                intent.putExtra("bData",idForNickname);
                intent.setClass(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
