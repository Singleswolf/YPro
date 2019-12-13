package com.zy.ypro.http.multipart

import android.text.TextUtils
import android.webkit.MimeTypeMap
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

object MultipartFactory {

    val TEXT_MEDIA_TYPE = MediaType.parse("text/plain")
    val MULTI_PART_MEDIA_TYPE = MediaType.parse("multipart/form-data")

    fun convertMap2RequestBodyMap(params: Map<String, String>): Map<String, RequestBody> {
        val map = HashMap<String, RequestBody>()
        val entries = params.entries.iterator()

        while (entries.hasNext()) {
            val entry = entries.next()
            if (!TextUtils.isEmpty(entry.value)) {
                map[entry.key] = createBodyFromString(entry.value)
            }
        }
        return map
    }

    fun createFileRequestBody(
        fileKey: String, file: File, start: Int,
        size: Long, onProgressListener: OnProgressListener?, mediaType: MediaType?
    ): MultipartBody.Part {
        return MultipartBody.Part
            .createFormData(
                fileKey, file.name, FileRequestBody(
                    onProgressListener!!, file,
                    start.toLong(), size, mediaType!!
                )
            )
    }

    @JvmOverloads
    fun createFileRequestBody(
        fileKey: String, file: File,
        onProgressListener: OnProgressListener? = null
    ): MultipartBody.Part {
        val mime = MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(getExtension(file.name))
        var mediaType: MediaType? = null
        if (!TextUtils.isEmpty(mime)) {
            mediaType = MediaType.parse(mime!!)
        }
        if (mediaType == null) {
            mediaType = MULTI_PART_MEDIA_TYPE
        }
        return createFileRequestBody(fileKey, file, 0, file.length(), onProgressListener, mediaType)
    }

    private fun getExtension(name: String): String {
        val dotPos = name.lastIndexOf('.')
        return if (0 <= dotPos) {
            name.substring(dotPos + 1)
        } else ""
    }

    @JvmOverloads
    fun createContentRequestBody(
        fileKey: String, content: ByteArray,
        name: String, onProgressListener: OnProgressListener? = null
    ): MultipartBody.Part {
        return MultipartBody.Part
            .createFormData(
                fileKey, name, ByteRequestBody(
                    onProgressListener!!,
                    content, 0, content.size.toLong(), MULTI_PART_MEDIA_TYPE!!
                )
            )
    }

    fun createBodyFromString(content: String): RequestBody {
        return RequestBody.create(TEXT_MEDIA_TYPE, content)
    }

}
