package com.yunjaena.imageloader;

import android.content.Context;

import com.yunjaena.imageloader.ImageLoadBuilder;

public class ImageLoader {
   public static final String TAG = "ImageLoader";

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static class Builder extends ImageLoadBuilder<Builder> {

        private Builder(Context context) {
            super(context);
        }

        public void getImageFromGallery() {
            super.getImageFromGallery();
        }

        public void getImageFromCamera(){
            super.getImageFromCamera();
        }

        public void showGalleryOrCameraSelectDialog(){
            super.showGalleryOrCameraSelectDialog();
        }

    }
}
