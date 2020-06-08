package tom.dev.com.taskmanagement.ui.picker

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import tom.dev.com.taskmanagement.listeners.DateListener
import java.util.*


@SuppressLint("ValidFragment")
class DatePickerFragment @SuppressLint("ValidFragment") constructor(
    dateListener: DateListener,
    id: Int
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var dateListener: DateListener? = dateListener
    private val idClick = id

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            activity, this,
            year,
            month,
            day
        )
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.dateListener?.date(view, year, month, dayOfMonth, idClick)
    }

}