package au.com.ricall.messaging.controller

import au.com.ricall.messaging.model.Message
import au.com.ricall.messaging.service.MessageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*
import javax.validation.Valid

@RestController
class MessageController @Autowired constructor(val messageService: MessageService){
    val logger: Logger = LoggerFactory.getLogger(MessageController::class.java)

    @PostMapping(
        path = ["/messages"],
        consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun postMessage(@Valid @RequestBody message: Message): Mono<ResponseEntity<Message>> {
        logger.info("Received: $message")

        return Mono.just(ResponseEntity.ok(messageService.save(message)))
    }

    @GetMapping(
        path = ["/messages/{messageId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun getMessage(@PathVariable messageId: String): Mono<ResponseEntity<Message>> {
        logger.info("Searching for $messageId")

        val message = messageService.find(UUID.fromString(messageId))
        if (message == null) {
            return Mono.just(ResponseEntity.notFound().build())
        }
        return Mono.just(ResponseEntity.ok(message))
    }
}