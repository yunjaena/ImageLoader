package com.yunjaena.imageloader;

import android.graphics.Bitmap;

import java.util.List;

public interface ImageLoadListener {
    void onImageAdded(List<Bitmap> bitmapList);

    void onImageFailed(ImageFailedType failedType);
}
