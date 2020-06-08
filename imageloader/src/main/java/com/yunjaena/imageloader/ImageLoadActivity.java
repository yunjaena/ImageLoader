package com.yunjaena.imageloader;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.yunjaena.imageloader.util.DateUtil;
import com.yunjaena.imageloader.util.ImageQualityUtil;
import com.yunjaena.imageloader.util.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageLoadActivity extends AppCompatActivity {
    public static final String TAG = "ImageLoadActivity";
    public static final int GALLERY_CAMERA_DIALOG_REQUEST = 96;
    public static final int GALLERY_IMAGE_REQUEST = 97;
    public static final int CAMERA_IMAGE_REQUEST = 98;
    public static final String REQUEST = "request";
    public static final String PERMISSION_DENY = "permissionDenyMessage";
    public static final String PERMISSION_TEXT = "permissionTitle";
    public static final String DIALOG_STYLE = "dialogStyle";
    public static final String PERMISSION_MESSAGE = "permissionMessage";
    public static final String IMAGE_OR_GALLERY_SELECT_DIALOG_TEXT = "cameraOrGallerySelectDialogTitle";
    public static final String IMAGE_OR_GALLERY_SELECT_DIALOG_MESSAGE = "cameraOrGallerySelectDialogMessage";
    public static final String GALLERY_BUTTON_TEXT = "galleryButtonText";
    public static final String CAMERA_BUTTON_TEXT = "cameraButtonText";
    public static final String OKAY_BUTTON_TEXT = "okayButtonText";
    public static final String CANCEL_BUTTON_TEXT = "cancelButtonText";
    public static final String GALLERY_INTENT_TEXT = "galleryIntentText";
    public static final String MAX_IMAGE = "maxImage";
    public static final String MAX_IMAGE_WARNING_TEXT = "maxImageWarningText";
    public static final String MULTI_IMAGE_SELECT = "multiImageSelect";
    public static final String IMAGE_CHANGE_SIZE = "changeImageSize";
    public static final String IMAGE_HEIGHT = "imageHeight";
    public static final String IMAGE_WIDTH = "imageWidth";

    public static ImageLoadListener imageLoadListener;
    private File currentPhotoFile;
    private String currentPhotoPath;
    private Context context;
    private CharSequence permissionDenyMessage;
    private CharSequence permissionTitle;
    private CharSequence permissionMessage;
    private CharSequence cameraOrGallerySelectDialogTitle;
    private CharSequence cameraOrGallerySelectDialogMessage;
    private CharSequence galleryButtonText;
    private CharSequence cameraButtonText;
    private CharSequence okayButtonText;
    private CharSequence cancelButtonText;
    private CharSequence galleryIntentText;
    private int maxImage;
    private CharSequence maxImageWarningText;
    private boolean multiImageSelect;
    private boolean changeImageSize;
    private int imageHeight;
    private int imageWidth;
    private int request;
    private int dialogStyle;

    public static void startActivity(Context context, Intent intent, ImageLoadListener imageLoadListener) {
        ImageLoadActivity.imageLoadListener = imageLoadListener;
        ToastUtil.getInstance().init(context);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        overridePendingTransition(0, 0);
        super.onCreate(savedInstanceState);
        setFromSaveInstance(savedInstanceState);
        context = this;
        startImageLoaderFromRequest(request);
    }

    public void startImageLoaderFromRequest(int requet) {
        switch (requet) {
            case GALLERY_IMAGE_REQUEST:
                getImageFromGallery();
                break;
            case CAMERA_IMAGE_REQUEST:
                getImageFromCamera();
                break;
            case GALLERY_CAMERA_DIALOG_REQUEST:
                showCameraOrGallerySelectDialog();
                break;
        }
    }

    public void setFromSaveInstance(Bundle saveInstance) {

        if (saveInstance != null) {
            permissionDenyMessage = saveInstance.getCharSequence(PERMISSION_DENY);
            permissionTitle = saveInstance.getCharSequence(PERMISSION_TEXT);
            permissionMessage = saveInstance.getCharSequence(PERMISSION_MESSAGE);
            dialogStyle = saveInstance.getInt(DIALOG_STYLE);
            cameraOrGallerySelectDialogTitle = saveInstance.getCharSequence(IMAGE_OR_GALLERY_SELECT_DIALOG_TEXT);
            cameraOrGallerySelectDialogMessage = saveInstance.getCharSequence(IMAGE_OR_GALLERY_SELECT_DIALOG_MESSAGE);
            galleryButtonText = saveInstance.getCharSequence(GALLERY_BUTTON_TEXT);
            cameraButtonText = saveInstance.getCharSequence(CAMERA_BUTTON_TEXT);
            okayButtonText = saveInstance.getCharSequence(OKAY_BUTTON_TEXT);
            cancelButtonText = saveInstance.getCharSequence(CANCEL_BUTTON_TEXT);
            galleryIntentText = saveInstance.getCharSequence(GALLERY_INTENT_TEXT);
            maxImage = saveInstance.getInt(MAX_IMAGE);
            maxImageWarningText = saveInstance.getCharSequence(MAX_IMAGE_WARNING_TEXT);
            multiImageSelect = saveInstance.getBoolean(MULTI_IMAGE_SELECT);
            changeImageSize = saveInstance.getBoolean(IMAGE_CHANGE_SIZE);
            imageHeight = saveInstance.getInt(IMAGE_HEIGHT);
            imageWidth = saveInstance.getInt(IMAGE_WIDTH);
            request = saveInstance.getInt(REQUEST);
        } else {
            permissionDenyMessage = getIntent().getCharSequenceExtra(PERMISSION_DENY);
            permissionTitle = getIntent().getCharSequenceExtra(PERMISSION_TEXT);
            permissionMessage = getIntent().getCharSequenceExtra(PERMISSION_MESSAGE);
            dialogStyle = getIntent().getIntExtra(DIALOG_STYLE, -1);
            cameraOrGallerySelectDialogTitle = getIntent().getCharSequenceExtra(IMAGE_OR_GALLERY_SELECT_DIALOG_TEXT);
            cameraOrGallerySelectDialogMessage = getIntent().getCharSequenceExtra(IMAGE_OR_GALLERY_SELECT_DIALOG_MESSAGE);
            galleryButtonText = getIntent().getCharSequenceExtra(GALLERY_BUTTON_TEXT);
            cameraButtonText = getIntent().getCharSequenceExtra(CAMERA_BUTTON_TEXT);
            okayButtonText = getIntent().getCharSequenceExtra(OKAY_BUTTON_TEXT);
            cancelButtonText = getIntent().getCharSequenceExtra(CANCEL_BUTTON_TEXT);
            galleryIntentText = getIntent().getCharSequenceExtra(GALLERY_INTENT_TEXT);
            maxImage = getIntent().getIntExtra(MAX_IMAGE, 5);
            maxImageWarningText = getIntent().getCharSequenceExtra(MAX_IMAGE_WARNING_TEXT);
            multiImageSelect = getIntent().getBooleanExtra(MULTI_IMAGE_SELECT, false);
            changeImageSize = getIntent().getBooleanExtra(IMAGE_CHANGE_SIZE, false);
            imageHeight = getIntent().getIntExtra(IMAGE_HEIGHT, 200);
            imageWidth = getIntent().getIntExtra(IMAGE_WIDTH, 200);
            request = getIntent().getIntExtra(REQUEST, GALLERY_CAMERA_DIALOG_REQUEST);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putCharSequence(PERMISSION_DENY, permissionDenyMessage);
        outState.putCharSequence(PERMISSION_TEXT, permissionTitle);
        outState.putCharSequence(PERMISSION_MESSAGE, permissionMessage);
        outState.putCharSequence(IMAGE_OR_GALLERY_SELECT_DIALOG_TEXT, cameraOrGallerySelectDialogTitle);
        outState.putCharSequence(IMAGE_OR_GALLERY_SELECT_DIALOG_MESSAGE, cameraOrGallerySelectDialogMessage);
        outState.putCharSequence(GALLERY_BUTTON_TEXT, galleryButtonText);
        outState.putCharSequence(CAMERA_BUTTON_TEXT, cameraButtonText);
        outState.putCharSequence(OKAY_BUTTON_TEXT, okayButtonText);
        outState.putCharSequence(CANCEL_BUTTON_TEXT, cancelButtonText);
        outState.putCharSequence(GALLERY_INTENT_TEXT, galleryIntentText);
        outState.putInt(MAX_IMAGE, maxImage);
        outState.putCharSequence(MAX_IMAGE_WARNING_TEXT, maxImageWarningText);
        outState.putBoolean(MULTI_IMAGE_SELECT, multiImageSelect);
        outState.putBoolean(IMAGE_CHANGE_SIZE, changeImageSize);
        outState.putInt(IMAGE_HEIGHT, imageHeight);
        outState.putInt(IMAGE_WIDTH, imageWidth);
        outState.putInt(REQUEST, request);

    }

    public void showCameraOrGallerySelectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, dialogStyle);
        builder.setTitle(cameraOrGallerySelectDialogTitle).setMessage(cameraOrGallerySelectDialogMessage);
        builder.setPositiveButton(cameraButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ImageLoadActivity.this.getImageFromCamera();
            }
        });

        builder.setNegativeButton(galleryButtonText, (new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ImageLoadActivity.this.getImageFromGallery();
            }
        }));

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                imageLoadListener.onImageFailed(ImageFailedType.CANCEL);
                dialog.dismiss();
                finish();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showPermissionDialog(final int permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, dialogStyle);

        builder.setTitle(permissionTitle).setMessage(permissionMessage);

        builder.setPositiveButton(okayButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                ImageLoadActivity.this.grantExternalStoragePermission(permission);
            }
        });

        builder.setNegativeButton(cancelButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                ToastUtil.getInstance().makeShort(permissionDenyMessage);
                imageLoadListener.onImageFailed(ImageFailedType.PERMISSION_DENY);
                finish();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                imageLoadListener.onImageFailed(ImageFailedType.CANCEL);
                dialog.dismiss();
                finish();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void getImageFromGallery() {
        if (!isWritePermissionGranted()) {
            showPermissionDialog(GALLERY_IMAGE_REQUEST);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, multiImageSelect);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(Intent.createChooser(intent, galleryIntentText), GALLERY_IMAGE_REQUEST);
    }

    /**
     * Get image from camera
     */
    public void getImageFromCamera() {
        if (!isWritePermissionGranted()) {
            showPermissionDialog(CAMERA_IMAGE_REQUEST);
            return;
        }

        dispatchTakePictureIntent();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    private File createImageFile() throws IOException {
        String timeStamp = DateUtil.getCurrentDateNoDot();
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public boolean isWritePermissionGranted() {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return permissionCheck != PackageManager.PERMISSION_DENIED;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e(TAG, "dispatchTakePictureIntent: ", ex);
            }
            if (photoFile != null) {
                Uri photoURI;
                if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT))
                    photoURI = FileProvider.getUriForFile(this,
                            "com.yunjaena.imageloader.provider",
                            photoFile);
                else
                    photoURI = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_IMAGE_REQUEST);
            } else {
                bitmapGetFailed(ImageFailedType.FILE_ERROR);
            }
        }
    }

    private boolean grantExternalStoragePermission(int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= 23) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                switch (requestCode) {
                    case GALLERY_IMAGE_REQUEST:
                        getImageFromGallery();
                        break;
                    case CAMERA_IMAGE_REQUEST:
                        getImageFromCamera();
                        break;
                }
            } else {
                ToastUtil.getInstance().makeShort(permissionDenyMessage);
                bitmapGetFailed(ImageFailedType.PERMISSION_DENY);
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_IMAGE_REQUEST:
                    if (data != null)
                        addImageFromGallery(data);
                    break;
                case CAMERA_IMAGE_REQUEST:
                    galleryAddPic();
                    addImageFromCamera();
                    break;
            }
        } else {
            bitmapGetFailed(ImageFailedType.CANCEL);
            finish();
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        currentPhotoFile = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(currentPhotoFile);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    public void addImageFromGallery(Intent data) {
        List<Bitmap> bitmapList = new ArrayList<>();
        try {
            if (data != null) {
                if (data.getClipData() == null) {
                    Bitmap bitmap = getBitmapFromContentResolver(data.getData());
                    bitmapList.add(bitmap);
                } else {
                    ClipData clipData = data.getClipData();
                    if (clipData.getItemCount() > maxImage) {
                        ToastUtil.getInstance().makeShort(maxImageWarningText);
                        return;
                    } else if (clipData.getItemCount() == 1) {
                        Uri uri = clipData.getItemAt(0).getUri();
                        Bitmap bitmap = getBitmapFromContentResolver(uri);
                        bitmapList.add(bitmap);
                    } else if (clipData.getItemCount() > 1 && clipData.getItemCount() < maxImage) {
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri uri = clipData.getItemAt(i).getUri();
                            Bitmap bitmap = getBitmapFromContentResolver(uri);
                            bitmapList.add(bitmap);
                        }
                    }
                }
                bitmapAdded(bitmapList);
            }
        } catch (Exception e) {
            bitmapGetFailed(ImageFailedType.GALLERY_ERROR);
        }
    }

    public void bitmapAdded(List<Bitmap> bitmapList) {
        if (imageLoadListener != null && bitmapList != null && !bitmapList.isEmpty()) {
            if (changeImageSize) {
                ImageQualityUtil.reduceSizeBitmap(bitmapList, imageWidth, imageHeight, new ImageQualityUtil.ImageConvert() {
                    @Override
                    public void onSuccess(List<Bitmap> bitmapList) {
                        imageLoadListener.onImageAdded(bitmapList);
                        finish();
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        imageLoadListener.onImageFailed(ImageFailedType.IMAGE_SIZE_CHANGE_ERROR);
                    }
                });
            } else {
                imageLoadListener.onImageAdded(bitmapList);
                finish();
            }
        }
    }

    public void bitmapGetFailed(ImageFailedType imageFailedType) {
        if (imageLoadListener != null) {
            imageLoadListener.onImageFailed(imageFailedType);
            finish();
        }
    }

    public Bitmap getBitmapFromContentResolver(Uri uri) {
        Bitmap bitmap;
        try {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            } else {
                ImageDecoder.Source source = ImageDecoder.createSource(context.getContentResolver(), uri);
                bitmap = ImageDecoder.decodeBitmap(source);
            }
        } catch (IOException e) {
            e.printStackTrace();
            bitmapGetFailed(ImageFailedType.CAMERA_ERROR);
            return null;
        }
        return bitmap;
    }

    public void addImageFromCamera() {
        List<Bitmap> bitmapList = new ArrayList<>();
        try {
            String filePath = currentPhotoFile.getAbsolutePath();
            if (filePath != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                bitmapList.add(bitmap);
                bitmapAdded(bitmapList);
            }
        } catch (Exception e) {
            bitmapGetFailed(ImageFailedType.CAMERA_ERROR);
        }

    }
}
