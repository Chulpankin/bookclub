package com.itis.bookclub.domain.utils

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigService @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig
) {
    fun isBookDetailsEnabled(): Boolean {
        return remoteConfig.getBoolean("bookdetails")
    }
}
