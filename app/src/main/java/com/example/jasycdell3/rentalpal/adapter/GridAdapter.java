package com.example.jasycdell3.rentalpal.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jasycdell3.rentalpal.R;
import com.example.jasycdell3.rentalpal.SavingImages;

import java.util.ArrayList;

/**
 * Created by JASYC DELL 3 on 6/13/2016.
 */
public class GridAdapter extends BaseAdapter {
    private ArrayList<String> ims_path;
    private Context context;
    public static ArrayList<String> arrayList = new ArrayList<>();


    public GridAdapter(Context context, ArrayList<String> ims_path) {
        this.ims_path = ims_path;
        this.context = context;

    }

    @Override
    public int getCount() {
        return ims_path.size();
    }

    @Override
    public Object getItem(int position) {
        return ims_path.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder mHolder;
        ((SavingImages) context).scoresToUpdate = new String[ims_path.size()];
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_grid, null);
            mHolder = new ViewHolder();


            mHolder.edit_comment = (EditText) convertView.findViewById(R.id.edit_comment);
            mHolder.imageView1 = (ImageView) convertView.findViewById(R.id.imageView1);
            convertView.setTag(mHolder);

            convertView.setTag(R.id.edit_comment, mHolder.edit_comment);
            convertView.setTag(R.id.imageView1, mHolder.imageView1);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.edit_comment.setTag(position);

        mHolder.imageView1.setImageURI(Uri.parse(ims_path.get(position)));
//        ((SavingImages) context).scoresToUpdate[position] = charSequence.toString();



        return convertView;
    }

    private class ViewHolder {

        private EditText edit_comment;
        private ImageView imageView1;
    }

}
