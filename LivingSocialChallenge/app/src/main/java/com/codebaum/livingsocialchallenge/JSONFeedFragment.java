package com.codebaum.livingsocialchallenge;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codebaum.livingsocialchallenge.model.FeedItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brandon on 11/26/14.
 */
public class JSONFeedFragment extends ListFragment
{
    private Callbacks callbacks = sDummyCallbacks;

    public interface Callbacks
    {
        /**
         * The fragment view is ready, so make the necessary data calls.
         */
        public void requestData();

        /**
         * Opens the specified url for the FeedItem.
         */
        public void openUrl(FeedItem item);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void requestData()
        {

        }

        @Override
        public void openUrl(FeedItem item)
        {

        }
    };

    public JSONFeedFragment()
    {
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try
        {
            callbacks = (Callbacks) activity;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement " + Callbacks.class.getSimpleName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        callbacks.requestData();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        FeedItem item = (FeedItem) getListView().getItemAtPosition(position);
        callbacks.openUrl(item);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        callbacks = sDummyCallbacks;
    }

    /**
     * Take the JSONArray response and set up the list adapter.
     * @param jsonArray
     */
    public void updateWith(JSONArray jsonArray)
    {
        List<FeedItem> feedItems = convertFrom(jsonArray);
        JSONFeedAdapter adapter = new JSONFeedAdapter(getActivity(), R.layout.row_feed_item, feedItems);
        setListAdapter(adapter);
    }

    /**
     * Convert the JSONArray into a list of FeedItems.
     * @param jsonArray
     * @return
     */
    private ArrayList<FeedItem> convertFrom(JSONArray jsonArray)
    {
        Type listType = new TypeToken<List<FeedItem>>()
        {
        }.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(jsonArray.toString(), listType);
    }
}
