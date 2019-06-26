package br.com.andersonmatte.buscacep.projeto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import br.com.andersonmatte.buscacep.R;

public class TipoBuscaActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton cep, endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_busca);
        radioGroup = (RadioGroup) findViewById(R.id.cepOuEndereco);
        cep = (RadioButton) findViewById(R.id.cepRB);
        endereco = (RadioButton) findViewById(R.id.enderecoRB);
    }

    public void onRadioButtonClicked(View view) {
        Intent intent = null;
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.cepRB:
                if (checked)
                    intent = new Intent(TipoBuscaActivity.this, BuscaPorCepActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.enderecoRB:
                if (checked)
                    intent = new Intent(TipoBuscaActivity.this, BuscaPorEnderecoActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    //Botao voltar para activity com suporte bread crumb.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, TipoBuscaActivity.class);
        startActivity(intent);
        finish();
    }

}
