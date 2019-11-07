package com.antonio.entregaagil

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