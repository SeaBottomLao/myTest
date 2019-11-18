package com.example.text;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.os.Bundle;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MyWordsTag";
    private ContentResolver resolver;
    Button buttonQuery = null;
    Button buttonInsert = null;
    Button  buttonDelete = null;
    Button  buttonUpdate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resolver = this.getContentResolver();
        Button buttonQuery = (Button)findViewById(R.id.btn_edit_text);
        Button buttonInsert = (Button) findViewById(R.id.btn_add);
        Button buttonDelete = (Button) findViewById(R.id.btn_delete);
        Button buttonUpdate = (Button) findViewById(R.id.btn_edit);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.vocabularybook.provider/Database");
               String strWord = "Banana";
               String strMeaning = "香蕉";
               String strSample = "this is a Banana";
               ContentValues values = new ContentValues();
               values.put("ENG",strWord);
               values.put("CHN",strMeaning);
               values.put("SAMPLE",strSample);
               Uri newUri = getContentResolver().insert(uri,values);
                Toast.makeText(MainActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.vocabularybook.provider/Database");
                ContentValues values = new ContentValues();
                values.put("CHN","香蕉");
                getContentResolver().update(uri,values,"ENG=?",new String[]{"Banana"});
                Toast.makeText(MainActivity.this,"编辑成功！",Toast.LENGTH_SHORT).show();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.vocabularybook.provider/Database");
                getContentResolver().delete(uri,"ENG = ?",new String []{"Banana"});
                Toast.makeText(MainActivity.this,"删除成功！请在单词本中查看是否成功删除",Toast.LENGTH_SHORT).show();
            }
        });
        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.example.vocabularybook.provider/Database");
                Cursor cursor = getContentResolver().query(uri,null,"ENG=?",new String[]{"Apple"},null);
                while(cursor.moveToNext()){
                    Log.e("ENG",cursor.getString(1));
                    Log.e("CHN", cursor.getString(2));
                    Log.e("SAMPLE", cursor.getString(3));
                    Toast.makeText(MainActivity.this,"查询成功，请在logcat查询！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
