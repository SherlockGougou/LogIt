package cc.shinichi.library.logit

import android.text.TextUtils
import android.util.Log
import cc.shinichi.library.logit.constant.Type
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 * 日志工具类
 *
 * @author kirito
 */
object LogIt {

    private var onLogcatListener: OnLogcatListener? = null

    private val LINE_SEPARATOR: String = System.getProperty("line.separator") as String
    private const val DEFAULT_MESSAGE = "executed"
    private const val JSON_INDENT = 4

    fun setOnLogcatListener(listener: OnLogcatListener?) {
        onLogcatListener = listener
    }

    fun v() {
        printLog(Type.VERBOSE, null, DEFAULT_MESSAGE)
    }

    fun v(msg: Any?) {
        printLog(Type.VERBOSE, null, msg)
    }

    fun v(tag: String?, msg: String?) {
        printLog(Type.VERBOSE, tag, msg)
    }

    fun d() {
        printLog(Type.DEBUG, null, DEFAULT_MESSAGE)
    }

    fun d(msg: Any?) {
        printLog(Type.DEBUG, null, msg)
    }

    fun d(tag: String?, msg: Any?) {
        printLog(Type.DEBUG, tag, msg)
    }

    fun i() {
        printLog(Type.INFO, null, DEFAULT_MESSAGE)
    }

    fun i(msg: Any?) {
        printLog(Type.INFO, null, msg)
    }

    fun i(tag: String?, msg: Any?) {
        printLog(Type.INFO, tag, msg)
    }

    fun w() {
        printLog(Type.WARN, null, DEFAULT_MESSAGE)
    }

    fun w(msg: Any?) {
        printLog(Type.WARN, null, msg)
    }

    fun w(tag: String?, msg: Any?) {
        printLog(Type.WARN, tag, msg)
    }

    fun e() {
        printLog(Type.ERROR, null, DEFAULT_MESSAGE)
    }

    fun e(msg: Any?) {
        printLog(Type.ERROR, null, msg)
    }

    fun e(tag: String?, msg: Any?) {
        printLog(Type.ERROR, tag, msg)
    }

    fun json(jsonFormat: String?) {
        printLog(Type.JSON, null, jsonFormat)
    }

    fun json(tag: String?, jsonFormat: String?) {
        printLog(Type.JSON, tag, jsonFormat)
    }

    private fun printLog(type: Type, tagStr: String?, objectMsg: Any?) {
        val stackTrace = Thread.currentThread().stackTrace
        val index = 4
        val className = stackTrace[index].fileName
        var methodName = stackTrace[index].methodName
        val lineNumber = stackTrace[index].lineNumber
        val tag = tagStr ?: className
        methodName = methodName.substring(0, 1).toLowerCase(Locale.CHINA) + methodName.substring(1)
        val stringBuilder = StringBuilder()
        stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodName)
            .append(" ] ")
        val msg = objectMsg?.toString() ?: "Log with null Object"
        if (type != Type.JSON) {
            stringBuilder.append(msg)
        }
        val logStr = stringBuilder.toString()
        // 回调，可以自己实现保存到本地、上传日志到接口等功能
        onLogcatListener?.onLogcat(type, tag, logStr)
        when (type) {
            Type.VERBOSE -> Log.v(tag, logStr)
            Type.DEBUG -> Log.d(tag, logStr)
            Type.INFO -> Log.i(tag, logStr)
            Type.WARN -> Log.w(tag, logStr)
            Type.ERROR -> Log.e(tag, logStr)
            Type.JSON -> {
                if (TextUtils.isEmpty(msg)) {
                    Log.d(tag, "Empty or Null json content")
                    return
                }
                var message: String? = null
                try {
                    if (msg.startsWith("{")) {
                        val jsonObject = JSONObject(msg)
                        message = jsonObject.toString(JSON_INDENT)
                    } else if (msg.startsWith("[")) {
                        val jsonArray = JSONArray(msg)
                        message = jsonArray.toString(JSON_INDENT)
                    }
                } catch (e: JSONException) {
                    e(tag, e.localizedMessage)
                    return
                }
                printLine(tag, true)
                message = logStr + LINE_SEPARATOR + message
                val lines = message.split(LINE_SEPARATOR).toTypedArray()
                val jsonContent = StringBuilder()
                for (line in lines) {
                    jsonContent.append("║ ").append(line).append(LINE_SEPARATOR)
                }
                if (jsonContent.toString().length > 3200) {
                    Log.w(tag, "jsonContent.length = " + jsonContent.toString().length)
                    val chunkCount = jsonContent.toString().length / 3200
                    var i = 0
                    while (i <= chunkCount) {
                        val max = 3200 * (i + 1)
                        if (max >= jsonContent.toString().length) {
                            Log.w(tag, jsonContent.substring(3200 * i))
                        } else {
                            Log.w(tag, jsonContent.substring(3200 * i, max))
                        }
                        i++
                    }
                } else {
                    Log.w(tag, jsonContent.toString())
                }
                printLine(tag, false)
            }
            else -> {}
        }
    }

    fun printLine(tag: String?, isTop: Boolean) {
        if (isTop) {
            printLog(
                Type.DEBUG,
                tag,
                "╔═══════════════════════════════════════════════════════════════════════════════════════"
            )
        } else {
            printLog(
                Type.DEBUG,
                tag,
                "╚═══════════════════════════════════════════════════════════════════════════════════════"
            )
        }
    }

    interface OnLogcatListener {

        fun onLogcat(type: Type, tag: String?, message: String?)
    }
}