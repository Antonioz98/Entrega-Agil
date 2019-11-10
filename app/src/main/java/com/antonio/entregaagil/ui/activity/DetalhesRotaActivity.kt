package com.antonio.entregaagil.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Rota
import com.antonio.entregaagil.ui.adapter.list.AssinantesAdapter
import kotlinx.android.synthetic.main.detalhes_rota.*
import org.koin.android.ext.android.inject

const val ROTA_TAG = "ROTA_TAG"
const val ROTA_POSICAO = "ROTA_POSICAO"
const val DELETAR_ROTA = "deletar"

class DetalhesRotaActivity : AppCompatActivity() {

    private val adapter: AssinantesAdapter by inject()
    private var rota = Rota()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalhes_rota)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        rota = intent.getParcelableExtra(ROTA_TAG)
        title = rota.descricao
        configuraAdapter()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_detalhes_rota, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_deletar -> deletar()
            R.id.menu_editar -> alteraDescricao()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun alteraDescricao() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Altera descricao da rota")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setText(rota.descricao)

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        input.layoutParams = lp
        builder.setView(input)
        builder.setPositiveButton("Alterar") { dialog, which ->
            rota.descricao = (input.text.toString())
        }
        builder.setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }

        builder.show()
    }

    private fun configuraAdapter() {
        detalhes_rota_assinantes_recyclerView.adapter = adapter
        val assinantes = rota.assinantes
        adapter.atualizaLista(assinantes)
        adapter.clickListener = { assinante, posicao ->
            //            abreFormularioPreenchido(assinante, posicao)
        }
    }

    private fun deletar() {
        val posicao = intent.getIntExtra(ROTA_POSICAO, 0)
        val intent = Intent()
        intent.putExtra(ROTA_TAG, rota)
        intent.putExtra(ROTA_POSICAO, posicao)
        intent.putExtra(DELETAR_ROTA, true)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}