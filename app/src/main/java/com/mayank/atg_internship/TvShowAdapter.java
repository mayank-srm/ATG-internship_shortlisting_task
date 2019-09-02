package com.mayank.atg_internship;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder>{

        private List<TvShow> TvShowList;
        private Context context;

TvShowAdapter(List<TvShow> TvShowList)
        {
        this.TvShowList=TvShowList;
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_main,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        context=parent.getContext();
        return viewHolder;
        }

@Override
public void onBindViewHolder(ViewHolder holder,final int position){
        TvShow tvShow=TvShowList.get(position);

        int pos = position+1;
        holder.textTvShow.setText(tvShow.getTvshow());
        Picasso.get().load(tvShow.getImgTvshow()).into(holder.imgTvShow);
       // holder.imgTvShow.setImageResource(tvShow.getImgTvshow());
        holder.cv.setOnClickListener(view -> {

          //  Toast.makeText(context,"The position is: "+tvShow.getImgTvshow(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context,HomeActivity.class);
            intent.putExtra("Title", tvShow.getTvshow());
            intent.putExtra("URL", tvShow.getImgTvshow());
            context.startActivity(intent);
                  });
}

@Override
public int getItemCount(){
        return TvShowList.size();
        }

class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imgTvShow;
    TextView textTvShow;
    CardView cv;

    ViewHolder(View itemView) {
        super(itemView);
        imgTvShow = itemView.findViewById(R.id.imgTvshow);
        textTvShow = itemView.findViewById(R.id.textTvshow);
        cv = itemView.findViewById(R.id.cv);
    }
}
}