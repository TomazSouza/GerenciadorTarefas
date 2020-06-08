package tom.dev.com.taskmanagement.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tom.dev.com.taskmanagement.R
import tom.dev.com.taskmanagement.listeners.OnClickListenerTask
import tom.dev.com.taskmanagement.network.models.Tasks

class MyAdapter(onClickListener: OnClickListenerTask) : RecyclerView.Adapter<ViewHolder>() {

    private var tasks: List<Tasks> = ArrayList()
    private val mNothinTask = arrayListOf("Nenhuma tarefa cadastrada")
    private var mOnClickListenerTask = onClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (tasks.size > 0) tasks.size else  mNothinTask.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (tasks.size > 0 && tasks.isNotEmpty()) {
            showWidgets(holder)
            holder.mTextTaskName.text = tasks[position].task
            holder.mTextPriority.text = tasks[position].priority
            holder.mTextProgress.text = tasks[position].progress
            holder.mTextStartDate.text = tasks[position].start_date
            holder.mTextEndDate.text = tasks[position].end_date

            holder.mBtnEditTask.setOnClickListener { v -> this.mOnClickListenerTask.onListClick(tasks[position].id_task) }
            holder.mBtnDelelteTask.setOnClickListener { v ->
                this.mOnClickListenerTask.onDeleteClick(tasks[position].id_task, tasks[position].user_id)
            }
        } else {
            holder.mLayTasksNot.visibility = View.VISIBLE
            holder.mLayTasks.visibility = View.GONE
            holder.mTextNenhumaTarefa.text = mNothinTask[position]
        }

    }

    private fun showWidgets(holder: ViewHolder) {
        holder.mLayTasksNot.visibility = View.GONE
        holder.mLayTasks.visibility = View.VISIBLE
    }

    fun setTasks(tasks: List<Tasks>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    fun nothinTaskResult() {
        mNothinTask[0] ="Nenhuma tarefa cadastrada"
        notifyDataSetChanged()
    }

}