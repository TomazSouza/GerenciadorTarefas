package tom.dev.com.taskmanagement.ui.home

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_backdrop.*
import kotlinx.android.synthetic.main.foldable_backdrop.*
import kotlinx.android.synthetic.main.main_backdrop.*
import tom.dev.com.taskmanagement.R
import tom.dev.com.taskmanagement.baseapp.BaseApp
import tom.dev.com.taskmanagement.di.component.DaggerHomeComponent
import tom.dev.com.taskmanagement.di.module.HomeModule
import tom.dev.com.taskmanagement.listeners.OnClickListenerTask
import tom.dev.com.taskmanagement.network.models.Tasks
import tom.dev.com.taskmanagement.recycler.MyAdapter
import tom.dev.com.taskmanagement.ui.login.LoginActivity
import tom.dev.com.taskmanagement.ui.tasks.TaskFormActivity
import tom.dev.com.taskmanagement.constants.TaskConstants
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), HomeContract.View, OnClickListenerTask {

    @Inject
    lateinit var mPresenterHome: HomeContract.Presenter

    lateinit var mBaseApp: BaseApp

    private lateinit var viewAdapter: MyAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backdrop)


        this.injectDependecy()
        this.mPresenterHome.attach(this)

        this.mBaseApp = applicationContext as BaseApp

        setSupportActionBar(home_toolbar)
        title = "Tarefas"

        backdrop_view
            .buildWithToolbar(home_toolbar) // set a toolbar in backdrop

        btn_home.setOnClickListener {
            val intent = Intent(this, TaskFormActivity::class.java)
            startActivityForResult(intent, TaskConstants.TASK_ACTIVITY_REQUEST_CODE)
        }

        btnExit.setOnClickListener {
            mBaseApp.destroySession()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        this.loadRecyclerView()
        this.loadTasks()

    }

    private fun loadRecyclerView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(this)

        recyclerview_task.apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onStop() {
        super.onStop()
        this.mPresenterHome.unsubscribe()
        this.showProgress(false)
        super.onStop()
    }

    private fun loadTasks() {
        this.mPresenterHome.getAllTasks(mBaseApp.getIdUser())
    }

    private fun injectDependecy() {
        val homeComponent = DaggerHomeComponent.builder()
            .homeModule(HomeModule()).build()
        homeComponent.inject(this)
    }

    override fun successGetAllTasks(tasks: List<Tasks>) {
        mBaseApp.setTasks(tasks)
        viewAdapter.setTasks(tasks)
    }

    override fun nothinTask() {
        this.loadRecyclerView()
//        this.viewAdapter.nothinTaskResult()
    }

    override fun failureGetTasks() {
        Toast.makeText(this, "Falha ao tentar buscar tarefas", Toast.LENGTH_LONG).show()
    }

    override fun failureDelete() {
        Toast.makeText(this, "Falha ao tentar excluir", Toast.LENGTH_LONG).show()
    }

    override fun successDelete() {
        this.loadTasks()
    }

    override fun onListClick(idTask: Int) {
        Toast.makeText(this, "ID Task $idTask", Toast.LENGTH_LONG).show()
//        val intent = Intent(this, TaskFormActivity::class.java)
//        intent.putExtra(TaskConstants.ID_TASK_KEY, idTask)
//        startActivity(intent)
    }

    override fun onDeleteClick(idTask: Int, userId: Int) {
        val dialog = messageDeleteTask(idTask, userId)
        dialog?.show()
    }

    private fun messageDeleteTask(idTask: Int, userId: Int): AlertDialog? {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
            .setMessage("Deseja realmente excluír?")
            .setTitle("Excluir Tarefa")
            .setIcon(R.drawable.ic_dialog_icon)
            .setPositiveButton(getString(R.string.text_btn_yes), object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    showProgress(true)
                    mPresenterHome.deleteTask(idTask, userId)
                    dialog?.dismiss()
                }
            })
            .setNegativeButton(getString(R.string.text_btn_not), object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.dismiss()
                }
            })
        return dialog.create()
    }


    override fun showProgress(show: Boolean) {
        if (show) {
            progressBarList.visibility = View.VISIBLE
        } else {
            progressBarList.visibility = View.GONE
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TaskConstants.TASK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (mBaseApp.getIdUser() != null && mBaseApp.getIdUser() != 0) {
                showProgress(true)
                this.mPresenterHome.getAllTasks(mBaseApp.getIdUser())
            }
        } else {
            viewAdapter.setTasks(mBaseApp.getTasks())
        }
    }


}
