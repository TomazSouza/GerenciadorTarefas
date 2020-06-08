package tom.dev.com.taskmanagement.network.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)

data class User(
    val id_user: Int = 0,
    val name: String = "",
    val email: String = "",
    val password: String = ""
)

