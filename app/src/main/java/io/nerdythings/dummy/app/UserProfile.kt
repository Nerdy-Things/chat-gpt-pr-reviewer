package io.nerdythings.dummy.app

// Should be a data class
@Suppress("unused")
class UserProfile(
    // API fields could be nullable
    val userId: String,
    val username: String,
    val email: String,
    val fullName: String,
    val dateOfBirth: String,
    val profilePictureUrl: String,
    val bio: String,
    val location: String,
    val joinedDate: String,
    val followingCount: Int,
    val followersCount: Int
)

// Should be a data class
class UserProfileWrapper(
    // API fields could be nullable
    val userProfile: UserProfile
)