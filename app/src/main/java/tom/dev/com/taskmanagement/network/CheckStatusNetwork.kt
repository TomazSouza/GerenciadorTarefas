package tom.dev.com.taskmanagement.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.greenrobot.eventbus.EventBus
import tom.dev.com.taskmanagement.utility.NetworkAvailable
import tom.dev.com.taskmanagement.utility.UpdateNetworkStatus

class CheckStatusNetwork : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val eventBus = EventBus.getDefault()
        eventBus.post(context?.let { NetworkAvailable.isAvailable(it) }?.let { UpdateNetworkStatus(it) })
    }

}