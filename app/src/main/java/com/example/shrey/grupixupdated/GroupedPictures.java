package com.example.shrey.grupixupdated;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;
import com.amazonaws.services.rekognition.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupedPictures extends AppCompatActivity {

    int check = 0;
    ArrayList<String> paths = new ArrayList<>();
    static final int REQUEST_PERMISSION_KEY = 1;
    GroupedPictures.LoadAlbum loadAlbumTask;
    GridView grid;
    ArrayList<HashMap<String, String>> albumList = new ArrayList<>();
    ArrayList<String> FilePathString = new ArrayList<>();
    ArrayList<String> FileNameString = new ArrayList<>();
    String[] FilePathStrings = new String[100];
    String[] FileNameStrings = new String[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouped_pictures);

        grid = (GridView) findViewById(R.id.gridview);

        int iDisplayWidth = getResources().getDisplayMetrics().widthPixels ;
        Resources resources = getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = iDisplayWidth / (metrics.densityDpi / 160f);

        if(dp < 360)
        {
            dp = (dp - 17) / 2;
            float px = Function.convertDpToPixel(dp, getApplicationContext());
            grid.setColumnWidth(Math.round(px));
        }

        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if(!Function.hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
        }

    }

    //create the inherited class from the AsyncTask Class
    //(you can create within your activity class)
    class AsyncTaskRunner extends AsyncTask<String,String,String> {
        @Override
        public String doInBackground(String ... input){
            testTask(); // call the testTask() method that i have created
            return null; // this override method must return String
        }
    }

    public void testTask() {
        Float similarityThreshold = 90F;
        AmazonRekognitionClient amazonRekognitionClient;

        int count = 1;

        ArrayList<Integer> done = new ArrayList<>();
        //HashMap<String, ArrayList<String>> myMap = new HashMap<>();
        if(check == 0) {
            for(int i = 0; i < paths.size(); i++) {
                if(!done.contains(i)) {
                    ArrayList<String> temp = new ArrayList<>();
                    temp.add(paths.get(i));
                    FilePathString.add(paths.get(i));
                    for (int j = i; j < paths.size(); j++) {
                        if(!(paths.get(i).equals(paths.get(j)))) {
                            File image1 = new File(paths.get(i));
                            BitmapFactory.Options bmOptions1 = new BitmapFactory.Options();
                            Bitmap bitmap1 = BitmapFactory.decodeFile(image1.getAbsolutePath(),bmOptions1);
                            File image2 = new File(paths.get(j));
                            BitmapFactory.Options bmOptions2 = new BitmapFactory.Options();
                            Bitmap bitmap2 = BitmapFactory.decodeFile(image2.getAbsolutePath(),bmOptions2);

                            ByteBuffer sourceImageBytes=null;
                            ByteBuffer targetImageBytes=null;

                            ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, bos1);
                            byte[] bitmapdata1 = bos1.toByteArray();

                            ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, bos2);
                            byte[] bitmapdata2 = bos2.toByteArray();

                            sourceImageBytes = ByteBuffer.wrap(bitmapdata1);
                            targetImageBytes = ByteBuffer.wrap(bitmapdata2);

                            Image source = new Image().withBytes(sourceImageBytes);
                            Image target = new Image().withBytes(targetImageBytes);

                            // Initialize the Amazon Cognito credentials provider
                            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                                    getApplicationContext(),
                                    "Identity Pool ID removed for security purposes", // Identity pool ID
                                    Regions.AP_SOUTHEAST_2 // Region
                            );

                            //Log.d("Credentials", "ALL OK");

                            amazonRekognitionClient = new AmazonRekognitionClient(credentialsProvider);

                            CompareFacesRequest request = new CompareFacesRequest().withSourceImage(source).withTargetImage(target).withSimilarityThreshold(similarityThreshold);

                            CompareFacesResult compareFacesResult= amazonRekognitionClient.compareFaces(request);

                            List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
                            for (CompareFacesMatch match: faceDetails) {
                                ComparedFace face = match.getFace();
                                float confidence = face.getConfidence();
                                if(confidence > 90F) {
                                    done.add(j);
                                    temp.add(paths.get(j));
                                    FilePathString.add(paths.get(j));
                                }
                                //Log.d("Rekognition", "The API works properly");
                                //Log.d("Confidence", confidence+" "+i+" "+j);
                            }
                        }
                    }
                    int x = temp.size();
                    for(int k = 0; k < x; k++)
                        FileNameString.add("Person " + count);
                    count++;
                }
            }

            for(int index = 0; index < FilePathString.size(); index++) {
                FilePathStrings[index] = FilePathString.get(index);
                FileNameStrings[index] = FileNameString.get(index);
            }
            check++;
        }

    }


    class LoadAlbum extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            albumList.clear();
        }

        protected String doInBackground(String... args) {
            String xml = "";

            String path;
            String album;
            String timestamp;
            String countPhoto;
            Uri uriExternal = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri uriInternal = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;

            String[] projection = { MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED };
            Cursor cursorExternal = getContentResolver().query(uriExternal, projection, "_data IS NOT NULL) GROUP BY (bucket_display_name",
                    null, null);
            Cursor cursorInternal = getContentResolver().query(uriInternal, projection, "_data IS NOT NULL) GROUP BY (bucket_display_name",
                    null, null);
            Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal,cursorInternal});

            while (cursor.moveToNext()) {

                album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                Cursor cursorExternal1 = getContentResolver().query(uriExternal, projection, "bucket_display_name = \""+album+"\"", null, null);
                Cursor cursorInternal1 = getContentResolver().query(uriInternal, projection, "bucket_display_name = \""+album+"\"", null, null);
                Cursor cursor1 = new MergeCursor(new Cursor[]{cursorExternal1,cursorInternal1});
                while (cursor1.moveToNext()) {

                    path = cursor1.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));

                    paths.add(path);
                    //Log.d("CAME IN", paths.size()+"");

                }
                cursor1.close();

            }
            cursor.close();

            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute();

            return xml;
        }



        @Override
        protected void onPostExecute(String xml) {

            GridViewAdapter adapter;
            // Pass String arrays to LazyAdapter Class
            adapter = new GridViewAdapter(GroupedPictures.this, FilePathStrings, FileNameStrings);
            // Set the Adapter to the GridView
            grid.setAdapter(adapter);

            // Capture gridview item click
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Intent i = new Intent(GroupedPictures.this, ViewImage.class);
                    // Pass String arrays FilePathStrings
                    i.putExtra("filepath", FilePathStrings);
                    // Pass String arrays FileNameStrings
                    i.putExtra("filename", FileNameStrings);
                    // Pass click position
                    i.putExtra("position", position);
                    startActivity(i);
                }

            });

        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_PERMISSION_KEY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    loadAlbumTask = new LoadAlbum();
                    loadAlbumTask.execute();
                } else
                {
                    Toast.makeText(GroupedPictures.this, "You must accept permissions.", Toast.LENGTH_LONG).show();
                }
            }
        }

    }




    @Override
    protected void onResume() {
        super.onResume();

        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if(!Function.hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY);
        }else{
            loadAlbumTask = new LoadAlbum();
            loadAlbumTask.execute();
        }
    }
}