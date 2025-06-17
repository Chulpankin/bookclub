package com.itis.bookclub.domain.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class CrashlyticsService @Inject constructor(
    private val crashlytics: FirebaseCrashlytics
) {

    fun logScreenEvent(event: ScreenEvent, customKey: String) {

        crashlytics.setCustomKey("user_id", customKey)
        crashlytics.log(event.key)
    }
}

enum class ScreenEvent(val key: String) {
    SIGN_IN("launch_sign_in"),
    SIGN_UP("launch_sign_up"),
    MAIN("launch_main"),
    DETAILS("launch_details");
}
