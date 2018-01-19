package com.example.jasycdell3.rentalpal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.jasycdell3.rentalpal.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by JASYC DELL 3 on 6/13/2016.
 */
public class DemoAdapter extends BaseAdapter {
    Context mContext;
    private static final int CAMERA_REQUEST = 1888;
    EditText edit_comment;

    ImageView imageview1;
    RelativeLayout relativeLayout12;

    public DemoAdapter(Context mContext) {
        this.mContext = mContext;


    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return 4;
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
            convertView = inflater.inflate(R.layout.demo_grid, null);
        }
        edit_comment = (EditText) convertView.findViewById(R.id.edit_comment);

        imageview1 = (ImageView) convertView.findViewById(R.id.img12);
        relativeLayout12 = (RelativeLayout) convertView.findViewById(R.id.relative12);


        try {

            imageview1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    ((Activity) mContext).startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            });


        } catch (Exception e) {

            e.printStackTrace();
        }

        return convertView;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Drawable dr = new BitmapDrawable(photo);
            (relativeLayout12).setBackgroundDrawable(dr);
            imageview1.setVisibility(View.GONE);
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP

        }
    }


}


