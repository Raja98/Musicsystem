package com.example.su.musicsystem;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by su on 1/2/18.
 */

public class  BackgroundSoundService extends Service {

MediaPlayer mp;
String  path="",title;

    public static final String NOTIFY_PREVIOUS = "com.delarostudios.notificationdemo.previous";
    public static final String NOTIFY_DELETE = "com.delarostudios.notificationdemo.delete";
    public static final String NOTIFY_PAUSE = "com.delarostudios.notificationdemo.pause";
    public static final String NOTIFY_PLAY = "com.delarostudios.notificationdemo.play";
    public static final String NOTIFY_NEXT = "com.delarostudios.notificationdemo.next";
    private static final int NOTIFICATION_ID_CUSTOM_BIG = 9;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        Log.d("mhfdb",path);
        Log.d("service", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void onStart(Intent intent, int startId) {
        path=intent.getExtras().getString("path");

        title=intent.getExtras().getString("title");
        Log.d("tjdhfjh",title);
        mp = new MediaPlayer();
        try {
            mp.setDataSource(path);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
        String hgdsg=intent.getAction();
        Log.d("djfgjdgf",hgdsg);

        if (Constant.ACTION.STARTFOREGROUND_ACTION.equals(hgdsg)) {

            Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

        } else if (intent.getAction().equals(Constant.ACTION.PREV_ACTION)) {
            Toast.makeText(this, "Clicked Previous", Toast.LENGTH_SHORT).show();
            Log.d("log1", "Clicked Previous");
        } else if (intent.getAction().equals(Constant.ACTION.PLAY_ACTION)) {
            Toast.makeText(this, "Clicked Play", Toast.LENGTH_SHORT).show();
            Log.d("log2", "Clicked Play");
        } else if (intent.getAction().equals(Constant.ACTION.NEXT_ACTION)) {
            Toast.makeText(this, "Clicked Next", Toast.LENGTH_SHORT).show();
            Log.d("log3", "Clicked Next");
        } else if (intent.getAction().equals(
                Constant.ACTION.STOPFOREGROUND_ACTION)) {
            Log.d("log4", "Received Stop Foreground Intent");
            Toast.makeText(this, "Service Stoped", Toast.LENGTH_SHORT).show();
            stopForeground(true);
            stopSelf();
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
//        mp.stop();
    }
    Notification status;
    private final String LOG_TAG = "NotificationService";

    private void showNotification() {
//////    Using RemoteViews to bind custom layouts into Notification
//        RemoteViews views = new RemoteViews(getPackageName(),
//                R.layout.status_bar);
//        RemoteViews bigViews = new RemoteViews(getPackageName(),
//                R.layout.status_bar_expanded);
////
//// showing default album image
//        views.setViewVisibility(R.id.status_bar_icon, View.VISIBLE);
//        views.setViewVisibility(R.id.status_bar_album_art, View.GONE);
//        bigViews.setImageViewBitmap(R.id.status_bar_album_art,
//                Constant.getDefaultAlbumArt(this));

//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        notificationIntent.setAction(Constant.ACTION.MAIN_ACTION);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                notificationIntent, 0);
////
////        Intent previousIntent = new Intent(this, Musicactivity.class);
////        previousIntent.setAction(Constant.ACTION.PREV_ACTION);
////        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
////                previousIntent, 0);
////
////        Intent playIntent = new Intent(this, Musicactivity.class);
////        playIntent.setAction(Constant.ACTION.PLAY_ACTION);
////        PendingIntent pplayIntent = PendingIntent.getService(this, 0,
////                playIntent, 0);
////
////        Intent nextIntent = new Intent(this, Musicactivity.class);
////        nextIntent.setAction(Constant.ACTION.NEXT_ACTION);
////        PendingIntent pnextIntent = PendingIntent.getService(this, 0,
////                nextIntent, 0);
////
////        Intent closeIntent = new Intent(this, Musicactivity.class);
////        closeIntent.setAction(Constant.ACTION.STOPFOREGROUND_ACTION);
////        PendingIntent pcloseIntent = PendingIntent.getService(this, 0,
////                closeIntent, 0);
//
////        views.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
////        bigViews.setOnClickPendingIntent(R.id.status_bar_play, pplayIntent);
////
////        views.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
////        bigViews.setOnClickPendingIntent(R.id.status_bar_next, pnextIntent);
////
////        views.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);
////        bigViews.setOnClickPendingIntent(R.id.status_bar_prev, ppreviousIntent);
////
////        views.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
////        bigViews.setOnClickPendingIntent(R.id.status_bar_collapse, pcloseIntent);
////
//        views.setImageViewResource(R.id.status_bar_play,
//                R.drawable.apollo_holo_dark_pause);
//        bigViews.setImageViewResource(R.id.status_bar_play,
//                R.drawable.apollo_holo_dark_pause);
//
//        views.setTextViewText(R.id.status_bar_track_name, "Song Title");
//        bigViews.setTextViewText(R.id.status_bar_track_name, "Song Title");
//
//        views.setTextViewText(R.id.status_bar_artist_name, "Artist Name");
//        bigViews.setTextViewText(R.id.status_bar_artist_name, "Artist Name");
//
//        bigViews.setTextViewText(R.id.status_bar_album_name, "Album Name");
//
//        status = new Notification.Builder(this).build();
//        status.contentView = views;
//        status.bigContentView = bigViews;
//        status.flags = Notification.FLAG_ONGOING_EVENT;
//        status.icon = R.drawable.musicplayer;
//        status.contentIntent = pendingIntent;
//        startForeground(Constant.NOTIFICATION_ID.FOREGROUND_SERVICE, status);

//        NotificationCompat.Builder notificationbuilder=new NotificationCompat.Builder(this);
//        Intent intent= new Intent(this,MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
//
//
//        notificationbuilder.setContentTitle(title);
//        notificationbuilder.setContentText("Music is playing......");
//        notificationbuilder.setAutoCancel(true);
//        notificationbuilder.setSmallIcon(R.mipmap.ic_launcher);
////        notificationbuilder.setSound(defaultSoundUri);
//        notificationbuilder.setContentIntent(pendingIntent);
//        NotificationManager notifactionmanager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        notifactionmanager.notify(0,notificationbuilder.build());

    }


}
