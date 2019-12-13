package com.zy.ypro.http.multipart


import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.IOException
import java.io.InputStream
import kotlin.math.min

abstract class StreamRequestBody(
    private val mProgressListener: OnProgressListener?, private val mObject: Any,
    private val mStart: Long, size: Long, private val mMediaType: MediaType
) : RequestBody() {
    private val mEnd: Long

    internal abstract val inputStream: InputStream?

    init {
        mEnd = mStart + size
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return mEnd - mStart
    }

    override fun contentType(): MediaType? {
        return mMediaType
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val buffer = ByteArray(BUFFER_SIZE)
        val inStream = inputStream
        try {
            inStream!!.skip(mStart)
            var length: Int = -1
            val total = contentLength().toInt()
            var bytesRead = 0
            while ({ length = inStream.read(buffer, 0, min(buffer.size, total - bytesRead));length }() > 0) {
                sink.write(buffer, 0, length)
                bytesRead += length
                if (mProgressListener != null && mProgressListener.onProgress(
                        bytesRead,
                        total,
                        mObject
                    )
                ) {
                    throw UploadCancelledException()
                }
            }
        } finally {
            if (inStream != null) {
                try {
                    inStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    companion object {

        private val BUFFER_SIZE = 4096
    }

}
