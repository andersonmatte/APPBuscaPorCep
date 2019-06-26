package br.com.andersonmatte.buscacep.projeto.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import br.com.andersonmatte.buscacep.R;
import br.com.andersonmatte.buscacep.projeto.api.ViaCepAPI;
import br.com.andersonmatte.buscacep.projeto.base.AppCompatActivityBase;
import br.com.andersonmatte.buscacep.projeto.entity.Endereco;
import br.com.andersonmatte.buscacep.projeto.entity.Estado;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaPorEnderecoActivity extends AppCompatActivityBase {

    private EditText editTextEndereco;

    private Spinner spinnerUF;
    private Spinner spinnerMunicipios;

    private Long idSelecionado = null;
    private String nomeMunicipioParaValidacoes = null;

    protected List<Estado> listaEstado = new LinkedList<>();
    private List<String> listaNomeEstado = new LinkedList<>();
    protected RealmResults<Estado> estadoRealmResults;

    private List<String> listaNomeMunicipio = new LinkedList<>();

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_por_endereco);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        exibirProgress(true);
        editTextEndereco = (EditText) findViewById(R.id.nomeRua);
        spinnerUF = findViewById(R.id.spinnerUF);
        spinnerMunicipios = findViewById(R.id.spinnerMunicipios);
        this.populaSpinnerEstados();
        //Clique do botão buscar endereço por CEP.
        Button buttonBuscarPorEndereco = (Button) findViewById(R.id.btn_buscarEnderecoEnd);
        buttonBuscarPorEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validaForm()) {
                    //Busca os dados na API Viacep com auxilio do Retrofit e Gson
                    // para fazer o parse do Json para classe Java.
                    ViaCepAPI viaCepAPI = ViaCepAPI.retrofit.create(ViaCepAPI.class);
                    buscaSiglaPorListaEstado();
                    final Call<List<Endereco>> callUsuario = viaCepAPI.getEnderecosPorLogradouro(buscaSiglaPorListaEstado(), nomeMunicipioParaValidacoes, editTextEndereco.getText().toString());
                    callUsuario.enqueue(new Callback<List<Endereco>>() {
                        @Override
                        public void onResponse(Call<List<Endereco>> call, Response<List<Endereco>> response) {
                            List<Endereco> listaEnderecoAuxiliar = new LinkedList<>();
                            listaEnderecoAuxiliar = response.body();
                            if (listaEnderecoAuxiliar != null && !listaEnderecoAuxiliar.isEmpty()) {
                                //prepara o objeto para passar para a EnderecoActivity.
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("resultado", (Serializable) listaEnderecoAuxiliar);
                                //Chama a EnderecoActivity já com o objeto populado.
                                Intent intentPerfil = new Intent(BuscaPorEnderecoActivity.this, EnderecoActivity.class);
                                intentPerfil.putExtra("endereco", bundle);
                                startActivity(intentPerfil);
                            } else {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.logradouro_naoEncontrado), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Endereco>> call, Throwable t) {
                            Log.i("ERRO", getResources().getString(R.string.erro_buscaServico) + t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private String buscaSiglaPorListaEstado() {
        if (this.listaEstado != null && !this.listaEstado.isEmpty()) {
            for (int i = 0; i < this.listaEstado.size(); i++) {
                if (this.listaEstado.get(i).getId().equals(idSelecionado)) {
                    return listaEstado.get(i).getSigla();
                }
            }
        }
        return "";
    }

    //Valida se o logradouro foi preenchido.
    private Boolean validaForm() {
        if (editTextEndereco.getText().toString().isEmpty()) {
            editTextEndereco.setError(getResources().getString(R.string.erro_validaForm_endereco));
            return false;
        } else {
            return true;
        }
    }

    private void populaSpinnerEstados() {
        this.populaListaEstadoPorResultsBD();
        for (int i = 0; i < this.listaEstado.size(); i++) {
            this.listaNomeEstado.add(this.listaEstado.get(i).getNome());
        }
        Collections.sort(this.listaNomeEstado);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, this.listaNomeEstado);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUF.setAdapter(adapter);
        spinnerUF.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                exibirProgress(true);
                String nomeEstado = (String) parent.getSelectedItem();
                displayEstadoData(nomeEstado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populaListaEstadoPorResultsBD() {
        this.estadoRealmResults = realm.where(Estado.class).findAllAsync();
        this.estadoRealmResults.load();
        this.listaEstado = this.estadoRealmResults;
    }

    public void displayEstadoData(String nomeEstado) {
        //Limpa o id de estado anterior.
        this.setIdSelecionado(null);
        if (nomeEstado != null) {
            for (Estado estado : this.listaEstado) {
                if (estado != null && estado.getNome() != null && estado.getNome().equals(nomeEstado)) {
                    this.setIdSelecionado(estado.getId());
                    this.listaNomeMunicipio = new LinkedList<>();
                    this.listaNomeMunicipio = estado.getMunicipios();
                    populaSpinnerMunicipios();
                }
            }
        }
    }

    private void populaSpinnerMunicipios() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, this.listaNomeMunicipio);
        spinnerMunicipios.setAdapter(adapter);
        spinnerMunicipios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displayMunicipioData(parent.getSelectedItem().toString());
                exibirProgress(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void displayMunicipioData(String nomeMunicipio) {
        this.setNomeMunicipioParaValidacoes(null);
        if (nomeMunicipio != null) {
            this.setNomeMunicipioParaValidacoes(nomeMunicipio);
        }
    }

    private void exibirProgress(boolean exibir) {
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    public Long getIdSelecionado() {
        return idSelecionado;
    }

    public void setIdSelecionado(Long idSelecionado) {
        this.idSelecionado = idSelecionado;
    }

    public String getNomeMunicipioParaValidacoes() {
        return nomeMunicipioParaValidacoes;
    }

    public void setNomeMunicipioParaValidacoes(String nomeMunicipioParaValidacoes) {
        this.nomeMunicipioParaValidacoes = nomeMunicipioParaValidacoes;
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
