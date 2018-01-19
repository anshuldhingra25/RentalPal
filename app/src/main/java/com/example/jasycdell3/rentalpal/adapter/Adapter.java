package com.example.jasycdell3.rentalpal.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jasycdell3.rentalpal.ListCar;
import com.example.jasycdell3.rentalpal.ListRoom;
import com.example.jasycdell3.rentalpal.Model;
import com.example.jasycdell3.rentalpal.R;

import java.util.ArrayList;


public class Adapter extends BaseAdapter {

    Context mContext;
    ArrayList<Model> models;

    public Adapter(Context mContext, ArrayList<Model> models) {
        this.mContext = mContext;
        this.models = models;


    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder mHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list, null);
            mHolder = new ViewHolder();


            mHolder.txt_car_direction = (TextView) convertView.findViewById(R.id.txt_direction);
            mHolder.selected = (ImageView) convertView.findViewById(R.id.img_camera);
            mHolder.imgs = (ImageView) convertView.findViewById(R.id.img_direction);
            convertView.setTag(mHolder);

            convertView.setTag(R.id.txt_direction, mHolder.txt_car_direction);
            convertView.setTag(R.id.img_camera, mHolder.selected);
            convertView.setTag(R.id.img_direction, mHolder.imgs);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.selected.setTag(position);
        mHolder.txt_car_direction.setText(models.get(position).getCar_direction());
        mHolder.imgs.setImageResource(models.get(position).getCar_images());
        //below function is used for compare two strings
        if (!models.get(position).isSelected()) {
            mHolder.selected.setImageResource(R.drawable.camra_grey);

        } else {
            mHolder.selected.setImageResource(R.drawable.rightred);
        }
        final Model model = (Model) models.get(position);

        model.setListItemPosition(position);
        mHolder.selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Camera permission has not been granted.
                    if (mContext instanceof ListCar) {
                        ((ListCar) mContext).requestCameraPermission();
                    } else {
                        ((ListRoom) mContext).requestCameraPermission();
                    }
                } else {
                    if (mContext instanceof ListCar) {
                        ((ListCar) mContext).captureImage(model.getListItemPosition());
                    } else {
                        ((ListRoom) mContext).captureImage(model.getListItemPosition());
                    }
                }


            }
        });


        return convertView;
    }

    private class ViewHolder {

        private TextView txt_car_direction;
        private ImageView selected, imgs;
    }

    public void setImageInItem(int position, Bitmap imageSrc, String imagePath) {
        Model model = (Model) models.get(position);

        model.setSelected(true);
        notifyDataSetChanged();
    }

}
