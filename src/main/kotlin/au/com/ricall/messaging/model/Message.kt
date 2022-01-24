package au.com.ricall.messaging.model

import java.util.*
import javax.validation.constraints.Size
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class Message(
    val identifier: UUID? = null,
    val from: UUID,
    val to: UUID,
    @field:Size(min = 1, max = 4000)
    val message: String,
) {
    companion object {
        val UNDEFINED: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")
    }
    constructor(): this(from = UNDEFINED, to = UNDEFINED, message = "")
}