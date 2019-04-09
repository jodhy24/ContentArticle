package com.example.contentarticle.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Delete;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contentarticle.R;
import com.example.contentarticle.activity.AddActivity;
import com.example.contentarticle.activity.DetailContentActivity;
import com.example.contentarticle.activity.HomeActivity;
import com.example.contentarticle.helper.DatabaseClient;
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
        ImageView imageedit, imagedelete;


        public ContentViewHolder( View itemView) {
            super(itemView);
            textviewjudul = itemView.findViewById(R.id.judul);
            textviewtanggal = itemView.findViewById(R.id.tanggalitem);
            imageedit = itemView.findViewById(R.id.editimage);
            imagedelete = itemView.findViewById(R.id.deleteimage);

            itemView.setOnClickListener(this);

            imagedelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("WARNING");
                    builder.setMessage("Are You Sure ?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DeleteContent(contentList.get(getAdapterPosition()));
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

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

    private void DeleteContent (final Content content) {


        // Ini untuk save database
        class deleteContent extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(mContext)
                        .getAppDatabase()
                        .contentDao()
                        .delete(content);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(mContext, "Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, HomeActivity.class));
                ((Activity)mContext).finish();
            }

        }
        deleteContent dl = new deleteContent();
        dl.execute();


    }


}
