package com.kelompok3.kebeletlaundry

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(Runnable { // This method will be executed once the timer is over
            val i = Intent(this, LoginActivity::class.java)
            val j = Intent(this, MainActivity::class.java)
            val jwt: String? = this.getSharedPreferences("TDG_APP", MODE_PRIVATE).getString("jwt", null)

            if (jwt == null)
                startActivity(i)
            else
                startActivity(j)
            finish()
        }, 2500)
    }
}