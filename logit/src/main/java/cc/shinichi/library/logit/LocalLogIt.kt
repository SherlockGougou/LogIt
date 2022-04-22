package cc.shinichi.library.logit

import android.util.Log
import cc.shinichi.library.logit.constant.Type
import cc.shinichi.library.logit.util.ExecutorIt
import cc.shinichi.library.logit.util.InitProvider.Companion.app
import java.io.*
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*

/**
 * 本地日志记录工具
 *
 * @author kirito
 */
object LocalLogIt {

    private const val TAG = "LocalLogIt"

    /**
     * log日志存放路径
     */
    private var logPath: String? = null

    /**
     * 初始化，须在使用之前设置，最好在Application创建时调用,获得文件储存路径,在后面加"/log"建立子文件夹
     */
    fun init() {
        logPath = localLogPath
        val fileLog = File("$logPath/")
        if (!fileLog.exists()) {
            fileLog.mkdirs()
        }
    }

    /**
     * 获得文件存储路径
     *
     * @return
     */
    val localLogPath: String
        get() {
            val filesDir = File(app?.getExternalFilesDir(null), "localLog")
            return filesDir.absolutePath
        }

    /**
     * 获得文件压缩后的保存路径
     *
     * @return
     */
    val zipLocalLogPath: String
        get() {
            val filesDir = File(app?.getExternalFilesDir(null), "zipLog")
            return filesDir.absolutePath
        }

    fun v(tag: String, msg: String) {
        write(Type.VERBOSE, tag, msg)
    }

    fun d(tag: String, msg: String) {
        write(Type.DEBUG, tag, msg)
    }

    fun i(tag: String, msg: String) {
        write(Type.INFO, tag, msg)
    }

    fun w(tag: String, msg: String) {
        write(Type.WARN, tag, msg)
    }

    fun e(tag: String, msg: String) {
        write(Type.ERROR, tag, msg)
    }

    /**
     * 将log信息写入文件中
     *
     * @param type
     * @param tag
     * @param msg
     */
    fun write(type: Type, tag: String?, msg: String?) {
        ExecutorIt.execute {
            if (null == logPath) {
                Log.e(TAG, "logPath == null ，未初始化LogToFile")
                return@execute
            }
            // 按小时进行文件分割
            val currentTimeMillis = System.currentTimeMillis()
            var format = SimpleDateFormat("yyyy-MM-dd-HH-00-00", Locale.CHINA)
            val todayNowHour = format.format(currentTimeMillis)
            val fileName = logPath + File.separator + todayNowHour + "-debugLog.log"
            // 检查文件是否超过3MB，超过就清空内容
            clearCaches(fileName)
            // 不存在创建
            val fileDir = File(logPath)
            if (!fileDir.exists()) {
                val mkdirs = fileDir.mkdirs()
            }
            val file = File(fileName)
            if (!file.exists()) {
                try {
                    val newFile = file.createNewFile()
                    if (newFile) {
                        // todo 写入基本信息
                        i("baseInfo", "=================================================================")
                        i("baseInfo", "=================================================================")
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
            val now = format.format(currentTimeMillis)
            // 日志命名规则:时间+日志类型+key+内容
            val log = "$now-$type $tag $msg\n"
            var fos: FileOutputStream? = null
            var bw: BufferedWriter? = null
            try {
                // 这里的第二个参数代表追加还是覆盖，true为追加，false为覆盖
                fos = FileOutputStream(fileName, true)
                bw = BufferedWriter(OutputStreamWriter(fos, StandardCharsets.UTF_8))
                bw.write(log)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    bw?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun getAllLogFilePath(): MutableList<String> {
        val list = mutableListOf<String>()
        val files = File(logPath).listFiles()
        if (files != null) {
            for (file in files) {
                val fileName = file.name
                if (fileName.endsWith(".log")) {
                    val filePath = file.absolutePath
                    list.add(filePath)
                }
            }
        }
        return list
    }

    /**
     * 如果大于10MB，清空缓存文件内容
     */
    private fun clearCaches(path: String) {
        path.let {
            val file = File(it)
            if (file.length() / 1024 > 1024 * 10) {
                file.delete()
            }
        }
    }
}