package com.zy.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;

import androidx.annotation.DrawableRes;

import java.io.File;

/**
 * Created by yong on 2019/3/5.
 * 该类为图片加载框架的通用属性封装，不能耦合任何一方的框架
 */
public class LoaderOptions {
    protected int placeholderResId;
    protected int errorResId;
    protected boolean isCenterCrop;
    protected boolean isCenterInside;
    protected boolean skipMemoryCache; //是否缓存到本地
    protected boolean skipDiskCache;
    protected Bitmap.Config config = Bitmap.Config.RGB_565;
    protected int targetWidth;
    protected int targetHeight;
    protected float radius; //圆角角度
    protected float degrees; //旋转角度.注意:picasso针对三星等本地图片，默认旋转回0度，即正常位置。此时不需要自己rotate
    protected Drawable placeholder;
    protected View targetView;//targetView展示图片
    protected BitmapCallBack callBack;
    protected String url;
    protected File file;
    protected int drawableResId;
    protected Uri uri;
    protected Context context;
    protected AsType asType;
    protected ILoaderStrategy loader;//实时切换图片加载库
    protected boolean isFitCenter;
    protected boolean isCircle;

    public LoaderOptions(Context context) {
        this.context = context;
    }

    public LoaderOptions load(String url) {
        this.url = url;
        return this;
    }

    public LoaderOptions load(File file) {
        this.file = file;
        return this;
    }

    public LoaderOptions load(int drawableResId) {
        this.drawableResId = drawableResId;
        return this;
    }

    public LoaderOptions load(Uri uri) {
        this.uri = uri;
        return this;
    }

    public LoaderOptions asBitmap() {
        this.asType = AsType.BITMAP;
        return this;
    }

    public LoaderOptions asGif() {
        this.asType = AsType.GIF;
        return this;
    }

    public LoaderOptions asFile() {
        this.asType = AsType.FILE;
        return this;
    }

    public LoaderOptions asDrawable() {
        this.asType = AsType.DRAWABLE;
        return this;
    }

    public void into(View targetView) {
        this.targetView = targetView;
        ImageLoader.getInstance().loadOptions(this);
    }

    public void bitmap(BitmapCallBack callBack) {
        this.callBack = callBack;
        ImageLoader.getInstance().loadOptions(this);
    }

    public LoaderOptions loader(ILoaderStrategy imageLoader) {
        this.loader = imageLoader;
        return this;
    }

    public LoaderOptions placeholder(@DrawableRes int placeholderResId) {
        this.placeholderResId = placeholderResId;
        return this;
    }

    public LoaderOptions placeholder(Drawable placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public LoaderOptions error(@DrawableRes int errorResId) {
        this.errorResId = errorResId;
        return this;
    }

    public LoaderOptions centerCrop() {
        isCenterCrop = true;
        return this;
    }

    public LoaderOptions circleCrop() {
        isCircle = true;
        return this;
    }

    public LoaderOptions centerInside() {
        isCenterInside = true;
        return this;
    }

    public LoaderOptions fitCenter() {
        isFitCenter = true;
        return this;
    }

    public LoaderOptions config(Bitmap.Config config) {
        this.config = config;
        return this;
    }

    public LoaderOptions resize(int targetWidth, int targetHeight) {
        this.targetWidth = targetWidth;
        this.targetHeight = targetHeight;
        return this;
    }

    /**
     * 圆角
     *
     * @param bitmapAngle 度数
     * @return
     */
    public LoaderOptions round(float bitmapAngle) {
        this.radius = bitmapAngle;
        return this;
    }

    public LoaderOptions skipMemoryCache(boolean skipLocalCache) {
        this.skipMemoryCache = skipLocalCache;
        return this;
    }

    public LoaderOptions skipDiskCache(boolean skipNetCache) {
        this.skipDiskCache = skipNetCache;
        return this;
    }

    public LoaderOptions rotate(float degrees) {
        this.degrees = degrees;
        return this;
    }

    public enum AsType {
        BITMAP,
        GIF,
        DRAWABLE,
        FILE
    }

}


