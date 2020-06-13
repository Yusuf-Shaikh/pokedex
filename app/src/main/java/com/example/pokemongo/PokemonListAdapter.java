package com.example.pokemongo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.MyViewHolder> {

    Context context;
    List<Pokemon> PokemonList;

    public PokemonListAdapter(Context context, List<Pokemon> pokemonList) {
        this.context = context;
        PokemonList = pokemonList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //Load image
        Glide.with(context).load(PokemonList.get(position).getImg()).into(holder.pokemon_image);
        //set name
        holder.pokemon_name.setText(PokemonList.get(position).getName());

        //Event
        holder.setItemClickListner(new IItemClickListner() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(context,"Click at Pokemon: "+ PokemonList.get(position).getName(),Toast.LENGTH_SHORT).show();

                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(Common.KEY_ENABLE_HOME).putExtra("num",PokemonList.get(position).getNum()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return PokemonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView pokemon_image;
        TextView pokemon_name;

        IItemClickListner iItemClickListner;

        public void setItemClickListner(IItemClickListner itemClickListner) {
            this.iItemClickListner = itemClickListner;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pokemon_image = (ImageView)itemView.findViewById(R.id.pokemon_image);
            pokemon_name = (TextView)itemView.findViewById(R.id.txt_pokemon_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iItemClickListner.onClick(view,getAdapterPosition());
        }
    }
}
