package com.greg.mareu.reunion_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.greg.mareu.R;
import com.greg.mareu.di.DI;
import com.greg.mareu.dialog_box.ParticipantsListDialog;
import com.greg.mareu.events.DeleteReunionEvent;
import com.greg.mareu.events.ParticipantsListEvent;
import com.greg.mareu.model.Reunion;
import com.greg.mareu.service.ReunionApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListReunionActivity extends AppCompatActivity {

    private List<Reunion> mReunion;
    private ReunionApiService mApiService;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mApiService = DI.getReunionApiService();
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        initList();
    }

    private void initList() {
        mReunion = mApiService.getReunions();
        mRecyclerView.setAdapter(new ReunionRecyclerViewAdapter(mReunion));
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * If user click on delete button
     */

    @Subscribe
    public void onDeleteReunion(DeleteReunionEvent event) {
        mApiService.deleteReunion(event.reunion);
        initList();
    }

    @Subscribe
    public void onParticipantsList(ParticipantsListEvent event){
        openParticipantsDialog(event.reunion);
    }

    public void openParticipantsDialog(Reunion reunion){
        ParticipantsListDialog participantsListDialog = new ParticipantsListDialog(reunion);
        participantsListDialog.show(getSupportFragmentManager(), "exemple");
    }

    @OnClick(R.id.add_reunion)
    void addReunion(){AddReunionActivity.navigate(this);}
}
