package br.com.andersonmatte.buscacep.projeto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.andersonmatte.buscacep.R;
import br.com.andersonmatte.buscacep.projeto.base.AppCompatActivityBase;

public class SplashScreenActivity extends AppCompatActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Busca já no inicio do APP a lista de estados.
        super.recuperarEstados();
        //Controla o tempo de exibição da Splash Screen.
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarHome();
            }
        }, 2000);
    }


    //Chama a BuscaPorCepActivity.
    private void mostrarHome() {
        Intent intent = new Intent(SplashScreenActivity.this, TipoBuscaActivity.class);
        startActivity(intent);
        finish();
    }

}
