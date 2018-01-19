package com.example.jasycdell3.rentalpal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.jasycdell3.rentalpal.adapter.Adapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class ListCar extends AppCompatActivity {


    private AdView adView_list_car;
    public static String arr[] = new String[9];
    Button list_car_next;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    Adapter modelAdapter;


    ArrayList<Model> models = new ArrayList<>();
    public static final String TAG = "ListCar";
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_STORAGE = 1;
    int position;
    String imageTempName;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.light_grey));
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        }
        setContentView(R.layout.activity_list_car);
        Log.e("ARRAY",Arrays.toString(arr));
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

        ListView listView = (ListView) findViewById(R.id.list_car);
        modelAdapter = new Adapter(this, models);
        listView.setAdapter(modelAdapter);
        adView_list_car = (AdView) findViewById(R.id.adView_list_car);
        list_car_next = (Button) findViewById(R.id.list_car_next);
        AdRequest adRequest = new AdRequest.Builder()
                // Check the LogCat to get your test device ID
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)

                .build();
        adView_list_car.loadAd(adRequest);
        list_car_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListCar.this, SavingImages.class);
                intent.putExtra("string-array", arr);
                startActivity(intent);
            }
        });

    }

    public void captureImage(int pos) {
        position = pos;


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, 100);
    }

    /**
     * Set capture image to database and set to image preview
     *
     * @param data
     */
    private void onCaptureImageResult(Bitmap data) {

        // Bundle extras = data.getExtras();


        //log the data or use it as needed.

//        String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), (Bitmap) extras.get("data"), "Title", null);
//        Log.i("Extras", path);

        //  Bitmap imageBitmap = (Bitmap) extras.get("data");
        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP

        Uri tempUri = getImageUri(getApplicationContext(), data, imageTempName);
        String picturePath = getRealPathFromURI(tempUri);
        Log.d("KEY", picturePath);
        arr[position] = picturePath;
        Log.e("ArrayValue", "" + position + "of array is :" + Arrays.toString(arr));
        modelAdapter.setImageInItem(position, data, picturePath);

    }


    //sasasas
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURIa(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == 100) {

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(fileUri.getPath(), options);
                int imageHeight = options.outHeight;
                int imageWidth = options.outWidth;
                Log.e("imageHeight", "" + imageHeight);
                Log.e("imageWidth", "" + imageWidth);

                int height = 0;
                int width = 0;
                if (imageHeight > imageWidth) {
                    height = 800;
                    width = 480;
                } else if (imageHeight < imageWidth) {
                    height = 480;
                    width = 800;
                }
//                BitmapFactory.Options options = new BitmapFactory.Options();
//
//                // downsizing image as it throws OutOfMemory Exception for larger
//                // images
//                options.inSampleSize = 8;
//                Bitmap bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 700);
                Bitmap bitmap = decodeSampledBitmapFromFile(fileUri.getPath(), (int) (imageWidth / 3 + 0.5d), (int) (imageHeight / 3 + 0.5d));

//                Bitmap photo = (Bitmap) data.getExtras().get("data");

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
//                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
//
//                // CALL THIS METHOD TO GET THE ACTUAL PATH
//                File finalFile = new File(getRealPathFromURIa(tempUri));
//
//                Log.i("Received.", finalFile.toString());


                onCaptureImageResult(bitmap);


            }
        }


    }

    public Uri getImageUri(Context inContext, Bitmap inImage, String imageName) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //  inImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

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
                Snackbar.make(findViewById(R.id.parent_list_car), R.string.permision_available_storage,
                        Snackbar.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "CAMERA permission was NOT granted.");
                Snackbar.make(findViewById(R.id.parent_list_car), R.string.permissions_not_granted,
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
        if (ActivityCompat.shouldShowRequestPermissionRationale((ListCar.this),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(TAG,
                    "Displaying camera permission rationale to provide additional context.");
            Snackbar.make(findViewById(R.id.parent_list_car), R.string.permission_storage_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(ListCar.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_STORAGE);
                        }
                    })
                    .show();
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(ListCar.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE);
        }
        // END_INCLUDE(camera_permission_reque
    }

    @Override
    public void onPause() {
        if (adView_list_car != null) {
            adView_list_car.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView_list_car != null) {
            adView_list_car.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView_list_car != null) {
            adView_list_car.destroy();
        }
        super.onDestroy();
    }



    /*helper methods*/

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }
    @Override
    public void onBackPressed() {
        Arrays.fill(arr, null);
        Log.e("ARRAY",Arrays.toString(arr));
        finish();
        // your code.
    }


}

