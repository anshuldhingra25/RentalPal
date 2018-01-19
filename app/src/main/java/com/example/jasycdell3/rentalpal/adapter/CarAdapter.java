package com.example.jasycdell3.rentalpal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jasycdell3.rentalpal.Config;
import com.example.jasycdell3.rentalpal.R;

/**
 * Created by JASYC DELL 3 on 6/3/2016.
 */
public class CarAdapter extends BaseAdapter {

    Context mContext;
    TypedArray imgs, camera_images;
    String[] car_directions;
    public static int global_position;


    public CarAdapter(Context mContext, TypedArray imgs, String[] car_directions, TypedArray camera_images) {
        this.mContext = mContext;
        this.imgs = imgs;
        this.camera_images = camera_images;
        this.car_directions = car_directions;


    }

    @Override
    public int getCount() {
        return car_directions.length;
    }

    @Override
    public Object getItem(int position) {
        return car_directions[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list, null);
        }

        ImageView img_direction = (ImageView) convertView.findViewById(R.id.img_direction);
        ImageView img_camera = (ImageView) convertView.findViewById(R.id.img_camera);
        TextView txt_direction = (TextView) convertView.findViewById(R.id.txt_direction);
        Activity activity = (Activity) mContext;

        Config config = new Config(activity);
        if (config.getScreenSize() < 4) {
            txt_direction.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }


        try {

            img_direction.setImageResource(imgs.getResourceId(position, -1));
            txt_direction.setText(car_directions[position]);
           // img_camera.setImageResource(R.drawable.camra_grey);
              img_camera.setImageResource(camera_images.getResourceId(position, -1));


            //  img_camera.setImageResource(camera_images.getResourceId(position, -1));
            /*if (mContext instanceof ListCar) {
                img_camera.setImageResource(R.drawable.camra_grey);
                if (ListCar.arr[position] != null) {
                    img_camera.setImageResource(R.drawable.camra_green);
                }
                img_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
                        global_position = position;
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        ((Activity) mContext).startActivityForResult(cameraIntent, ListCar.CAMERA_REQUEST);
                    }
                });
            } else if (mContext instanceof ListRoom) {
                img_camera.setImageResource(camera_images.getResourceId(position, -1));
            }
*/
        } catch (Exception e) {

            e.printStackTrace();
        }

        return convertView;
    }

}
