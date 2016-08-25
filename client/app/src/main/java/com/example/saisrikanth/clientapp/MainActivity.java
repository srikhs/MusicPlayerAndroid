package com.example.saisrikanth.clientapp;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;

import com.example.saisrikanth.common.MusicInterface;

public class MainActivity extends Activity {


    private MusicInterface mMusicService;
    private boolean mIsBound;

    ListView listView;
    protected static final String TAG = "ClientApp";
    private static final String CUSTOM_INTENT = "SaisrikanthIntent";

    private int item;
    String[] recordList;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);

        //Lists the song in the List View
        String[] values = new String[] { "Song 1", "Song 2", "Song 3"  };
        //Populates the list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //Gets position of click on the List View
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                item = position + 1;
            }
        });
        //Play Button Clicked
        final Button PlayButton = (Button) findViewById(R.id.play_button);
        PlayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (mIsBound) {
                        Toast.makeText(getApplicationContext(),
                                "Song " + String.valueOf(item), Toast.LENGTH_LONG).show();
                        Log.i(TAG, "ugo succeeded!");
                        mMusicService.playMusic(item);
                        //Plays the song by connecting with service
                    }
                } catch (Exception e) {

                    Log.e(TAG, e.toString());

                }
            }
        });
        //Stop Music
        final Button StopButton = (Button) findViewById(R.id.stop_button);
        StopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (mIsBound) {
                        mMusicService.stopMusic();
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.toString());

                }
            }
        });
        //Pause Button
        final Button PauseButton = (Button) findViewById(R.id.pause_button);
        PauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (mIsBound)
                        mMusicService.pauseMusic();
                } catch (RemoteException e) {

                    Log.e(TAG, e.toString());

                }

            }
        });
//Resume Button
        final Button ResumeButton = (Button) findViewById(R.id.resume_button);
        ResumeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (mIsBound)
                        mMusicService.resumeMusic();
                } catch (RemoteException e) {
                    Log.e(TAG, e.toString());
                }
            }
        });

//View Button
        final Button ViewButton = (Button) findViewById(R.id.view_button);
        ViewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    if (mIsBound)
                        recordList = mMusicService.TransactionHistory();
                    Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                    intent.putExtra("Project4Saisrikanth", recordList);
                    startActivity(intent);

                } catch (RemoteException e) {

                    Log.e(TAG, e.toString());
                }

            }
        });
    }
//Binding to service
    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsBound) {
            boolean b = false;
            Intent i = new Intent(MusicInterface.class.getName());
            ResolveInfo info = getPackageManager().resolveService(i, Context.BIND_AUTO_CREATE);
           i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
         //   Intent intent = new Intent(MusicInterface.class.getName());
           //b= bindService(intent, this.mConnection, Context.BIND_AUTO_CREATE);
            b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);
            if (b) {
                Log.i(TAG, mIsBound+ "Ugo says bindService() succeeded!");
                //mIsBound=true;
            } else {
                Log.i(TAG, "Ugo says bindService() failed!");
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

//Conncectiong establishment
    private final ServiceConnection mConnection = new ServiceConnection() {

        // Called when a connection to the Service has been established
        public void onServiceConnected(ComponentName className, IBinder iservice) {

            mMusicService = MusicInterface.Stub.asInterface(iservice);
            Log.i(TAG, "Ugo says Checking!");
            mIsBound = true;

        }

        // Called when a connection to the Service has been lost
        public void onServiceDisconnected(ComponentName className) {

            mMusicService = null;
            Log.i(TAG, "Ugo says Checking123!");
            mIsBound = false;

        }

    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIsBound) {
            unbindService(this.mConnection);
        }
    }

}
