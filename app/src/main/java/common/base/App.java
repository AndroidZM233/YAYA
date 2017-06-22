/*
 *
 * @author Echo
 * @created 2016.6.3
 * @email bairu.xu@speedatagroup.com
 * @version $version
 *
 */


package common.base;

import android.app.Activity;
import android.os.Handler;
import java.util.LinkedList;
import java.util.List;
import common.base.dialog.ToastUtils;
import common.utils.NetUtil;

public class App extends BaseApplication  {

    private static App instance = null;

    public static App getInstance() {
        if (null == instance) {
            instance = new App();
        }
        return instance;

    }

    private List<Activity> activityList = new LinkedList<Activity>();

    public App() {
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    //遍历所有Activity并finish

    //结束整个应用程序
    public void exit() {

        //遍历 链表，依次杀掉各个Activity
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        //杀掉，这个应用程序的进程，释放 内存
        int id = android.os.Process.myPid();
        if (id != 0) {
            android.os.Process.killProcess(id);
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        NetUtil.init(this);
        ToastUtils.init(this, new Handler());
    }

    /**
     * 异常退出处理
     */
    @Override
    protected void onAppExceptionDestroy() {

    }

}
