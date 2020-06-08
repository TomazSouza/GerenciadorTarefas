package tom.dev.com.taskmanagement.refactor

interface ContractResponse<R> {
    fun onSuccess(respose: R)
    fun onFailure(throwable: Throwable)
}