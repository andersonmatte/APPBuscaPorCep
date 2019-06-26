package br.com.andersonmatte.buscacep.projeto.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.andersonmatte.buscacep.R;
import br.com.andersonmatte.buscacep.projeto.entity.Endereco;

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
            CharSequence naoInformado = " " + context.getResources().getText(R.string.nao_informado);
            //Popula os atributos do Endereco com o obejeto recebido.
            getLogradouro(position, view, naoInformado);
            getComplemento(position, view, naoInformado);
            getBairro(position, view, naoInformado);
            getCep(position, view, naoInformado);
            getCidade(position, view, naoInformado);
            getUf(position, view, naoInformado);
            getCopy(position, view);
        }
        return view;
    }

    private void getLogradouro(int position, View view, CharSequence naoInformado) {
        textViewLogradouro = (TextView) view.findViewById(R.id.logradouro);
        textViewLogradouro.setText(listaEndereco.get(position).getLogradouro() != null
                || !listaEndereco.get(position).getLogradouro().isEmpty() ? context.getResources().getText(R.string.logradouro) + " " + listaEndereco.get(position).getLogradouro()
                : context.getResources().getText(R.string.logradouro) + " " + naoInformado);
    }

    private void getComplemento(int position, View view, CharSequence naoInformado) {
        textViewComplemento = (TextView) view.findViewById(R.id.complemento);
        textViewComplemento.setText(listaEndereco.get(position).getComplemento() != null
                || !listaEndereco.get(position).getComplemento().isEmpty() ? context.getResources().getText(R.string.complemento) + " " + listaEndereco.get(position).getComplemento()
                : context.getResources().getText(R.string.complemento) + " " + naoInformado);
    }

    private void getBairro(int position, View view, CharSequence naoInformado) {
        textViewBairro = (TextView) view.findViewById(R.id.bairro);
        textViewBairro.setText(listaEndereco.get(position).getBairro() != null
                || !listaEndereco.get(position).getBairro().isEmpty() ? context.getResources().getText(R.string.bairro) + " " + listaEndereco.get(position).getBairro()
                : context.getResources().getText(R.string.bairro) + " " + naoInformado);
    }

    private void getCep(int position, View view, CharSequence naoInformado) {
        textViewCep = (TextView) view.findViewById(R.id.cep);
        textViewCep.setText(listaEndereco.get(position).getCep() != null
                || !listaEndereco.get(position).getCep().isEmpty() ? context.getResources().getText(R.string.cep) + " " + listaEndereco.get(position).getCep()
                : context.getResources().getText(R.string.cep) + " " + naoInformado);
    }

    private void getCidade(int position, View view, CharSequence naoInformado) {
        textViewCidade = (TextView) view.findViewById(R.id.cidade);
        textViewCidade.setText(listaEndereco.get(position).getCidade() != null
                || !listaEndereco.get(position).getCidade().isEmpty() ? context.getResources().getText(R.string.cidade) + " " + listaEndereco.get(position).getCidade()
                : context.getResources().getText(R.string.cidade) + " " + naoInformado);
    }

    private void getUf(int position, View view, CharSequence naoInformado) {
        textViewUf = (TextView) view.findViewById(R.id.uf);
        textViewUf.setText(listaEndereco.get(position).getUf() != null
                || !listaEndereco.get(position).getUf().isEmpty() ? context.getResources().getText(R.string.uf) + " " + listaEndereco.get(position).getUf()
                : context.getResources().getText(R.string.uf) + " " + naoInformado);
    }

    @SuppressWarnings("deprecation")
    private void getCopy(final int position, View view) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listaEndereco.get(position) != null){
                    ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(listaEndereco.get(position).toString());
                    Toast.makeText(context, context.getResources().getText(R.string.area_transferencias), Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

}
