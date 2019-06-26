package br.com.andersonmatte.buscacep.projeto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import br.com.andersonmatte.buscacep.R;
import br.com.andersonmatte.buscacep.projeto.adapter.EnderecoAdapter;
import br.com.andersonmatte.buscacep.projeto.entity.Endereco;

public class EnderecoActivity extends AppCompatActivity {

    private List<Endereco> listaEnderecoRecebido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);
        //Recebe os dados passados na Intent da Classe BuscaPorCepActivity por mecanismo de Bundle.
        Bundle bundle = getIntent().getBundleExtra("endereco");
        if (bundle != null) {
            this.listaEnderecoRecebido = (List<Endereco>) bundle.getSerializable("resultado");
            this.populaListViewEnderecos(this.listaEnderecoRecebido);
        }
    }

    public void populaListViewEnderecos(List<Endereco> listaEnderecoRecebido) {
        if (listaEnderecoRecebido != null && !listaEnderecoRecebido.isEmpty()) {
            ListView listView = (ListView) findViewById(R.id.listView);
            final EnderecoAdapter adapter = new EnderecoAdapter(this, listaEnderecoRecebido);
            listView.setAdapter(adapter);
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