package com.example.ringtone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button1;
    private Context mContext;
    private Activity mActivity;
    private EditText eText;
    private DatePickerDialog picker;
    private TextView mEtHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=(Button)findViewById(R.id.ringButton1);
        button=(Button)findViewById(R.id.ringButton);
        mContext= getApplicationContext();
        mActivity=MainActivity.this;


        mEtHelp = (TextView)findViewById(R.id.et_help);
        mEtHelp.setClickable(true);
        mEtHelp.setMovementMethod(LinkMovementMethod.getInstance());
        String helpLink = "<a href='https://www.youtube.com/watch?time_continue=3&v=scSH1P7mHVs'>Need Help?</a>";
        mEtHelp.setText(Html.fromHtml(helpLink));
        mEtHelp.setVisibility(View.VISIBLE);
        //new commit
        if(mEtHelp.getVisibility()==View.VISIBLE){
            Log.d("visiblr","YESSS");
        }
        else {
            Log.d("visiblr","NOOO");

        }

        eText=(EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


        try {

            showRadioButtonDialog( jasonA());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        final Ringtone ringtone = RingtoneManager.getRingtone(mContext,uri);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
                tg.startTone(ToneGenerator.TONE_DTMF_1);*/




                ringtone.play();

            }
        });



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
                tg.startTone(ToneGenerator.TONE_DTMF_1);*/

                /*MediaPlayer player = MediaPlayer.create(mActivity, Settings.System.DEFAULT_RINGTONE_URI);
                player.start();*/
                ///https://stackoverflow.com/questions/14089380/how-do-i-stop-the-currently-playing-ringtone//

                final Handler handler = new Handler();
                long timeout = 7000;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (ringtone.isPlaying())
                            ringtone.stop();
                    }
                }, timeout);
                

                Toast.makeText(getApplicationContext(),"ghfghfggfdgfddf",Toast.LENGTH_LONG).show();
               // ringtone.stop();
            }
        });


    }

    String jasonA() throws JSONException {
        String str = "{\"status\": 1, \"data\": [[1, \"English\"], [2, \"Kannada\"], [3, \"hindi\"]]}";

        JSONObject jsonObject = new JSONObject(str);

        JSONArray languages=jsonObject.getJSONArray("data");
        int status= jsonObject.getInt("status");

        JSONArray lang=(JSONArray)languages.get(0);

        System.out.println("data::"+lang.get(0));
        return str;

    }

    private void showRadioButtonDialog(String str) throws JSONException {

        JSONObject jsonObject = new JSONObject(str);

        JSONArray languages=jsonObject.getJSONArray("data");
        //int status= jsonObject.getInt("status");
        String[] langArray = new String[languages.length()];

        final HashMap<Integer,String> hm=new HashMap<Integer,String>();

        for (int i=0;i<languages.length();i++){
            JSONArray lang=(JSONArray)languages.get(i);
            Log.d("langIndex::",lang.getString(0));

            hm.put(i,lang.getString(0));
            langArray[i]=lang.getString(1);
        }

        Log.d("langIndexi::",hm.get(0));





        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Choose Language");
        int checkedItem = 0;
        alertDialog.setSingleChoiceItems(langArray, checkedItem, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s= String.valueOf(which);
                Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
                Log.d("langID", Objects.requireNonNull(hm.get(which)));
                dialog.dismiss();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

}



