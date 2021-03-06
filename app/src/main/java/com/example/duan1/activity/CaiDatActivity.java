package com.example.duan1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.databinding.ActivityCaiDatBinding;
import com.example.duan1.inteface.CaiDat_Interface;
import com.example.duan1.presenter.CaiDat_Precenter;

public class CaiDatActivity extends AppCompatActivity implements CaiDat_Interface {
    private Switch sCdNnCan;
    private Switch sCdNnChuongbao;
    private CaiDat_Precenter caiDat_precenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCaiDatBinding activityCaiDatBinding = DataBindingUtil.setContentView(this, R.layout.activity_cai_dat);
        Toolbar toolbar = findViewById(R.id.toolbarCD);
        toolbar.setTitle("Cài Đặt");
        setSupportActionBar(toolbar);

        caiDat_precenter = new CaiDat_Precenter(this);

        sCdNnCan = (Switch) findViewById(R.id.s_cd_nn_can);
        sCdNnChuongbao = (Switch) findViewById(R.id.s_cd_nn_chuongbao);


        activityCaiDatBinding.setCaidatprecenter(caiDat_precenter);
    }

    @Override
    public void setJob_s_cd_nn_can(boolean x) {
        if (x) {
            Toast.makeText(this, "setJob_s_cd_nn_can is on", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "setJob_s_cd_nn_can is off", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setJob_s_cd_nn_chuongbao(boolean x) {
        if (x) {
            Toast.makeText(this, "setJob_s_cd_nn_chuongbao is on", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "setJob_s_cd_nn_chuongbao is off", Toast.LENGTH_SHORT).show();
        }
    }
}
