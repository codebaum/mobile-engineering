package com.codebaum.livingsocialchallenge;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codebaum.livingsocialchallenge.model.FeedItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by brandon on 11/26/14.
 */
public class JSONFeedAdapter extends ArrayAdapter<FeedItem>
{
    Context context;
    int resource;

    public JSONFeedAdapter(Context context, int resource, List<FeedItem> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
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

            holder = new ViewHolder(row);

            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) row.getTag();
        }

        FeedItem item = super.getItem(position);

        Picasso.with(context).load(item.getSrc()).into(holder.image);
        holder.attrib.setText(item.getAttrib());
        holder.desc.setText(item.getDesc());
        Picasso.with(context).load(item.getUser().getAvatar().getSrc()).into(holder.avatar);
        holder.username.setText(item.getUser().getUsername());

        return row;
    }

    static class ViewHolder
    {
        @InjectView(R.id.image)
        ImageView image;

        @InjectView(R.id.attrib)
        TextView attrib;

        @InjectView(R.id.desc)
        TextView desc;

        @InjectView(R.id.avatar)
        ImageView avatar;

        @InjectView(R.id.username)
        TextView username;

        public ViewHolder(View view)
        {
            ButterKnife.inject(this, view);
        }
    }
}
