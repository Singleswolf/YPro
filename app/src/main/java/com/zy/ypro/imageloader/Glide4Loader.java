package com.zy.ypro.imageloader;//package com.zy.imageloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.security.MessageDigest;

/**
 * 使用Glide 4.7.1
 * Created by yong on 2019/3/5.
 */
public class Glide4Loader implements ILoaderStrategy {

    private Context mContext;

    public Glide4Loader(Context context) {
        mContext = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadImage(LoaderOptions options) {
        RequestManager requestManager = null;
        RequestBuilder requestBuilder = null;
        RequestOptions requestOptions = new RequestOptions();
        if (options.context != null) {
            requestManager = Glide.with(options.context);
        }
        if (requestManager == null) {
            throw new NullPointerException("requestManager must not be null");
        }

        if (options.asType != null) {
            switch (options.asType) {
                case BITMAP:
                    requestBuilder = requestManager.asBitmap();
                    break;
                case GIF:
                    requestBuilder = requestManager.asGif();
                    break;
                case DRAWABLE:
                    requestBuilder = requestManager.asDrawable();
                    break;
                case FILE:
                    requestBuilder = requestManager.asFile();
                    break;
            }
        } else {
            requestBuilder = requestManager.asBitmap();
        }

        if (options.url != null) {
            requestBuilder.load(options.url);
        } else if (options.file != null) {
            requestBuilder.load(options.file);
        } else if (options.drawableResId != 0) {
            requestBuilder.load(options.drawableResId);
        } else if (options.uri != null) {
            requestBuilder.load(options.uri);
        }

        if (options.targetHeight > 0 && options.targetWidth > 0) {
            requestOptions.override(options.targetWidth, options.targetHeight);
        }
        if (options.isCenterInside) {
            requestOptions.centerInside();
        } else if (options.isCenterCrop) {
            requestOptions.centerCrop();
        } else if (options.isFitCenter) {
            requestOptions.fitCenter();
        } else if (options.isCircle) {
            requestOptions.circleCrop();
        }
        if (options.config != null) {
            if (options.config == Bitmap.Config.RGB_565) {
                requestOptions.format(DecodeFormat.PREFER_RGB_565);
            } else {
                requestOptions.format(DecodeFormat.PREFER_ARGB_8888);
            }
        }
        if (options.errorResId != 0) {
            requestOptions.error(options.errorResId);
        }
        if (options.placeholderResId != 0) {
            requestOptions.placeholder(options.placeholderResId);
        }
        if (options.radius != 0) {
            requestOptions.transform(new GlideRoundTransformation(options.radius));
        }
        if (options.skipMemoryCache) {
            requestOptions.skipMemoryCache(options.skipMemoryCache);
        }
        if (options.skipDiskCache) {
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        }
        if (options.degrees != 0) {
            requestOptions.transform(new RotateTransformation(options.degrees));
        }

        if (options.targetView instanceof ImageView) {
            requestBuilder.apply(requestOptions).into(((ImageView) options.targetView));
        } else if (options.callBack != null) {
            requestBuilder.apply(requestOptions).into(new GlideTarget(options.callBack));
        }
    }

    @Override
    public void clearMemoryCache() {
        Glide.get(mContext).clearMemory();
    }

    @Override
    public void clearDiskCache() {
        Glide.get(mContext).clearDiskCache();
    }

    class GlideTarget extends SimpleTarget<Bitmap> {
        BitmapCallBack callBack;

        protected GlideTarget(BitmapCallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            if (this.callBack != null) {
                this.callBack.onBitmapLoaded(resource);
            }
        }

        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {
            super.onLoadFailed(errorDrawable);
            if (this.callBack != null) {
                this.callBack.onBitmapFailed(new Exception("load failed"));
            }
        }
    }

    class RotateTransformation extends BitmapTransformation {

        private float rotateRotationAngle = 0f;
        private final String ID = "rotateDegree()";
        private final byte[] ID_BYTES = ID.getBytes(CHARSET);

        public RotateTransformation(float rotateRotationAngle) {
            this.rotateRotationAngle = rotateRotationAngle;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotateRotationAngle);
            return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        }

        //重写epquals和hashcode方法，确保对象唯一性，以和其他的图片变换做区分
        @Override
        public boolean equals(Object o) {
            return o instanceof RotateTransformation;
        }

        @Override
        public int hashCode() {
            return ID.hashCode();
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            messageDigest.update(ID_BYTES);
        }
    }

    class GlideRoundTransformation extends BitmapTransformation {
        private float bitmapAngle;
        private final String ID = "radius()";
        private final byte[] ID_BYTES = ID.getBytes(CHARSET);

        protected GlideRoundTransformation(float corner) {
            this.bitmapAngle = corner;
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap source, int outWidth, int outHeight) {
            float roundPx = bitmapAngle;//圆角的横向半径和纵向半径
            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());
            final RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(source, rect, rect, paint);
            return output;
        }

        //重写epquals和hashcode方法，确保对象唯一性，以和其他的图片变换做区分
        @Override
        public boolean equals(Object o) {
            return o instanceof GlideRoundTransformation;
        }

        @Override
        public int hashCode() {
            return ID.hashCode();
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            messageDigest.update(ID_BYTES);
        }
    }
}
