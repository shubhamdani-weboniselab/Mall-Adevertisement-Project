package adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
import com.webonise.malladvertisementproject.DetailOfferViewActivity;
import com.webonise.malladvertisementproject.R;

import utils.Constants;

/**
 * Created by webonise on 27/8/15.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private String[] discountPercentage;
    private String[] urls;
    private String[] description;
    private Context context;
    private LatLng[] latLngs;

    private LocationManager manager;


    public RecycleViewAdapter(String[] DiscountPercentage, String[] urls, String[] Description,  LatLng[] latLngs, Context context) {
        this.discountPercentage = DiscountPercentage;
        this.urls = urls;
        this.description = Description;
        this.latLngs = latLngs;
        this.context = context;
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
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
                if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    startActivity(position, viewHolder);}
                else {
                    enableLocation();
                }


            }
        });
        Picasso.with(context).load(urls[position]).noFade().into(viewHolder.productImage);
    }

    private void startActivity(int position, ViewHolder viewHolder) {
        Intent startDetailOfferViewActivity = new Intent(context, DetailOfferViewActivity.class);
        startDetailOfferViewActivity.putExtra(Constants.DETAIL_URL, urls[position]);
        startDetailOfferViewActivity.putExtra(Constants.DESCRIPTION, viewHolder.tvDescription.getText());
        startDetailOfferViewActivity.putExtra(Constants.DISCOUNT, viewHolder.tvDiscount.getText());
        startDetailOfferViewActivity.putExtra(Constants.LONGITUDE,latLngs[position].longitude);
        startDetailOfferViewActivity.putExtra(Constants.LATITUDE,latLngs[position].latitude);
        context.startActivity(startDetailOfferViewActivity);
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

    private void enableLocation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(R.string.gps_requiered)
                .setCancelable(false)
                .setPositiveButton(R.string.enable,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        }).setNegativeButton(R.string.exit,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(context, R.string.should_enable_location, Toast.LENGTH_LONG).show();

                            }
                        });
        builder.create();
        builder.show();

    }
}
