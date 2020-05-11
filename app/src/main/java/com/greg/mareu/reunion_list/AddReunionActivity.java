package com.greg.mareu.reunion_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.greg.mareu.R;
import com.greg.mareu.di.DI;
import com.greg.mareu.model.Reunion;
import com.greg.mareu.service.ReunionApiService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddReunionActivity extends AppCompatActivity
{
    @BindView(R.id.addImage) CircleImageView image;
    @BindView(R.id.addAboutLyt) TextInputLayout aboutInput;
    @BindView(R.id.addDayLyt) TextInputLayout dayInput;
    @BindView(R.id.addHourLyt) TextInputLayout hourInput;
    @BindView(R.id.addRoomLyt) TextInputLayout roomInput;
    @BindView(R.id.addParticipantsLyt) TextInputLayout participantsInput;
    @BindView(R.id.create) Button addButton;

    private String mRandomImage;
    private ReunionApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getReunionApiService();
        init();
    }

    public void init() {
        mRandomImage = randomImage();
        Glide.with(this).load(mRandomImage).into(image);
        aboutInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) { addButton.setEnabled(s.length() > 0);}
        });
    }

    @OnClick(R.id.create)
    void addReunion() {
        Reunion reunion = new Reunion(
                System.currentTimeMillis(),
                mRandomImage,
                aboutInput.getEditText().getText().toString(),
                dayInput.getEditText().getText().toString(),
                hourInput.getEditText().getText().toString(),
                roomInput.getEditText().getText().toString(),
                participantsInput.getEditText().getText().toString()
        );
        mApiService.createReunion(reunion);
        finish();
    }

    /**
     * Generate a random image
     * @return String
     */

    String randomImage() { return "https://placeimg.com/640/480/any" + System.currentTimeMillis(); }

    /**
     * Used to navigate to this AddReunionActivity
     * @param listReunionActivity
     */
    public static void navigate(ListReunionActivity listReunionActivity) {
        Intent i = new Intent(listReunionActivity, AddReunionActivity.class);
        ActivityCompat.startActivity(listReunionActivity, i, null);
    }


}
