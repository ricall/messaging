package au.com.ricall.messaging.service

import au.com.ricall.messaging.model.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService {
    private val repository: MutableMap<UUID, UserDetails> = mutableMapOf()

    fun find(userIdentifier: UUID) = repository[userIdentifier]

    fun save(userDetails: UserDetails) {
        repository[userDetails.identifier] = userDetails
    }
}