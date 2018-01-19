package com.example.jasycdell3.rentalpal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jasycdell3.rentalpal.adapter.Adapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class DemoList extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    Adapter modelAdapter;

    public static String arr[] = new String[9];

    ArrayList<Model> models = new ArrayList<>();
    public static final String TAG = "MainActivity";
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_STORAGE = 1;
    int position;
    String imageTempName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_list);
        Model model = new Model(getResources().getStringArray(R.array.carPositions)[0], false,
                R.drawable.camra_grey, R.drawable.car_front);
        models.add(model);
        model = new Model(getResources().getStringArray(R.array.carPositions)[1], false,
                R.drawable.camra_grey, R.drawable.car_front);
        models.add(model);
        model = new Model(getResources().getStringArray(R.array.carPositions)[2], false,
                R.drawable.camra_grey, R.drawable.front_plus);
        models.add(model);
        model = new Model(getResources().getStringArray(R.array.carPositions)[3], false,
                R.drawable.camra_grey, R.drawable.car_left);
        models.add(model);
        model = new Model(getResources().getStringArray(R.array.carPositions)[4], false,
                R.drawable.camra_grey, R.drawable.car_left);
        models.add(model);
        model = new Model(getResources().getStringArray(R.array.carPositions)[5], false,
                R.drawable.camra_grey, R.drawable.left_plus);
        models.add(model);
        model = new Model(getResources().getStringArray(R.array.carPositions)[6], false,
                R.drawable.camra_grey, R.drawable.car_right);
        models.add(model);
        model = new Model(getResources().getStringArray(R.array.carPositions)[7], false,
                R.drawable.camra_grey, R.drawable.car_right);
        models.add(model);
        model = new Model(getResources().getStringArray(R.array.carPositions)[8], false,
                R.drawable.camra_grey, R.drawable.car_plus);
        models.add(model);

        ListView listView = (ListView) findViewById(R.id.listView1);
//        imgs = getResources().obtainTypedArray(R.array.random_imgs);
//        car_directions = getResources().getStringArray(R.array.carPositions);
//
//        camera_images = getResources().obtainTypedArray(R.array.camera_images);
        modelAdapter = new Adapter(this, models);
        listView.setAdapter(modelAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DemoList.this,SavingImages.class);
                intent.putExtra("string-array", arr);
                startActivity(intent);
            }
        });
    }

    public void captureImage(int pos) {
        position = pos;

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    /**
     * Set capture image to database and set to image preview
     *
     * @param data
     */
    private void onCaptureImageResult(Intent data) {

        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");

        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        Uri tempUri = getImageUri(getApplicationContext(), imageBitmap, imageTempName);
        String picturePath = getRealPathFromURI(tempUri);
        arr[position] = picturePath;
        Log.e("ArrayValue",""+position+"of array is :" + Arrays.toString(arr));
        modelAdapter.setImageInItem(position, imageBitmap, picturePath);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 100) {
                onCaptureImageResult(data);
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage, String imageName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, imageName, null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_STORAGE) {
            // BEGIN_INCLUDE(permission_result)
            // Received permission result for camera permission.
            Log.i(TAG, "Received response for Camera permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                Log.i(TAG, "CAMERA permission has now been granted. Showing preview.");
                Snackbar.make(findViewById(R.id.parent_rel), R.string.permision_available_storage,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "CAMERA permission was NOT granted.");
                Snackbar.make(findViewById(R.id.parent_rel), R.string.permissions_not_granted,
                        Snackbar.LENGTH_SHORT).show();

            }
            // END_INCLUDE(permission_result)

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void requestCameraPermission() {
        Log.i(TAG, "CAMERA permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale((DemoList.this),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(TAG,
                    "Displaying camera permission rationale to provide additional context.");
            Snackbar.make(findViewById(R.id.parent_rel), R.string.permission_storage_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(DemoList.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_STORAGE);
                        }
                    })
                    .show();
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(DemoList.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE);
        }
        // END_INCLUDE(camera_permission_reque
    }
}