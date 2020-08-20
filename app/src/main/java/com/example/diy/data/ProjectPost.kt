package com.example.diy.data

import com.google.firebase.Timestamp

data class ProjectPost(
    var id: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    val title: String = "",
    val subHeading: String = "",
    val body: String = ""
)