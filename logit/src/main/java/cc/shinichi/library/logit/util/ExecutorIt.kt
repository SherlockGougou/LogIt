package cc.shinichi.library.logit.util

import java.util.concurrent.Executors

/**
 * @ProjectName: LogIt
 * @Package: cc.shinichi.library.logit.util
 * @ClassName: ExecutorIt
 * @Description: java类作用描述
 * @Author: Kirito qinglingou@gmail.com
 * @CreateDate: 2022/4/22 15:01
 */
class ExecutorIt {

    private val executorService = Executors.newSingleThreadExecutor()

    companion object {
        fun execute(runnable: Runnable) {
            ExecutorIt().executorService.execute(runnable)
        }
    }
}