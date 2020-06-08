package tom.dev.com.taskmanagement.network.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)

class BaseResponse {

    @JsonProperty("id_user")
    var id_user: Int? = null

    @JsonProperty("error")
    var error: Boolean = false

    @JsonProperty("message")
    var message: String? = null

    @JsonProperty("tasks")
    var tasks: List<Tasks>? = null

    override fun toString(): String {
        return "BaseResponse(id_user=$id_user, error=$error, message=$message, tasks=$tasks)"
    }

}