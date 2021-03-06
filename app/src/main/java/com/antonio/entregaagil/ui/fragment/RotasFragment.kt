package com.antonio.entregaagil.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearSmoothScroller
import com.antonio.entregaagil.R
import com.antonio.entregaagil.constante.ROTA_TAG
import com.antonio.entregaagil.modelo.Rota
import com.antonio.entregaagil.ui.activity.MapsActivity
import com.antonio.entregaagil.ui.adapter.list.RotasAdapter
import com.antonio.entregaagil.ui.viewmodel.RotasViewModel
import kotlinx.android.synthetic.main.fragment_rotas.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class RotasFragment : Fragment() {

    private val adapter: RotasAdapter by inject()
    private val viewModel: RotasViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_rotas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confguraFAB()
        configuraAdapter()
        viewModel.atualizaListaDeRotas(adapter)
    }

    private fun configuraAdapter() {
        fragment_rotas_recyclerView.adapter = adapter
        adapter.clickListener = { rota, posicao ->
            if (posicao != null) {
                val destination =
                    "destination=${Uri.encode("${rota.assinantes[0].endereco} ${rota.assinantes[0].numero}, ${rota.assinantes[0].bairro}, Garça")}"

                val mode = "travelmode=driving"
                var wayPoints = "waypoints="
                rota.assinantes.forEachIndexed { index, assinante ->
                    if (index != 0) {
                        val endereco = Uri.encode("${assinante.endereco} ${assinante.numero}, ${assinante.bairro}, Garça")
                        wayPoints = "$wayPoints$endereco|"
                    }
                }

                val navigation = "https://www.google.com/maps/dir/?api=1&$destination&$mode&$wayPoints"
                val navigationUri = Uri.parse(navigation)
                val intent = Intent(Intent.ACTION_VIEW, navigationUri)

                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
//                val intent = Intent(context, MapsActivity::class.java)
//                intent.putParcelableArrayListExtra("rotas", ArrayList(adapter.rotas))
//                intent.putExtra("posicao", posicao)
//                startActivity(intent)
            } else {
                abreDetalheDaRota(rota)
            }
        }
    }

    private fun abreDetalheDaRota(rota: Rota) {
        val bundle = Bundle()
        bundle.putParcelable(ROTA_TAG, rota)
        fragment_rotas_recyclerView.findNavController().navigate(R.id.rotas_to_navigation_detalhes_rota, bundle)
    }

    private fun confguraFAB() {
        fragment_rotas_fab.setOnClickListener {
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle(getString(R.string.descricao_nova_rota))
            val input = EditText(context)
            input.inputType = InputType.TYPE_CLASS_TEXT

            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            input.layoutParams = lp
            builder.setView(input)
            builder.setPositiveButton(getString(R.string.cadastrar)) { dialog, which ->
                cadastraRota(input.text.toString())
                scrollToFinal()
            }
            builder.setNegativeButton(getString(R.string.cancelar)) { dialog, which -> dialog.cancel() }

            builder.show()
        }
    }


    private fun cadastraRota(descricao: String) {
        val rota = Rota()
        rota.descricao = descricao
        viewModel.atualizaRota(rota)
    }

    private fun scrollToFinal() {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = adapter.itemCount
        fragment_rotas_recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
    }
}
