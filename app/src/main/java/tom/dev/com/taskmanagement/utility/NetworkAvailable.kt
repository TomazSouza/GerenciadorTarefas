package tom.dev.com.taskmanagement.utility

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log


object NetworkAvailable {

    fun isAvailable(context: Context): Boolean {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

            if (connectivityManager!!.activeNetworkInfo != null &&
                connectivityManager.activeNetworkInfo.isConnected
            ) {
                return true
            }

        } catch (e: NullPointerException) {
            Log.ERROR
        }
        return false
    }

}