package adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.webonise.malladvertisementproject.R;

/**
 * Created by webonise on 18/9/15.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private String[] jsonResponses;
    private Context context;
    private LayoutInflater inflater;

    public ViewPagerAdapter(String jsonResponses[], Context context) {
        this.jsonResponses = jsonResponses;
        this.context = context;
    }

    @Override
    public int getCount() {
        return jsonResponses.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_pager_layout, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.sliding_image_view);
        Picasso.with(context).load(jsonResponses[position]).into(imageView);
        ((ViewPager) container).addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
