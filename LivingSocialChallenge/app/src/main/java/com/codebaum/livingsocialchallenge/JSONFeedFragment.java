package com.codebaum.livingsocialchallenge;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by brandon on 11/26/14.
 */
public class JSONFeedFragment extends ListFragment
{
    private Callbacks callbacks;

    public interface Callbacks
    {
        public void requestData();
    }

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
        }
        catch (ClassCastException e)
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

        if (callbacks != null)
        {
            callbacks.requestData();
        }
    }
}
