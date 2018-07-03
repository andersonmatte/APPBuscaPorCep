package br.com.andersonmatte.buscacep.activity.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import br.com.andersonmatte.buscacep.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Controla o tempo de exibição da Splash Screen.
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarHome();
            }
        }, 2000);
    }

    //Chama a BuscaCepActivity.
    private void mostrarHome() {
        Intent intent = new Intent(SplashScreenActivity.this, BuscaCepActivity.class);
        startActivity(intent);
        finish();
    }

}
