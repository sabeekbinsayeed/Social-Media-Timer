package com.example.sabeek.try3;

import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class browser extends AppCompatActivity {
    WifiManager wifiManager;
    TextView wifiStatusTextView;
    Switch wifiSwitch;
    WebView wv;
    int flag=0;

    TextView TV ,tv2;

    Button start, pause, reset, lap,newactivity ;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

    Handler handler;

    int Seconds, Minutes, MilliSeconds ;

    ListView listView ;

    String[] ListElements = new String[] {  };

    List<String> ListElementsArrayList ;

    ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        // wifiStatusTextView = (TextView) findViewById(R.id.wifi_status_text_view);
        //  wifiSwitch = (Switch) findViewById(R.id.wifi_switch);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
       wv= (WebView) findViewById(R.id.webviewyou);
        WebSettings ws=wv.getSettings();
      // ws.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl("https://www.google.com/");
      //  wv.loadUrl("https://play.google.com/store/apps?hl=en_us");
       // Intent intent= new Intent();
        //intent.setAction(Intent.)
      //  intent.setAction(Intent.ACTION_SEND);
       // intent.putExtra(Intent.EXTRA_TEXT, "Hello");
        //intent.setType("text/plain");
       // intent.setPackage("com.facebook.orca");

       /// try
       // {
       //     startActivity(intent);
        //}
        //catch (ActivityNotFoundException ex)
       // {
       //     Toast.makeText(this,
        //            "Oups!Can't open Facebook messenger right now. Please try again later.",
             //       Toast.LENGTH_SHORT).show();
        //}





        TV = (TextView)findViewById(R.id.textviewstopyou);
        tv2=findViewById(R.id.textview2you);
        String AIM= getIntent().getStringExtra("Aim3");
        //String sessionId= getIntent().getStringExtra("EXTRA_SESSION_ID");
        //Log.d("myTag", AIM);
        tv2.setText(AIM);
        start = (Button)findViewById(R.id.button1stopwatchyou);
        pause = (Button)findViewById(R.id.button2stopwatchyou);
        reset = (Button)findViewById(R.id.button3stopwatchyou);
        //lap = (Button)findViewById(R.id.button4) ;
        // listView = (ListView)findViewById(R.id.listview1);
        //newactivity=findViewById(R.id.newactivitybutton);

        handler = new Handler() ;

        //  ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        //   adapter = new ArrayAdapter<String>(Main3Activity.this,
        //       android.R.layout.simple_list_item_1,
        //       ListElementsArrayList
        //);

        // listView.setAdapter(adapter);
        start();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                reset.setEnabled(false);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

                reset.setEnabled(true);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                TV.setText("00:00:00");

                ListElementsArrayList.clear();

                adapter.notifyDataSetChanged();
            }
        });



    }

    @Override
    protected void onPause() {
      //  start();
        super.onPause();
        TimeBuff += MillisecondTime;

        handler.removeCallbacks(runnable);

        reset.setEnabled(true);
       //
        //Toast.makeText(browser.this,"pauses e jasce",Toast.LENGTH_SHORT).show();
    }
    protected void onReset() {
        MillisecondTime = 0L ;
        StartTime = 0L ;
        TimeBuff = 0L ;
        UpdateTime = 0L ;
        Seconds = 0 ;
        Minutes = 0 ;
        MilliSeconds = 0 ;

        TV.setText("00:00:00");


    }
        //  start();



    @Override
    protected void onStop() {
        //start();
        super.onStop();

     //   Toast.makeText(browser.this,"hello",Toast.LENGTH_SHORT).show();
//
    }

    public void onBackPressed() {
        if(wv.canGoBack())
            wv.goBack();
        else
            super.onBackPressed();
    }



    void start()
    {

        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);

        reset.setEnabled(false);


    }
    void pause() {
        super.onPause();
        TimeBuff += MillisecondTime;

        handler.removeCallbacks(runnable);

        reset.setEnabled(true);

    }
    void reset()
    {
        MillisecondTime = 0L ;
        StartTime = 0L ;
        TimeBuff = 0L ;
        UpdateTime = 0L ;
        Seconds = 0 ;
        Minutes = 0 ;
        MilliSeconds = 0 ;

        TV.setText("00:00:00");

    }

    public Runnable runnable = new Runnable() {


        public void run() {
//Toast.makeText(browser.this,"amitumi",Toast.LENGTH_SHORT).show();
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            TV.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
            // String milli=String.format("%02d", Seconds);
            // String TIME= getIntent().getStringExtra("Time");
            //  Log.d("my", TIME);

            String milli=Minutes+"";
            String milli3=String.format("%02d", Seconds);

            String sessionId= getIntent().getStringExtra("EXTRA_SESSION_ID3");
            Log.d("my", sessionId);
            int min=Integer.parseInt(milli);
            int sec=Integer.parseInt(milli3);



            if (min == 1 && flag==0) {
             //   ll.setBackgroundColor(Color.RED);
                Toast.makeText(browser.this, "1 min wasted", Toast.LENGTH_SHORT).show();
                flag=2;

            }
            if (min == 2 && flag==2) {
                //ll.setBackgroundColor(Color.BLUE);
                Toast.makeText(browser.this, "2 min wasted", Toast.LENGTH_SHORT).show();
                flag++;
            }
            if (min == 3 && flag==3) {
              //  ll.setBackgroundColor(Color.MAGENTA);
                flag++;
                Toast.makeText(browser.this, "time wasted 3 min", Toast.LENGTH_SHORT).show();
            }
            if (min == 4 && flag==4) {
//                flag++;
                Toast.makeText(browser.this, "time wasted 4 min", Toast.LENGTH_SHORT).show();
            }


            int ami=Integer.parseInt(sessionId);
            if(ami>5)
            {
                if(ami==sec)
                {
                    wifiManager.setWifiEnabled(false);
                    finish();
                   //onPause();
                    pause()
                            ;
                }


            }
           else if(ami==min)
            {
                wifiManager.setWifiEnabled(false);
                finish();
               // onStop();
                pause();
             //   onStop();
                //Log.d("you", sessionId);
                //Toast.makeText(Main3Activity.this,"hello",Toast.LENGTH_SHORT).show();

            }



            //String a=Character.toString(milli.charAt(0));

        }

    };


}
