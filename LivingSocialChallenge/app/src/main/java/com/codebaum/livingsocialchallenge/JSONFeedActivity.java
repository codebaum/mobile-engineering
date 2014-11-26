package com.codebaum.livingsocialchallenge;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.codebaum.livingsocialchallenge.network.CustomJsonArrayRequest;

import org.json.JSONArray;


public class JSONFeedActivity extends Activity implements JSONFeedFragment.Callbacks
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_feed);

        if (savedInstanceState == null)
        {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new JSONFeedFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_json_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void requestData()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = getString(R.string.json_feed_url);

        queue.add(new CustomJsonArrayRequest(url, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray response)
            {
                handle(response);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                handle(error);
            }
        }));
    }

    private void handle(JSONArray response)
    {
        JSONFeedFragment fragment = (JSONFeedFragment) getFragmentManager().findFragmentById(R.id.container);
        fragment.updateWith(response);
    }

    private void handle(VolleyError error)
    {
    }
}
