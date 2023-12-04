package com.dev.quixabus.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Horario (
    var id: String = "",
    var horario: String = "",
    var quantidadeViagens: String = "",
    var responsavelOnibus: String = "",
    var embarque: Boolean = false,
    var desembarque: Boolean = false,
): Parcelable