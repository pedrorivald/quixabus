package com.dev.quixabus.model

import android.os.Parcelable
import com.dev.quixabus.util.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post (
    var id: String = "",
    var texto: String = "",
    var data: String  = ""
): Parcelable {
    init {
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}