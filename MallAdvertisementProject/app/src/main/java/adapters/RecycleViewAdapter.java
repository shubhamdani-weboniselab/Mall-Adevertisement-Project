package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.webonise.malladvertisementproject.DetailOfferViewActivity;
import com.webonise.malladvertisementproject.R;
import utils.CoOdrinates;
import utils.Constants;

/**
 * Created by webonise on 27/8/15.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private String[] discountPercentage;
    private String[] urls;
    private String[] description;
    private Context context;
    private CoOdrinates[] coOdrinates;


    public RecycleViewAdapter(String[] DiscountPercentage, String[] urls, String[] Description, CoOdrinates[] coOdrinates, Context context) {
        this.discountPercentage = DiscountPercentage;
        this.urls = urls;
        this.description = Description;
        this.coOdrinates = coOdrinates;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        /*universal image loader goes here*/
        viewHolder.tvDiscount.setText(discountPercentage[position]);
        viewHolder.tvDescription.setText(description[position]);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startDetailOfferViewActivity = new Intent(context, DetailOfferViewActivity.class);
                startDetailOfferViewActivity.putExtra(Constants.DETAIL_URL, urls[position]);
                startDetailOfferViewActivity.putExtra(Constants.DESCRIPTION, viewHolder.tvDescription.getText());
                startDetailOfferViewActivity.putExtra(Constants.DISCOUNT, viewHolder.tvDiscount.getText());
                startDetailOfferViewActivity.putExtra(Constants.LONGITUDE,coOdrinates[position].getLongitude());
                startDetailOfferViewActivity.putExtra(Constants.LATITUDE,coOdrinates[position].getLatitude());
                context.startActivity(startDetailOfferViewActivity);

            }
        });
        Picasso.with(context).load(urls[position]).into(viewHolder.productImage);
    }

    @Override
    public int getItemCount() {
        return discountPercentage.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDiscount;
        public TextView tvDescription;
        public ImageView productImage;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvDiscount = (TextView) itemView.findViewById(R.id.tvDiscount);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);
        }

    }
}
