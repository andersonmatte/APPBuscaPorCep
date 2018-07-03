package br.com.andersonmatte.buscacep.activity.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.andersonmatte.buscacep.R;
import br.com.andersonmatte.buscacep.activity.api.ViaCepAPI;
import br.com.andersonmatte.buscacep.activity.entidade.Endereco;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaCepActivity extends AppCompatActivity {

    private AppCompatEditText editTextCep;
    private TextInputLayout txtlayoutCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_cep);
        //Busca o CEP digitado pelo usuário.
        editTextCep = (AppCompatEditText) findViewById(R.id.editTextCep);
        txtlayoutCep = (TextInputLayout) findViewById(R.id.txtlayoutCep);
        //Formata o CEP digitado pelo usuário com o MaskEditTextChangedListener.
        MaskEditTextChangedListener maskCep = new MaskEditTextChangedListener("#####-###", editTextCep);
        editTextCep.addTextChangedListener(maskCep);
        //Clique do botão buscar endereço por CEP.
        Button buttonBuscarUsuario = (Button) findViewById(R.id.btn_buscarEndereco);
        buttonBuscarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaForm()){
                    //Busca os dados na API Viacep com auxilio do Retrofit e Gson
                    // para fazer o parse do Json para classe Java.
                    ViaCepAPI viaCepAPI  = ViaCepAPI.retrofit.create(ViaCepAPI.class);
                    final Call<Endereco> callUsuario = viaCepAPI.getEnderecoPorCep(editTextCep.getText().toString());
                    callUsuario.enqueue(new Callback<Endereco>() {
                        @Override
                        public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                            Endereco endereco = response.body();
                            if(endereco != null && endereco.getCep() != null){
                                //prepara o objeto para passar para a EnderecoActivity.
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("resultado", endereco);
                                //Chama a EnderecoActivity já com o objeto populado.
                                Intent intentPerfil = new Intent(BuscaCepActivity.this, EnderecoActivity.class);
                                intentPerfil.putExtra("endereco", bundle);
                                startActivity(intentPerfil);
                            } else
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.cep_naoEncontrado), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<Endereco> call, Throwable t) {
                            Log.i("ERRO", getResources().getString(R.string.erro_buscaServico) + t.getMessage());
                        }
                    });
                }
            }
        });
    }

    //Valida se o CEP foi preenchido.
    private Boolean validaForm() {
        if (editTextCep.getText().toString().isEmpty()) {
            txtlayoutCep.setErrorEnabled(true);
            txtlayoutCep.setError(getResources().getString(R.string.erro_validaForm));
            return false;
        } else {
            txtlayoutCep.setErrorEnabled(false);
            return true;
        }
    }

}
