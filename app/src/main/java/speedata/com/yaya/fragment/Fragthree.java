package speedata.com.yaya.fragment;

import android.view.View;
import android.widget.ListView;

import java.util.List;

import common.adapter.CommonAdapter;
import common.adapter.ViewHolder;
import common.base.frag.BaseFragment;
import speedata.com.yaya.R;
import speedata.com.yaya.bean.Store;
import speedata.com.yaya.dao.StoreDao;

/**
 * Created by 张明_ on 2016/8/17.
 */
public class Fragthree extends BaseFragment {
    private ListView lv;
    @Override
    public int setFragmentLayout() {
        return R.layout.frag_three;
    }

    @Override
    public void findById(View view) {
        lv= (ListView) view.findViewById(R.id.lv_f3);
        StoreDao storeDao=new StoreDao(mActivity);
        final List<Store> stores = storeDao.imQueryList();
        CommonAdapter commonAdapter=new CommonAdapter(mActivity,stores,R.layout.adapter_f3) {
            @Override
            public void convert(ViewHolder helper, Object item, int position) {
                helper.setText(R.id.tv_f3_adName,stores.get(position)
                .getName());
                helper.setText(R.id.tv_f3_adChNum,stores.get(position)
                        .getCheckNum());
                helper.setText(R.id.tv_f3_adStNum,stores.get(position)
                        .getStoreNum()+"");
                helper.setText(R.id.tv_f3_adNum,stores.get(position)
                        .getNum()+"");
            }
        };
        lv.setAdapter(commonAdapter);
    }
}
