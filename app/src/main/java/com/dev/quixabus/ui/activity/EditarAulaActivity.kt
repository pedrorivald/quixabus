package com.dev.quixabus.ui.activity

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.dao.AulasDao
import com.dev.quixabus.databinding.ActivityEditarAulaBinding
import com.dev.quixabus.model.Aula
import com.dev.quixabus.model.DiaSemana
import com.dev.quixabus.util.TopBar

class EditarAulaActivity : AppCompatActivity(R.layout.activity_editar_aula) {

    private var id: Int? = null
    private var diaSelecionado: String? = null
    private val dao = AulasDao()
    private var aula: Aula? = null
    private val binding by lazy {
        ActivityEditarAulaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dados = intent.extras
        id = dados?.getInt("id")

        if (id != null) {
            aula = dao.buscaPorId(id!!)
        }

        if (aula != null) {
            configuraAula(aula!!)
        }

        configuraBotaoSalvar()
        configuraDropdownDiasDaSemana()
        TopBar().configura(supportFragmentManager, R.id.activity_editar_aula_fragment_top_bar)

        setContentView(binding.root)
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityEditarAulaBotaoSalvar

        botaoSalvar.setOnClickListener {
            val aula = criarAula()
            dao.atualizar(aula)
            finish()
        }
    }

    private fun configuraAula(aula: Aula) {
        binding.activityEditarAulaNome.setText(aula.nome)
        binding.activityEditarAulaProfessor.setText(aula.professor)
        binding.activityEditarAulaTurma.setText(aula.turma)

        diaSelecionado = diaSemanaText(aula.diaSemana)
        (binding.activityEditarAulaSemana.editText as AutoCompleteTextView).setText(diaSelecionado, false)

        binding.activityEditarAulaBloco.setText(aula.bloco)
        binding.activityEditarAulaSala.setText(aula.sala)
        binding.activityEditarAulaHorarioinicio.setText(aula.horarioInicio)
        binding.activityEditarAulaHorariofim.setText(aula.horarioFim)
    }

    private fun criarAula(): Aula {
        val campoNome = binding.activityEditarAulaNome
        val nome = campoNome.text.toString()

        val campoProfessor = binding.activityEditarAulaProfessor
        val professor = campoProfessor.text.toString()

        val campoTurma = binding.activityEditarAulaTurma
        val turma = campoTurma.text.toString()

        val diaDaSemana = diaSemanaSelecionado(diaSelecionado)

        val campoBloco = binding.activityEditarAulaBloco
        val bloco = campoBloco.text.toString()

        val campoSala = binding.activityEditarAulaSala
        val sala = campoSala.text.toString()

        val campoHorarioInicio = binding.activityEditarAulaHorarioinicio
        val horarioInicio = campoHorarioInicio.text.toString()

        val campoHorarioFim = binding.activityEditarAulaHorariofim
        val horarioFim = campoHorarioFim.text.toString()

        return Aula(
            id = aula!!.id,
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
        val diaDaSemanaEditText = binding.activityEditarAulaSemana.editText as? AutoCompleteTextView
        val adapterDiasDaSemana = ArrayAdapter(this, R.layout.dias_da_semana, diasDaSemana)

        diaDaSemanaEditText?.setAdapter(adapterDiasDaSemana)

        diaDaSemanaEditText?.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id ->
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

    private fun diaSemanaText(dia: DiaSemana): String {
        return when (dia) {
            DiaSemana.DOMINGO -> "Domingo"
            DiaSemana.SEGUNDA -> "Segunda"
            DiaSemana.TERCA -> "Terça"
            DiaSemana.QUARTA -> "Quarta"
            DiaSemana.QUINTA -> "Quinta"
            DiaSemana.SEXTA -> "Sexta"
            DiaSemana.SABADO -> "Sábado"
            else -> {
                "Segunda"
            }
        }
    }
}