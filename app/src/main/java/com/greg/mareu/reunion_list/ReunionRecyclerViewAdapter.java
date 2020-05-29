package com.greg.mareu.reunion_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.greg.mareu.R;
import com.greg.mareu.events.DeleteReunionEvent;
import com.greg.mareu.events.ParticipantsListEvent;
import com.greg.mareu.model.Reunion;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReunionRecyclerViewAdapter extends RecyclerView.Adapter<ReunionRecyclerViewAdapter.ViewHolder>
{
    private final List<Reunion> mReunion;
    private  ListReunionActivity mListReunionActivity;

    public ReunionRecyclerViewAdapter(List<Reunion> mReunion)
    {
        this.mReunion = mReunion;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reunion_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Reunion reunion = mReunion.get(position);
        Glide.with(holder.mImage.getContext())
                .load(reunion.getReunionAvatar())
                .into(holder.mImage);

        holder.mAbout.setText(reunion.getAboutReunion());
        holder.mRoom.setText(reunion.getRoom());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.mDay.setText(simpleDateFormat.format(reunion.getDay()));
        holder.mStartHour.setText(reunion.getStartTime());
        holder.mEndHour.setText(reunion.getEndTime());

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EventBus.getDefault().post(new DeleteReunionEvent(reunion));
            }
        });

        holder.mOpenParticipantsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ParticipantsListEvent(reunion));
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mReunion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.reunion_image) ImageView mImage;
        @BindView(R.id.reunion_about) TextView mAbout;
        @BindView(R.id.reunion_room) TextView mRoom;
        @BindView(R.id.startReunion) TextView mStartHour;
        @BindView(R.id.endReunion) TextView mEndHour;
        @BindView(R.id.reunion_day) TextView mDay;
        @BindView(R.id.delete_button) ImageButton mDelete;
        @BindView(R.id.openParticipantsList) RelativeLayout mOpenParticipantsListButton;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
