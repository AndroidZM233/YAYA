package speedata.com.yaya.dao;

import android.content.Context;

import com.elsw.base.db.orm.dao.ABaseDao;

import speedata.com.yaya.bean.Store;

/**
 * Created by 张明_ on 2016/8/18.
 */
public class StoreDao extends ABaseDao<Store> {

    public StoreDao(Context context) {
        super(new DBInsideHelper(context),  Store.class);
    }
}
