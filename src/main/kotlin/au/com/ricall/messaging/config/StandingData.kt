package au.com.ricall.messaging.config

import au.com.ricall.messaging.model.Message
import au.com.ricall.messaging.model.UserDetails
import au.com.ricall.messaging.service.MessageService
import au.com.ricall.messaging.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class StandingData {
    companion object {
        val USER_1 = UUID.fromString("f254910e-0c4c-405f-a97c-be6562424e69")
        val USER_2 = UUID.fromString("1deb20bf-d486-420f-b3f7-52a27c42268e")
    }

    @Bean
    fun init(userService: UserService, messageService: MessageService) = CommandLineRunner {
        listOf(
            UserDetails(identifier = USER_1, firstName = "John", lastName = "Smith", emailAddress = "john.smith@test.com"),
            UserDetails(identifier = USER_2, firstName = "Mary", lastName = "Jones", emailAddress = "mary.jones@test.com"),
        ).forEach(userService::save)
        listOf(
            Message(identifier = UUID.fromString("4308448d-ebc4-4941-b855-e3fd5b79f1b6"), from = USER_1, to = USER_2, message = "First Message"),
            Message(identifier = UUID.fromString("6d11be94-f2b1-4257-9b4c-02d2f01ef87b"), from = USER_1, to = USER_2, message = "Second Message"),
            Message(identifier = UUID.fromString("c40022a1-93d6-4ed9-824c-afc5b57ff2ae"), from = USER_2, to = USER_1, message = "Responding to First Message"),
            Message(identifier = UUID.fromString("f8b9524e-4abc-4bc3-bcfe-4d2492f3baad"), from = USER_2, to = USER_1, message = "Responding to Second Message"),
        ).forEach(messageService::saveExisting)
    }
}