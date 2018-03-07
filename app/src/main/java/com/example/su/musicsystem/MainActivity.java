package com.example.su.musicsystem;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final int MYpermissionrequest=1;
    ArrayList<Detailshow>arrayList;
    ArrayAdapter<String>adapter;
    Viewadapter vb;
 String path="";
  RecyclerView recv_rc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recv_rc=(RecyclerView)findViewById(R.id.recv_rc);
        recv_rc.setLayoutManager(new LinearLayoutManager(this));

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MYpermissionrequest);
            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MYpermissionrequest);
            }
        }
        else
        {


           result();
        }

    }

    public void result() {
      arrayList= new ArrayList<>();
        //getmusic();
        scanDeviceForMp3Files();
//        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
//        lv1.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MYpermissionrequest:
                if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
               if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
               {
                   Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                   result();
               }
               else
               {
                   Toast.makeText(this, "Not granted", Toast.LENGTH_SHORT).show();
                   finish();
               }
            }

        }
    }

//    public void getmusic()
//    {
//        ContentResolver contentResolver=getContentResolver();
//        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        Cursor songcursor=contentResolver.query(uri,null,null,null,null);
//        if(songcursor!=null && songcursor.moveToFirst()) {
//            int songtitle = songcursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
//            do {
//
//                String song=songcursor.getString(songtitle);
//                Log.d("jhsdhjhdk",song);
//                arrayList.add(song);
//                Log.d("arrlist", String.valueOf(arrayList.size()));
//
//
//            }while (songcursor.moveToNext());
//
//        }
//    }


    private List<String> scanDeviceForMp3Files(){
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM
        };
        final String sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";
        List<String> mp3Files = new ArrayList<>();
        arrayList= new ArrayList<>();

        Cursor cursor = null;
        try {
            Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            cursor = getContentResolver().query(uri, projection, selection, null, sortOrder);


            if( cursor != null){
                cursor.moveToFirst();

                while( !cursor.isAfterLast() ){
                    String title = cursor.getString(0);
                    Log.d("title",title);
                    String artist = cursor.getString(1);
                    Log.d("artist",artist);
                    String path = cursor.getString(2);

                    Log.d("path",path);

                    String displayName  = cursor.getString(3);
                    Log.d("displayname",displayName);
                    String songDuration = cursor.getString(4);
                    String album=cursor.getString(5);
                    Log.d("album",album);
                    cursor.moveToNext();
                    if(path != null && path.endsWith(".mp3")) {
                        mp3Files.add(path);
                        Detailshow ob= new Detailshow();
                        ob.setTitle(title);
                        ob.setDuration(songDuration);
                        ob.setPath(path);
                        arrayList.add(ob);
                        vb= new Viewadapter(MainActivity.this,arrayList);
                        recv_rc.setAdapter(vb);
                    }

                }

            }

            // print to see list of mp3 files
            for( String file : mp3Files) {
                Log.i("TAG", file);
            }

        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }finally{
            if( cursor != null){
                cursor.close();
            }
        }
        return mp3Files;
    }

}
