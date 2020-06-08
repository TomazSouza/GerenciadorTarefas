package tom.dev.com.taskmanagement.listeners

import android.widget.DatePicker

interface DateListener {
    fun date(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int, id: Int)
}