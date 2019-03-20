package br.com.andersonmatte.buscacep.projeto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.andersonmatte.buscacep.R;
import br.com.andersonmatte.buscacep.projeto.entidade.Endereco;

public class EnderecoAdapter extends ArrayAdapter<Endereco> {

    private List<Endereco> listaEndereco;
    private Context context;
    private TextView textViewLogradouro, textViewComplemento, textViewBairro, textViewCep, textViewCidade, textViewUf;

    public EnderecoAdapter(Context context, List<Endereco> listaEnderecoRecebido) {
        super(context, 0, listaEnderecoRecebido);
        this.listaEndereco = listaEnderecoRecebido;
        this.context = context;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.lista_endereco, null);
        //Aqui ocorre a mágica no setTag onde é passado a posição da ListView!
        view.setTag(position);
        if (listaEndereco.get(position) != null) {
            //Popula os atributos do Endereco com o obejeto recebido.
            textViewLogradouro = (TextView) view.findViewById(R.id.logradouro);
            textViewLogradouro.setText(listaEndereco.get(position).getLogradouro() != null ? context.getResources().getText(R.string.logradouro) + " " + listaEndereco.get(position).getLogradouro() : null);

            textViewComplemento = (TextView) view.findViewById(R.id.complemento);
            textViewComplemento.setText(listaEndereco.get(position).getComplemento() != null ? context.getResources().getText(R.string.complemento) + " " + listaEndereco.get(position).getComplemento() : null);

            textViewBairro = (TextView) view.findViewById(R.id.bairro);
            textViewBairro.setText(listaEndereco.get(position).getBairro() != null ? context.getResources().getText(R.string.bairro) + " " + listaEndereco.get(position).getBairro() : null);

            textViewCep = (TextView) view.findViewById(R.id.cep);
            textViewCep.setText(listaEndereco.get(position).getCep() != null ? context.getResources().getText(R.string.cep) + " " + listaEndereco.get(position).getCep() : null);

            textViewCidade = (TextView) view.findViewById(R.id.cidade);
            textViewCidade.setText(listaEndereco.get(position).getCidade() != null ? context.getResources().getText(R.string.cidade) + " " + listaEndereco.get(position).getCidade() : null);

            textViewUf = (TextView) view.findViewById(R.id.uf);
            textViewUf.setText(listaEndereco.get(position).getUf() != null ? context.getResources().getText(R.string.uf) + " " + listaEndereco.get(position).getUf() : null);
        }
        return view;
    }

}
