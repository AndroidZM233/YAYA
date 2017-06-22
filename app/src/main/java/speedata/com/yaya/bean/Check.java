package speedata.com.yaya.bean;

import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Table;

/**
 * Created by 张明_ on 2016/8/18.
 */
@Table(name = "check")
public class Check {
    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private String size;

    @Column(name = "color")
    private String color;

    @Column(name = "sellNum")
    private String sellNum;
}
