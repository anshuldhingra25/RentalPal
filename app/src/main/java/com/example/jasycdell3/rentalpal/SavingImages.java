package com.example.jasycdell3.rentalpal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.jasycdell3.rentalpal.adapter.GridAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SavingImages extends AppCompatActivity {

    GridAdapter adapter;
    Button save_images, btn_back, btn_next;
    public GridView gridview;
    String send_path;
    private String[] stringArray;
    private AdView adView_savingimages;
    ArrayList<String> comment_values = new ArrayList<>();
    public String[] scoresToUpdate;
    EditText description;
    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_saving_images);
        Intent intent = getIntent();
        stringArray = intent.getStringArrayExtra("string-array");
        List<String> list = new ArrayList<String>();

        for (String s : stringArray) {
            if (s != null && s.length() > 0) {
                list.add(s);
            }
        }

        stringArray = list.toArray(new String[list.size()]);
        gridview = (GridView) findViewById(R.id.gridView1);
        save_images = (Button) findViewById(R.id.save_images);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_back = (Button) findViewById(R.id.btn_back);
        description = (EditText) findViewById(R.id.description);
        // adapter = new NormalAdapter(this, img_array);
        adapter = new GridAdapter(this, new ArrayList<String>(Arrays.asList(stringArray)));
        gridview.setAdapter(adapter);


        /** get all values of the EditText-Fields */

        adView_savingimages = (AdView) findViewById(R.id.adView_savingimages);
        AdRequest adRequest = new AdRequest.Builder()
                // Check the LogCat to get your test device ID
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)

                .build();
        adView_savingimages.loadAd(adRequest);
        save_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stringArray.length == 0) {
                    Toast.makeText(getApplicationContext(), "No image is selected", Toast.LENGTH_SHORT).show();
                } else {


                    Bitmap b = getWholeListViewItemsToBitmap(gridview);

                    //Save bitmap
                    String extr = Environment.getExternalStorageDirectory() + "/DCIM/";
                    String fileName = "Screenshot.jpg";
                    File myPath = new File(extr, fileName);

                    send_path = String.valueOf(myPath);
                    Log.e("myPath", "" + send_path);
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(myPath);
                        b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                        MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), b, "Screen", "screen");
                        Toast.makeText(getApplicationContext(), "Image save successfully in" + send_path, Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = 1;
                getAllValues();
                //GridAdapter.arrayList.clear();
//                Log.e("scoresToUpdate", "" + GridAdapter.arrayList);
//                Intent intent1 = new Intent(SavingImages.this, Finish.class);
//                intent1.putExtra("path", send_path);
//                intent1.putExtra("string-array", stringArray);
//                intent1.putExtra("string-array_description", getAllValues());
//                startActivity(intent1);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static Bitmap getWholeListViewItemsToBitmap(GridView gridView) {

        GridView grid_view = gridView;
        GridAdapter adapter = (GridAdapter) grid_view.getAdapter();
        int itemscount = adapter.getCount();
        int allitemsheight = 0;
        List<Bitmap> bmps = new ArrayList<Bitmap>();

        for (int i = 0; i < itemscount; i++) {

            View childView = adapter.getView(i, null, grid_view);
            childView.measure(View.MeasureSpec.makeMeasureSpec(grid_view.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
            childView.setDrawingCacheEnabled(true);
            childView.buildDrawingCache();
            bmps.add(childView.getDrawingCache());
            allitemsheight += childView.getMeasuredHeight();
        }

        Bitmap bigbitmap = Bitmap.createBitmap(grid_view.getMeasuredWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
        Canvas bigcanvas = new Canvas(bigbitmap);

        Paint paint = new Paint();
        int iHeight = 0;

        for (int i = 0; i < bmps.size(); i++) {
            Bitmap bmp = bmps.get(i);
            bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
            iHeight += bmp.getHeight();

            bmp.recycle();
            bmp = null;
        }


        return bigbitmap;
    }

    @Override
    public void onPause() {
        if (adView_savingimages != null) {
            adView_savingimages.pause();
        }
        if (a != 1) {

            Intent i = new Intent(SavingImages.this, Splash.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView_savingimages != null) {
            adView_savingimages.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView_savingimages != null) {
            adView_savingimages.destroy();
        }
        super.onDestroy();
    }

    public void getAllValues() {
        comment_values.clear();
        View parentView = null;


        for (int i = 0; i < gridview.getCount(); i++) {
            parentView = getViewByPosition(i, gridview);

            String getString = ((EditText) parentView
                    .findViewById(R.id.edit_comment)).getText().toString();

            comment_values.add(getString);


        }
        Log.e("comment_values", "" + comment_values);
        Intent intent1 = new Intent(SavingImages.this, Finish.class);

        intent1.putExtra("description", description.getText().toString());
        intent1.putExtra("path", send_path);
        intent1.putExtra("string-array", stringArray);
        intent1.putExtra("string-array_description", comment_values);
        startActivity(intent1);

    }

    public View getViewByPosition(int pos, GridView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
//    @Override
//    protected void onUserLeaveHint()
//    {
//        Intent intent = new Intent(getApplicationContext(), Splash.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("EXIT", true);
//        startActivity(intent);
//        super.onUserLeaveHint();
//    }


}