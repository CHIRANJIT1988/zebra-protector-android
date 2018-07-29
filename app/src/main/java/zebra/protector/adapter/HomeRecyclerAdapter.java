package zebra.protector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import zebra.protector.R;
import zebra.protector.app.MyApplication;
import zebra.protector.helper.Helper;
import zebra.protector.model.Product;


public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.VersionViewHolder>
{

    ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    Context context = null;
    OnItemClickListener clickListener;

    private List<Product> category;


    public HomeRecyclerAdapter(Context context, List<Product> category)
    {
        this.context = context;
        this.category = category;
    }


    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerlist_item_dashboard, viewGroup, false);
        return new VersionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i)
    {

        if (imageLoader == null)
        {
            imageLoader = MyApplication.getInstance().getImageLoader();
        }


        versionViewHolder.name.setText(Helper.toCamelCase(category.get(i).getCategoryName()));

        //Animation animation = AnimationUtils.loadAnimation(context, R.anim.network_image_view);
        //versionViewHolder.thumbnail.startAnimation(animation);
        //ObjectAnimator.ofFloat(versionViewHolder.thumbnail, View.ALPHA, 0.2f, 1.0f).setDuration(2000).start();
        //versionViewHolder.thumbnail.setImageUrl(CATEGORY_IMAGE_URL + category.get(i).getCategoryThumbnail(), imageLoader);



        //TypedArray images = context.getResources().obtainTypedArray(R.array.home_activities_image);
        //versionViewHolder.thumbnail.setImageResource(images.getResourceId(i, -1));

        //String imageFileName = category.get(i).getCategoryThumbnail();
        //int imgResId = context.getResources().getIdentifier(imageFileName, "drawable", "educing.tech.customer");
        //versionViewHolder.thumbnail.setImageResource(imgResId);
    }


    @Override
    public int getItemCount()
    {
        return category == null ? 0 : category.size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView name;
        //NetworkImageView thumbnail;

        public VersionViewHolder(View itemView)
        {

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
          //  thumbnail = (NetworkImageView) itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }


    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }


    public void SetOnItemClickListener(final OnItemClickListener itemClickListener)
    {
        this.clickListener = itemClickListener;
    }
}