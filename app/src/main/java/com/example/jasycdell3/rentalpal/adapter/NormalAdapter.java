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

/**
 * Created by JASYC DELL 3 on 6/13/2016.
 */
public class NormalAdapter extends BaseAdapter {
    Context mContext;
    String[] img_str_array;


    public NormalAdapter(Context mContext, String[] img_str_array) {
        this.mContext = mContext;
        this.img_str_array = img_str_array;
    }

    @Override
    public int getCount() {
        return img_str_array.length;
    }

    @Override
    public Object getItem(int position) {
        return img_str_array[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_grid, null);
        }
        EditText edit_comment = (EditText) convertView.findViewById(R.id.edit_comment);

        ImageView imageview1 = (ImageView) convertView.findViewById(R.id.imageView1);


        try {

            imageview1.setImageURI(Uri.parse(img_str_array[position]));


        } catch (Exception e) {

            e.printStackTrace();
        }

        return convertView;
    }

}
