package com.theavengers.movieapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class MyXMLAdapter extends RecyclerView.Adapter<MyXMLAdapter.MyViewHolder> {
    AllPizza allPizza[];
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(allPizza[position].getName());
        holder.email.setText(allPizza[position].getCost());
        holder.mobno.setText(allPizza[position].getDescription());
    }

    @Override
    public int getItemCount() {
        return allPizza.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,mobno;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            email=(TextView)itemView.findViewById(R.id.email);
            mobno=(TextView)itemView.findViewById(R.id.mobNo);
        }
    }
    public MyXMLAdapter(AllPizza[] allPizza) {
        this.allPizza=allPizza;
    }


}


    <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        xmlns:app="http://schemas.android.com/apk/res-auto">
<android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="6dp"
        android:layout_marginBottom="6dp"
        android:layout_marginLeft="6dp"
        android:layout_marginRight="6dp"
        >
<LinearLayout
            android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="vertical">

<TextView
                android:id="@+id/name"
                        android:layout_width="match_parent"
                        style="@android:style/TextAppearance.DeviceDefault.Medium"
                        android:layout_height="wrap_content"
                        android:fontFeatureSettings=""
                        android:textColor="#0059ff"
                        android:textStyle="bold" />
<TextView android:layout_height="wrap_content"
        android:layout_width="match_parent"
        style="@android:style/TextAppearance.DeviceDefault.Medium"
        android:textColor="#6bff00bb"

        android:id="@+id/email"/>
<TextView android:layout_height="wrap_content"
        android:layout_width="match_parent"
        style="@android:style/TextAppearance.DeviceDefault.Medium"
        android:id="@+id/mobNo"/>
</LinearLayout>
</android.support.v7.widget.CardView>
</LinearLayout>