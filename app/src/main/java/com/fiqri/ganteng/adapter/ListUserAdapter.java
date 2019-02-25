package com.fiqri.ganteng.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fiqri.ganteng.R;
import com.fiqri.ganteng.TrackingActivity;
import com.fiqri.ganteng.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ViewHolder> {

    List<User> userList;

    public ListUserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int index) {
        viewHolder.tvItemUsername.setText(userList.get(index).getName());
        viewHolder.tvItemEmail.setText(userList.get(index).getEmail());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // opsi
                // Bundle bundle = new Bundle();
                // bundle.putString("id", userList.get(index).getUuid());

                Intent intent = new Intent(v.getContext(), TrackingActivity.class);
                intent.putExtra("id", userList.get(index).getUuid());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (userList == null) return 0;
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_username)
        TextView tvItemUsername;
        @BindView(R.id.tv_item_email)
        TextView tvItemEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
