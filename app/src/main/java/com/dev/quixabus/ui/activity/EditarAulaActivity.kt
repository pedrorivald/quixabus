package com.dev.quixabus.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev.quixabus.R
import com.dev.quixabus.dao.AulasDao
import com.dev.quixabus.databinding.ActivityEditarAulaBinding
import com.dev.quixabus.model.Aula
import com.dev.quixabus.util.FirebaseHelper
import com.dev.quixabus.util.TopBar

class EditarAulaActivity : AppCompatActivity(R.layout.activity_editar_aula) {

    private lateinit var aulaId: String
    private var diaSelecionado: String = "Segunda"
    private val dao = AulasDao()
    private lateinit var aula: Aula
    private val binding by lazy {
        ActivityEditarAulaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        aulaId = intent.getStringExtra("aulaId")!!
        buscaAula()

        configuraBotaoSalvar()
        configuraDropdownDiasDaSemana()
        TopBar().configura(supportFragmentManager, R.id.activity_editar_aula_fragment_top_bar)

        setContentView(binding.root)
    }

    private fun buscaAula() {
        dao.buscarPorId(aulaId, FirebaseHelper.getIdUser()?:"") { aula ->
            if(aula != null) {
                this.aula = Aula(
                    id = aula.id,
                    diaSemana = aula.diaSemana,
                    nome = aula.nome,
                    professor = aula.professor,
                    bloco = aula.bloco,
                    sala = aula.sala,
                    turma = aula.turma,
                    horarioInicio = aula.horarioInicio,
                    horarioFim = aula.horarioFim
                )

                configuraAula(this.aula)
            } else {
                Toast.makeText(this, "Não foi possível obter a aula.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityEditarAulaBotaoSalvar

        botaoSalvar.setOnClickListener {
            validarAula()
        }
    }

    private fun configuraAula(aula: Aula) {
        binding.activityEditarAulaNome.setText(aula.nome)
        binding.activityEditarAulaProfessor.setText(aula.professor)
        binding.activityEditarAulaTurma.setText(aula.turma)

        diaSelecionado = aula.diaSemana
        (binding.activityEditarAulaSemana.editText as AutoCompleteTextView).setText(diaSelecionado, false)

        binding.activityEditarAulaBloco.setText(aula.bloco)
        binding.activityEditarAulaSala.setText(aula.sala)
        binding.activityEditarAulaHorarioinicio.setText(aula.horarioInicio)
        binding.activityEditarAulaHorariofim.setText(aula.horarioFim)
    }

    private fun validarAula() {
        val campoNome = binding.activityEditarAulaNome
        val nome = campoNome.text.toString().trim()

        val campoProfessor = binding.activityEditarAulaProfessor
        val professor = campoProfessor.text.toString().trim()

        val campoTurma = binding.activityEditarAulaTurma
        val turma = campoTurma.text.toString().trim()

        val diaDaSemana = diaSelecionado.trim()

        val campoBloco = binding.activityEditarAulaBloco
        val bloco = campoBloco.text.toString().trim()

        val campoSala = binding.activityEditarAulaSala
        val sala = campoSala.text.toString().trim()

        val campoHorarioInicio = binding.activityEditarAulaHorarioinicio
        val horarioInicio = campoHorarioInicio.text.toString().trim()

        val campoHorarioFim = binding.activityEditarAulaHorariofim
        val horarioFim = campoHorarioFim.text.toString().trim()

        if(nome.isNotEmpty()
            && professor.isNotEmpty()
            && turma.isNotEmpty()
            && diaDaSemana.isNotEmpty()
            && bloco.isNotEmpty()
            && sala.isNotEmpty()
            && horarioInicio.isNotEmpty()
            && horarioFim.isNotEmpty()) {

            dao.atualizar(aulaId,
                FirebaseHelper.getIdUser()?:"",
                diaDaSemana,
                nome,
                professor,
                bloco,
                sala,
                turma,
                horarioInicio,
                horarioFim) { sucesso ->
                    hideKeyboard()

                    if(sucesso) {
                        finish()
                        Toast.makeText(this, "Aula editada com sucesso!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Não foi possível editar a aula, tente novamente.", Toast.LENGTH_SHORT).show()
                    }
            }

        } else {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken , 0)
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
}