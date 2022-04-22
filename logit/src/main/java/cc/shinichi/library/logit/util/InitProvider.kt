package cc.shinichi.library.logit.util

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import cc.shinichi.library.logit.LocalLogIt
import cc.shinichi.library.logit.LogIt
import cc.shinichi.library.logit.constant.Type

/**
 * @ProjectName: LogIt
 * @Package: cc.shinichi.library.logit.util
 * @ClassName: InitProvider
 * @Description: java类作用描述
 * @Author: Kirito qinglingou@gmail.com
 * @CreateDate: 2022/4/22 14:58
 */
class InitProvider : ContentProvider() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var app: Context? = null
    }

    override fun onCreate(): Boolean {
        app = context?.applicationContext
        LocalLogIt.init()
        LogIt.setOnLogcatListener(object : LogIt.OnLogcatListener {
            override fun onLogcat(type: Type, tag: String?, message: String?) {
                LocalLogIt.write(type, tag, message)
            }
        })
        return true
    }

    override fun query(p0: Uri, p1: Array<out String>?, p2: String?, p3: Array<out String>?, p4: String?): Cursor? {
        return null
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }
}