package com.dev.quixabus.model

import android.os.Parcelable
import com.dev.quixabus.util.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class Aula (
    var id: String = "",
    var diaSemana: String = "",
    var nome: String = "",
    var professor: String = "",
    var bloco: String = "",
    var sala: String = "",
    var turma: String = "",
    var horarioInicio: String = "",
    var horarioFim: String = "",
): Parcelable {
    init {
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}