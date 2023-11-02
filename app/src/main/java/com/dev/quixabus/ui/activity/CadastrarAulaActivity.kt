package com.dev.quixabus.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.dev.quixabus.R
import com.dev.quixabus.model.DiaSemana
import android.widget.AdapterView.OnItemClickListener
import com.dev.quixabus.dao.AulasDao
import com.dev.quixabus.databinding.ActivityCadastrarAulaBinding
import com.dev.quixabus.model.Aula

class CadastrarAulaActivity : AppCompatActivity(R.layout.activity_cadastrar_aula) {

    private var diaSelecionado: String? = null
    private val dao = AulasDao()
    private val binding by lazy {
        ActivityCadastrarAulaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraBotaoSalvar()
        configuraDropdownDiasDaSemana()
        setContentView(binding.root)
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityCadastrarAulaBotaoSalvar

        botaoSalvar.setOnClickListener {
            val aula = criarAula()
            dao.adicionar(aula)
            finish()
        }
    }

    private fun criarAula(): Aula {
        val campoNome = binding.activityCadastrarAulaNome
        val nome = campoNome.text.toString()

        val campoProfessor = binding.activityCadastrarAulaProfessor
        val professor = campoProfessor.text.toString()

        val campoTurma = binding.activityCadastrarAulaTurma
        val turma = campoTurma.text.toString()

        val diaDaSemana = diaSemanaSelecionado(diaSelecionado)

        val campoBloco = binding.activityCadastrarAulaBloco
        val bloco = campoBloco.text.toString()

        val campoSala = binding.activityCadastrarAulaSala
        val sala = campoSala.text.toString()

        val campoHorarioInicio = binding.activityCadastrarAulaHorarioinicio
        val horarioInicio = campoHorarioInicio.text.toString()

        val campoHorarioFim = binding.activityCadastrarAulaHorariofim
        val horarioFim = campoHorarioFim.text.toString()

        return Aula(
            id = (0..9999).random(),
            nome = nome,
            professor = professor,
            turma = turma,
            diaSemana = diaDaSemana,
            bloco = bloco,
            sala = sala,
            horarioInicio = horarioInicio,
            horarioFim = horarioFim
        )
    }

    private fun configuraDropdownDiasDaSemana() {
        val diasDaSemana = listOf("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado")
        val diaDaSemanaEditText = binding.activityCadastrarAulaSemana.editText as? AutoCompleteTextView
        val adapterDiasDaSemana = ArrayAdapter(this, R.layout.dias_da_semana, diasDaSemana)

        diaDaSemanaEditText?.setAdapter(adapterDiasDaSemana)
        diaDaSemanaEditText?.setText("Segunda", false)

        diaDaSemanaEditText?.onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                diaSelecionado = adapterDiasDaSemana.getItem(position).toString()
            }
    }

    private fun diaSemanaSelecionado(diaText: String?): DiaSemana {
        return when (diaText) {
            "Domingo" -> DiaSemana.DOMINGO
            "Segunda" -> DiaSemana.SEGUNDA
            "Terça" -> DiaSemana.TERCA
            "Quarta" -> DiaSemana.QUARTA
            "Quinta" -> DiaSemana.QUINTA
            "Sexta" -> DiaSemana.SEXTA
            "Sábado" -> DiaSemana.SABADO
            else -> {
                DiaSemana.SEGUNDA
            }
        }
    }
}