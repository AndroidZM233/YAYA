/*
 *
 * @author Echo
 * @created 2016.6.3
 * @email bairu.xu@speedatagroup.com
 * @version $version
 *
 */

package speedata.com.yaya.activity;

import android.content.Context;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import common.base.App;
import common.base.act.FragActBase;
import common.base.dialog.ToastUtils;
import common.event.ViewMessage;
import common.utils.StringUtil;
import speedata.com.yaya.R;
import speedata.com.yaya.bean.Store;
import speedata.com.yaya.dao.StoreDao;

import static common.utils.LogUtil.DEGUG_MODE;

@EActivity(R.layout.act_login)
public class LoginAct extends FragActBase {
    private static final String TAG = DEGUG_MODE ? "LoginAct" : LoginAct.class.getSimpleName();
    private static final boolean debug = true;
    @ViewById
    ImageView setting;
    @ViewById
    ImageView imageView7;
    @ViewById
    EditText telEt;
    @ViewById
    ImageView telEtClearbtn;
    @ViewById
    ImageView imageView8;
    @ViewById
    EditText pwdEt;
    @ViewById
    ImageView pwdEtClearbtn;
    @ViewById
    Button loginBtn;
    @ViewById
    TextView versionTx;
    @ViewById
    TextView deviceId;
    @ViewById
    CheckBox cb_loginRem;
    @ViewById
    Button exitBtn;


    private String userName;
    private String pwd;
    private boolean nofirst;

    @Click
    void setting() {

    }

    @Click
    void loginBtn() {
    openAct(MainAct.class,true);
    }

    @Click
    void exitBtn() {
        App.getInstance().exit();
    }

    @AfterViews
    protected void main() {
        telEt.setText("user");
        pwdEt.setText("1234");
        nofirst= Boolean.parseBoolean(getXml("first","false"));
        if (!nofirst){
            StoreDao storeDao=new StoreDao(mContext);
            Store store=new Store();
            store.setSize("XXL");
            store.setName("鸭鸭羽绒服男款");
            store.setColor("蓝色");
            store.setStoreNum(555);
            store.setNum("0");
            store.setBarCode("12345678");
            storeDao.imInsert(store);
            Store store1=new Store();
            store1.setSize("M");
            store1.setName("鸭鸭羽绒服女款");
            store1.setColor("红色");
            store1.setStoreNum(555);
            store1.setNum("0");
            store1.setBarCode("87654321");
            storeDao.imInsert(store1);
            setXml("first","true");
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    public boolean checkParms(String... parms) {
        if (parms != null && parms.length == 2) {
            if (StringUtil.isBlank(parms[0])) {
                ToastUtils.showShort("用户名不能为空");
                return false;
            }
//            if (StringUtil.isBlank(parms[1])) {
//                ToastUtils.showShort("密码为空");
//                return false;
//            }
            return true;
        } else {
            return false;
        }
    }


    @Override
    protected Context regieterBaiduBaseCount() {
        return null;
    }

    @Override
    protected void initTitlebar() {

    }

    @Override
    public void onEventMainThread(ViewMessage viewMessage) {

    }
}

