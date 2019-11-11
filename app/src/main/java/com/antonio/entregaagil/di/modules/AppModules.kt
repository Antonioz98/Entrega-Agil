package com.antonio.entregaagil.di.modules

import android.content.Context
import com.antonio.entregaagil.ui.adapter.list.AssinantesAdapter
import com.antonio.entregaagil.ui.adapter.list.DetalhesRotaAdapter
import com.antonio.entregaagil.ui.adapter.list.RotasAdapter
import com.antonio.entregaagil.ui.viewmodel.AssinantesViewModel
import com.antonio.entregaagil.ui.viewmodel.RotasViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single<AssinantesAdapter> {
        AssinantesAdapter(get<Context>())
    }
    single<DetalhesRotaAdapter> {
        DetalhesRotaAdapter(get<Context>())
    }
    single<RotasAdapter> {
        RotasAdapter(get<Context>())
    }
    viewModel<AssinantesViewModel> {
        AssinantesViewModel()
    }
    viewModel<RotasViewModel> {
        //(id: Long) ->
        RotasViewModel()
    }
//    single<NoticiaDAO> {
//            get<AppDatabase>().noticiaDAO
//        }
}