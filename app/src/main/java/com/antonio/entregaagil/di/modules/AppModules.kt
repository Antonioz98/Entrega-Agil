package com.antonio.entregaagil.di.modules

import android.content.Context
import com.antonio.entregaagil.database.AssinanteHelper
import com.antonio.entregaagil.database.FirebaseHelper
import com.antonio.entregaagil.database.RotaHelper
import com.antonio.entregaagil.modelo.Usuario
import com.antonio.entregaagil.ui.adapter.list.*
import com.antonio.entregaagil.ui.viewmodel.AssinantesViewModel
import com.antonio.entregaagil.ui.viewmodel.LoginViewModel
import com.antonio.entregaagil.ui.viewmodel.RotasViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {

    single<AssinantesAdapter> {
        AssinantesAdapter(get<Context>())
    }
    single<AssinantesAdapter2> {
        AssinantesAdapter2(get<Context>())
    }
    single<DetalhesRotaAdapter> {
        DetalhesRotaAdapter(get<Context>())
    }
    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }
    single<Usuario> {
        Usuario.newUser(get<FirebaseAuth>().currentUser)
    }
    single<RotasAdapter> {
        RotasAdapter(get<Context>())
    }
    single<RotasAdapter2> {
        RotasAdapter2(get<Context>())
    }
    single<FirebaseHelper> {
        FirebaseHelper((FirebaseFirestore.getInstance()))
    }
    single<AssinanteHelper> {
        AssinanteHelper(get<FirebaseHelper>())
    }
    single<RotaHelper> {
        RotaHelper(get<FirebaseHelper>())
    }
    viewModel<AssinantesViewModel> {
        AssinantesViewModel(get<AssinanteHelper>())
    }
    viewModel<LoginViewModel> {
        LoginViewModel(get<FirebaseAuth>())
    }
    viewModel<RotasViewModel> {
        //(id: Long) ->
        RotasViewModel(get<RotaHelper>(),get<AssinanteHelper>() )
    }
}