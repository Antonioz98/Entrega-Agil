package com.antonio.entregaagil

import com.antonio.entregaagil.ui.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel<MainViewModel> {
        //(id: Long) ->
        MainViewModel()
    }
//    single<NoticiaDAO> {
//            get<AppDatabase>().noticiaDAO
//        }
}