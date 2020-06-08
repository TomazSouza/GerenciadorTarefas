package tom.dev.com.taskmanagement.listeners

interface OnClickListenerTask {
    fun onListClick(idTask: Int)
    fun onDeleteClick(idTask: Int, userId: Int)
}