package au.com.ricall.messaging.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class EmailService {
    val logger: Logger = LoggerFactory.getLogger(EmailService::class.java)

    fun send(emailAddress: String, body: String) {
        Mono.just(emailAddress)
            .delayElement(Duration.ofMillis(1000))
            .doOnNext { email -> logger.info("Sent email '${body}' to '${email}'") }
            .subscribe()
    }
}