package com.codebaum.livingsocialchallenge;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by brandon on 11/26/14.
 */
public class JSONFeedAdapter extends ArrayAdapter<FeedItem>
{
    Context context;
    int resource;
    List<FeedItem> objects;

    public JSONFeedAdapter(Context context, int resource, List<FeedItem> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        ViewHolder holder;

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.txtTitle = (TextView) row.findViewById(android.R.id.text1);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }

        FeedItem item = objects.get(position);
        holder.txtTitle.setText(item.getSrc());

        return row;
    }

    static class ViewHolder
    {
        TextView txtTitle;
    }
}
