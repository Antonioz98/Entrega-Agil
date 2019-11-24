package com.antonio.entregaagil.ui.activity

import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.antonio.entregaagil.R
import com.antonio.entregaagil.modelo.Rota
import com.antonio.entregaagil.ui.adapter.list.AssinantesAdapter2
import com.antonio.entregaagil.ui.adapter.list.RotasAdapter2
import com.antonio.entregaagil.ui.viewmodel.RotasViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_maps.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var rotas: ArrayList<Rota>
    private var posicao = 0
    private val rotasViewModel by viewModel<RotasViewModel>()
    private val adapterAssinates: RotasAdapter2 by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        rotas = intent.getParcelableArrayListExtra<Rota>("rotas")
        posicao = intent.getIntExtra("posicao", 0)
        map_nav.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        map_nav.adapter = adapterAssinates
        adapterAssinates.clickListener = { _, posicao ->
            if (posicao != null) {
                this.posicao = posicao
                atualizaRotas()
            }
        }
        adapterAssinates.atualizaLista(rotas)
    }

    private fun atualizaRotas() {
        mMap.clear()
        rotasViewModel.getRota(rotas[posicao]).observe(this, Observer {
            val polylineOptions = PolylineOptions()
            it?.assinantes?.forEach { assinante ->
                val endereco = "${assinante.endereco} ${assinante.numero}, ${assinante.bairro}, Garça"
                val coordenada = pegaCoordenadaDoEndereco(endereco)
                if (coordenada != null) {
                    MarkerOptions().apply {
                        position(coordenada)
                        title("${assinante.endereco}, ${assinante.numero}")
                        if (assinante.complemento.isNotEmpty()) snippet(assinante.complemento)
                        mMap.addMarker(this)
                        polylineOptions.add(coordenada)
                    }
                } else {
                    naoConseguiuMarcar()
                }
            }
            polylineOptions.width(5f).color(Color.RED)
            mMap.addPolyline(polylineOptions)
        })
    }

    private fun naoConseguiuMarcar() {

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val recife = LatLng(-22.218275, -49.645796)
        val cameraPosition = CameraPosition.Builder().zoom(15f).target(recife).build()

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        atualizaRotas()
    }

    private fun pegaCoordenadaDoEndereco(endereco: String): LatLng? {
        try {
            val geocoder = Geocoder(this)
            val resultados = geocoder.getFromLocationName(endereco, 1)
            if (resultados.isNotEmpty()) {
                return LatLng(resultados[0].latitude, resultados[0].longitude)
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

}
