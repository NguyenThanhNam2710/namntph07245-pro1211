package com.example.duan1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.duan1.R;
import com.example.duan1.activity.CachTinhBMIActivity;
import com.example.duan1.activity.ThemMT_DLActivity;
import com.example.duan1.databinding.FragmentTinhToanBinding;
import com.example.duan1.inteface.TinhToan_Interface;
import com.example.duan1.model.NguoiDung;
import com.example.duan1.presenter.TinhToan_Precenter;

import java.text.DecimalFormat;

public class TinhToanFragment extends Fragment implements TinhToan_Interface {

    private EditText edtTtName;
    private EditText edtTtAge;
    private Spinner spn_tt_Sex;
    private EditText edtTtHeight;
    private EditText edtTtWeight;
    private Button btnTtTinhToan;
    private CardView cvTtRefresh;
    private CardView cvTtNextDMT;
    private CardView cvTtNextBMI;

    private TinhToan_Precenter tinhToan_precenter;
    private FragmentTinhToanBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tinh_toan, container, false);
        init();
        tinhToan_precenter = new TinhToan_Precenter(this);
        binding.setTinhtoanprecenter(tinhToan_precenter);

        return binding.getRoot();
    }

    private void init() {

        edtTtName = (EditText) binding.getRoot().findViewById(R.id.edt_tt_Name);
        edtTtAge = (EditText) binding.getRoot().findViewById(R.id.edt_tt_Age);
        spn_tt_Sex = binding.getRoot().findViewById(R.id.spn_tt_Sex);
        edtTtHeight = (EditText) binding.getRoot().findViewById(R.id.edt_tt_Height);
        edtTtWeight = (EditText) binding.getRoot().findViewById(R.id.edt_tt_Weight);

        btnTtTinhToan = (Button) binding.getRoot().findViewById(R.id.btn_tt_TinhToan);

        cvTtRefresh = (CardView) binding.getRoot().findViewById(R.id.cv_tt_Refresh);
        cvTtNextDMT = (CardView) binding.getRoot().findViewById(R.id.cv_tt_NextDMT);
        cvTtNextBMI = (CardView) binding.getRoot().findViewById(R.id.cv_tt_NextBMI);
    }

    @Override
    public void setJob_btn_tt_TinhToan() {
        NguoiDung nguoiDung = new NguoiDung();
        nguoiDung.setTenND(edtTtName.getText().toString().trim());
        nguoiDung.setGioitinh(spn_tt_Sex.getSelectedItem().toString());
        nguoiDung.setTuoiND(edtTtAge.getText().toString().trim());
        nguoiDung.setChieucao(edtTtHeight.getText().toString().trim());
        nguoiDung.setCannang(edtTtWeight.getText().toString().trim());

        double tinh = tinhToan_precenter.tinhtoan(nguoiDung);
        if (tinh > 0) {

            DecimalFormat decimalFormat = new DecimalFormat("#.00");

            tinhToan_precenter.setStatusBMI(tinh);

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setView(R.layout.dialog_show_tt);
            final AlertDialog dialog = alertDialog.show();

            Button back = dialog.findViewById(R.id.btn_showTT_back);
            TextView tvTtShowBMI = dialog.findViewById(R.id.tv_showTT_ShowBMI);
            TextView tvTtShowStatus = (TextView) dialog.findViewById(R.id.tv_showTT_ShowStatus);
            TextView tvTtShowComment = (TextView) dialog.findViewById(R.id.tv_showTT_ShowComment);


            tvTtShowBMI.setText("Chỉ số BMI: " + decimalFormat.format(tinh));
            if (tinh < 18.5) {
                tvTtShowStatus.setText("Gầy");
                tvTtShowComment.setText("Hãy ăn các loại thức ăn chứa nhiều chất đạm (protein), chất béo tốt, tinh bột… để tăng cân nha !!!");
            } else if (tinh < 24.9) {
                tvTtShowStatus.setText("Trạng thái cơ thể: " + "Bình thường");
                tvTtShowComment.setText("Hãy ăn uống điều độ và tâp thể dục thường xuyên để phát huy nha !!!");
            } else if (tinh <= 25) {
                tvTtShowStatus.setText("Trạng thái cơ thể: " + "Thừa cân");
                tvTtShowComment.setText("Tình trạng của bạn chưa quá nặng, hãy ăn uống điều độ và tâp thể dục để lấy lại vóc dáng  !!!");
            } else if (tinh < 29.9) {
                tvTtShowStatus.setText("Trạng thái cơ thể: " + "Tiền béo phì");
                tvTtShowComment.setText("Bạn nên xây dựng chế độ ăn hợp lí hơn, chánh xa các món ăn dầu mỡ và nên tập gym để có chế độ luyện tập chuẩn xác nhất  !!!");

            } else if (tinh < 34.9) {
                tvTtShowStatus.setText("Trạng thái cơ thể: " + "Béo phì độ I");
                tvTtShowComment.setText("Bí đao luộc từ lâu đã được biết đến là một trong những loại thức ăn giảm cân có hiệu quả cao nhất. " +
                        "Trong bí đao chứa hàm lượng chất xơ và nước khi ăn bí đao bạn sẽ cảm thấy no lâu hơn," +
                        " cảm giác thèm ăn cũng giảm đi, bí đoa còn chứa một loại axit đặc biệt có tác dụng gây ức chế quá trình hình thành mỡ thừa dưới ra." +
                        " Bạn thử nó chưa !!!");
            } else if (tinh < 39.9) {
                tvTtShowStatus.setText("Trạng thái cơ thể: " + "Béo phì độ II");
                tvTtShowComment.setText("Tình trạng của bạn đang tệ đi, hãy ăn uống điều độ và tâp thể dục để lấy lại vóc dáng  !!!");
            } else {
                tvTtShowStatus.setText("Trạng thái cơ thể: " + "Béo phì độ III");
                tvTtShowComment.setText("Tình trạng của bạn rất tệ, hãy ăn uống điều độ và tâp thể dục để lấy lại vóc dáng  !!!");
            }
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    setJob_cv_tt_Refresh();
                }
            });
        }
    }

    @Override
    public void setJob_cv_tt_Refresh() {
        edtTtAge.setText("");
        edtTtHeight.setText("");
        edtTtName.setText("");
        spn_tt_Sex.setSelection(0);
        edtTtWeight.setText("");
    }

    @Override
    public void setJob_cv_tt_NextDMT() {
        Intent intent = new Intent(getActivity(), ThemMT_DLActivity.class);
        intent.putExtra("MT-DL", 0);
        startActivity(intent);

    }

    @Override
    public void setJob_cv_tt_NextBMI() {
        Intent intent = new Intent(getActivity(), CachTinhBMIActivity.class);
        startActivity(intent);

    }

    @Override
    public void setError_EmptyName() {
        edtTtName.setText("null");
    }

    @Override
    public void setError_EmptyAge() {
        edtTtAge.setText("null");
    }

    @Override
    public void setError_EmptySex() {
        spn_tt_Sex.setSelection(0);
    }

    @Override
    public void setError_EmptyWeight() {
        edtTtWeight.setError("Cân nặng không được để trống");
        edtTtWeight.requestFocus();
    }

    @Override
    public void setError_EmptyHeight() {
        edtTtHeight.setError("Chiều cao không được để trống");
        edtTtHeight.requestFocus();
    }

    @Override
    public void setStatusBMI(double statusBMI) {

    }

    @Override
    public void setError_AgeNotIsNumber() {
        edtTtAge.setError("Tuổi không đúng định dạng");
        edtTtAge.requestFocus();
    }

    @Override
    public void setError_HeightNotIsNumber() {
        edtTtHeight.setError("Chiều cao không đúng định dạng");
        edtTtHeight.requestFocus();
    }

    @Override
    public void setError_WeightNotIsNumber() {
        edtTtWeight.setError("Cân nặng không đúng định dạng");
        edtTtWeight.requestFocus();
    }

    @Override
    public void setError_falseAge() {
        edtTtAge.setError("Tuổi không được là số âm");
        edtTtAge.requestFocus();
    }

    @Override
    public void setError_falseWeight() {
        edtTtWeight.setError("Cân nặng không được là số âm");
        edtTtWeight.requestFocus();

    }

    @Override
    public void setError_falseHeight() {
        edtTtHeight.setError("Chiều cao không được là số âm");
        edtTtHeight.requestFocus();

    }
}
