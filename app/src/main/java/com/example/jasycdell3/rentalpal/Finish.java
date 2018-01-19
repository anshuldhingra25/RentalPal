package com.example.jasycdell3.rentalpal;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Finish extends AppCompatActivity implements View.OnClickListener {
    private AdView adView_finish;
    AppCompatButton finished, finish_edit, finish_view_document, share_via_email, share_via_messenger, share_other;
    TextView view_previous;
    static String rec_Path;
    private static String FILE = Environment.getExternalStorageDirectory() + "/Rental/RentalPal.pdf";
    String _path;
    static Image image;
    private String[] stringArrayPath;
    Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.WHITE);
    Font headingfont = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD);
    Font headingdetails = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
    ArrayList<String> arraydescription = new ArrayList<>();
    int indentation = 0;
    private String description;
    int a = 0;
    //whatever


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_finish);
        Bundle bundle;
        bundle = getIntent().getExtras();
        makedirctory();
        rec_Path = bundle.getString("path");
        description = bundle.getString("description");
        arraydescription = (ArrayList<String>) bundle.getStringArrayList("string-array_description");
        Log.e("arraydescription", "" + arraydescription);
        stringArrayPath = getIntent().getStringArrayExtra("string-array");
        Log.e("stringArrayPath", "" + Arrays.toString(stringArrayPath));
        finished = (AppCompatButton) findViewById(R.id.finished);
        finish_edit = (AppCompatButton) findViewById(R.id.finish_edit);
        finish_view_document = (AppCompatButton) findViewById(R.id.finish_view_document);
        share_via_email = (AppCompatButton) findViewById(R.id.share_via_email);
        share_via_messenger = (AppCompatButton) findViewById(R.id.share_via_messenger);
        share_other = (AppCompatButton) findViewById(R.id.share_other);
        view_previous = (TextView) findViewById(R.id.view_previous);
        finished.setOnClickListener(this);
        finish_edit.setOnClickListener(this);
        finish_view_document.setOnClickListener(this);
        share_via_email.setOnClickListener(this);
        share_via_messenger.setOnClickListener(this);
        share_other.setOnClickListener(this);
        adView_finish = (AdView) findViewById(R.id.adView_finish);
        AdRequest adRequest = new AdRequest.Builder()
                // Check the LogCat to get your test device ID
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)

                .build();
        adView_finish.loadAd(adRequest);
    }

    @Override
    public void onPause() {
        if (adView_finish != null)
            if (a != 1) {

                Intent i = new Intent(Finish.this, Splash.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView_finish != null) {
            adView_finish.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView_finish != null) {
            adView_finish.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.finished:
                try {
                    // imagetopdf();
                    allImagestoPDF(stringArrayPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.finish_edit:
                finish();
                break;
            case R.id.finish_view_document:
                File file = new File(FILE);

                //file should contain path of pdf file/
                Uri path = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(path, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this,
                            "No Application Available to View PDF",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.share_via_email:
                Intent email = new Intent(Intent.ACTION_SEND);

                File file1 = new File(FILE);

                //file should contain path of pdf file/
                Uri path1 = Uri.fromFile(file1);
                email.putExtra(Intent.EXTRA_STREAM, path1);
                email.setType("application/pdf");
                startActivity(email);
                break;
            case R.id.share_via_messenger:
                messenger_sendMsg("message");
                break;
            case R.id.share_other:
                shareOther();
                break;
            case R.id.view_previous:
                break;
            default:
                break;
        }


    }


    public void allImagestoPDF(String[] IMAGES) throws IOException, DocumentException {
        Image img = Image.getInstance(IMAGES[0]);
        float width = img.getScaledWidth();
        float height = img.getScaledHeight();
        Log.e("Height", "" + height);
        Log.e("width", "" + width);
        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
        document.open();
        Paragraph preface1 = new Paragraph("RentalPal", headingfont);
        preface1.setAlignment(Element.ALIGN_CENTER);
        Paragraph preface2 = new Paragraph("" + description, headingdetails);
        preface2.setAlignment(Element.ALIGN_CENTER);
        document.add(preface1);
        document.add(preface2);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        // writer.setPageEvent(event);
        int index = 0;
       /* for (String image : IMAGES) {
//            img = Image.getInstance(image);

            img = Image.getInstance(image);
            Image image11 = img;

            document.setPageSize(new Rectangle(img.getScaledWidth(), img.getScaledHeight()));
            document.add(img);
            img.scaleAbsolute(0, 0);
//            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
//                    - document.rightMargin() - indentation) / img.getWidth()) * 100;
            // img.scalePercent(100);
//            document.setPageSize(img);

            document.newPage();
            //img.setAbsolutePosition(0, 0);

            document.add(new Paragraph("Description\n" + arraydescription.get(index), boldFont));
            //   document.add(new Paragraph("Description", boldFont));

            //document.add(getWatermarkedImage(cb, img1, "Bruno and Ingeborg"));

            index++;
        }*/
        for (String image : IMAGES) {
//            img = Image.getInstance(image);
//            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
//                    - document.rightMargin() - 0) / img.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
//            img.scalePercent(scaler);
//            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            //img.setAlignment(Image.LEFT| Image.TEXTWRAP);

            PdfPTable table = new PdfPTable(1);
            //  table.setWidthPercentage(100);
//            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.setWidths(new int[]{1});
            PdfPCell cell;
            cell = new PdfPCell(Image.getInstance(image), true);

            cell.setCellEvent(new WatermarkedCell(DateFormat.getDateTimeInstance().format(new Date())));
            table.addCell(cell);

            PdfPCell cellTwo = new PdfPCell(new Phrase("Description of above image\n" + arraydescription.get(index)));

            cellTwo.setBorder(Rectangle.NO_BORDER);
            BaseColor myColor = WebColors.getRGBColor("#FFFFFF");
            cellTwo.setBackgroundColor(myColor);

            table.addCell(cellTwo);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
//            img = Image.getInstance(image);
            document.add(table);
//            table.addCell(new PdfPCell(img, true));

//            document.add(table);


            //  document.add(img);
/*
            Paragraph preface3 = new Paragraph(""+ arraydescription.get(index), headingdetails);
            preface3.setAlignment(Element.TITLE);
            document.add(preface3);*/
            index++;
        }
        document.close();
        Toast.makeText(getApplicationContext(), "PDF document generted", Toast.LENGTH_SHORT).show();

    }


    private void imagetopdf() throws Exception {
        try {
            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();

            addImage(document);

            document.close();
            Toast.makeText(getApplicationContext(), "PDF document generted", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void addImage(Document document) {

        try {
            Bitmap bitmap = BitmapFactory.decodeFile(rec_Path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            image = Image.getInstance(byteArray);  ///Here i set byte array..you can do bitmap to byte array and set in image...
            document.addAuthor("RentalPal");
            document.addHeader("All images of room/car available in this pdf", "header");
            document.newPage();
        } catch (BadElementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // image.scaleAbsolute(150f, 150f);
        try {
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
            image.scalePercent(scaler);
            image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(image);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


//        Document document = new Document();
//        PdfWriter.getInstance(document, new FileOutputStream("YourPDFHere.pdf"));
//        document.open();
//        Image image = Image.getInstance(rec_Path);
//        document.add(new Paragraph("Your Heading for the Image Goes Here"));
//        document.add(image);
//        document.close();


    public void makedirctory() {
        _path = Environment.getExternalStorageDirectory() + "/Rental/" + System.currentTimeMillis();


        String dir = Environment.getExternalStorageDirectory() + "/Rental/";
        File imageDirectory = new File(dir);
        imageDirectory.mkdirs();

    }

    private void shareOther() {


        Intent email = new Intent(Intent.ACTION_SEND);

        File file1 = new File(FILE);

        //file should contain path of pdf file/
        Uri path1 = Uri.fromFile(file1);
        email.putExtra(Intent.EXTRA_STREAM, path1);
        email.setType("application/pdf");
        startActivity(Intent.createChooser(email, "Share pdf using"));

    }

    private void messenger_sendMsg(String text) {
        PackageManager pm = Finish.this.getPackageManager();
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(rec_Path);
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("image/jpeg");
            PackageInfo info = pm.getPackageInfo("com.facebook.orca", PackageManager.GET_META_DATA);  //Check if package exists or not. If not then codein catch block will be called
            waIntent.setPackage("com.facebook.orca");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
            try {
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
            waIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
            startActivity(Intent.createChooser(waIntent, "Share Image"));
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(getApplicationContext(), "We could not find Messenger installed on your device.", Toast.LENGTH_SHORT).show();
        }
    }

    class WatermarkedCell implements PdfPCellEvent {
        String watermark;

        public WatermarkedCell(String watermark) {
            this.watermark = watermark;
        }

        public void cellLayout(PdfPCell cell, Rectangle position,
                               PdfContentByte[] canvases) {
            PdfContentByte canvas = canvases[PdfPTable.TEXTCANVAS];
            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER,
                    new Phrase(watermark, boldFont),
                    (position.getLeft() + position.getRight()) / 2,
                    (position.getBottom() + position.getTop()) / 2, 30);
        }
    }

}

