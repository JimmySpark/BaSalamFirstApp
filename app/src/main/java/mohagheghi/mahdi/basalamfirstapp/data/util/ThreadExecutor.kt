package mohagheghi.mahdi.basalamfirstapp.data.util

import java.util.concurrent.Executor

class ThreadExecutor : Executor {
    override fun execute(command: Runnable?) {
        Thread(command).start()
    }
}