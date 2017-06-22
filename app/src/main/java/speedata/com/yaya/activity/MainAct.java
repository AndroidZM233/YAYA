/*
 *
 * @author Echo
 * @created 2016.6.3
 * @email bairu.xu@speedatagroup.com
 * @version v1.0
 *
 */

package speedata.com.yaya.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import common.base.App;
import common.base.act.FragActBase;
import common.base.dialog.ToastUtils;
import common.event.ViewMessage;
import common.view.CustomTitlebar;
import speedata.com.yaya.R;
import speedata.com.yaya.fragment.FragOne;
import speedata.com.yaya.fragment.FragTwo;
import speedata.com.yaya.fragment.Fragthree;

import static common.utils.LogUtil.DEGUG_MODE;

@EActivity(R.layout.act_main)
public class MainAct extends FragActBase {
    private static final String TAG = DEGUG_MODE ? "MainAct" : MainAct.class.getSimpleName();
    private static final boolean debug = true;

    @ViewById
    CustomTitlebar titlebar;
    @ViewById
    LinearLayout main_ll1;
    @ViewById
    LinearLayout main_ll2;
    @ViewById
    LinearLayout main_ll3;
    @ViewById
    TextView tv1;
    @ViewById
    TextView tv2;
    @ViewById
    TextView tv3;

    @Click
    void main_ll1(){
        openFragment(new FragOne());
        main_ll1.setBackgroundColor(Color.parseColor("#ffffff"));
        main_ll2.setBackgroundColor(Color.parseColor("#D7D2D2"));
        main_ll3.setBackgroundColor(Color.parseColor("#D7D2D2"));
    }
    @Click
    void main_ll2(){
        openFragment(new FragTwo());
        main_ll1.setBackgroundColor(Color.parseColor("#D7D2D2"));
        main_ll2.setBackgroundColor(Color.parseColor("#ffffff"));
        main_ll3.setBackgroundColor(Color.parseColor("#D7D2D2"));
    }
    @Click
    void main_ll3(){
        openFragment(new Fragthree());
        main_ll1.setBackgroundColor(Color.parseColor("#D7D2D2"));
        main_ll2.setBackgroundColor(Color.parseColor("#D7D2D2"));
        main_ll3.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    public void openFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        // 提交事物
        transaction.commit();
    }




    @AfterViews
    protected void main() {
        App.getInstance().addActivity(MainAct.this);
        setSwipeEnable(false);
        openFragment(new FragOne());
    }

    @Override
    protected Context regieterBaiduBaseCount() {
        return null;
    }

    @Override
    protected void initTitlebar() {
        titlebar.setTitlebarStyle(CustomTitlebar.TITLEBAR_STYLE_NORMAL);
        titlebar.setAttrs(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setXml("BACK","back");
                openAct(LoginAct.class,true);
            }
        }, "主界面", null);
    }

    @Override
    public void onEventMainThread(ViewMessage viewMessage) {

    }

    private long mkeyTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.ACTION_DOWN:
//                if ((System.currentTimeMillis() - mkeyTime) > 2000) {
//                    mkeyTime = System.currentTimeMillis();
//                    ToastUtils.showLong("再按一次退出");
//                } else {
//                    try {
//                        pressHome();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                openAct(LoginAct.class,true);
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}

