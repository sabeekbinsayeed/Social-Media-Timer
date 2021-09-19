package com.example.sabeek.try3;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
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
import android.os.Handler;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Netoff extends AppCompatActivity {
    WifiManager wifiManager;
    TextView wifiStatusTextView;
    Switch wifiSwitch;
    WebView wv;
    int flag=0;
    LinearLayout ll;

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
        setContentView(R.layout.activity_netoff);
        wv= (WebView) findViewById(R.id.webview);
        WebSettings ws=wv.getSettings();
        ws.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
       // String h="https://www.facebook.com/notifications";
      wv.loadUrl("https://www.youtube.com/");
      //https://www.facebook.com/notifications
   //     wv.loadUrl("https://www.facebook.com/messages/t/1815838131875603");

      //  https://www.facebook.com/messages/new/
    //   wv.loadUrl("https://play.google.com/store/apps/details?id=my packagename");
     //   wv.loadUrl("https://www.messenger.com/");
       // Uri uri = Uri.parse("fb-messenger://user/");
       // uri = ContentUris.withAppendedId(uri, Long.parseLong(peopleId));


        ll=findViewById(R.id.linearlayoutid);



        TV = (TextView)findViewById(R.id.textviewstopmy);
        tv2=findViewById(R.id.textview2);
        String AIM= getIntent().getStringExtra("Aim2");
        //String sessionId= getIntent().getStringExtra("EXTRA_SESSION_ID");
        //Log.d("myTag", AIM);
        tv2.setText(AIM);
        start = (Button)findViewById(R.id.button1stopwatchmy);
        pause = (Button)findViewById(R.id.button2stopwatchmy);
        reset = (Button)findViewById(R.id.button3stopwatchmy);
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
    void pause()
    {

        TimeBuff += MillisecondTime;

        handler.removeCallbacks(runnable);

        reset.setEnabled(true);

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
            // String milli=String.format("%02d", Seconds);
            // String TIME= getIntent().getStringExtra("Time");
            //  Log.d("my", TIME);

            String milli=Minutes+"";
            String milli3=String.format("%02d", Seconds);
            String sessionId= getIntent().getStringExtra("EXTRA_SESSION_ID2");
            Log.d("my", sessionId);
            int min=Integer.parseInt(milli);
            int sec=Integer.parseInt(milli3);

           // if((min==1||min==2||min==3)&& flag==0)
           // {
              //  Toast.makeText(Netoff.this,sec+1 +" mins gone",Toast.LENGTH_SHORT).show();
               // flag=1;
            //}
            if (min == 3 && flag==0) {
                ll.setBackgroundColor(Color.RED);
                Toast.makeText(Netoff.this, "3 min wasted", Toast.LENGTH_SHORT).show();
                flag=2;

            }
            if (min == 7 && flag==2) {
                ll.setBackgroundColor(Color.BLUE);
                Toast.makeText(Netoff.this, "7 min wasted", Toast.LENGTH_SHORT).show();
                flag++;
            }
            if (min == 12 && flag==3) {
                ll.setBackgroundColor(Color.MAGENTA);
                flag++;
                Toast.makeText(Netoff.this, "time wasted 12 min", Toast.LENGTH_SHORT).show();
            }
            if (min == 15 && flag==4) {
                ll.setBackgroundColor(Color.BLACK);
                flag++;
                Toast.makeText(Netoff.this, "time wasted 15 min", Toast.LENGTH_SHORT).show();
            }


            int ami=Integer.parseInt(sessionId);
            if(ami>40)
            {
                if(ami==sec)
                {
                    wifiManager.setWifiEnabled(false);
                    finish();
                    pause();
                    //onPause();
                  //  onStop();
                }


            }
            else if(ami==min)
            {
                wifiManager.setWifiEnabled(false);
                pause();
                finish();
               // onStop();
                //   onStop();
                //Log.d("you", sessionId);
                //Toast.makeText(Main3Activity.this,"hello",Toast.LENGTH_SHORT).show();

            }


            //String a=Character.toString(milli.charAt(0));

        }

    };


}
