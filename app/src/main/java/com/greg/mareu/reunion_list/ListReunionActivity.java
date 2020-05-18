package com.greg.mareu.reunion_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);
        return true;
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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.by_date:
                Toast.makeText(this, "Clic sur filtre par date", Toast.LENGTH_SHORT).show();
                break;
            case R.id.by_room:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
