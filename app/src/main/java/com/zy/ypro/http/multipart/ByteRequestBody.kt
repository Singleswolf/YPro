package com.zy.ypro.http.multipart

import okhttp3.MediaType
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream

class ByteRequestBody(
    progressListener: OnProgressListener, private val mContent: ByteArray,
    start: Long, size: Long, mediaType: MediaType
) : StreamRequestBody(progressListener, mContent, start, size, mediaType) {

    override val inputStream: InputStream?
        @Throws(IOException::class)
        get() = ByteArrayInputStream(mContent)
}
