package speedata.com.yaya.dao;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.elsw.base.db.orm.dao.ABaseDao;

import speedata.com.yaya.bean.Sell;

/**
 * Created by 张明_ on 2016/8/18.
 */
public class SellDao extends ABaseDao<Sell> {

    public SellDao(Context context) {
        super(new DBInsideHelper(context),  Sell.class);
    }
}
