package tom.dev.com.taskmanagement.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*
import tom.dev.com.taskmanagement.R
import tom.dev.com.taskmanagement.baseapp.BaseApp
import tom.dev.com.taskmanagement.di.component.DaggerRegisterComponent
import tom.dev.com.taskmanagement.di.module.RegisterModule
import tom.dev.com.taskmanagement.network.models.User
import tom.dev.com.taskmanagement.ui.login.LoginActivity
import tom.dev.com.taskmanagement.ui.tasks.TaskFormActivity
import tom.dev.com.taskmanagement.utility.NetworkAvailable
import javax.inject.Inject

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    @Inject
    lateinit var mPresenterImpl: RegisterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        this.dependencyInject()
        this.mPresenterImpl.attach(this)

        btnNext.setOnClickListener {
            createNewUser()
        }
    }


    fun createNewUser() {

        val userName = editTextName.text.toString().trim()
        val userEmail = editTextEmail.text.toString().trim()
        val userPasswd = editTextPassword.text.toString().trim()

        if (NetworkAvailable.isAvailable(this)) {

            if (validateInputData(userName, userEmail, userPasswd)) {
                showProgress(true)
                this.mPresenterImpl.newUser(User(name = userName, email = userEmail, password = userPasswd))
            }

        } else {
            Toast.makeText(this@RegisterActivity, getString(R.string.text_not_connected), Toast.LENGTH_LONG).show()
        }

    }

    override fun onStop() {
        this.showProgress(false)
        this.mPresenterImpl.unsubscribe()
        super.onStop()
    }

    private fun validateInputData(userName: String, userEmail: String, userPasswd: String): Boolean {

        when {
            TextUtils.isEmpty(userName) -> {
                textInputLayPassword.isErrorEnabled = false
                textInputLayEmail.isErrorEnabled = false
                textInputLayName.isErrorEnabled = true
                textInputLayName.error = getString(R.string.text_error_name)
                return false
            }
            TextUtils.isEmpty(userEmail) -> {
                textInputLayPassword.isErrorEnabled = false
                textInputLayEmail.isErrorEnabled = true
                textInputLayName.isErrorEnabled = false
                textInputLayEmail.error = getString(R.string.text_error_email)
                return false
            }
            TextUtils.isEmpty(userPasswd) -> {
                textInputLayPassword.isErrorEnabled = true
                textInputLayEmail.isErrorEnabled = false
                textInputLayName.isErrorEnabled = false
                textInputLayPassword.error = getString(R.string.text_error_passwd)
                return false
            }
            else -> {
                textInputLayPassword.isErrorEnabled = false
                return true
            }
        }

    }


    private fun dependencyInject() {
        val registerComponent = DaggerRegisterComponent.builder()
            .registerModule(RegisterModule(this))
            .build()
        registerComponent.inject(this)
    }

    override fun successCreate(idUser: Int) {
        val baseApp = applicationContext as BaseApp
        baseApp.setIdUser(idUser)
        startActivity(Intent(this, TaskFormActivity::class.java))
        finish()
    }

    override fun failureCreate() {
        Snackbar.make(registerAcitivity, getString(R.string.text_error_save), Snackbar.LENGTH_LONG).show()
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progressBarRegister.visibility = View.VISIBLE
            btnNext.visibility = View.GONE
        } else {
            progressBarRegister.visibility = View.GONE
            btnNext.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}
