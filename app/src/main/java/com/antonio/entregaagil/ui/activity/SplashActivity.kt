package com.antonio.entregaagil.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.antonio.entregaagil.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animBounce = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.bounce)
        val animRotate = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.rotate)

        ivIconApp.startAnimation(animBounce)
        ivIconSplash.startAnimation(animRotate)

        tvTitleSplash.alpha = 0f
        tvTitleSplash.translationX = 500f
        tvTitleSplash.animate().alpha(1f).translationX(0f).setDuration(800).setStartDelay(300).start()

        tvSubtitleSplash.alpha = 0f
        tvSubtitleSplash.translationX = 500f
        tvSubtitleSplash.animate().alpha(1f).translationX(0f).setDuration(800).setStartDelay(600).start()

        val auth = FirebaseAuth.getInstance()
        Handler().postDelayed({
            if (auth.currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            } else {
                startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
            finish()
        }, 3000L)

    }

}