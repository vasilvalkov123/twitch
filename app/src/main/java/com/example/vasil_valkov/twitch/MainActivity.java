package com.example.vasil_valkov.twitch;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        TextView game,viewers, quality;

        private String url = "https://api.twitch.tv/kraken/streams/stormstudio_d2cl_ru";

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            game = (TextView) rootView.findViewById(R.id.game_name);
            viewers = (TextView) rootView.findViewById(R.id.game_viewers);
            quality = (TextView) rootView.findViewById(R.id.game_quality);

            new JSONParse().execute();

            return rootView;
        }


        private class JSONParse extends AsyncTask<String, String, JSONObject> {

            @Override
            protected JSONObject doInBackground(String... params) {
                JSONparser parse = new JSONparser();

                JSONObject json = parse.getJSONFromUrl(url);
                return json;
            }

            @Override
            protected void onPostExecute(JSONObject json) {

                JSONObject object = null;
                try {
                    object = json.getJSONObject("stream");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                assert object != null;
                String game_name = null;
                String  game_viewers = null;
                String  game_quality = null;
                try {
                    game_name = object.getString("game");
                    game_viewers = object.getString("viewers");
                    game_quality = object.getString("video_height");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                game.setText(game_name);
                viewers.setText(game_viewers);
                quality.setText(game_quality);
            }
        }
    }
}
