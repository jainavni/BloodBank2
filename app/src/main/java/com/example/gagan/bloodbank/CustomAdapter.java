package com.example.gagan.bloodbank;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>implements Filterable {

    private List<ListItem> listItems;
    private List<ListItem> mOriginalValues;
    private Context context;

    public CustomAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.mOriginalValues = listItems;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListItem listItem=listItems.get(position);
        holder.name.setText(listItem.getName());
        holder.bloodgroup.setText(listItem.getBloodgroup());
        holder.city.setText(listItem.getCity());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                try {
                    listItems = (ArrayList<ListItem>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                try {
                    ArrayList<ListItem> FilteredArrList = new ArrayList();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList(listItems); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String data = new Gson().toJson(mOriginalValues.get(i));
                            if (data.toLowerCase().contains(constraint.toString())) {
                                FilteredArrList.add(mOriginalValues.get(i));
                            }
                        }
                        // set the Filtered result to returnl
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return results;
            }
        };
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView bloodgroup;
        public TextView city;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            name= (TextView)itemView.findViewById(R.id.name);
            bloodgroup=(TextView)itemView.findViewById(R.id.bloodgroup);
            city=(TextView)itemView.findViewById(R.id.city);
            linearLayout=itemView.findViewById(R.id.linearlayout);
        }


    }
}
