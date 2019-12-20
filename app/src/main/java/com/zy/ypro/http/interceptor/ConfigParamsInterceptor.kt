package com.zy.ypro.http.interceptor

import android.text.TextUtils
import com.zy.ypro.http.RetrofitConfig
import com.zy.ypro.http.multipart.StreamRequestBody
import com.zy.ypro.http.utils.ParamsUtils
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.util.*


/**
 * 将 config 里面的 param 的 body、url 整合到 request 里去
 */
class ConfigParamsInterceptor(private val mConfig: RetrofitConfig.Params) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url()

        val queryParameterNames = originalHttpUrl.queryParameterNames()
        // 如果是 get 那么其储存的是 url 上的 param，如果是 post 那么其储存的是 body 中的 param
        val params = HashMap<String, String>()
        var multiParams: Map<String, String>? = null
        val get = "GET".equals(original.method(), ignoreCase = true)
        if (get) {
            if (queryParameterNames.isNotEmpty()) {
                for (key in queryParameterNames) {
                    params[key] = originalHttpUrl.queryParameter(key)!!
                }
            }
        } else if (original.body() != null) {
            if (original.body() is FormBody) {
                val originBody = original.body() as FormBody?
                val len = originBody!!.size()
                for (i in 0 until len) {
                    if (!params.containsKey(originBody.name(i))) {
                        params[originBody.name(i)] = originBody.value(i)
                    }
                }
            } else if (original.body() is MultipartBody) {
                multiParams = extractMultipartParams(original)
                params.putAll(multiParams)
            }
        }

        val pair = ParamsUtils.obtainParams(mConfig, params, get)
        val builder = Request.Builder()
        // 设置 request 的 body
        if (!get) {
            if (original.body() is MultipartBody) {
                val body = original.body() as MultipartBody?
                val multipartBuilder = MultipartBody.Builder((original.body() as MultipartBody).boundary())
                multipartBuilder.setType(body!!.type())
                val partList = ArrayList<MultipartBody.Part>(body.parts())

                for (part in partList) {
                    multipartBuilder.addPart(part.headers(), part.body())
                }

                val postParams = pair.second
                if (postParams != null && !postParams.isEmpty()) {
                    for ((key, value) in postParams) {

                        if (multiParams != null && multiParams.containsKey(key)) {
                            continue
                        }

                        multipartBuilder.addFormDataPart(key, value)
                    }
                }

                builder.method(original.method(), multipartBuilder.build())
            } else if (original.body() is FormBody || original.body() == null || original.body()!!.contentLength() == 0L) {
                // 存在一部分没有任何参数的请求，只需要通用参数，这部分请求的 body 的 Length 是 0
                // 需要处理成 FormBody
                val formBuilder = FormBody.Builder()
                val postParams = pair.second

                if (original.body() is FormBody) {
                    val originBody = original.body() as FormBody?
                    for (i in 0 until originBody!!.size()) {
                        val name = originBody.name(i)
                        val value = originBody.value(i)
                        if (postParams != null && postParams.containsKey(name) && TextUtils.equals(value, postParams[name])) {
                            postParams.remove(name)
                        }
                    }
                }

                if (postParams != null) {
                    for ((key, value) in postParams) {
                        formBuilder.add(key, value)
                    }
                }
                builder.method(original.method(), formBuilder.build())
            }
        } else {
            builder.method(original.method(), original.body())
        }

        // 设置 request 的 head 、url、tag

        val lastHeaders = original.headers()
        val headers = mConfig.headers
        for ((key, value) in headers) {
            lastHeaders.newBuilder().add(key, value)
        }
        builder.headers(lastHeaders)

        builder.url(buildUrl(originalHttpUrl, pair.first))
        builder.tag(original.tag())

        return chain.proceed(builder.build())
    }

    @Throws(IOException::class)
    private fun extractMultipartParams(original: Request): Map<String, String> {
        val multipartBody = original.body() as MultipartBody?
        val params = HashMap<String, String>()
        val size = multipartBody!!.size()
        for (i in 0 until size) {
            val part = multipartBody.part(i)
            if (part.body() !is StreamRequestBody && part.headers() != null) {
                val headerName = part.headers()!!.get(part.headers()!!.name(0))
                // multipart 的数据在添加成 FormData 的时候的格式是 form-data; name="", 这里是把对应的 key 取出
                val index = headerName!!.indexOf(NAME)
                // 取出来的 key 的名称会有 双引号阔起来，所以这里将双引号去除掉
                val name = headerName.substring(index + NAME.length, headerName.length - 1)

                // body 是一个 RequestBody 的形式，需要通过 buffer 输出到 Byte[] 数组中转换
                val buffer = Buffer()
                val content = ByteArray(part.body().contentLength().toInt())
                part.body().writeTo(buffer)
                buffer.readFully(content)
                params[name] = String(content, Charset.forName("UTF-8"))
                buffer.close()
            }
        }
        return params
    }

    private fun buildUrl(url: HttpUrl, params: Map<String, String>?): HttpUrl {
        if (params == null || params.isEmpty()) {
            return url
        }
        val builder = url.newBuilder()

        for ((key, value) in params) {
            if (url.queryParameter(key) == null) {
                builder.addQueryParameter(key, value)
            } else {
                builder.setQueryParameter(key, value)
            }
        }
        return builder.build()
    }

    companion object {

        private val NAME = "name=\""
    }
}
