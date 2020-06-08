package com.yunjaena.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.yunjaena.imageloader.ImageFailedType;
import com.yunjaena.imageloader.ImageLoadListener;
import com.yunjaena.imageloader.ImageLoader;
import com.yunjaena.imageloader.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ImageLoadListener {
    private Context context;
    private Thread imageShowingThread;
    private Handler handler;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        handler = new Handler();
        imageView = findViewById(R.id.main_image_view);
        findViewById(R.id.main_gallery_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.with(context).
                        setImageLoadListener(MainActivity.this).
                        getImageFromGallery();
            }
        });
        findViewById(R.id.main_camera_multiple_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.with(context).
                        setImageLoadListener(MainActivity.this).
                        getImageFromCamera();
            }
        });
        findViewById(R.id.main_gallery_multiple_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.with(context).
                        setImageLoadListener(MainActivity.this).
                        setMultiImageSelect(true).
                        getImageFromGallery();
            }
        });


        findViewById(R.id.main_select_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader.with(context).
                        setImageLoadListener(MainActivity.this).
                        setMultiImageSelect(true).
                        setMaxImage(10).
                        setChangeImageSize(true).
                        setImageHeight(500).
                        setImageWidth(500).
                        showGalleryOrCameraSelectDialog();
            }
        });
    }

    @Override
    public void onImageAdded(final List<Bitmap> bitmapList) {
        if (imageShowingThread != null)
            imageShowingThread.interrupt();

        if (bitmapList.size() == 1) {
            imageShowingThread = null;
            setImage(bitmapList.get(0));
        } else if (bitmapList.size() > 1) {
            showLoadedImage(bitmapList);
        }
    }

    @Override
    public void onImageFailed(ImageFailedType failedType) {
        ToastUtil.getInstance().makeShort(failedType.name());
    }

    public void showLoadedImage(final List<Bitmap> showingBitmap) {
        imageShowingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        for (int i = 0; i < showingBitmap.size(); i++) {
                            if (Thread.interrupted()) break;
                            final int index = i;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    setImage(showingBitmap.get(index));
                                }
                            });
                            Thread.sleep(1000);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        imageShowingThread.start();
    }

    public void setImage(Bitmap bitmap) {
        ToastUtil.getInstance().makeShort(String.format(Locale.KOREA, "bitmap size : %f MB", sizeOf(bitmap)));
        imageView.setImageBitmap(bitmap);
    }


    protected double sizeOf(Bitmap data) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        data.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        return imageInByte.length / 1000000.0;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (imageShowingThread != null)
            imageShowingThread.interrupt();
    }
}
