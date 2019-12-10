package com.jangkriek.ridwanharts.movyou.main;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jangkriek.ridwanharts.movyou.DetilActivity;
import com.jangkriek.ridwanharts.movyou.R;
import com.jangkriek.ridwanharts.movyou.favorit.DetilMovie;
import com.jangkriek.ridwanharts.movyou.favorit.ItemResult;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.CONTENT_URI;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context context;
    private List<ItemResult>upcominNowplaying;

    public MenuAdapter(Context context, List<ItemResult>upcominNowplaying){
        this.context=context;
        this.upcominNowplaying=upcominNowplaying;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {

        holder.judul.setText(upcominNowplaying.get(i).getmJudul());
        holder.deskripsi.setText(upcominNowplaying.get(i).getmDeskripsi());
        String tanggalRilis = upcominNowplaying.get(i).getmTanggal();
        SimpleDateFormat formatTanggal = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date tanggal = formatTanggal.parse(tanggalRilis);
            SimpleDateFormat formatBaru = new SimpleDateFormat("E, MMM dd, yyyy");
            String rilis = formatBaru.format(tanggal);
            holder.tanggal.setText(rilis);

        }catch (ParseException p){
            p.printStackTrace();
        }
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+upcominNowplaying.get(i).getmGambar()).fit().into(holder.imgGambar);
        Log.e("cek", upcominNowplaying.get(i).getmGambar());
        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Share : "+upcominNowplaying.get(i).getmJudul(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDetil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Detail : "+upcominNowplaying.get(i).getmJudul()+" | ID : "+upcominNowplaying.get(i).getmovieId(),Toast.LENGTH_SHORT).show();
                String state = "0";
                Intent intent = new Intent(context, DetilActivity.class);
                Uri uri = Uri.parse(CONTENT_URI + "/" + upcominNowplaying.get(i).getmovieId());
                intent.putExtra(DetilActivity.MOVIE_ID, upcominNowplaying.get(i).getmovieId());
                intent.putExtra(DetilActivity.STATE, state);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(upcominNowplaying == null)return 0;
        return upcominNowplaying.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView judul, deskripsi, tanggal, btnDetil;
        public ImageView imgGambar, ivShare;

        public ViewHolder(View itemView){
            super(itemView);

            judul = (TextView)itemView.findViewById(R.id.tv_judul2);
            imgGambar = (ImageView)itemView.findViewById(R.id.iv_film2);
            deskripsi = (TextView)itemView.findViewById(R.id.tv_deskripsi2);
            tanggal = (TextView)itemView.findViewById(R.id.tv_tanggal2);
            btnDetil = itemView.findViewById(R.id.btnDetail);
            ivShare = itemView.findViewById(R.id.iv_share);

        }
    }

    public List<ItemResult>getResult(){

        return upcominNowplaying;
    }
}