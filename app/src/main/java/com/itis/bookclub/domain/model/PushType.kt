package com.itis.bookclub.domain.model

sealed class PushType {
    data class Notification(val title: String, val message: String) : PushType()
    data class Silent(val payload: String) : PushType()
    data class Feature(val data: String) : PushType()
}