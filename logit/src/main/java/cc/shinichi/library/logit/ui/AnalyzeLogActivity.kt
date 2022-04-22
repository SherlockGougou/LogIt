package cc.shinichi.library.logit.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cc.shinichi.library.logit.LocalLogIt
import cc.shinichi.library.logit.R
import cc.shinichi.library.logit.util.IOUtil

class AnalyzeLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyze_log)

        loadLog()
    }

    fun loadLog() {
        val allLogFilePath = LocalLogIt.getAllLogFilePath()
        allLogFilePath.sort()
        // 从file中读取一行行的文本，放到List中
        val logList = mutableListOf<String>()
        allLogFilePath.forEach {
            IOUtil.readTextFromFile(it).split("\n").forEach { msg ->
                logList.add(msg)
            }
        }
        val previewList = findViewById<RecyclerView>(R.id.previewList)
        previewList.layoutManager = LinearLayoutManager(this)

        val analyzeLogAdapter = AnalyzeLogAdapter(logList)
        previewList.adapter = analyzeLogAdapter
    }
}