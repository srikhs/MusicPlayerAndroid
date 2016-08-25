package com.example.saisrikanth.serviceapp;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.example.saisrikanth.common.MusicInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ServiceApp extends Service {
    private MediaPlayer mPlayer;
    private int length = 0;
    private long bigN = 0;
    private DBCreate dbC;
    private String state;

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = MediaPlayer.create(this, R.raw.clip1);
        dbC = new DBCreate(this);
//Initializes DB
    }
  //Insert to DB
    private void insertDB(long n, String req) {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat formatter1 = new SimpleDateFormat("hh:mm:ss");
        String date = formatter.format(today);
        String time = formatter1.format(today);
        ContentValues values = new ContentValues();
        values.put(DBCreate.songNumber, n);
        values.put(DBCreate.date, date);
        values.put(DBCreate.time, time);
        values.put(DBCreate.requestMade, req);
        values.put(DBCreate.currentState, state);

        dbC.getWritableDatabase().insert(DBCreate.TABLE_NAME,
                null, values);
        values.clear();
    }
    //Read from DB
    private Cursor DBReader() {
        return dbC.getWritableDatabase().query(
                DBCreate.TABLE_NAME, DBCreate.columns,
                null, new String[] {}, null, null, null);
    }
    //Associated with Interface
    private final MusicInterface.Stub mBinder = new MusicInterface.Stub() {
        //Plays Music
        public void playMusic(long n) {
            Log.i("srikhsCheck", n + " Ugo says Service succeeded!");
            if (n == 1 || n==2 || n==3) {
                bigN=n;
                Log.i("srikhsCheck", n + " Ugo says 12Sing12 succeeded!");
                //Check if it's already playing
                if (mPlayer.isPlaying()) {
                    stopPlaying(n);
                    Log.i("srikhsCheck", n + " Ugo says Sing12 succeeded!");
                    if (n==1)
                    {
                        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.clip1);
                        mPlayer.start();
                    }
                    if (n==2)
                    {
                        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.clip2);
                        mPlayer.start();
                    }
                    if (n==3)
                    {
                        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.clip3);
                        mPlayer.start();
                    }
                    insertDB(n, "Play");
                    state = "Playing Song " + n;
                } else {
                    Log.i("srikhsCheck", n + " Ugo says Sing1233 succeeded!");

                    if (n==1)
                    {
                        mPlayer = MediaPlayer.create(getApplicationContext(),
                                R.raw.clip1);
                        mPlayer.start();
                    }
                    if (n==2)
                    {
                        mPlayer = MediaPlayer.create(getApplicationContext(),
                                R.raw.clip2);
                        mPlayer.start();
                    }
                    if (n==3)
                    {
                        mPlayer = MediaPlayer.create(getApplicationContext(),
                                R.raw.clip3);
                        mPlayer.start();
                    }
                    insertDB(n, "Play");
                    state = "Playing Song " + n;
                }
            }
        }
        //PauseMusic
        public void pauseMusic() {
            if (null != mPlayer) {
                mPlayer.pause();
                length = mPlayer.getCurrentPosition();
            }
            insertDB(bigN, "Pause");
            state = "Paused Song " + bigN;
        }
        //ResumeMusic
        public void resumeMusic() {
            if (null != mPlayer) {
                mPlayer.seekTo(length);
                mPlayer.start();
            }
            insertDB(bigN, "Resume");
            state = "Resumed Song " + bigN;
        }
        //StopMusic
        public void stopMusic() {
            if (mPlayer != null) {
                mPlayer.stop();
            }
            insertDB(bigN, "Stop");
            state = "Stopped Song " + bigN;

        }
    //Showing the transaction
        public String[] TransactionHistory() {
            int i = 1;
            Cursor cursorReader = DBReader();
            String[] AnswerList = new String[100];
            String[] data;
            if (cursorReader != null) {
                while (cursorReader.moveToNext()) {
                    data = new String[6];
                    data[0] = cursorReader.getString(0);
                    data[1] = cursorReader.getString(1);
                    data[2] = cursorReader.getString(2);
                    data[3] = cursorReader.getString(3);
                    data[4] = cursorReader.getString(4);
                    if (cursorReader.getString(5) != null)
                    {data[5] = cursorReader.getString(5);}
                    else{
                        data[5] = "Started App";}
                    AnswerList[i] = "[" + data[0] +"]" + "[Date : " + data[2] + "] [Time : " + data[3] + "] [Request : " + data[4] + " Song " + data[1] + "] [Previous State : " + data[5]+"]";
                    i++;
                }
                cursorReader.close();
            }
            List<String> list = new ArrayList<String>();
            for (String stringVal : AnswerList) {
                if (stringVal != null && stringVal.length() > 0) {
                    list.add(stringVal);
                }
            }AnswerList = list.toArray(new String[list.size()]);
            return AnswerList;

        }


    };
    //WHen play button is clicked while another song is playing
    private void stopPlaying(long n) {
        if (mPlayer != null) {
            Log.i("srikhsCheck", n+" Ugo says Sing12 succeeded!");
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}

