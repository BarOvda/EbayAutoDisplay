package com.example.ebayautodisplayllibrary.logic.ebay_plugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebayautodisplayllibrary.R;
import com.example.ebayautodisplayllibrary.logic.EbayTitle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EbayListAdapter extends RecyclerView.Adapter<EbayListAdapter.GameViewHolder> {
    private Context mContext;
    private List<EbayTitle> mItems;
    private int style;
    private int oriantion;
    private int textColor;
    private Drawable cardBackgrond;

    public EbayListAdapter(Context mContext, List<EbayTitle> mGames, int style, int textColor, Drawable cardBackgroundColor,int oriantion) {
        this.mItems = new ArrayList<>();
        this.mContext = mContext;
        this.mItems = mGames;
        this.style = style;
        this.oriantion = oriantion;
        this.textColor = textColor;
        this.cardBackgrond=cardBackgroundColor;
    }

    @NonNull
    @Override
    public EbayListAdapter.GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(style==0)
             v = LayoutInflater.from(mContext).inflate(R.layout.ebay_item, parent, false);
        else
             v = LayoutInflater.from(mContext).inflate(R.layout.ebay_item_modern, parent, false);
        return new EbayListAdapter.GameViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EbayListAdapter.GameViewHolder holder, final int position) {
        final EbayTitle currentTitle = mItems.get(position);
        if(oriantion==1) {
            holder.imageView.getLayoutParams().width = 240;
            holder.imageView.requestLayout();

        }



        holder.textViewName.setText((currentTitle.getTitle()));
        Picasso.with(holder.imageView.getContext()).load(currentTitle.getGalleryUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
        holder.priceView.setText(currentTitle.getCurrentPrice() + "$");
        if(cardBackgrond!=null){
            holder.parentLayout.setBackground(this.cardBackgrond);
        }
        if(this.textColor!=0) {
            holder.textViewName.setTextColor(textColor);
            holder.priceView.setTextColor(textColor);
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentTitle.getItemUrl()));
                mContext.startActivity(browserIntent);

            }
        });
    }
    public void addToExistingList(List<EbayTitle> dataModelList){
        mItems.addAll(dataModelList);
        notifyItemRangeChanged(dataModelList.size()-1 , dataModelList.size());
    }
    @Override
    public int getItemCount() {
return  mItems.size();    }

    public class GameViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageView;
        public TextView priceView;
        public CardView parentLayout;

        public GameViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.view_name_ebay);
            imageView = itemView.findViewById(R.id.view_image_ebay);
            priceView = itemView.findViewById(R.id.view_price_ebay);
            parentLayout = itemView.findViewById(R.id.parent_layout_ebay);
        }


    }

}