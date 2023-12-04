package com.dev.quixabus.ui.activity

import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.dao.AulasDao
import com.dev.quixabus.databinding.ActivityCadastrarAulaBinding
import com.dev.quixabus.model.Aula
import com.dev.quixabus.util.TopBar

class CadastrarAulaActivity : AppCompatActivity(R.layout.activity_cadastrar_aula) {

    private var diaSelecionado: String = "Segunda"
    private val dao = AulasDao()
    private val binding by lazy {
        ActivityCadastrarAulaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configuraBotaoSalvar()
        configuraDropdownDiasDaSemana()
        TopBar().configura(supportFragmentManager, R.id.activity_cadastrar_aula_fragment_top_bar)
        setContentView(binding.root)
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityCadastrarAulaBotaoSalvar

        botaoSalvar.setOnClickListener {
            validarAula()
        }
    }

    private fun validarAula() {
        val campoNome = binding.activityCadastrarAulaNome
        val nome = campoNome.text.toString().trim()

        val campoProfessor = binding.activityCadastrarAulaProfessor
        val professor = campoProfessor.text.toString().trim()

        val campoTurma = binding.activityCadastrarAulaTurma
        val turma = campoTurma.text.toString().trim()

        val diaDaSemana = diaSelecionado.trim()

        val campoBloco = binding.activityCadastrarAulaBloco
        val bloco = campoBloco.text.toString().trim()

        val campoSala = binding.activityCadastrarAulaSala
        val sala = campoSala.text.toString().trim()

        val campoHorarioInicio = binding.activityCadastrarAulaHorarioinicio
        val horarioInicio = campoHorarioInicio.text.toString().trim()

        val campoHorarioFim = binding.activityCadastrarAulaHorariofim
        val horarioFim = campoHorarioFim.text.toString().trim()

        val aula = Aula(
            nome = nome,
            professor = professor,
            turma = turma,
            diaSemana = diaDaSemana,
            bloco = bloco,
            sala = sala,
            horarioInicio = horarioInicio,
            horarioFim = horarioFim
        )

        if(nome.isNotEmpty()
            && professor.isNotEmpty()
            && turma.isNotEmpty()
            && diaDaSemana.isNotEmpty()
            && bloco.isNotEmpty()
            && sala.isNotEmpty()
            && horarioInicio.isNotEmpty()
            && horarioFim.isNotEmpty()) {

            dao.salvar(aula) { sucesso ->
                if(sucesso) {
                    finish()
                    Toast.makeText(this,"Aula cadastrada!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Não foi possível cadastrar a Aula, tente novamente mais tarde.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }
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
}