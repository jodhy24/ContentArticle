package com.example.contentarticle.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.contentarticle.R;
import com.example.contentarticle.activity.DetailContentActivity;
import com.example.contentarticle.model.room.Content;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    private Context mContext;
    private List<Content> contentList;

    public ContentAdapter(Context mContext, List<Content> contentList) {
        this.mContext = mContext;
        this.contentList = contentList;
    }

    @Override
    public ContentViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_content, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ContentViewHolder holder, int position) {
        Content content = contentList.get(position);
        holder.textviewjudul.setText(content.getJudul());
        holder.textviewtanggal.setText(content.getTanggal());
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    class ContentViewHolder extends RecyclerView.ViewHolder implements View

            .OnClickListener {

        TextView textviewjudul, textviewtanggal;


        public ContentViewHolder( View itemView) {
            super(itemView);
            textviewjudul = itemView.findViewById(R.id.judul);
            textviewtanggal = itemView.findViewById(R.id.tanggalitem);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            Content content = contentList.get(getAdapterPosition());
            Intent intent = new Intent(mContext, DetailContentActivity.class);
            intent.putExtra("contentData", content);
            mContext.startActivity(intent);
            ((Activity)mContext).finish();
        }
    }
}
