package zebra.protector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import zebra.protector.R;
import zebra.protector.helper.OnTaskCompleted;
import zebra.protector.model.Address;


public class AddressRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private Context context = null;
    private OnItemClickListener clickListener;
    private OnTaskCompleted listener;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    public AddressRecyclerAdapter(Context context, OnTaskCompleted listener)
    {
        this.context = context;
        this.listener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {


        if(i == TYPE_HEADER)
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_item_new_address, viewGroup, false);
            return new VHHeader(view);
        }

        else if(i == TYPE_ITEM)
        {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerlist_address, viewGroup, false);
            return new VersionViewHolder(view);
        }

        throw new RuntimeException("there is no type that matches the type " + i + " + make sure your using types correctly");
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i)
    {

        if( i == 0)
        {

            /*if (holder instanceof VHHeader)
            {
                VHHeader header = (VHHeader) holder;
            }*/

            return;
        }

        if(holder instanceof VersionViewHolder)
        {

            VersionViewHolder versionViewHolder = (VersionViewHolder) holder;

            Address address = Address.addressList.get(i-1);


            StringBuilder sAddress = new StringBuilder().append(address.address.toUpperCase()).append(", ")
                    .append(address.landmark.toUpperCase()).append(", ").append(address.city.toUpperCase())
                    .append(", ").append(address.state.toUpperCase()).append(", ").append(address.pincode);


            versionViewHolder.name.setText(address.name.toUpperCase());
            versionViewHolder.address.setText(sAddress);
            versionViewHolder.phone_no.setText(String.valueOf("M# " + address.phone_no));

            versionViewHolder.btn_continue.setTag(i);
        }
    }


    @Override
    public int getItemCount()
    {
        return Address.addressList == null ? 0 : Address.addressList.size() + 1;
    }


    class VHHeader extends RecyclerView.ViewHolder
    {

        Button btnAddNew;

        public VHHeader(View itemView)
        {

            super(itemView);

            btnAddNew = (Button) itemView.findViewById(R.id.btnAddNew);
            btnAddNew.setOnClickListener(onButtonClickListener);
        }


        private View.OnClickListener onButtonClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                if(v.getId() == R.id.btnAddNew)
                {
                    listener.onTaskCompleted(true, 300, "add");
                }
            }
        };
    }


    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView name, address, phone_no;
        Button btn_continue;


        public VersionViewHolder(View itemView)
        {

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            address = (TextView) itemView.findViewById(R.id.address);
            phone_no = (TextView) itemView.findViewById(R.id.phone_no);
            btn_continue = (Button) itemView.findViewById(R.id.btnContinue);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {
            clickListener.onItemClick(v, getAdapterPosition());
        }
    }


    @Override
    public int getItemViewType(int position)
    {

        if(position == 0)
        {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
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