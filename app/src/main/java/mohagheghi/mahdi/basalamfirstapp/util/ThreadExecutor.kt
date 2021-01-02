package mohagheghi.mahdi.basalamfirstapp.util

import java.util.concurrent.Executor

class ThreadExecutor : Executor {
    override fun execute(command: Runnable?) {
        Thread(command).start()
    }
}