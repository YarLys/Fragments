package com.example.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ValuteAdapter extends RecyclerView.Adapter<ValuteAdapter.ValuteHolder> {
    ArrayList<Valute> valutes = new ArrayList<>();

    public ValuteAdapter(ArrayList<Valute> valutes) {
        this.valutes = valutes;
    }

    @NonNull
    @Override
    public ValuteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.onevalute, parent, false);

        return new ValuteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ValuteHolder holder, int position) {
        Valute valute = valutes.get(position);
        holder.Name.setText(valute.getName());
        holder.Value.setText(valute.getValue());
        holder.picture.setImageBitmap(valute.getPicture());
    }

    @Override
    public int getItemCount() {
        return valutes.size();
    }

    class ValuteHolder extends RecyclerView.ViewHolder {
        TextView Name, Value;
        ImageView picture;
        public ValuteHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name);
            Value = itemView.findViewById(R.id.Value);
            picture = itemView.findViewById(R.id.Picture);
        }
    }
}
