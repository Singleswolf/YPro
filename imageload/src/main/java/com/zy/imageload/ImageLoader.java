package com.zy.imageload;

import android.content.Context;

/**
 * 图片加载类
 * 策略或者静态代理模式，开发者只需要关心ImageLoader + LoaderOptions
 * Created by yong on 2018/3/4.
 */

public class ImageLoader {
    private static ILoaderStrategy sLoader;
    private static volatile ImageLoader sInstance;

    private ImageLoader() {
    }

    //单例模式
    public static ImageLoader getInstance() {
        if (sInstance == null) {
            synchronized (ImageLoader.class) {
                if (sInstance == null) {
                    sInstance = new ImageLoader();
                }
            }
        }
        return sInstance;
    }

    /**
     * 提供全局替换图片加载框架的接口，若切换其它框架，可以实现一键全局替换
     */
    public void setGlobalImageLoader(ILoaderStrategy loader) {
        sLoader = loader;
    }

    public LoaderOptions with(Context context) {
        return new LoaderOptions(context);
    }

    /**
     * 优先使用实时设置的图片loader，其次使用全局设置的图片loader
     *
     * @param options
     */
    public void loadOptions(LoaderOptions options) {
        if (options.loader != null) {
            options.loader.loadImage(options);
        } else {
            checkNotNull();
            sLoader.loadImage(options);
        }
    }

    public void clearMemoryCache() {
        checkNotNull();
        sLoader.clearMemoryCache();
    }

    public void clearDiskCache() {
        checkNotNull();
        sLoader.clearDiskCache();
    }

    private void checkNotNull() {
        if (sLoader == null) {
            throw new NullPointerException("you must be set your imageLoader at first!");
        }
    }
}
