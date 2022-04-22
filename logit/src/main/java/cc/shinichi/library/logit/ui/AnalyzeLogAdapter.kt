package cc.shinichi.library.logit.ui

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cc.shinichi.library.logit.R

/**
 * @ProjectName: LogIt
 * @Package: cc.shinichi.library.logit.ui
 * @ClassName: AnalyzeLogAdapter
 * @Description: java类作用描述
 * @Author: Kirito qinglingou@gmail.com
 * @CreateDate: 2022/4/22 16:48
 */
class AnalyzeLogAdapter(var data: MutableList<String>) : RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(View.inflate(parent.context, R.layout.item_log, null))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val tvLog = holder.itemView.findViewById<TextView>(R.id.tvLog)
        val log = data[position]
        tvLog.text = log
        if ((log.length > 30)) {
            val substring = log.substring(0, 30)
            when {
                substring.contains("-DEBUG") -> {
                    tvLog.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.log_color_debug))
                }
                substring.contains("-ERROR") -> {
                    tvLog.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.log_color_error))
                }
                substring.contains("-INFO") -> {
                    tvLog.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.log_color_info))
                }
                substring.contains("-VERBOSE") -> {
                    tvLog.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.log_color_verbose))
                }
                substring.contains("-WARN") -> {
                    tvLog.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.log_color_warn))
                }
            }
        } else {
            tvLog.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.log_color_verbose))
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}