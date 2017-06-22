package speedata.com.yaya.bean;

import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Table;

/**
 * Created by 张明_ on 2016/8/18.
 */
@Table(name = "store")
public class Store extends Sell{
    @Column(name = "storeNum")
    private int storeNum;

    @Column(name = "checkTime")
    private String checkTime;
    @Column(name = "checkNum")
    private String checkNum;
    @Column(name = "num")
    private String num;

    public int getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(int storeNum) {
        this.storeNum = storeNum;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(String checkNum) {
        this.checkNum = checkNum;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
