package com.zy.ypro.imageloader

/**
 * Created by yong on 2019/3/5.
 */

interface ILoaderStrategy {

    fun loadImage(options: LoaderOptions)

    /**
     * 清理内存缓存
     */
    fun clearMemoryCache()

    /**
     * 清理磁盘缓存
     */
    fun clearDiskCache()

}
