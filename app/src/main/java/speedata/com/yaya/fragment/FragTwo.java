package speedata.com.yaya.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import common.base.IBaseScanFragment;
import common.base.frag.BaseFragment;
import common.utils.ScanUtil;
import speedata.com.yaya.R;
import speedata.com.yaya.bean.Sell;
import speedata.com.yaya.bean.Store;
import speedata.com.yaya.dao.SellDao;
import speedata.com.yaya.dao.StoreDao;

/**
 * Created by 张明_ on 2016/8/17.
 */
public class FragTwo extends BaseFragment implements IBaseScanFragment{
    private Button btnOK;
    private EditText etCheckNum,edv_f2;
    private TextView tvName,tvSize,tvColor;
    private StoreDao storeDao;

    @Override
    public int setFragmentLayout() {
        return R.layout.frag_two;
    }

    @Override
    public void findById(View view) {
        tvColor= (TextView) view.findViewById(R.id.tv_f2_color);
        tvName= (TextView) view.findViewById(R.id.tv_f2_name);
        tvSize= (TextView) view.findViewById(R.id.tv_f2_size);
        edv_f2= (EditText) view.findViewById(R.id.edv_f2);
        etCheckNum= (EditText) view.findViewById(R.id.et_f2_checkNum);
        btnOK= (Button) view.findViewById(R.id.btn_f2_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(edv_f2.getText())){
                    showToast("请先扫描商品条码");
                    return;
                }

                String string = etCheckNum.getText().toString();
                if (TextUtils.isEmpty(string)){
                    showToast("盘点数不能为空");
                    return;
                }
                storeDao.execSql("update store set checkNum=? where BarCode=? "
                        , new String[]{string, mBarcode});

                showToast("盘点成功");

                clearn();
                edv_f2.setFocusable(true);
                edv_f2.setFocusableInTouchMode(true);
                edv_f2.requestFocus();
            }
        });
    }

    private void clearn() {
        tvColor.setText("");
        tvSize.setText("");
        tvName.setText("");
        etCheckNum.setText("");
    }

    private ScanUtil scanUtil;

    @Override
    public void onResume() {
        super.onResume();
        scanUtil = new ScanUtil(mActivity);
        scanUtil.setOnScanListener(new ScanUtil.OnScanListener() {

            @Override
            public void getBarcode(String barcode) {
                onGetBarcode(barcode);
            }
        });
    }

    @Override
    public void onPause() {
        scanUtil.stopScan();
        super.onPause();
    }


    //TODO 获取条码
    private String mBarcode;
    private Store storeBean;
    private List<Store> tempStore;

    @Override
    public void onGetBarcode(String barcode) {
        mBarcode = barcode.replace("\n", "");
        mBarcode = mBarcode.replace("\r", "");
        mBarcode = mBarcode.replace("\u0000","");
        edv_f2.setText(mBarcode);
        etCheckNum.setText("");
        storeDao = new StoreDao(mActivity);
        try {
            tempStore = storeDao.imQueryList("BarCode=?", new String[]{mBarcode});
            if (tempStore.size() > 0) {
                storeBean = tempStore.get(0);
                setUI(storeBean);
            }
        } catch (Exception e) {
            Toast.makeText(mActivity, "条码不匹配", Toast.LENGTH_SHORT).show();
        }

        etCheckNum.setFocusable(true);
        etCheckNum.setFocusableInTouchMode(true);
        etCheckNum.requestFocus();

    }

    private void setUI(Store storeBean) {
        tvSize.setText(storeBean.getSize());
        tvName.setText(storeBean.getName());
        tvColor.setText(storeBean.getColor());
    }
}
