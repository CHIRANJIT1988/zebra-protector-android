package zebra.protector.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import zebra.protector.R;
import zebra.protector.helper.Helper;
import zebra.protector.model.Store;


public class SubCategoryRecyclerAdapter extends RecyclerView.Adapter<SubCategoryRecyclerAdapter.VersionViewHolder>
{

    private Context context = null;
    private OnItemClickListener clickListener;

    private List<Store> storeList;
    private String[] bgColors;
    private DecimalFormat df = new DecimalFormat("0.00");


    public SubCategoryRecyclerAdapter(Context context, List<Store> storeList)
    {
        this.context = context;
        this.storeList = storeList;
        bgColors = context.getResources().getStringArray(R.array.user_color);

    }


    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerlist_item_sub_category, viewGroup, false);
        return new VersionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i)
    {

        Store store = storeList.get(i);

        versionViewHolder.name.setText(Helper.toCamelCase(store.name));
        /*versionViewHolder.distance.setText(String.valueOf(store.distance + "km"));

        if(store.delivery_status == 2)
        {
            versionViewHolder.delivery_charge.setText(String.valueOf("BOOKING AVAILABLE VIA CHAT"));
        }

        else if(store.delivery_status == 1)
        {

            if(store.delivery_charge == 0 && store.amount == 0)
            {
                versionViewHolder.delivery_charge.setText(String.valueOf("FREE HOME DELIVERY"));
            }

            else
            {
                versionViewHolder.delivery_charge.setText(String.valueOf("FREE DELIVERY ON PURCHASE Rs. " + df.format(store.amount)));
            }
        }

        else
        {
            versionViewHolder.delivery_charge.setText(String.valueOf("HOME DELIVERY NOT AVAILABLE"));
        }


        if(store.is_online == 0)
        {
            versionViewHolder.online.setTextColor(ContextCompat.getColor(context, R.color.myTextSecondaryColor));
            versionViewHolder.online.setText(String.valueOf("OFFLINE"));
        }

        else
        {
            versionViewHolder.online.setTextColor(ContextCompat.getColor(context, R.color.green));
            versionViewHolder.online.setText(String.valueOf("ONLINE"));
        }


        ShapeDrawable background = new ShapeDrawable();
        background.setShape(new OvalShape()); // or RoundRectShape()

        versionViewHolder.product_category.setText(store.getName().substring(0, 1).toUpperCase());

        String color = bgColors[i % bgColors.length];
        background.getPaint().setColor(Color.parseColor(color));

        versionViewHolder.product_category.setBackground(background);*/
    }


    @Override
    public int getItemCount()
    {
        return storeList == null ? 0 : storeList.size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView name;

        public VersionViewHolder(View itemView)
        {

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);

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