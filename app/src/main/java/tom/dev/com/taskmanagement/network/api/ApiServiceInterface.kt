package tom.dev.com.taskmanagement.network.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.*
import tom.dev.com.taskmanagement.network.models.BaseResponse
import tom.dev.com.taskmanagement.network.models.Tasks
import tom.dev.com.taskmanagement.network.models.User
import tom.dev.com.taskmanagement.constants.TaskConstants

interface ApiServiceInterface {

    @POST("/user/save")
    fun registerUser(@Body user: User): Observable<BaseResponse>?

    @POST("/user/login")
    fun loginUser(@Body user: User): Observable<BaseResponse>?

    @POST("/task/save")
    fun createTask(@Body tasks: Tasks): Observable<BaseResponse>?

    @GET("/tasks/{user_id}")
    fun getAllTasks(@Path("user_id") user_id: Int): Observable<BaseResponse>?

    @DELETE("/task/delete/{id_task}/{user_id}")
    fun deleteTask(@Path("id_task") id_task: Int, @Path("user_id") user_id: Int): Observable<BaseResponse>?

    @PUT("/task/{id_task}/update")
    fun updateTask(@Path("id_task") id_task: Int, @Body tasks: Tasks): Observable<BaseResponse>?

    companion object Factory {

        fun create(): ApiServiceInterface {

            val mRetrofit = Retrofit.Builder()
                .baseUrl(TaskConstants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()

            return mRetrofit.create(ApiServiceInterface::class.java)
        }

    }

}