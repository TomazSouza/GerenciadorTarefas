package tom.dev.com.taskmanagement.network.models

import com.google.gson.Gson

data class DetailsViewModel(val tasks: List<Tasks>) {
    fun toJosn(): String {
        return Gson().toJson(this)
    }
}