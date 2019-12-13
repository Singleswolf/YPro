package com.zy.ypro.utils

import android.os.Environment
import android.text.TextUtils
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 * 日志的功能操作类 可将日志保存至SD卡
 */
object Logger {

    /**
     * 是否显示json格式的日志
     * 1.true,json字符串打印成json格式的日志
     * 2.false,打印普通字符串
     */
    val isShowJsonLog = false
    val JSON_INDENT = 4
    val LINE_SEPARATOR = System.getProperty("line.separator")
    val LEVEL_ALL = 0
    val LEVEL_NONE = 3
    val LEVEL_VERBOSE = 1
    val LEVEL_DEBUG = 2
    val LEVEL_INFO = 3
    val LEVEL_WARN = 4
    val LEVEL_ERROR = 5
    val LEVEL_ASSERT = 6
    //日志打印时间Format
    private val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val LOG_FMT = SimpleDateFormat("yyyy-MM-dd")
    //DEBUG级别开关
    var LOG_LEVEL = LEVEL_ALL
    //是否保存至SD卡
    private var SAVE_TO_SD = false
    //保存LOG日志的目录
    private var LOG_DIR_PATH = Environment.getExternalStorageDirectory().absolutePath
    //过滤日志
    private var TAG = "XLogger"
    private val mutexPrintJson = Any()

    /**
     * 用于打印错误级的日志信息
     *
     * @param tag    LOG TAG
     * @param strMsg 打印信息
     */
    fun e(tag: String, strMsg: String) {
        eLog(LEVEL_ERROR, tag, strMsg)
    }

    fun e(strMsg: String) {
        e(TAG, strMsg)
    }

    fun e(msg: String, e: Throwable) {
        e(msg + e.message)
    }

    fun e(tag: String, msg: String, e: Throwable) {
        e(tag, msg + e.message)
    }

    fun e(tag: String, logFormat: String, vararg params: Any) {
        eLog(LEVEL_ERROR, tag, logFormat, *params)
    }

    fun e(logFormat: String, vararg params: Any) {
        e(TAG, logFormat, *params)
    }

    fun e(`object`: Any) {
        e(toString(`object`))
    }

    /**
     * 用于打印描述级的日志信息
     *
     * @param tag    LOG TAG
     * @param strMsg 打印信息
     */
    fun d(tag: String, strMsg: String) {
        eLog(LEVEL_DEBUG, tag, strMsg)
    }

    fun d(strMsg: String) {
        d(TAG, strMsg)
    }

    fun d(msg: String, e: Throwable) {
        d(msg + e.message)
    }

    fun d(tag: String, msg: String, e: Throwable) {
        d(tag, msg + e.message)
    }

    fun d(tag: String, logFormat: String, vararg params: Any) {
        eLog(LEVEL_DEBUG, tag, logFormat, *params)
    }

    fun d(logFormat: String, vararg params: Any) {
        d(TAG, logFormat, *params)
    }

    /**
     * 用于打印info级的日志信息
     *
     * @param tag    LOG TAG
     * @param strMsg 打印信息
     */
    fun i(tag: String, strMsg: String) {
        eLog(LEVEL_INFO, tag, strMsg)
    }

    fun i(strMsg: String) {
        i(TAG, strMsg)
    }

    fun i(tag: String, logFormat: String, vararg params: Any) {
        eLog(LEVEL_INFO, tag, logFormat, *params)
    }

    fun i(logFormat: String, vararg params: Any) {
        i(TAG, logFormat, *params)
    }

    /**
     * 用于打印v级的日志信息
     *
     * @param tag    LOG TAG
     * @param strMsg 打印信息
     */
    fun v(tag: String, strMsg: String) {
        eLog(LEVEL_VERBOSE, tag, strMsg)
    }

    fun v(strMsg: String) {
        v(TAG, strMsg)
    }

    fun v(tag: String, logFormat: String, vararg params: Any) {
        eLog(LEVEL_VERBOSE, tag, logFormat, *params)
    }

    fun v(logFormat: String, vararg params: Any) {
        v(TAG, logFormat, *params)
    }

    /**
     * 用于打印w级的日志信息
     *
     * @param tag    LOG TAG
     * @param strMsg 打印信息
     */
    fun w(tag: String, strMsg: String) {
        eLog(LEVEL_WARN, tag, strMsg)
    }

    fun w(strMsg: String) {
        w(TAG, strMsg)
    }

    fun w(tag: String, logFormat: String, vararg params: Any) {
        eLog(LEVEL_WARN, tag, logFormat, *params)
    }

    fun w(logFormat: String, vararg params: Any) {
        w(TAG, logFormat, *params)
    }

    /**
     * 将日志信息保存至SD卡
     *
     * @param tag    LOG TAG
     * @param strMsg 保存的打印信息
     */
    fun storeLog(tag: String, strMsg: String) {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            synchronized(LOG_FMT) {
                try {
                    val logname = LOG_FMT.format(Date(System.currentTimeMillis())) + ".txt"
                    val parent = File(LOG_DIR_PATH)
                    if (!(parent.exists() && parent.isDirectory)) {
                        parent.mkdirs()
                    }
                    val file = File(parent, logname)
                    // 输出
                    val fos = FileOutputStream(file, true)
                    val out = PrintWriter(fos)

                    out.println(fmt.format(System.currentTimeMillis()) + "  >>" + tag + "<<  " + strMsg + '\r'.toString())
                    out.flush()
                    out.close()

                } catch (e1: FileNotFoundException) {
                    e1.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 获取DEBUG状态
     *
     * @return
     */
    fun init(tag: String, level: Int, isSaveLog: Boolean, logDirPath: String) {
        TAG = tag
        LOG_LEVEL = level
        SAVE_TO_SD = isSaveLog
        LOG_DIR_PATH = logDirPath
    }

    /**
     * log太长，显示不全，拼接输出
     *
     * @param level
     * @param tag
     * @param text
     * @author qinbaowei
     */
    fun log(level: Int, tag: String, text: String) {
        var text = text
        val PART_LEN = 3000
        do {
            val clipLen = if (text.length > PART_LEN) PART_LEN else text.length
            val clipText = text.substring(0, clipLen)
            text = if (clipText.length == text.length) "" else text.substring(clipLen)
            when (level) {
                LEVEL_INFO -> Log.i(tag, clipText)
                LEVEL_DEBUG -> Log.d(tag, clipText)
                LEVEL_WARN -> Log.w(tag, clipText)
                LEVEL_VERBOSE -> Log.v(tag, clipText)
                LEVEL_ERROR -> Log.e(tag, clipText)
                LEVEL_ASSERT -> Log.wtf(tag, clipText)
                else -> {
                }
            }
        } while (text.length > 0)
    }

    private fun eLog(level: Int, tag: String, logFormat: String, vararg logParams: Any) {
        var msg: String? = null
        if (level >= LOG_LEVEL) {
            msg = if (logParams != null && logParams.size > 0) String.format(
                logFormat,
                *logParams
            ) else logFormat
            log(level, tag, "----$msg")
        }
        if (SAVE_TO_SD) {
            if (msg == null) {
                msg = if (logParams != null && logParams.size > 0) String.format(
                    logFormat,
                    *logParams
                ) else logFormat
            }
            storeLog(tag, msg)
        }
    }

    fun printJson(tag: String, msg: String?, header: String) {
        if (LEVEL_DEBUG < LOG_LEVEL) {
            return
        }

        if (!isShowJsonLog) {
            e(tag, "http--responseBody:" + msg!!)
            return
        }

        object : Thread() {
            override fun run() {
                synchronized(mutexPrintJson) {

                    var message: String?

                    if (msg != null) {
                        try {
                            if (msg.startsWith("{")) {
                                val jsonObject = JSONObject(msg)
                                message = jsonObject.toString(JSON_INDENT)
                            } else if (msg.startsWith("[")) {
                                val jsonArray = JSONArray(msg)
                                message = jsonArray.toString(JSON_INDENT)
                            } else {
                                message = msg
                            }
                        } catch (e: JSONException) {
                            message = msg
                        }

                    } else {
                        message = "null"
                    }

                    line(true)
                    message = header + LINE_SEPARATOR + message
                    val lines =
                        message.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }
                            .toTypedArray()
                    val linesBuffer = StringBuffer()
                    linesBuffer.append(LINE_SEPARATOR)
                    for (line in lines) {
                        l(LEVEL_VERBOSE, "║ $line")
                    }
                    line(false)
                }
            }
        }.start()
    }

    fun isEmpty(line: String): Boolean {
        return TextUtils.isEmpty(line) || line == "\n" || line == "\t" || TextUtils.isEmpty(line.trim { it <= ' ' })
    }

    fun line(isTop: Boolean) {
        if (isTop) {
            l(
                LEVEL_VERBOSE,
                "╔═══════════════════════════════════════════════════════════════════════════════════════"
            )
        } else {
            l(
                LEVEL_VERBOSE,
                "╚═══════════════════════════════════════════════════════════════════════════════════════"
            )
        }
    }

    /**
     * @param depth 2,the method it self;3,the method who call this method
     * @return filename + method name + line number
     */
    private fun getFileNameMethodLineNumber(depth: Int): String {
        var info = ""
        try {
            val e = Thread.currentThread().stackTrace[depth]
            info = String.format("[(%1\$s:%2\$d)#%3\$s]", e.fileName, e.lineNumber, e.methodName)
        } catch (e: Exception) {
            Log.e("log", "get stack trace element failed!!!")
        }

        return info
    }

    fun lv(logFormat: String, vararg logParam: Any) {
        l(LEVEL_VERBOSE, logFormat, *logParam)
    }

    fun ld(logFormat: String, vararg logParam: Any) {
        l(LEVEL_DEBUG, logFormat, *logParam)
    }

    fun li(logFormat: String, vararg logParam: Any) {
        l(LEVEL_INFO, logFormat, *logParam)
    }

    fun lw(logFormat: String, vararg logParam: Any) {
        l(LEVEL_WARN, logFormat, *logParam)
    }

    fun lw(e: Throwable?) {
        if (null != e) {
            val message = e.message
            message?.let { l(LEVEL_WARN, it) }
        }
    }

    fun le(logFormat: String, vararg logParam: Any) {
        l(LEVEL_ERROR, logFormat, *logParam)
    }

    fun le(e: Throwable?) {
        if (null != e) {
            val message = e.message
            message?.let { l(LEVEL_ERROR, it) }
        }
    }

    private fun l(level: Int, logFormat: String, vararg logParam: Any) {
        try {
            if (level >= LOG_LEVEL || SAVE_TO_SD) {
                val log = String.format(logFormat, *logParam)
                val logs = createLog(log)
                if (level >= LOG_LEVEL) {
                    log(level, logs[0], logs[1])
                }
                if (SAVE_TO_SD) {
                    storeLog(logs[0], logs[1])
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun createLog(log: String): Array<String> {
        var tag: String = getFileNameMethodLineNumber(6)
            tag = "[$TAG]$tag"
        return arrayOf(tag, log)
    }

    private fun toString(obj: Any?): String {
        if (obj == null) {
            return "null"
        }
        if (!obj.javaClass.isArray) {
            return obj.toString()
        }
        if (obj is BooleanArray) {
            return Arrays.toString(obj as BooleanArray?)
        }
        if (obj is ByteArray) {
            return Arrays.toString(obj as ByteArray?)
        }
        if (obj is CharArray) {
            return Arrays.toString(obj as CharArray?)
        }
        if (obj is ShortArray) {
            return Arrays.toString(obj as ShortArray?)
        }
        if (obj is IntArray) {
            return Arrays.toString(obj as IntArray?)
        }
        if (obj is LongArray) {
            return Arrays.toString(obj as LongArray?)
        }
        if (obj is FloatArray) {
            return Arrays.toString(obj as FloatArray?)
        }
        if (obj is DoubleArray) {
            return Arrays.toString(obj as DoubleArray?)
        }
        return if (obj is Array<*>) {
            Arrays.deepToString(obj as Array<Any>?)
        } else "Couldn't find a correct type for the object"
    }
}
