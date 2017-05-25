package dali.iot;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.json.JSONException;
import org.json.JSONObject;

import pl.pawelkleczkowski.customgauge.CustomGauge;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private FloatingActionButton  motion;
    private Button config,Alarm,graph;
    private CustomGauge cg;
    private ProgressDialog mProgress;
    String ip_new =RetreiveData.broker;
    MqttAndroidClient mqttAndroidClient;
    String url = "192.168.1.50";
    String clientToken = "token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Iot ");
        mProgress=new ProgressDialog(this);

        cg=(CustomGauge)findViewById(R.id.gauge1);
        cg.setPointSize(30);
        new AyncTelelmetry().execute();
        //mqttAndroidClient = new MqttAndroidClient(getApplicationContext(), url, clientToken);
    }

    public void Sync(View view){
        new AyncTelelmetry().execute();
    }

    public class AyncTelelmetry extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {

            return RetreiveData.conn(ip_new);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgress.dismiss();
            if(s!=null){
                JSONObject jsObject= null;
                try {
                    jsObject = new JSONObject(s);
                    cg.setPointSize(jsObject.getInt("temperature"));
                    if(jsObject.getString("motion").equals("true")){
                        motion.setBackgroundColor(getResources().getColor(R.color.md_green_500));
                    }else{
                        motion.setBackgroundColor(getResources().getColor(R.color.md_red_500));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setMessage("Wait please ...");
            mProgress.setCancelable(false);
            mProgress.show();
        }



    }
    public  void ConfigIp(View view){
        dialog();
    }
    public void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Get Number");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ip = input.getText().toString();
                ip_new=ip;
                Log.i("new ip",ip_new);


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
