package speedata.com.yaya.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
public class FragOne extends BaseFragment implements IBaseScanFragment{

    private StoreDao storeDao;
    private TextView tvName,tvSize,tvColor;
    private EditText etSell,etPrice,edv_f1;
    private Button btnOK;

    @Override
    public int setFragmentLayout() {
        return R.layout.frag_one;
    }

    @Override
    public void findById(View view) {
        tvColor= (TextView) view.findViewById(R.id.tv_f1_color);
        tvName= (TextView) view.findViewById(R.id.tv_f1_name);
        tvSize= (TextView) view.findViewById(R.id.tv_f1_size);
        etPrice= (EditText) view.findViewById(R.id.et_f1_price);
        etSell= (EditText) view.findViewById(R.id.et_f1_sell);
        btnOK= (Button) view.findViewById(R.id.btn_f1_ok);
        edv_f1= (EditText) view.findViewById(R.id.edv_f1);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edv_f1.getText())){
                    showToast("请先扫描商品条码");
                    return;
                }
                if (TextUtils.isEmpty(etSell.getText())){
                    showToast("请输入销售数量");
                    return;
                }
                if (TextUtils.isEmpty(etPrice.getText())){
                    showToast("请输入商品售出价格");
                    return;
                }
                Sell sell=new Sell();
                SellDao sellDao=new SellDao(mActivity);
                sell.setColor(storeBean.getColor());
                sell.setName(storeBean.getName());
                sell.setPrice(etPrice.getText().toString());
                sell.setSize(storeBean.getSize());
                sell.setSellNum(etSell.getText().toString());
                sellDao.imInsert(sell);
                int sellNum= Integer.parseInt(etSell.getText().toString());
                int result=storeBean.getStoreNum()-sellNum;
                int selledNum=Integer.parseInt(storeBean.getNum());
                int newNum=selledNum+sellNum;
                storeDao.execSql("update store set storeNum=? where BarCode=? "
                        , new String[]{result+"", mBarcode});
                storeDao.execSql("update store set num=? where BarCode=? "
                        , new String[]{newNum+"", mBarcode});

                showToast("登记成功");

                clearn();

                edv_f1.setFocusable(true);
                edv_f1.setFocusableInTouchMode(true);
                edv_f1.requestFocus();
            }
        });
    }

    private void clearn() {
        tvColor.setText("");
        tvName.setText("");
        tvSize.setText("");
        etSell.setText("");
        etPrice.setText("");
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
        edv_f1.setFocusable(true);
        edv_f1.setFocusableInTouchMode(true);
        edv_f1.requestFocus();
        mBarcode = barcode.replace("\n", "");
        mBarcode = mBarcode.replace("\r", "");
        mBarcode = mBarcode.replace("\u0000","");
        edv_f1.setText(mBarcode);
        etPrice.setText("");
        etSell.setText("");
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

        etSell.setFocusable(true);
        etSell.setFocusableInTouchMode(true);
        etSell.requestFocus();


    }

    private void setUI(Store storeBean) {
        tvSize.setText(storeBean.getSize());
        tvName.setText(storeBean.getName());
        tvColor.setText(storeBean.getColor());
    }


}
