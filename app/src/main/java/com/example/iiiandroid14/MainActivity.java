package com.example.iiiandroid14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CALL_LOG)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.READ_CALL_LOG},
                    1);

        }else{
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            init();
        }
    }

    private void init(){
        contentResolver = getContentResolver();
    }

    public void test1(View view){
        Cursor c = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null);
//        String[] fields = c.getColumnNames();
//        for (String field : fields){
//            Log.v("brad", field);
//        }

        int indexName = c.getColumnIndex("display_name");
        int indexData1 = c.getColumnIndex("data1");

        while (c.moveToNext()){
            String name = c.getString(indexName);
            String tel = c.getString(indexData1);
            Log.v("brad", name + ":" + tel);
        }

        c.close();
    }


    public void test2(View view) {
        Cursor c = contentResolver.query(
                Settings.System.CONTENT_URI,
                null,null,null,null);

//        String[] fields = c.getColumnNames();
//        for (String field : fields){
//            Log.v("brad", field);
//        }

        int indexName = c.getColumnIndex("name");
        int indexvalue = c.getColumnIndex("value");
        while (c.moveToNext()){
            String name = c.getString(indexName);
            String value = c.getString(indexvalue);
            Log.v("brad", name + ":" + value);
        }


    }

    public void test3(View view) {
        Cursor c = contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                null,null,null,null);

//        String[] fields = c.getColumnNames();
//        for (String field : fields){
//            Log.v("brad", field);
//        }

        //CallLog.Calls.CACHED_NAME
        //CallLog.Calls.NUMBER
        //CallLog.Calls.TYPE => CallLog.Calls.INCOMING_TYPE; CallLog.Calls.OUTGOING_TYPE, CallLog.Calls.MISSED_TYPE
        //CallLog.Calls.DATE;
        //CallLog.Calls.DURATION;


        int indexName = c.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int indexTel = c.getColumnIndex(CallLog.Calls.NUMBER);
        int indexType = c.getColumnIndex(CallLog.Calls.TYPE);
        int indexDate = c.getColumnIndex(CallLog.Calls.DATE);
        int indexDuration = c.getColumnIndex(CallLog.Calls.DURATION);

        while (c.moveToNext()){
            String name = c.getString(indexName);
            String tel = c.getString(indexTel);
            long dateValue = Long.parseLong(c.getString(indexDate));
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(dateValue);

            Log.v("brad", name + ":" + tel +":" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1));
        }

    }
}
