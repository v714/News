package com.example.vineet.news;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button Btn ;
    ListView view;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Btn = (Button) findViewById(R.id.Tapbutton);
        view = (ListView) findViewById(R.id.listView);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo code
            }
        });
        DownloadData downloaddata = new DownloadData();
        downloaddata.execute("http://feeds.feedburner.com/NdtvNews-TopStories");
    }

    private class DownloadData extends AsyncTask <String , Void , String> {

        private String fileContents ;

        @Override
        protected String doInBackground(String... params) {
            fileContents = downloadXML (params [0]);
            if (fileContents == null) {
                Log.d("DownloadData" , " Error downloading");
            }
            return fileContents;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d ("DownloadData" ," result is " +result);



        }

        private String downloadXML(String urlPATH) {
            StringBuilder buff = new StringBuilder();

            try {
                URL url = new URL(urlPATH);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int responseCode = connection.getResponseCode();
                Log.d ("DownloadData" , "Responce code is " + responseCode);
                InputStream str = connection.getInputStream();
                InputStreamReader  red = new InputStreamReader(str);

                int charRead;
                char [] inputBuffer = new char[400];
                while(true) {
                    charRead = red.read(inputBuffer);
                    if(charRead <=0) {
                        break;
                    }
                    buff.append(String.copyValueOf(inputBuffer , 0 , charRead));
                }
                return buff.toString();


            } catch (Exception e) {
                Log.d("DownloadData" , "I/O Error " + e.getMessage());

            }
            return null;

        }
    }
}
