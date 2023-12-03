package com.dev.quixabus.model

import android.os.Parcelable
import com.dev.quixabus.util.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comentario (
    var id: String = "",
    var texto: String = "",
    var data: String  = "",
    var usuarioId: String = ""
): Parcelable {
    init {
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}