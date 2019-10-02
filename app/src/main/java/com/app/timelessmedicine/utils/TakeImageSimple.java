package com.app.timelessmedicine.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.widget.Toast;
import androidx.exifinterface.media.ExifInterface;
import com.app.timelessmedicine.utils.LogUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;


public class TakeImageSimple extends Activity {
    public static String path = "";
    private final String capture_dir = Environment.getExternalStorageDirectory() + "/RimoApp/";
    Activity activity;

    private final int CAMERA_PIC_REQUEST = 1888, REQ_CODE_PICK_IMAGE = 1;

    MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
    public static final int RECORD_PERMISSION_REQUEST_CODE = 1;
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int CAMERA_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 4;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 3;
    Uri imageFileUri;
    Bundle b;



    public TakeImageSimple() {
    }

    public TakeImageSimple(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = getIntent().getExtras();
        if (b != null) {
            String image_from = b.getString("from");
            if (image_from.equalsIgnoreCase("camera")) {
                File file = new File(capture_dir);
                if (!file.exists()) {
                    file.mkdirs();
                }
                path = capture_dir + System.currentTimeMillis() + ".jpg";
//				imageFileUri = Uri.fromFile(new File(path));
//				imageFileUri = FileProvider.getUriForFile(TakeImage.this,BuildConfig.APPLICATION_ID + ".provider",new File(path));
                imageFileUri = FileProvider.getUriForFile(TakeImageSimple.this, "com.app.timelessmedicine.provider", new File(path));

                if (!marshMallowPermission.checkPermissionForCamera()) {
                    marshMallowPermission.requestPermissionForCamera();
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                }
            } else if (image_from.equalsIgnoreCase("gallery")) {
                if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                    marshMallowPermission.requestPermissionForExternalStorage();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!marshMallowPermission.checkPermissionForExternalStorage()) {
                        marshMallowPermission.requestPermissionForExternalCameraStorage();
                    } else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
                        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                    }
                } else {
                    onBackPressed();
                }
                return;
            }
            case CAMERA_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                } else {
                    onBackPressed();
                }
                return;
            }
            case EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
                } else {
                    onBackPressed();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void setImageCrop(final String path) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            final int sample = Math.min(options.outWidth / 500,
                    options.outHeight / 250);
            options.inSampleSize = sample;
            options.inJustDecodeBounds = false;
            options.inTempStorage = new byte[16 * 1024];
            Bitmap bm = BitmapFactory.decodeFile(path, options);

//            Bitmap bm = BitmapFactory.decodeFile(path);
            int orientation = 0;
            try {
                final ExifInterface exif = new ExifInterface(path);
                final String exifOrientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                orientation = Integer.valueOf(exifOrientation);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            final Matrix mat = new Matrix();
            if (orientation == 1) {
                mat.postRotate(0);
            } else if (orientation == 6) {
                mat.postRotate(90);
            } else if (orientation == 8) {
                mat.postRotate(270);
            } else if (orientation == 3) {
                mat.postRotate(180);
            }
            Log.i("Orientation", "" + orientation);
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), mat, true);
            savebitmap(bm, path);
            bm.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savebitmap(final Bitmap img, final String imagePath) {
        try {
            final File f = new File(imagePath);
            if (f.isFile()) {
                f.delete();
            }
            img.compress(Bitmap.CompressFormat.JPEG, 20, new FileOutputStream(new File(imagePath)));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_CODE_PICK_IMAGE) {
                try {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        Cursor cursor = getContentResolver()
                                .query(selectedImage,
                                        new String[]{android.provider.MediaStore.Images.ImageColumns.DATA},
                                        null, null, null);
                        cursor.moveToFirst();
                        path = cursor.getString(0);
                        // start cropping activity for pre-acquired image saved on the device
                        startCropImageActivity(Uri.fromFile(new File(path)));

                    }
                } catch (OutOfMemoryError error) {
                    error.printStackTrace();
                    path = "";
                    setResult(RESULT_CANCELED);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    path = "";
                    setResult(RESULT_CANCELED);
                    finish();
                }

            }
            if (requestCode == CAMERA_PIC_REQUEST) {
                try {
                    Bitmap bm = BitmapFactory.decodeFile(path);
                    Log.i("Camera pixels--->", bm.getWidth() + " " + bm.getHeight());
                    // start cropping activity for pre-acquired image saved on the device
                    startCropImageActivity(Uri.fromFile(new File(path)));
                } catch (OutOfMemoryError error) {
                    error.printStackTrace();
                    path = "";
                    setResult(RESULT_CANCELED);
                    finish();
                } catch (final Exception e) {
                    e.printStackTrace();
                    path = "";
                    setResult(RESULT_CANCELED);
                    finish();
                }
            }
            // handle result of CropImageActivity
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    //  ((ImageButton) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
                    Uri resultUri = result.getUri();
                    File file = null;

                    String cropPath = resultUri.toString();
                    if (cropPath != null) {
                        try {
                            file = new File(new URI(cropPath));
                            path = file.getPath();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }

                        Bitmap bm = BitmapFactory.decodeFile(path);

                        Log.i("pixels--->", bm.getWidth() + " " + bm.getHeight());
                        LogUtils.log("CropPath", path);
                        Intent intent = new Intent();
                        intent.putExtra("filePath", path);
                        setResult(RESULT_OK, intent);
                        finish();
                    }


                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                }
            }

        } else {
            path = "";
            setResult(RESULT_CANCELED);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String SaveImage(Bitmap finalBitmap) {
        String fullPath = "";

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/tabibi_croped_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getPath();
    }

    private void startCropImageActivity(Uri uri) {
        String cropType = b.getString("cropType");

        if (cropType.equals("rectangle")){
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ? CropImageView.CropShape.RECTANGLE : CropImageView.CropShape.RECTANGLE)
                    .setFixAspectRatio(true)
                    .start(this);


        }else {

            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ? CropImageView.CropShape.RECTANGLE : CropImageView.CropShape.RECTANGLE)
                    .setFixAspectRatio(true)
                    .start(this);
        }
    }

    public class MarshMallowPermission {
        Activity activity;

        public MarshMallowPermission(Activity activity) {
            this.activity = activity;
        }

        public boolean checkPermissionForRecord() {
            int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }

        public boolean checkPermissionForExternalStorage() {
            int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }

        public boolean checkPermissionForCamera() {
            int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }

        public void requestPermissionForRecord() {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
                Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App UserSettings for additional functionality.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_PERMISSION_REQUEST_CODE);
            }
        }

        public boolean requestPermissionForExternalStorage() {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(activity, "External Storage permission needed. Please allow in App UserSettings for additional functionality.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
            }
            return true;
        }

        public boolean requestPermissionForExternalCameraStorage() {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(activity, "External Storage permission needed. Please allow in App UserSettings for additional functionality.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
            }
            return true;
        }

        public boolean requestPermissionForCamera() {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {

                //   Toast.makeText(activity, R.string.camera_permission_needed, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            }
            return true;
        }
    }




}