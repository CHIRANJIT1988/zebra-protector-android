package zebra.protector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import zebra.protector.R;
import zebra.protector.model.MyCalendar;


public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.VersionViewHolder>
{

    private Context context = null;
    private OnItemClickListener clickListener;

    private List<MyCalendar> calendarList;


    public CalendarRecyclerAdapter(Context context, List<MyCalendar> calendarList)
    {
        this.context = context;
        this.calendarList = calendarList;
    }


    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerlist_item_calendar, viewGroup, false);
        return new VersionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i)
    {

        MyCalendar calendar = calendarList.get(i);

        versionViewHolder.day.setText(calendar.s_date);
        versionViewHolder.date.setText(String.valueOf(calendar.date));
        versionViewHolder.month.setText(calendar.s_month);
    }


    @Override
    public int getItemCount()
    {
        return calendarList == null ? 0 : calendarList.size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView day, date, month;

        public VersionViewHolder(View itemView)
        {

            super(itemView);

            day = (TextView) itemView.findViewById(R.id.day);
            date = (TextView) itemView.findViewById(R.id.date);
            month = (TextView) itemView.findViewById(R.id.month);

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