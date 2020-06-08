package tom.dev.com.taskmanagement.recycler

import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import tom.dev.com.taskmanagement.R

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var mTextTaskName: AppCompatTextView
    var mTextProgress: AppCompatTextView
    var mTextPriority: AppCompatTextView
    var mTextStartDate: AppCompatTextView
    var mTextEndDate: AppCompatTextView
    var mTextNenhumaTarefa: AppCompatTextView
    var mLayTasks: ConstraintLayout
    var mLayTasksNot: ConstraintLayout
    var mBtnEditTask: AppCompatImageButton
    var mBtnDelelteTask: AppCompatImageButton

    init {

        this.mTextTaskName = itemView.findViewById(R.id.textTaskName)
        this.mTextProgress = itemView.findViewById(R.id.textProgress)
        this.mTextPriority = itemView.findViewById(R.id.textPriority)
        this.mTextStartDate = itemView.findViewById(R.id.textStartDate)
        this.mTextEndDate = itemView.findViewById(R.id.textEndDate)
        this.mLayTasks = itemView.findViewById(R.id.constraintLayTask)
        this.mLayTasksNot = itemView.findViewById(R.id.constraintLayNot)
        this.mBtnEditTask = itemView.findViewById(R.id.btnEditTask)
        this.mBtnDelelteTask = itemView.findViewById(R.id.btnDeleteTask)

        this.mTextNenhumaTarefa = itemView.findViewById(R.id.textNenhumaTarefa)
    }

}