package com.example.gagan.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BloodBankAdapter extends RecyclerView.Adapter<BloodBankAdapter.BankViewHolder>  {

    private List<BloodBankItem> listItems;
    private Context context;
    BloodBankClickListener mListener;

    public interface BloodBankClickListener {
        void onItemClick(View view,int position);
    }

    public BloodBankAdapter(List<BloodBankItem> listItems, Context context, BloodBankClickListener listener) {
        this.listItems = listItems;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public  BloodBankAdapter.BankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_bank_item, parent, false);
        return new BloodBankAdapter.BankViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final BloodBankAdapter.BankViewHolder holder, final int position) {
        final BloodBankItem listItem = listItems.get(position);
        holder.bankName.setText(listItem.getBloodBankName());
        holder.bankCity.setText(listItem.getCity());
        holder.bankState.setText(listItem.getState());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Position " + position , Toast.LENGTH_SHORT).show();
                if (position != RecyclerView.NO_POSITION) {
                    // mainActivity.itemClicked(position);
                    mListener.onItemClick(view, position);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class BankViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            public TextView bankName;
            public TextView bankDistrict;
            public TextView bankCity;
            public TextView bankState;
            LinearLayout linearLayout;

            public BankViewHolder(View itemView) {
                super(itemView);

                bankName= (TextView)itemView.findViewById(R.id.bank_name);
                bankCity=(TextView)itemView.findViewById(R.id.bank_city);
                bankState=(TextView)itemView.findViewById(R.id.bank_state);
                linearLayout = itemView.findViewById(R.id.bloodbank_linearlayout);
                itemView.setClickable(true);
                linearLayout.setOnClickListener(this);

            }


            @Override
            public void onClick(View view) {
                mListener.onItemClick(view,getAdapterPosition());
            }
        }}
