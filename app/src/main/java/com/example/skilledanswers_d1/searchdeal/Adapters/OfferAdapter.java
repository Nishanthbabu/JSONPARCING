package com.example.skilledanswers_d1.searchdeal.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skilledanswers_d1.searchdeal.Models.OffersModel;
import com.example.skilledanswers_d1.searchdeal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SkilledAnswers-D1 on 30-03-2016.
 */
public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.Viewholder> {
    private Context context = null;
    private ArrayList<OffersModel> offersModels = null;
    private Viewholder viewholder = null;
    private int lastPosition = -1;

    public OfferAdapter(Context context, ArrayList<OffersModel> offersModels) {
        this.context = context;
        this.offersModels = offersModels;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.deal_row, null);
        viewholder = new Viewholder(itemLayoutView);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        holder._productName.setText(offersModels.get(position).get_productName());
//        holder._productImage.setImageBitmap(offersModels.get(position).get_productImage());
        Picasso.with(context).load(offersModels.get(position).get_productImage()).resize(175,175).into(holder._productImage);
        holder._productSalePrice.setText(offersModels.get(position).get_saleprice());
        holder._productSalePrice.append("Rs.");
        holder._productActualPrice.setText(offersModels.get(position).get_actualPrice());
        holder._productActualPrice.append("Rs.");
        holder._productSaleDesc.setText(offersModels.get(position).get_saleDetails());
        holder._productCompanyLogo.setImageBitmap(offersModels.get(position).get_companyLogo());
        setAnimation(holder._CardView, position);

    }

    @Override
    public int getItemCount() {
        return offersModels.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder
    {
        private TextView _productName = null;
        private ImageView _productImage = null;
        private TextView _productSalePrice = null;
        private TextView _productActualPrice = null;
        private TextView _productSaleDesc = null;
        private ImageView _productCompanyLogo = null;
        private CardView _CardView = null;
        public Viewholder(View itemView) {
            super(itemView);
            _productName=(TextView)itemView.findViewById(R.id.deal_row_productName);
            _productImage=(ImageView)itemView.findViewById(R.id.deal_row_productImage);
            _productSalePrice=(TextView)itemView.findViewById(R.id.deal_row_productSalePrice);
            _productActualPrice=(TextView)itemView.findViewById(R.id.deal_row_productActualPrice);
            _productSaleDesc=(TextView)itemView.findViewById(R.id.deal_row_productDiscountDetails);
            _productCompanyLogo=(ImageView)itemView.findViewById(R.id.deal_row_productDiscountCompanyLogo);
            _CardView=(CardView)itemView.findViewById(R.id.deal_row_card_view);
            _productActualPrice.setPaintFlags(_productActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }

//    public void animate(Viewholder viewHolder) {
//       Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.down_from_top);
//        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
//    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anticipateovershoot_interpolator);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
