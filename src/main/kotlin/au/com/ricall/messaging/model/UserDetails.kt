package au.com.ricall.messaging.model

import java.util.*

data class UserDetails(
    val identifier: UUID,
    val firstName: String,
    val lastName: String,
    val emailAddress: String,
)