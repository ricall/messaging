package au.com.ricall.messaging.service

import au.com.ricall.messaging.exception.MessageException
import au.com.ricall.messaging.model.Message
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageService(
    val userService: UserService,
    val emailService: EmailService,
) {
    val repository: MutableMap<UUID, Message> = mutableMapOf()

    fun find(messageIdentifier: UUID) = repository[messageIdentifier]

    fun save(message: Message): Message {
        val id = UUID.randomUUID()
        val savedMessage = Message(identifier = id, from = message.from, to = message.to, message = message.message)

        repository[id] = savedMessage

        sendEmailForMessage(savedMessage)
        return savedMessage
    }

    fun sendEmailForMessage(message: Message) {
        val recipient = userService.find(message.to) ?: throw MessageException("Unknown recipient ${message.to}")

        emailService.send(recipient.emailAddress, """
            |Hi ${recipient.firstName} ${recipient.lastName},
            |  A new message has been received.
            |   
            |You can view it [here](http://localhost:8080/messages/${message.identifier})
            |
            |Regards,
            |
            |MessageBot.
        """.trimMargin())
    }

    fun saveExisting(message: Message) {
        if (message.identifier != null) {
            repository[message.identifier] = message
        }
    }
}