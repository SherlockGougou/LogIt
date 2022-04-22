package cc.shinichi.logit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import cc.shinichi.library.logit.LogIt
import cc.shinichi.library.logit.ui.AnalyzeLogActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            LogIt.d("Hello World ${System.currentTimeMillis()}")
            LogIt.i("Hello World ${System.currentTimeMillis()}")
            LogIt.w("Hello World ${System.currentTimeMillis()}")
            LogIt.e("Hello World ${System.currentTimeMillis()}")
            LogIt.v("Hello World ${System.currentTimeMillis()}")
            val json = "{\n" +
                    "    \"code\": 0,\n" +
                    "    \"message\": \"系统处理正常\",\n" +
                    "    \"data\": {\n" +
                    "        \"id\": null,\n" +
                    "        \"version\": null,\n" +
                    "        \"bVersion\": 1646897701098,\n" +
                    "        \"type\": null,\n" +
                    "        \"isForce\": null,\n" +
                    "        \"platform\": null,\n" +
                    "        \"model\": null,\n" +
                    "        \"requiredVersion\": null,\n" +
                    "        \"url\": null,\n" +
                    "        \"fileSize\": null,\n" +
                    "        \"hash\": null,\n" +
                    "        \"md5\": null,\n" +
                    "        \"startTime\": null,\n" +
                    "        \"endTime\": null,\n" +
                    "        \"boxIds\": null,\n" +
                    "        \"exBoxIds\": null,\n" +
                    "        \"flavors\": null,\n" +
                    "        \"companyIds\": null,\n" +
                    "        \"areaIds\": null\n" +
                    "    }\n" +
                    "}"
            LogIt.json(json)
        }

        findViewById<Button>(R.id.analyze).setOnClickListener {
            startActivity(Intent(this, AnalyzeLogActivity::class.java))
        }
    }
}