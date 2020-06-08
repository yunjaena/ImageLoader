package com.yunjaena.imageloader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class ImageQualityUtil {
    public static final String TAG = "ImageQualityUtil";

    public static Bitmap reduceSize(Bitmap bitmap, int requestWidth, int requestHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) requestWidth) / width;
        float scaleHeight = ((float) requestHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
    }

    public static void reduceSizeBitmap(final List<Bitmap> bitmapList, final int requestWidth, final int requestHeight, final ImageConvert imageConvert) {
        final Handler handler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<Bitmap> convertBitmapList = new ArrayList<>();
                for (Bitmap bitmap : bitmapList) {
                    Bitmap convertBitmap = reduceSize(bitmap, requestWidth, requestHeight);
                    if (convertBitmap != null) {
                        Log.d(TAG, "size: " + sizeOf(convertBitmap));
                        convertBitmapList.add(convertBitmap);
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageConvert.onFailed(new Throwable("Image convert failed"));
                            }
                        });
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageConvert.onSuccess(convertBitmapList);
                    }
                });
            }
        }).start();
    }

    private static double sizeOf(byte[] bitmapByte) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapByte, 0, bitmapByte.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        return imageInByte.length / 1000000.0;
    }

    private static double sizeOf(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        return imageInByte.length / 1000000.0;
    }

    public interface ImageConvert {
        void onSuccess(List<Bitmap> bitmapList);

        void onFailed(Throwable throwable);
    }
}
