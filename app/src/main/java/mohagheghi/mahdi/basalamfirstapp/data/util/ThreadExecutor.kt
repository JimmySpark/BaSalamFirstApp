package mohagheghi.mahdi.basalamfirstapp.data.util

import java.util.concurrent.Executor
import javax.inject.Inject

class ThreadExecutor @Inject constructor() : Executor {
    override fun execute(command: Runnable?) {
        Thread(command).start()
    }
}