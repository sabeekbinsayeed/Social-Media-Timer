package com.example.sabeek.try3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Database db;
    //Database2 db2;
    WifiManager wifiManager;
    EditText ed1,ed2;
    String currenttime;
    String currenttimes;
   // int count;
   // TextView wifiStatusTextView;
    Switch wifiSwitch;
    float sum=0;
    int visited=0;
    int flag=0;
    int judge=0;
    double foo;
    Button bt1,bt5;
    int reqhour,reqmin;
    String lastenter;
    String timefactor,reason;
    String requredtime;
    String srequredtime;
    //String AIM;
    Button bt2,bt3,database,clear;
    int presentlasthour,presentlastmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new Database(this);
      //  db2=new Database2(this);
        ed1=findViewById(R.id.edittext1);
        ed2=findViewById(R.id.edittext2);
        Date currentTime = Calendar.getInstance().getTime();
        currenttimes = currentTime.toString();
        currenttime=currenttimes.substring(11,19);
        String presententerhour=currenttime.substring(0,2);
         presentlasthour=Integer.parseInt(presententerhour);
        String presententermin=currenttime.substring(3,5);
         presentlastmin=Integer.parseInt(presententermin);
        database=findViewById(R.id.databaseid);
        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cr=db.getData();
                if(cr.getCount()==0)
                {
                    showData("eror done","nothing found");
                    return;
                }
                StringBuffer sb=new StringBuffer();
                while(cr.moveToNext())
                {
                    sb.append("ID :"+cr.getString(0)+"\n");
                    sb.append("Reason to spent :"+cr.getString(1)+"\n");
                    sb.append("Time spent :"+cr.getString(2)+"\n");
                    sb.append("Used at :"+cr.getString(3)+"\n");
                    sb.append("Entered after :"+cr.getString(4)+"\n");
                   String inter=cr.getString(2);
                   String nextpart=inter.substring(2,4);
                   int innextpart=Integer.parseInt(nextpart);
                   String firstpart=inter.substring(0,1);
                   float infirstpart=Float.parseFloat(firstpart);
                   float flnext= (float) (innextpart*.01)+infirstpart;
                   sum=sum+flnext;
                   //flnext tai final
                   //float final=infirstpart+flnext;
                 //  Toast.makeText(MainActivity.this,flnext+"",Toast.LENGTH_LONG).show();




                }
                if(sum>=8)
                {
                    Toast.makeText(MainActivity.this, "You have wasted 8 min in your life", Toast.LENGTH_SHORT).show();
                }
               sb.append("Total Time wasted :"+sum+"\n");
                sum=0;
                showData("RESultSet",sb.toString());
            timeget();
               //timecalcu();
                Toast.makeText(MainActivity.this,requredtime,Toast.LENGTH_SHORT).show();


            }
        });

        clear=findViewById(R.id.clearid);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteall();
                SharedPreferences sp=getSharedPreferences("cleared", Context.MODE_PRIVATE);
                SharedPreferences sp2=getSharedPreferences("userdetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=sp.edit();
                ed.putString("lastclear",currenttime);
                ed.commit();
      ed=sp2.edit();
                ed.putString("username","0");
               // ed.putString("password",password);
                ed.commit();
               // Toast.makeText(MainActivity.this,"commited",Toast.LENGTH_SHORT).show();

            }
        });
        timegetclear();
       // timeget();
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //wifiStatusTextView = (TextView) findViewById(R.id.wifi_status_text_view);
        wifiSwitch = (Switch) findViewById(R.id.wifi_switch);
        if (wifiManager.isWifiEnabled()) {
            //  wifiStatusTextView.setText("Wifi is currently ON");
            wifiSwitch.setChecked(true);
        } else {
            // wifiStatusTextView.setText("Wifi is currently OFF");
            wifiSwitch.setChecked(false);
        }
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wifiManager.setWifiEnabled(true);
                    // wifiStatusTextView.setText("Wifi is currently ON");
                    //Toast.makeText(MainActivity.this, "Wifi may take a moment to turn on", Toast.LENGTH_LONG).show();
                } else {
                    wifiManager.setWifiEnabled(false);
                    //   wifiStatusTextView.setText("Wifi is currently OFF");
                }
            }
        });
        bt2=findViewById(R.id.neton);
        bt5=findViewById(R.id.msg);
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed1.setText("msg");
                ed2.setText("1");
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason=ed1.getText().toString();
                timefactor=ed2.getText().toString();
                if(timefactor.matches("")&&reason.matches(""))
                {


                    Toast.makeText(MainActivity.this,"Please Enter the info",Toast.LENGTH_SHORT).show();
                }
                if(timefactor.matches(""))
                {

                    Toast.makeText(MainActivity.this,"Please Enter time",Toast.LENGTH_SHORT).show();
                }
                else if(reason.matches(""))
                {

                    Toast.makeText(MainActivity.this,"Please Enter your purpose",Toast.LENGTH_SHORT).show();
                }



                else {
                    //String aim = ed1.getText().toString();
                    //String times = ed2.getText().toString();
                    // int reqhour=0,reqmin;}
                    String aim2=ed1.getText().toString();
                    String times2=ed2.getText().toString();
                    Intent ins=new Intent(MainActivity.this,Netoff.class);
                    ins.putExtra("Aim2",aim2);
                    ins.putExtra("EXTRA_SESSION_ID2",times2);
                    // in.putExtra("EXTRA_SESSION_ID", "05");0
                    startActivity(ins);

                }


            }
        });
        bt3=findViewById(R.id.browser);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                reason=ed1.getText().toString();
                timefactor=ed2.getText().toString();
                if(timefactor.matches("")&&reason.matches(""))
                {


                    Toast.makeText(MainActivity.this,"Please Enter the info",Toast.LENGTH_SHORT).show();
                }
                if(timefactor.matches(""))
                {

                    Toast.makeText(MainActivity.this,"Please Enter time",Toast.LENGTH_SHORT).show();
                }
                else if(reason.matches(""))
                {

                    Toast.makeText(MainActivity.this,"Please Enter your purpose",Toast.LENGTH_SHORT).show();
                }



                else {
                    //String aim = ed1.getText().toString();
                    //String times = ed2.getText().toString();
                    // int reqhour=0,reqmin;}
                    String aim3=ed1.getText().toString();
                    String times3=ed2.getText().toString();
                    Intent ins=new Intent(MainActivity.this,browser.class);
                    ins.putExtra("Aim3",aim3);
                    ins.putExtra("EXTRA_SESSION_ID3",times3);
                    startActivity(ins);

                }



            }
        });
        bt1=findViewById(R.id.button1main);
        bt1.setOnClickListener(new View.OnClickListener() {
            SharedPreferences sp2;

            @Override

            public void onClick(View v) {

             //   Log.d("myTag", Aim);

                int count;
              //  String stcount;
                if(visited==0)
                {
                    timegetclear();
                     sp2=getSharedPreferences("userdetails", Context.MODE_PRIVATE);
                    if(sp2.contains("username"))
                    {
                        String username=sp2.getString("username","dont fount");
                        Toast.makeText(MainActivity.this,username+" time entered",Toast.LENGTH_SHORT).show();
                        visited=1;
                        //String password=sp.getString("password","dont");
                        //tv1.setText(username+"\n"+password);

                    }
                    else
                    {
                        SharedPreferences sp=getSharedPreferences("userdetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed=sp.edit();
                        ed.putString("username","0");
                        //  ed.putString("password",password);
                        ed.commit();
                        //count=0;

                    }



                }





               // SharedPreferences sp=getSharedPreferences("ocuurences", Context.MODE_PRIVATE);

                   // String occured=sp.getString("username","dont fount");
              //
                   // String password=sp.getString("password","dont");

                    reason=ed1.getText().toString();
                //Toast.makeText(MainActivity.this,"occured",Toast.LENGTH_SHORT).show();
                 timefactor=ed2.getText().toString();
                if(timefactor.matches("")&&reason.matches(""))
                {


                    Toast.makeText(MainActivity.this,"Please Enter the info",Toast.LENGTH_SHORT).show();
                }
                if(timefactor.matches(""))
                {

                    Toast.makeText(MainActivity.this,"Please Enter time",Toast.LENGTH_SHORT).show();
                }
               else if(reason.matches(""))
                {

                    Toast.makeText(MainActivity.this,"Please Enter your purpose",Toast.LENGTH_SHORT).show();
                }



                else
                {
                    String aim=ed1.getText().toString();
                    String times=ed2.getText().toString();
                   // int reqhour=0,reqmin;


                        Cursor cr=db.getData();
                        if(cr.getCount()==0)
                        {
                            String username2=sp2.getString("username","dont fount");
                            count=Integer.parseInt(username2);
                            count++;



                            //last
                            SharedPreferences sp=getSharedPreferences("userdetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor ed=sp.edit();
                            ed.putString("username",count+"");
                            //  ed.putString("password",password);
                            ed.commit();
                            showData("","nothing found");Intent in=new Intent(MainActivity.this,Main3Activity.class);
                            in.putExtra("Aim",aim);
                            in.putExtra("EXTRA_SESSION_ID",times);
                          requredtime="0:0";
                            in.putExtra("Time",requredtime);
                            // in.putExtra("EXTRA_SESSION_ID", "05");0
                            startActivity(in);


                            Toast.makeText(MainActivity.this,"after finishing your work ,stop using. Dont scroll",Toast.LENGTH_LONG).show();

                            return;
                        }
                        else

                        {
                            timeget();
                        }

                        if(judge==0 && flag==0)
                        {
                            Toast.makeText(MainActivity.this,reqhour+":"+reqmin,Toast.LENGTH_LONG).show();
                        }






                    if(reqhour==0 && judge==0)
                    {
                        Toast.makeText(MainActivity.this,"1 hour haven't passed, dont enter. Fight against this fitna",Toast.LENGTH_LONG).show();
//flag=1;
                    }
                    else
                    {
                        judge=1;
                    }
                    if(flag==1)
                    {
                        if(judge==1)
                        {
                            Intent in=new Intent(MainActivity.this,Main3Activity.class);
                            in.putExtra("Aim",aim);
                            in.putExtra("EXTRA_SESSION_ID",times);
                            in.putExtra("Time",requredtime);
                            String username2=sp2.getString("username","dont fount");
                             count=Integer.parseInt(username2);
                             count++;



                            //last
                            SharedPreferences sp=getSharedPreferences("userdetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor ed=sp.edit();
                            ed.putString("username",count+"");
                          //  ed.putString("password",password);
                            ed.commit();









                            // Toast.makeText(MainActivity.this,"after finishing your work ,stop using. Dont scroll",Toast.LENGTH_LONG).show();
                            // in.putExtra("EXTRA_SESSION_ID", "05");0
                            startActivity(in);
                           // Toast.makeText(MainActivity.this,"after finishing your work ,stop using. Dont scroll",Toast.LENGTH_LONG).show();

                        }
                       else
                        {
                            Toast.makeText(MainActivity.this,"Are you sure??",Toast.LENGTH_LONG).show();
                        }

                        judge=1;


                    }
                    flag=1;
                    }









            }
        });





    }
    public void showData(String title,String message)
    {
        AlertDialog.Builder build=new AlertDialog.Builder(this);
        build.setTitle(title);
        build.setMessage(message);
        build.setCancelable(true);
        build.show();

    }
   protected void onRestart() {
       super.onRestart();
       judge=0;
   //    Toast.makeText(MainActivity.this,"Restart is called",Toast.LENGTH_LONG).show();
       flag=0;
    //   visited=0;
   }
    @Override

    protected void onStart()
    {
        //Toast.makeText(MainActivity.this,"start is called",Toast.LENGTH_LONG).show();

        super.onStart();
        judge=0;
        flag=0;
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //wifiStatusTextView = (TextView) findViewById(R.id.wifi_status_text_view);
        wifiSwitch = (Switch) findViewById(R.id.wifi_switch);
        if (wifiManager.isWifiEnabled()) {
            //  wifiStatusTextView.setText("Wifi is currently ON");
            wifiSwitch.setChecked(true);
        } else {
            // wifiStatusTextView.setText("Wifi is currently OFF");
            wifiSwitch.setChecked(false);
        }
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wifiManager.setWifiEnabled(true);
                    // wifiStatusTextView.setText("Wifi is currently ON");
                    //Toast.makeText(MainActivity.this, "Wifi may take a moment to turn on", Toast.LENGTH_LONG).show();
                } else {
                    wifiManager.setWifiEnabled(false);
                    //   wifiStatusTextView.setText("Wifi is currently OFF");
                }
            }
        });

      //  Toast.makeText(getApplicationContext(),"Now onStart() calls", Toast.LENGTH_LONG).show(); //onStart Called
    }

    @Override
    protected void onResume() {
        super.onResume();
        judge=0;
       flag=0;
       visited=0;
       // Toast.makeText(MainActivity.this,"resume is called",Toast.LENGTH_LONG).show();
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //wifiStatusTextView = (TextView) findViewById(R.id.wifi_status_text_view);
        wifiSwitch = (Switch) findViewById(R.id.wifi_switch);
        if (wifiManager.isWifiEnabled()) {
            //  wifiStatusTextView.setText("Wifi is currently ON");
            wifiSwitch.setChecked(true);
        } else {
            // wifiStatusTextView.setText("Wifi is currently OFF");
            wifiSwitch.setChecked(false);
        }


        //  Toast.makeText(getApplicationContext(),"Now onStart() calls", Toast.LENGTH_LONG).show(); //onStart Called

    }

    void timeget()
    {
        StringBuffer sb=new StringBuffer();
        Cursor cr=db.getData();
        while(cr.moveToNext())
        {
            // sb.append("ID :"+cr.getString(0)+"\n");
            //  sb.append("Reason to spent :"+cr.getString(1)+"\n");
            // sb.append("Time spent :"+cr.getString(2)+"\n");
            //sb.append("Used at :"+cr.getString(3)+"\n");
            lastenter=cr.getString(3);
            //flnext tai final
            //float final=infirstpart+flnext;
            //  Toast.makeText(MainActivity.this,flnext+"",Toast.LENGTH_LONG).show();





        }
        String lastenterhour=lastenter.substring(0,2);
        int inlasthour=Integer.parseInt(lastenterhour);
        String lastentermin=lastenter.substring(3,5);
        int inlastmin=Integer.parseInt(lastentermin);
        reqhour=presentlasthour-inlasthour;
        reqmin=presentlastmin-inlastmin;
        if(reqmin<0)
        {
            reqhour=reqhour-1;
            reqmin=reqmin+60;
        }
        if(reqhour<0)
        {
            reqhour=reqhour+12;
        }
        requredtime=reqhour+":"+reqmin;
    }
    void timegetclear()
    {
        //Toast.makeText(MainActivity.this,"working",Toast.LENGTH_SHORT).show();
        StringBuffer sb=new StringBuffer();

        SharedPreferences sp=getSharedPreferences("cleared", Context.MODE_PRIVATE);

            String data=sp.getString("lastclear","dont fount");
           // String password=sp.getString("password","dont");

            String lastenterhour=data.substring(0,2);
        int inlasthour=Integer.parseInt(lastenterhour);
        String lastentermin=data.substring(3,5);
        int inlastmin=Integer.parseInt(lastentermin);
        int sreqhour=presentlasthour-inlasthour;
        int sreqmin=presentlastmin-inlastmin;
        if(sreqmin<0)
        {
            sreqhour=sreqhour-1;
            sreqmin=sreqmin+60;
        }
        if(sreqhour<0)
        {
            sreqhour=sreqhour+12;
        }
        srequredtime=sreqhour+":"+sreqmin;
        Toast.makeText(MainActivity.this,"Total day time gone= "+srequredtime,Toast.LENGTH_SHORT).show();
    }
}

