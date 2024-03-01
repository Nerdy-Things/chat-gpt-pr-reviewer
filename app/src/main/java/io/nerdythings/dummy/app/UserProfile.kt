package io.nerdythings.dummy.app

@Suppress("unused")
class UserProfile(
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

class UserProfileWrapper(
    val userProfile: UserProfile
)