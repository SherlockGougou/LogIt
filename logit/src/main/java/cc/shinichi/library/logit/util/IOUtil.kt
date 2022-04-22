package cc.shinichi.library.logit.util

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * @ProjectName: LogIt
 * @Package: cc.shinichi.library.logit.util
 * @ClassName: IOUtil
 * @Description: java类作用描述
 * @Author: Kirito qinglingou@gmail.com
 * @CreateDate: 2022/4/22 16:58
 */
object IOUtil {

    // 从文件中读取文本
    fun readTextFromFile(filePath: String): String {
        return try {
            val file = File(filePath)
            if (!file.exists()) {
                return ""
            }
            val fileReader = FileReader(file)
            val reader = BufferedReader(fileReader)
            val sb = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line).append("\n")
            }
            reader.close()
            fileReader.close()
            sb.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}