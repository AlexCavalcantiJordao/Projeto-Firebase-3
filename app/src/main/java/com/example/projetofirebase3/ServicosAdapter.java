package com.example.projetofirebase3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofirebase3.databinding.ServicosItemBinding;

import java.util.List;


public class ServicosAdapter extends RecyclerView.Adapter<ServicosAdapter.ServicosViewHolder> {

    private Context context;
    private List<Servicos> listaServicos;

    public ServicosAdapter(Context context, List<Servicos> listaServicos) {
        this.context = context;
        this.listaServicos = listaServicos;
    }

    @Override
    public ServicosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ServicosItemBinding itemLista = ServicosItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ServicosViewHolder(itemLista);
    }

    @Override
    public int getItemCount() {
        return listaServicos.size();
    }

    @Override
    public void onBindViewHolder(ServicosViewHolder holder, int position) {
        holder.imgServico.setImageResource(listaServicos.get(position).getImg());
        holder.txtServico.setText(listaServicos.get(position).getNome());
    }

    public class ServicosViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgServico;
        public TextView txtServico;

        public ServicosViewHolder(ServicosItemBinding binding) {
            super(binding.getRoot());
            imgServico = binding.imgServico;
            txtServico = binding.txtServico;
        }
    }
}

