package br.com.andersonmatte.buscacep.activity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.andersonmatte.buscacep.R;
import br.com.andersonmatte.buscacep.activity.entidade.Endereco;

public class EnderecoActivity extends AppCompatActivity {

    private Endereco enderecoRecebido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endereco);
        //Recebe os dados passados na Intent da Classe BuscaCepActivity por mecanismo de Bundle.
        Bundle bundle = getIntent().getBundleExtra("endereco");
        if (bundle != null) {
            this.enderecoRecebido = (Endereco) bundle.getSerializable("resultado");
            this.montaEndereco(this.enderecoRecebido);
        }
        //Clique do botão para fazer uma nova busca de endereço por CEP.
        Button buttonBuscarUsuario = (Button) findViewById(R.id.btn_buscarNovoEndereco);
        buttonBuscarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void montaEndereco(Endereco endereco){
        if (endereco != null){
            //Popula os atributos do Endereco com o obejeto recebido.
            TextView textViewLogradouro = (TextView) findViewById(R.id.logradouro);
            textViewLogradouro.setText(endereco.getLogradouro() != null ? getResources().getText(R.string.logradouro) + " " + endereco.getLogradouro() : null);

            TextView textViewComplemento = (TextView) findViewById(R.id.complemento);
            textViewComplemento.setText(endereco.getComplemento() != null ? getResources().getText(R.string.complemento) + " " + endereco.getComplemento() : null);

            TextView textViewBairro = (TextView) findViewById(R.id.bairro);
            textViewBairro.setText(endereco.getBairro() != null ? getResources().getText(R.string.bairro) + " " + endereco.getBairro() : null);

            TextView textViewCep = (TextView) findViewById(R.id.cep);
            textViewCep.setText(endereco.getCep() != null ? getResources().getText(R.string.cep) + " " + endereco.getCep() : null);

            TextView textViewCidade = (TextView) findViewById(R.id.cidade);
            textViewCidade.setText(endereco.getCidade() != null ? getResources().getText(R.string.cidade) + " " + endereco.getCidade() : null);

            TextView textViewUf = (TextView) findViewById(R.id.uf);
            textViewUf.setText(endereco.getUf() != null ? getResources().getText(R.string.uf) + " " + endereco.getUf() : null);

        }
    }

    //Botao voltar para activity com suporte bread crumb.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, BuscaCepActivity.class);
        startActivity(intent);
        finish();
    }

}
