package com.dev.quixabus.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FeedItem (
    val post: Post,
    val usuario: Usuario
): Parcelable
