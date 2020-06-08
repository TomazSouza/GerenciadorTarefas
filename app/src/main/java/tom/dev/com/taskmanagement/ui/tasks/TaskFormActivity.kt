package tom.dev.com.taskmanagement.ui.tasks

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_task_form.*
import tom.dev.com.taskmanagement.R
import tom.dev.com.taskmanagement.baseapp.BaseApp
import tom.dev.com.taskmanagement.constants.TaskConstants
import tom.dev.com.taskmanagement.di.component.DaggerTaskFormComponent
import tom.dev.com.taskmanagement.di.module.TaskFormModule
import tom.dev.com.taskmanagement.listeners.DateListener
import tom.dev.com.taskmanagement.network.models.Tasks
import tom.dev.com.taskmanagement.ui.home.HomeActivity
import tom.dev.com.taskmanagement.ui.login.LoginActivity
import tom.dev.com.taskmanagement.ui.picker.DatePickerFragment
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class TaskFormActivity : AppCompatActivity(),
    AdapterView.OnItemSelectedListener,
    DateListener, TaskFormContract.View {

    @Inject
    lateinit var mPresenterImpl: TaskFormContract.Presenter
    lateinit var mBaseApp: BaseApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)
        this.injectDependency()

        mBaseApp = applicationContext as BaseApp

        ButterKnife.bind(this)

        this.mPresenterImpl.attach(this)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.statusTarefa,
            android.R.layout.simple_spinner_dropdown_item
        )

        spinnerStatusTask.adapter = adapter
        spinnerStatusTask.onItemSelectedListener = this

        setSupportActionBar(toolbar_task)
        supportActionBar?.title = getString(R.string.title_register_task)

        editStartDate.setOnClickListener {
            val newFragment = DatePickerFragment(this, 1)
            newFragment.show(supportFragmentManager, "editStartDate")
            textInputLayStartDate.isErrorEnabled = false
        }

        editEndDate.setOnClickListener {
            val newFragment = DatePickerFragment(this, 2)
            newFragment.show(supportFragmentManager, "editEndDate")
            textInputLayEndDate.isErrorEnabled = false
        }

        editTaskName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textInputLayTask.isErrorEnabled = false
            }
        })

        if (intent.hasExtra(TaskConstants.ID_TASK_KEY)) {
            val idTask = intent.getIntExtra(TaskConstants.ID_TASK_KEY, 0)
            Toast.makeText(baseContext, "$idTask", Toast.LENGTH_SHORT).show()
            loadEditTask()
        }

    }

    override fun date(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int, id: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt-br", "BR"))
        val data = dateFormat.format(calendar.time)

        if (id == 1) {
            editStartDate.setText(data)
        } else {
            editEndDate.setText(data)
        }
    }

    private fun loadEditTask() {

    }

    override fun successUpdate() {

    }

    override fun failureUpdate() {

    }

    @OnClick(R.id.btnSaveTask)
    fun saveNewTask() {
        val taskName = editTaskName.text.toString().trim()
        val endDate = editEndDate.text.toString().trim()
        val startDate = editStartDate.text.toString().trim()

        if (validateTaskForm(taskName, startDate, endDate)) {
            showProgress(true)

            val priority: String = when {
                radionBtnHigh.isChecked -> sPriority[0]
                radionBtnAverage.isChecked -> sPriority[1]
                else -> sPriority[2]
            }

            val progress = sSelectedProgress
            this.mPresenterImpl.newTask(
                Tasks(
                    user_id = mBaseApp.getIdUser(),
                    task = taskName,
                    priority = priority,
                    progress = progress,
                    start_date = startDate,
                    end_date = endDate
                )
            )

        }
    }

    override fun onBackPressed() {
        mBaseApp.destroySession()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    @OnClick(R.id.btnCancel)
    fun cancel() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun successCreate() {
        setResult(Activity.RESULT_OK)
        Toast.makeText(this, "Tarefa salva com sucesso!", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun failureCreate() {
        Toast.makeText(this@TaskFormActivity, "Falha ao tentar salvar tarefa!",
            Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        this.mPresenterImpl.unsubscribe()
        this.showProgress(false)
        super.onStop()
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            btnCancel.isEnabled = false
            btnSaveTask.isEnabled = false
            progressBarTask.visibility = View.VISIBLE
        } else {
            btnSaveTask.isEnabled = true
            btnCancel.isEnabled = true
            progressBarTask.visibility = View.GONE
        }
    }

    private fun injectDependency() {
        val taskFormComponet = DaggerTaskFormComponent.builder()
            .taskFormModule(TaskFormModule())
            .build()
        taskFormComponet.inject(this)
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val textColor: TextView? = view as? TextView
        val listProgress = resources.getStringArray(R.array.statusTarefa)

        if (position == 0) {
            textColor?.setTextColor(Color.GRAY)
        } else {
            sSelectedProgress = listProgress[position]
        }

    }

    private fun validateTaskForm(taskName: String, startDate: String, endDate: String): Boolean {

        if (TextUtils.isEmpty(taskName)) {
            textInputLayTask.isErrorEnabled = true
            textInputLayTask.error = getString(R.string.text_task)
            return false
        } else if (TextUtils.isEmpty(endDate)) {
            textInputLayTask.isErrorEnabled = false
            textInputLayEndDate.isErrorEnabled = true
            textInputLayEndDate.error = getString(R.string.end_date)
            return false
        } else if (TextUtils.isEmpty(startDate)) {
            textInputLayTask.isErrorEnabled = false
            textInputLayStartDate.isErrorEnabled = true
            textInputLayStartDate.error = getString(R.string.start_date)
            return false
        } else if (spinnerStatusTask.selectedItem.toString().trim()
                .equals("Selecione", ignoreCase = true)
        ) {
            textInputLayTask.isErrorEnabled = false
            val selected: TextView = spinnerStatusTask.selectedView as TextView
            selected.setTextColor(Color.RED)
            return false
        }

        val validadeRadio =
            radionBtnHigh.isChecked || radionBtnLow.isChecked || radionBtnAverage.isChecked

        if (validadeRadio) {
            return true
        } else {
            Toast.makeText(
                this@TaskFormActivity,
                getString(R.string.text_select_priority),
                Toast.LENGTH_SHORT
            ).show()
        }
        textInputLayTask.isErrorEnabled = false

        return false
    }

    companion object {

        val sPriority = arrayListOf("Alta", "Média", "Baixa")
        lateinit var sSelectedProgress: String
    }


}
