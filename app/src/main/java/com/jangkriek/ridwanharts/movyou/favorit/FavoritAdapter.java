package com.jangkriek.ridwanharts.movyou.favorit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jangkriek.ridwanharts.movyou.DetilActivity;
import com.jangkriek.ridwanharts.movyou.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.CONTENT_URI;

public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.ViewHolder> {

    private Cursor listMovie;
    private Context context;

    public FavoritAdapter(Cursor items, Context context){
        this.context=context;
        setListMovie(items);
    }

    public void  setListMovie (Cursor items){
        this.listMovie = items;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FavoritAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_layout2,viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int i) {

        final DetilMovie movieList = getItem(i);

        holder.judul.setText(movieList.getmJudul());
        holder.deskripsi.setText(movieList.getmDeskripsi());
        String rilisTgl = movieList.getmTanggal();
        SimpleDateFormat formatTgl = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date tgl = formatTgl.parse(rilisTgl);
            SimpleDateFormat formatBaru = new SimpleDateFormat("E, MMM dd, yyyy");
            String rilis = formatBaru.format(tgl);
            holder.tanggal.setText(rilis);
        }catch (ParseException p){
            p.printStackTrace();
        }

        Picasso.get().load("http://image.tmdb.org/t/p/w500/"+movieList.getmGambar()).fit().into(holder.imgGambar);

        holder.btnDetil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Detail : "+movieList.getmJudul()+" | ID : "+movieList.getmId(),Toast.LENGTH_SHORT).show();
                String state = "1";
                Intent intent = new Intent(context, DetilActivity.class);
                Uri uri = Uri.parse(CONTENT_URI + "/" + movieList.getmId());
                intent.putExtra(DetilActivity.MOVIE_ID, movieList.getmId());
                intent.putExtra(DetilActivity.STATE, state);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });

        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Share : "+movieList.getmJudul(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private DetilMovie getItem(int i) {
        if (!listMovie.moveToPosition(i)){
            throw new IllegalStateException("Position invalid");
        }
        return new DetilMovie(listMovie);
    }

    @Override
    public int getItemCount() {
        if(listMovie == null)return 0;
        return listMovie.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView judul, deskripsi, tanggal, btnDetil;
        public ImageView imgGambar,ivShare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            judul = (TextView)itemView.findViewById(R.id.tv_judul2);
            deskripsi = (TextView)itemView.findViewById(R.id.tv_deskripsi2);
            tanggal = (TextView)itemView.findViewById(R.id.tv_tanggal2);
            imgGambar = (ImageView) itemView.findViewById(R.id.iv_film2);
            ivShare = itemView.findViewById(R.id.iv_share);
            btnDetil = itemView.findViewById(R.id.btnDetail);
        }

    }
}
