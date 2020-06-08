package com.yunjaena.imageloader;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

public abstract class ImageLoadBuilder<T extends ImageLoadBuilder> {
    protected ImageLoadListener imageLoadListener;
    private Context context;
    private int dialogStyle;
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
    private CharSequence maxImageWarningText;
    private int maxImage;
    private boolean multiImageSelect;
    private boolean changeImageSize;
    private int imageHeight;
    private int imageWidth;


    public ImageLoadBuilder(Context context) {
        this.context = context;
        this.dialogStyle = R.style.Theme_Transparent_ImageLoader_DialogStyle;
        this.permissionDenyMessage = getText(R.string.need_write_permission);
        this.permissionTitle = getText(R.string.need_write_permission_title);
        this.permissionMessage = getText(R.string.need_write_permission);
        this.cameraOrGallerySelectDialogTitle = getText(R.string.select);
        this.cameraOrGallerySelectDialogMessage = getText(R.string.select_gallery_or_camera);
        this.galleryButtonText = getText(R.string.gallery);
        this.cameraButtonText = getText(R.string.camera);
        this.okayButtonText = getText(R.string.okay);
        this.cancelButtonText = getText(R.string.cancel);
        this.galleryIntentText = getText(R.string.gallery_app_select);
        this.maxImageWarningText = getText(R.string.image_over_select_warning);
        this.maxImage = 5;
        this.multiImageSelect = false;
        this.changeImageSize = false;
        this.imageHeight = 200;
        this.imageWidth = 200;
    }

    public T setImageLoadListener(ImageLoadListener imageLoadListener) {
        this.imageLoadListener = imageLoadListener;
        return (T) this;
    }


    public T setDialogStyle(@StyleRes int dialogStyle) {
        this.dialogStyle = dialogStyle;
        return (T) this;
    }


    public T setPermissionDenyMessage(CharSequence permissionDenyMessage) {
        this.permissionDenyMessage = permissionDenyMessage;
        return (T) this;
    }

    public T setPermissionDenyMessage(@StringRes int permissionDenyMessage) {
        this.permissionDenyMessage = getText(permissionDenyMessage);
        return (T) this;
    }

    public T setPermissionTitle(CharSequence permissionTitle) {
        this.permissionTitle = permissionTitle;
        return (T) this;
    }

    public T setPermissionTitle(@StringRes int permissionTitle) {
        this.permissionTitle = getText(permissionTitle);
        return (T) this;
    }

    public T setPermissionMessage(CharSequence permissionMessage) {
        this.permissionMessage = permissionMessage;
        return (T) this;
    }

    public T setMaxImageWarningText(@StringRes int maxImageWarningText) {
        this.maxImageWarningText = getText(maxImageWarningText);
        return (T) this;
    }

    public T setMaxImageWarningText(CharSequence maxImageWarningText) {
        this.maxImageWarningText = maxImageWarningText;
        return (T) this;
    }

    public T setPermissionMessage(@StringRes int permissionMessage) {
        this.permissionMessage = getText(permissionMessage);
        return (T) this;
    }

    public T setCameraOrGallerySelectDialogTitle(CharSequence cameraOrGallerySelectDialogTitle) {
        this.cameraOrGallerySelectDialogTitle = cameraOrGallerySelectDialogTitle;
        return (T) this;
    }

    public T setCameraOrGallerySelectDialogTitle(@StringRes int cameraOrGallerySelectDialogTitle) {
        this.cameraOrGallerySelectDialogTitle = getText(cameraOrGallerySelectDialogTitle);
        return (T) this;
    }

    public T setCameraOrGallerySelectDialogMessage(CharSequence cameraOrGallerySelectDialogMessage) {
        this.cameraOrGallerySelectDialogMessage = cameraOrGallerySelectDialogMessage;
        return (T) this;
    }

    public T setCameraOrGallerySelectDialogMessage(@StringRes int cameraOrGallerySelectDialogMessage) {
        this.cameraOrGallerySelectDialogMessage = getText(cameraOrGallerySelectDialogMessage);
        return (T) this;
    }

    public T setGalleryButtonText(CharSequence galleryButtonText) {
        this.galleryButtonText = galleryButtonText;
        return (T) this;
    }

    public T setGalleryButtonText(@StringRes int galleryButtonText) {
        this.galleryButtonText = getText(galleryButtonText);
        return (T) this;
    }

    public T setCameraButtonText(CharSequence cameraButtonText) {
        this.cameraButtonText = cameraButtonText;
        return (T) this;
    }

    public T setCameraButtonText(@StringRes int cameraButtonText) {
        this.cameraButtonText = getText(cameraButtonText);
        return (T) this;
    }

    public T setOkayButtonText(CharSequence okayButtonText) {
        this.okayButtonText = okayButtonText;
        return (T) this;
    }

    public T setOkayButtonText(@StringRes int okayButtonText) {
        this.okayButtonText = getText(okayButtonText);
        return (T) this;
    }

    public T setCancelButtonText(CharSequence cancelButtonText) {
        this.cancelButtonText = cancelButtonText;
        return (T) this;
    }

    public T setCancelButtonText(@StringRes int cancelButtonText) {
        this.cancelButtonText = getText(cancelButtonText);
        return (T) this;
    }

    public T setGalleryIntentText(CharSequence galleryIntentText) {
        this.galleryIntentText = galleryIntentText;
        return (T) this;
    }

    public T setGalleryIntentText(@StringRes int galleryIntentText) {
        this.galleryIntentText = getText(galleryIntentText);
        return (T) this;
    }


    public T setMaxImage(int maxImage) {
        this.maxImage = maxImage;
        return (T) this;
    }

    public T setMultiImageSelect(boolean multiImageSelect) {
        this.multiImageSelect = multiImageSelect;
        return (T) this;
    }

    public T setChangeImageSize(boolean changeImageSize) {
        this.changeImageSize = changeImageSize;
        return (T) this;
    }

    public T setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
        return (T) this;
    }

    public T setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
        return (T) this;
    }


    protected void getImageFromGallery() {
        Intent intent = getIntent();
        intent.putExtra(ImageLoadActivity.REQUEST, ImageLoadActivity.GALLERY_IMAGE_REQUEST);
        ImageLoadActivity.startActivity(context, intent, imageLoadListener);
    }

    protected void getImageFromCamera() {
        Intent intent = getIntent();
        intent.putExtra(ImageLoadActivity.REQUEST, ImageLoadActivity.CAMERA_IMAGE_REQUEST);
        ImageLoadActivity.startActivity(context, intent, imageLoadListener);
    }

    protected void showGalleryOrCameraSelectDialog() {
        Intent intent = getIntent();
        intent.putExtra(ImageLoadActivity.REQUEST, ImageLoadActivity.GALLERY_CAMERA_DIALOG_REQUEST);
        ImageLoadActivity.startActivity(context, intent, imageLoadListener);
    }

    private CharSequence getText(@StringRes int stringResource) {
        return context.getResources().getString(stringResource);
    }

    private Intent getIntent() {
        Intent intent = new Intent(context, ImageLoadActivity.class);
        intent.putExtra(ImageLoadActivity.PERMISSION_DENY, permissionDenyMessage);
        intent.putExtra(ImageLoadActivity.DIALOG_STYLE, dialogStyle);
        intent.putExtra(ImageLoadActivity.PERMISSION_TEXT, permissionTitle);
        intent.putExtra(ImageLoadActivity.PERMISSION_MESSAGE, permissionMessage);
        intent.putExtra(ImageLoadActivity.IMAGE_OR_GALLERY_SELECT_DIALOG_TEXT, cameraOrGallerySelectDialogTitle);
        intent.putExtra(ImageLoadActivity.IMAGE_OR_GALLERY_SELECT_DIALOG_MESSAGE, cameraOrGallerySelectDialogMessage);
        intent.putExtra(ImageLoadActivity.GALLERY_BUTTON_TEXT, galleryButtonText);
        intent.putExtra(ImageLoadActivity.CAMERA_BUTTON_TEXT, cameraButtonText);
        intent.putExtra(ImageLoadActivity.OKAY_BUTTON_TEXT, okayButtonText);
        intent.putExtra(ImageLoadActivity.CANCEL_BUTTON_TEXT, cancelButtonText);
        intent.putExtra(ImageLoadActivity.GALLERY_INTENT_TEXT, galleryIntentText);
        intent.putExtra(ImageLoadActivity.MAX_IMAGE, maxImage);
        intent.putExtra(ImageLoadActivity.MAX_IMAGE_WARNING_TEXT, maxImageWarningText);
        intent.putExtra(ImageLoadActivity.MULTI_IMAGE_SELECT, multiImageSelect);
        intent.putExtra(ImageLoadActivity.IMAGE_CHANGE_SIZE, changeImageSize);
        intent.putExtra(ImageLoadActivity.IMAGE_HEIGHT, imageHeight);
        intent.putExtra(ImageLoadActivity.IMAGE_WIDTH, imageWidth);
        return intent;
    }

}
