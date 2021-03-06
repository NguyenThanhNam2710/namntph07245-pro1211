package com.example.duan1.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.R;
import com.example.duan1.adapter.MucTieuAdapter;
import com.example.duan1.dao.LichTrinhDao;
import com.example.duan1.dao.MucTieuDao;
import com.example.duan1.databinding.ActivityThemMtDlBinding;
import com.example.duan1.fragment.MucTieuFragment;
import com.example.duan1.inteface.AddMTDLActivity_Interface;
import com.example.duan1.model.LichTrinh;
import com.example.duan1.model.MucTieu;
import com.example.duan1.presenter.AddMTDLActivity_Precenter;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.provider.Settings.System.DATE_FORMAT;

public class ThemMT_DLActivity extends AppCompatActivity implements AddMTDLActivity_Interface {
    private EditText edtMtdlTitle;
    private EditText edtMtdlStartMT;
    private EditText edtMtdlEndMT;
    private ImageView imgMtdlRefresh;
    private ImageView imgMtdlDelete;
    private ImageView imgMtdlAdd;
    private EditText edtMtdlContent;
    private EditText edt_mtdl_startDL;
    private AddMTDLActivity_Precenter mtdlActivity_precenter;
    private ActivityThemMtDlBinding binding;
    private int mtdl;
    private MucTieuDao mucTieuDao;
    private LichTrinhDao lichTrinhDao;

    private LinearLayout lnMtdlRefresh;
    private LinearLayout lnMtdlDel;
    private LinearLayout lnMtdlInsert;

    private String sMT = "", eMT = "", sDL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_them_mt__dl);
        Toolbar toolbar = findViewById(R.id.toolbarMTDL);
        mucTieuDao = new MucTieuDao(this);
        lichTrinhDao = new LichTrinhDao(this);
        mtdl = getIntent().getIntExtra("MT-DL", -1);
        TextView tv_MTDL_ = binding.getRoot().findViewById(R.id.tv_MTDL_);
        LinearLayout ln_mtdl_MT = binding.getRoot().findViewById(R.id.ln_mtdl_MT);
        LinearLayout ln_mtdl_DL = binding.getRoot().findViewById(R.id.ln_mtdl_DL);

        lnMtdlRefresh = (LinearLayout) binding.getRoot().findViewById(R.id.ln_mtdl_refresh);
        lnMtdlDel = (LinearLayout) binding.getRoot().findViewById(R.id.ln_mtdl_del);
        lnMtdlInsert = (LinearLayout) binding.getRoot().findViewById(R.id.ln_mtdl_insert);


        if (mtdl == 0) {
            toolbar.setTitle("Mục Tiêu");
            tv_MTDL_.setText("TÊN MỤC TIÊU");
            ln_mtdl_MT.setVisibility(View.VISIBLE);
            ln_mtdl_DL.setVisibility(View.GONE);


        } else {
            toolbar.setTitle("Đặt Lịch");
            tv_MTDL_.setText("TÊN LỊCH HẸN");
            ln_mtdl_MT.setVisibility(View.GONE);
            ln_mtdl_DL.setVisibility(View.VISIBLE);
        }
        setSupportActionBar(toolbar);


        init();
        mtdlActivity_precenter = new AddMTDLActivity_Precenter(this);
        binding.setAddMTDLprecenter(mtdlActivity_precenter);

        edtMtdlStartMT.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ThemMT_DLActivity.this);
                alertDialog.setView(R.layout.dialog_select_date);
                final AlertDialog dialog = alertDialog.show();
                DatePicker dpCalendar = dialog.findViewById(R.id.dpCalendar);
                Calendar calendar = Calendar.getInstance();

                // Lấy ra năm - tháng - ngày hiện tại
                int year = calendar.get(calendar.YEAR);
                final int month = calendar.get(calendar.MONTH);
                int day = calendar.get(calendar.DAY_OF_MONTH);

                // Khởi tạo sự kiện lắng nghe khi DatePicker thay đổi
                dpCalendar.init(year, month, day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        Toast.makeText(ThemMT_DLActivity.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();
                        sMT = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        edtMtdlStartMT.setText(sMT);
                        dialog.dismiss();
                    }
                });

                return false;
            }
        });

        edtMtdlEndMT.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ThemMT_DLActivity.this);
                alertDialog.setView(R.layout.dialog_select_date);
                final AlertDialog dialog = alertDialog.show();
                DatePicker dpCalendar = dialog.findViewById(R.id.dpCalendar);
                Calendar calendar = Calendar.getInstance();

                // Lấy ra năm - tháng - ngày hiện tại
                int year = calendar.get(calendar.YEAR);
                final int month = calendar.get(calendar.MONTH);
                int day = calendar.get(calendar.DAY_OF_MONTH);

                // Khởi tạo sự kiện lắng nghe khi DatePicker thay đổi
                dpCalendar.init(year, month, day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        Toast.makeText(ThemMT_DLActivity.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();
                        eMT = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        edtMtdlEndMT.setText(eMT);
                        dialog.dismiss();
                    }
                });
                return false;
            }
        });

        edt_mtdl_startDL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ThemMT_DLActivity.this);
                alertDialog.setView(R.layout.dialog_select_date);
                final AlertDialog dialog = alertDialog.show();
                DatePicker dpCalendar = dialog.findViewById(R.id.dpCalendar);
                Calendar calendar = Calendar.getInstance();

                // Lấy ra năm - tháng - ngày hiện tại
                int year = calendar.get(calendar.YEAR);
                final int month = calendar.get(calendar.MONTH);
                int day = calendar.get(calendar.DAY_OF_MONTH);

                // Khởi tạo sự kiện lắng nghe khi DatePicker thay đổi
                dpCalendar.init(year, month, day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        Toast.makeText(ThemMT_DLActivity.this, dayOfMonth + "-" + (monthOfYear + 1) + "-" + year, Toast.LENGTH_SHORT).show();
                        sDL = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        edt_mtdl_startDL.setText(sDL);
                        dialog.dismiss();
                    }
                });
                return false;
            }
        });
    }

    private void init() {
        edtMtdlTitle = (EditText) findViewById(R.id.edt_mtdl_title);
        edtMtdlStartMT = (EditText) findViewById(R.id.edt_mtdl_startMT);
        edtMtdlEndMT = (EditText) findViewById(R.id.edt_mtdl_endMT);
        imgMtdlRefresh = (ImageView) findViewById(R.id.img_mtdl_refresh);
        imgMtdlDelete = (ImageView) findViewById(R.id.img_mtdl_delete);
        imgMtdlAdd = (ImageView) findViewById(R.id.img_mtdl_add);
        edtMtdlContent = (EditText) findViewById(R.id.edt_mtdl_content);
        edt_mtdl_startDL = findViewById(R.id.edt_mtdl_startDL);
    }


    @Override
    public void setJob_imgMtdlRefresh() {
        edtMtdlTitle.setText("");
        edtMtdlStartMT.setText("");
        edtMtdlEndMT.setText("");
        edt_mtdl_startDL.setText("");
        edtMtdlContent.setText("");
    }

    @Override
    public void setJob_imgMtdlDelete() {
        Toast.makeText(this, "delete if had data", Toast.LENGTH_SHORT).show();
    }

    private Date stringToDate(String aDate) {
        Date stringDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            stringDate = simpleDateFormat.parse(aDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stringDate;
    }

    public static boolean isDateValid(String date) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public void setJob_imgMtdlAdd() {
        String title = edtMtdlTitle.getText().toString().trim();
        String mtStart = edtMtdlStartMT.getText().toString().trim();
        String mtEnd = edtMtdlEndMT.getText().toString().trim();
        String dlStart = edt_mtdl_startDL.getText().toString().trim();
        String content = edtMtdlContent.getText().toString().trim();

        Calendar calendar = Calendar.getInstance();

        // Lấy ra năm - tháng - ngày hiện tại
        int year = calendar.get(calendar.YEAR);
        final int month = calendar.get(calendar.MONTH) + 1;
        int day = calendar.get(calendar.DAY_OF_MONTH);

        String today = day + "/" + month + "/" + year;
        if (mtdl == 0) {
            if (title.isEmpty()) {
                edtMtdlTitle.setError("Hãy đặt tên cho mục tiêu");
                edtMtdlTitle.requestFocus();
                return;
            } else if (content.isEmpty()) {
                edtMtdlContent.setError("Nội dung chưa có gì");
                edtMtdlContent.requestFocus();
                return;
            } else if (!isDateValid(mtStart)) {
                edtMtdlStartMT.setError("Ngày sai định dạng(ngày/tháng/năm)");
                edtMtdlStartMT.setText("");
                edtMtdlStartMT.requestFocus();
                return;
            } else if (stringToDate(mtStart).before(stringToDate(today))) {
                edtMtdlStartMT.setError("Ngày bắt đầu không nhỏ hơn hôm nay");
                edtMtdlStartMT.setText("");
                edtMtdlStartMT.requestFocus();
                return;
            } else if (!isDateValid(mtEnd)) {
                edtMtdlEndMT.setError("Ngày sai định dạng(ngày/tháng/năm)");
                edtMtdlEndMT.setText("");
                edtMtdlEndMT.requestFocus();
                return;
            } else if (stringToDate(mtEnd).before(stringToDate(mtStart))) {
                edtMtdlEndMT.setError("Ngày kết thúc phải lớn hơn ngày bắt đầu");
                edtMtdlEndMT.setText("");
                edtMtdlEndMT.requestFocus();
                return;
            } else {
                MucTieu mucTieu = new MucTieu(title, content, mtStart, mtEnd);
                boolean result = mucTieuDao.insertMT(mucTieu);
                if (result) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Tạo mục tiêu không thành công", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            if (title.isEmpty()) {
                edtMtdlTitle.setError("Hãy đặt tên cho lịch hẹn");
                edtMtdlTitle.requestFocus();
                return;
            } else if (content.isEmpty()) {
                edtMtdlContent.setError("Nội dung chưa có gì");
                edtMtdlContent.requestFocus();
                return;
            } else if (!isDateValid(dlStart)) {
                edt_mtdl_startDL.setError("Ngày sai định dạng(ngày/tháng/năm)");
                edt_mtdl_startDL.requestFocus();
                return;
            } else if (stringToDate(dlStart).before(stringToDate(today))) {
                edt_mtdl_startDL.setError("Ngày diễn ra không nhỏ hơn hôm nay");
                edt_mtdl_startDL.setText("");
                edt_mtdl_startDL.requestFocus();
                return;
            } else {
                LichTrinh lichTrinh = new LichTrinh(title, content, dlStart);
                boolean result = lichTrinhDao.insertLT(lichTrinh);
                if (result) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Tạo lịch trình không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
