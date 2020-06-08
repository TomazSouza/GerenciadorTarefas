package tom.dev.com.taskmanagement.network.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)

data class Tasks(
    val id_task: Int = 0,
    val user_id: Int = 0,
    val task: String = "",
    val priority: String = "",
    val progress: String = "",
    val start_date: String = "",
    val end_date: String = ""
)
