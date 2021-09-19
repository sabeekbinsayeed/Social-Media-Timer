package com.example.sabeek.try3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static com.example.sabeek.try3.R.color.colorPrimaryDark;

public class Main3Activity extends AppCompatActivity {
    WifiManager wifiManager;
    TextView wifiStatusTextView;
    Switch wifiSwitch;
    Database db;
    WebView wv;
    LinearLayout ll;
    String reqtime;
    int judge=1;
    int done=0;
String AIM;
    int flag=0;

    TextView TV ,tv2;

    Button start, pause, reset, lap,newactivity ;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

    Handler handler;

    int Seconds, Minutes, MilliSeconds ;

    ListView listView ;
    String url;
    String timeentered;

    String[] ListElements = new String[] {  };

    List<String> ListElementsArrayList ;
    String currenttime;
    String currenttimes;

    ArrayAdapter<String> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        // wifiStatusTextView = (TextView) findViewById(R.id.wifi_status_text_view);
        //  wifiSwitch = (Switch) findViewById(R.id.wifi_switch);
        super.onCreate(savedInstanceState);
        AIM= getIntent().getStringExtra("Aim");
        db=new Database(this);
        setContentView(R.layout.activity_main3);
        wv= (WebView) findViewById(R.id.webview);
        WebSettings ws=wv.getSettings();
        ws.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        url="https://www.facebook.com/";
        if(AIM.equals("msg"))
        {
            url="https://www.facebook.com/messages/new/";
        }
        if(AIM.equals("you"))
        {
            url="https://www.youtube.com/";
        }
        if(AIM.equals("google"))
        {
            url="https://www.google.com/messages/new/";
        }
        if(AIM.equals("m"))
        {
            url="https://mobile.facebook.com/home.php?soft=messages";
        }
        if(AIM.equals("not"))
        {
            url="https://m.facebook.com/notifications.php?more";
        }
        if(AIM.equals("win"))
        {
            url="https://m.facebook.com/notifications.php?more";
        }
        if(AIM.equals("champ"))
        {
            url="https://m.facebook.com/notifications.php?more";
        }



                 timeentered= getIntent().getStringExtra("Time");
        wv.loadUrl(url);
        ll=findViewById(R.id.linearlayoutid);
        Date currentTime = Calendar.getInstance().getTime();
         currenttimes = currentTime.toString();
         currenttime=currenttimes.substring(11,19);



        TV = (TextView)findViewById(R.id.textviewstop);
        tv2=findViewById(R.id.textview2);

        //String sessionId= getIntent().getStringExtra("EXTRA_SESSION_ID");
        //Log.d("myTag", AIM);
        tv2.setText(AIM);
        start = (Button)findViewById(R.id.button1stopwatch);
        pause = (Button)findViewById(R.id.button2stopwatch);
        reset = (Button)findViewById(R.id.button3stopwatch);
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




    }
    public void onReset()
    {  MillisecondTime = 0L ;
        StartTime = 0L ;
        TimeBuff = 0L ;
        UpdateTime = 0L ;
        Seconds = 0 ;
        Minutes = 0 ;
        MilliSeconds = 0 ;

        TV.setText("00:00:00");

      //  ListElementsArrayList.clear();

        //adapter.notifyDataSetChanged();

    }

    @Override
    protected void onPause() {
        super.onPause();
        TimeBuff += MillisecondTime;

        handler.removeCallbacks(runnable);

        reset.setEnabled(true);
    }

    public void onUse()
    {onDestroy();}
    void Reset()
    {
        MillisecondTime = 0L ;
        StartTime = 0L ;
        TimeBuff = 0L ;
        UpdateTime = 0L ;
        Seconds = 0 ;
        Minutes = 0 ;
        MilliSeconds = 0 ;

        TV.setText("00:00:00");

       // ListElementsArrayList.clear();

       // adapter.notifyDataSetChanged();
    }

    public void onBackPressed() {
        if(wv.canGoBack())
        {

            wv.goBack();


           // db.insert(amis);

        }

        else
        {
            super.onBackPressed();
            //Toast.makeText(this, "howa ", Toast.LENGTH_SHORT).show();

            long rowid= db.insert(AIM,"" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds),currenttime,timeentered);
            if(rowid==-1)
            {

                Toast.makeText(this,"not done,keep patience",Toast.LENGTH_LONG).show();


            }
            else
            {

                Toast.makeText(this,"inserted row"+rowid+" alhumdulillah",Toast.LENGTH_LONG).show();
            }
            Toast.makeText(this,"Masha Allah , good job done",Toast.LENGTH_LONG).show();

          //  Reset();
            //judge=0;
            finish();
            onPause();
            onReset();
         //   done=1;
         //   handler.removeCallbacks(Runnable );
            //onPause();
          //  onStop();
         //   onDestroy();



        }


    }



    void start()
    {

        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);

        reset.setEnabled(false);


    }

    public Runnable runnable = new Runnable() {

        public void run() {

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
            //if(done==1)
            //{
              //  finish();
            //}
            // String milli=String.format("%02d", Seconds);
            // String TIME= getIntent().getStringExtra("Time");
            //  Log.d("my", TIME);

            String milli = Minutes + "";
            String milli3 = String.format("%02d", Seconds);
            String sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
            Log.d("my", sessionId);
            int min = Integer.parseInt(milli);
            int sec = Integer.parseInt(milli3);


           // if ((min == 1 || min == 2 || min == 3 || min == 4) && flag == 0) {
             //   Toast.makeText(Main3Activity.this, min + " mins gone", Toast.LENGTH_SHORT).show();
            //    flag = 1;
          //  }

            if (min == 1 && flag==0) {

                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        v.vibrate(500);
                    }

                ll.setBackgroundColor(Color.RED);
                Toast.makeText(Main3Activity.this, "1 min wasted\n dont scroll, go back", Toast.LENGTH_SHORT).show();
              flag=2;

            }
            if (min == 2 && flag==2) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(500);
                }
                ll.setBackgroundColor(Color.BLUE);
                Toast.makeText(Main3Activity.this, "2 min wasted\nare you still scrolling??", Toast.LENGTH_SHORT).show();
                flag++;
            }
            if (min == 3 && flag==3) {
                ll.setBackgroundColor(Color.MAGENTA);
                flag++;
                Toast.makeText(Main3Activity.this, "time wasted 3 min", Toast.LENGTH_SHORT).show();
            }
            if (min == 4 && flag==4) {
                ll.setBackgroundColor(Color.BLACK);
                flag++;
                Toast.makeText(Main3Activity.this, "time wasted 4 min", Toast.LENGTH_SHORT).show();
            }
            int ami = Integer.parseInt(sessionId);
            if(AIM.equals("win"))
            {
                ami=30;
            }
            if(AIM.equals("champ"))
            {
                ami=30;
                if(sec==18)
                {
                    wv.loadUrl("https://www.facebook.com/messages/new/");


                }

            }

            if(ami>5)

            {
                if (ami == sec) {
                    wifiManager.setWifiEnabled(false);

                    String amis=Integer.toString(ami);
                    if(judge==1)
                    {
                        long rowid= db.insert(AIM,"" + Minutes + ":"
                                + String.format("%02d", Seconds) + ":"
                                + String.format("%03d", MilliSeconds),currenttime,timeentered);
                        if(rowid==-1)
                        {

                            Toast.makeText(Main3Activity.this,"not done,keep patience",Toast.LENGTH_LONG).show();


                        }
                        else
                        {

                            Toast.makeText(Main3Activity.this,"inserted row"+rowid+" alhumdulillah",Toast.LENGTH_LONG).show();
                        }
                        judge=0;
                    }

                    finish();
                    onPause();
                    //onStop();
                }


            }
            else if(ami==min)

            {
                String amis=Integer.toString(ami);
                if(judge==1)
                {
                    long rowid= db.insert(AIM,"" + Minutes + ":"
                            + String.format("%02d", Seconds) + ":"
                            + String.format("%03d", MilliSeconds),currenttime,timeentered);
                    if(rowid==-1)
                    {

                        Toast.makeText(Main3Activity.this,"not done,keep patience",Toast.LENGTH_LONG).show();


                    }
                    else
                    {

                        Toast.makeText(Main3Activity.this,"inserted row"+rowid+" alhumdulillah",Toast.LENGTH_LONG).show();
                    }
                    judge=0;
                }

                wifiManager.setWifiEnabled(false);
                finish();

               // onStop();
                //   onStop();
                //Log.d("you", sessionId);
                //Toast.makeText(Main3Activity.this,"hello",Toast.LENGTH_SHORT).show();

            }


        }




        //String a=Character.toString(milli.charAt(0));




    };


}
