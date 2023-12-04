package com.dev.quixabus.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Parada (
    var id: String = "",
    var endereco: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var tipo: String = ""
): Parcelable