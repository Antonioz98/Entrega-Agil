package com.antonio.entregaagil.di.modules

import android.content.Context
import com.antonio.entregaagil.ui.adapter.list.AssinantesAdapter
import com.antonio.entregaagil.ui.viewmodel.AssinantesViewModel
import com.antonio.entregaagil.ui.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single<AssinantesAdapter> {
        AssinantesAdapter(get<Context>())
    }
    viewModel<AssinantesViewModel> {
        AssinantesViewModel()
    }
    viewModel<MainViewModel> {
        //(id: Long) ->
        MainViewModel()
    }
//    single<NoticiaDAO> {
//            get<AppDatabase>().noticiaDAO
//        }
}