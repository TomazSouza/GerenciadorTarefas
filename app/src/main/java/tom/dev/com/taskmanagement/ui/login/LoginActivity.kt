package tom.dev.com.taskmanagement.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import tom.dev.com.taskmanagement.R
import tom.dev.com.taskmanagement.baseapp.BaseApp
import tom.dev.com.taskmanagement.di.component.DaggerLoginComponent
import tom.dev.com.taskmanagement.di.module.LoginModule
import tom.dev.com.taskmanagement.network.models.User
import tom.dev.com.taskmanagement.ui.home.HomeActivity
import tom.dev.com.taskmanagement.ui.register.RegisterActivity
import tom.dev.com.taskmanagement.utility.NetworkAvailable
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginContract.View {

    @Inject
    lateinit var mPresenterImpl: LoginContract.Presenter
    lateinit var mBaseApp: BaseApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.injectDependency()
        this.mPresenterImpl.attach(this)
        this.mPresenterImpl.verifyApiOnline()
        mBaseApp = applicationContext as BaseApp

        btnLogin.setOnClickListener {
            if (NetworkAvailable.isAvailable(this)) {
                if (validateFields()) {
                    showProgress(true)
                    val user = User(email = editEmail.text.toString().trim(), password = editPassword.text.toString().trim())

                    this.mPresenterImpl.signInUser(user)
                }
            } else {
                Toast.makeText(this@LoginActivity, getString(R.string.text_not_connected), Toast.LENGTH_LONG).show()
            }
        }

        btnRegister.setOnClickListener { startRegister() }

    }

    private fun injectDependency() {
        val activityComponent = DaggerLoginComponent.builder()
            .loginModule(LoginModule(this))
            .build()
        activityComponent.inject(this)
    }

    fun startRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun validateFields(): Boolean {
        if (TextUtils.isEmpty(editEmail.text.toString().trim())) {

            textInputPassword.isErrorEnabled = false

            textInputLayoutEmail.isErrorEnabled = true
            textInputLayoutEmail.error = getString(R.string.text_email_empty)
            return false
        } else if (TextUtils.isEmpty(editPassword.text.toString().trim())) {

            textInputLayoutEmail.isErrorEnabled = false

            textInputPassword.isErrorEnabled = true
            textInputPassword.error = getString(R.string.text_passwd_empty)
            return false
        }

        textInputLayoutEmail.isErrorEnabled = false
        textInputPassword.isErrorEnabled = false

        return true
    }

    override fun onStop() {
        this.mPresenterImpl.unsubscribe()
        this.showProgress(false)
        super.onStop()
    }



    override fun successSignInUser(idUser: Int) {
        if (idUser != null) {
            mBaseApp.setIdUser(idUser)
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun manterConectado(baseApp: BaseApp) {
        if (baseApp.getIsChecked()) {
            showProgress(true)
            this.mPresenterImpl.signInUser(baseApp.getDataUser()!!)
        }
    }

    override fun failureSignInUser(message: String) {
        if (message == null) {
            Toast.makeText(this@LoginActivity, getString(R.string.error_nothing_user), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
            btnLogin.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            btnLogin.visibility = View.VISIBLE
        }

    }

}
