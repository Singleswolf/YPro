package com.zy.ypro.http.multipart

import okhttp3.MediaType
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

/**
 * 上传文件并带回调进度
 */
class FileRequestBody(
    progressListener: OnProgressListener, private val mFile: File,
    start: Long, size: Long, mediaType: MediaType
) : StreamRequestBody(progressListener, mFile, start, size, mediaType) {

    internal override val inputStream: InputStream
        @Throws(IOException::class)
        get() = FileInputStream(mFile)
}
