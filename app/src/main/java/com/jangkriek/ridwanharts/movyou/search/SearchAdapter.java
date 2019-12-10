package com.jangkriek.ridwanharts.movyou.search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jangkriek.ridwanharts.movyou.DetilActivity;
import com.jangkriek.ridwanharts.movyou.R;
import com.jangkriek.ridwanharts.movyou.favorit.ItemResult;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.CONTENT_URI;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<ItemResult> mData;
    private Context context;

    public SearchAdapter(List<ItemResult>movieItemsList, Context context){
        this.mData=movieItemsList;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvJudul.setText(mData.get(position).getmJudul());
        holder.tvDeskripsi.setText(mData.get(position).getmDeskripsi());
        holder.tvNo.setText(""+(position+1)+". ");
        String aTanggal = mData.get(position).getmTanggal();
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = mFormat.parse(aTanggal);
            SimpleDateFormat newFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String mTanggal = newFormat.format(date);
            holder.tvTanggal.setText(mTanggal);
        }catch (ParseException p){
            p.printStackTrace();
        }
        Picasso.get().load("http://image.tmdb.org/t/p/w185/"+mData.get(position)
                .getmGambar()).placeholder(context.getResources()
                .getDrawable(R.drawable.ic_broken_image)).error(context
                .getResources().getDrawable(R.drawable.ic_broken_image)).into(holder.imgGambar);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = "0";
                Intent intent = new Intent(context, DetilActivity.class);
                /*intent.putExtra(DetilActivity.EXTRA_JUDUL, movie.getJudul());
                intent.putExtra(DetilActivity.EXTRA_DESKRIPSI, movie.getDeskripsi());
                intent.putExtra(DetilActivity.EXTRA_TANGGAL, movie.getTanggal());
                intent.putExtra(DetilActivity.EXTRA_RATING, movie.getRating());
                intent.putExtra(DetilActivity.EXTRA_GAMBAR, movie.getGambar());*/
                Uri uri = Uri.parse(CONTENT_URI + "/" + mData.get(position).getmovieId());
                intent.putExtra(DetilActivity.MOVIE_ID, mData.get(position).getmovieId());
                intent.putExtra(DetilActivity.STATE, state);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
    }

    public void setData(ArrayList<ItemResult> items){
        mData = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position){
        return 0;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvJudul;
        TextView tvDeskripsi;
        TextView tvTanggal;
        TextView tvNo;
        ImageView imgGambar;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNo = (TextView)itemView.findViewById(R.id.tv_no);
            tvJudul = (TextView)itemView.findViewById(R.id.tv_judul);
            tvDeskripsi = (TextView)itemView.findViewById(R.id.tv_deskripsi);
            tvTanggal = (TextView)itemView.findViewById(R.id.tv_tanggal);
            imgGambar = (ImageView)itemView.findViewById(R.id.iv_film);
            parentLayout = (LinearLayout)itemView.findViewById(R.id.rel_list);

        }
    }
}
