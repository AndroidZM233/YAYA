package speedata.com.yaya.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import speedata.com.yaya.R;

public class MainYaYaAct extends FragmentActivity {

    public static MainYaYaAct instance = null;

    private ViewPager mTabPager;
    private ImageView mTabImg;// 动画图片
    private ImageView mTab1, mTab2, mTab3, mTab4;
    private int zero = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int one;//单个水平动画位移
    private int two;
    private int three;
    private LinearLayout ll1, ll2, ll3, ll4;
    private LinearLayout mCloseBtn;
    private View layout;
    private boolean menu_display = false;
    private PopupWindow menuWindow;
    private LayoutInflater inflater;
    private Fragment fragment1, fragment2, fragment3;
    private List<Fragment> fragLists;
    private int displayWidth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_yaya);
        //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        instance = this;

        mTabPager = (ViewPager) findViewById(R.id.tabpager);
        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());

        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        mTab1 = (ImageView) findViewById(R.id.img_weixin);
        mTab2 = (ImageView) findViewById(R.id.img_address);
        mTab3 = (ImageView) findViewById(R.id.img_friends);
        mTab4 = (ImageView) findViewById(R.id.img_settings);
        mTabImg = (ImageView) findViewById(R.id.img_tab_now);
        ll1.setOnClickListener(new MyOnClickListener(0));
        ll2.setOnClickListener(new MyOnClickListener(1));
        ll3.setOnClickListener(new MyOnClickListener(2));
        ll4.setOnClickListener(new MyOnClickListener(3));
        Display currDisplay = getWindowManager().getDefaultDisplay();//获取屏幕当前分辨率
        displayWidth = currDisplay.getWidth();
        int displayHeight = currDisplay.getHeight();
        one = displayWidth / 4; //设置水平动画平移大小
        two = one * 2;
        three = one * 3;
        //Log.i("info", "获取的屏幕分辨率为" + one + two + three + "X" + displayHeight);

        //InitImageView();//使用动画
        //将要分页显示的View装入数组中
        fragLists = new ArrayList<Fragment>();
        fragment1 = new Fragment();
        fragment2 = new Fragment();
        fragment3 = new Fragment();

        //每个页面的view数据
        fragLists.add(fragment1);
        fragLists.add(fragment2);
        fragLists.add(fragment3);
        fragLists.add(fragment1);


        mTabPager.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));
    }

    //填充ViewPager的数据适配器
    class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {

        public MyFrageStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragLists.get(position);
        }

        @Override
        public int getCount() {
            return fragLists.size();
        }

        /**
         * 每次更新完成ViewPager的内容后，调用该接口，此处复写主要是为了让导航按钮上层的覆盖层能够动态的移动
         */
        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);//这句话要放在最前面，否则会报错
            //获取当前的视图是位于ViewGroup的第几个位置，用来更新对应的覆盖层所在的位置
            int currentItem = mTabPager.getCurrentItem();
            if (currentItem == currIndex) {
                return;
            }
            imageMove(mTabPager.getCurrentItem());
            currIndex = mTabPager.getCurrentItem();
        }

    }

    /**
     * 移动覆盖层
     *
     * @param moveToTab 目标Tab，也就是要移动到的导航选项按钮的位置
     *                  第一个导航按钮对应0，第二个对应1，以此类推
     */
    private void imageMove(int moveToTab) {
        int startPosition = 0;
        int movetoPosition = 0;

        startPosition = currIndex * (displayWidth / 4);
        movetoPosition = moveToTab * (displayWidth / 4);
        //平移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(startPosition, movetoPosition, 0, 0);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(200);
        mTabImg.startAnimation(translateAnimation);
    }

    //手动设置ViewPager要显示的视图
    private void changeView(int desTab) {
        mTabPager.setCurrentItem(desTab, true);
    }

    /**
     * 头标点击监听
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mTabPager.setCurrentItem(index, true);
        }
    }

    ;

    /* 页卡切换监听(原作者:D.Winter)
    */
    public class MyOnPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    mTab1.setImageDrawable(getResources().getDrawable(R.drawable.get3));
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                        mTab2.setImageDrawable(getResources().getDrawable(R.drawable.get2));
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                        mTab3.setImageDrawable(getResources().getDrawable(R.drawable.get2));
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(three, 0, 0, 0);
                        mTab4.setImageDrawable(getResources().getDrawable(R.drawable.get2));
                    }
                    break;
                case 1:
                    mTab2.setImageDrawable(getResources().getDrawable(R.drawable.get3));
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(zero, one, 0, 0);
                        mTab1.setImageDrawable(getResources().getDrawable(R.drawable.get2));
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                        mTab3.setImageDrawable(getResources().getDrawable(R.drawable.get2));
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(three, one, 0, 0);
                        mTab4.setImageDrawable(getResources().getDrawable(R.drawable.get2));
                    }
                    break;
                case 2:
                    mTab3.setImageDrawable(getResources().getDrawable(R.drawable.get3));
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(zero, two, 0, 0);
                        mTab1.setImageDrawable(getResources().getDrawable(R.drawable.get2));
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                        mTab2.setImageDrawable(getResources().getDrawable(R.drawable.get2));
                    } else if (currIndex == 3) {
                        animation = new TranslateAnimation(three, two, 0, 0);
                        mTab4.setImageDrawable(getResources().getDrawable(R.drawable.get2));
                    }
                    break;
                case 3:
                    mTab4.setImageDrawable(getResources().getDrawable(R.drawable.get3));
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(zero, three, 0, 0);
                        mTab1.setImageDrawable(getResources().getDrawable(R.drawable.get2));
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, three, 0, 0);
                        mTab2.setImageDrawable(getResources().getDrawable(R.drawable.get2));
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, three, 0, 0);
                        mTab3.setImageDrawable(getResources().getDrawable(R.drawable.get2));
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(150);
            mTabImg.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  //获取 back键

            if (menu_display) {         //如果 Menu已经打开 ，先关闭Menu
                menuWindow.dismiss();
                menu_display = false;
            } else {
                Intent intent = new Intent();
                intent.setClass(MainYaYaAct.this, MainAct.class);
                startActivity(intent);
            }
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {   //获取 Menu键
            if (!menu_display) {
                //获取LayoutInflater实例
                inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
                //这里的main布局是在inflate中加入的哦，以前都是直接this.setContentView()的吧？呵呵
                //该方法返回的是一个View的对象，是布局中的根
                layout = inflater.inflate(R.layout.main_menu, null);

                //下面我们要考虑了，我怎样将我的layout加入到PopupWindow中呢？？？很简单
                menuWindow = new PopupWindow(layout, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT); //后两个参数是width和height
                //menuWindow.showAsDropDown(layout); //设置弹出效果
                //menuWindow.showAsDropDown(null, 0, layout.getHeight());
                menuWindow.showAtLocation(this.findViewById(R.id.mainweixin), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                //如何获取我们main中的控件呢？也很简单
//				mClose = (LinearLayout)layout.findViewById(R.id.menu_close);
                mCloseBtn = (LinearLayout) layout.findViewById(R.id.menu_close_btn);


                //下面对每一个Layout进行单击事件的注册吧。。。
                //比如单击某个MenuItem的时候，他的背景色改变
                //事先准备好一些背景图片或者颜色
                mCloseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        //Toast.makeText(Main.this, "退出", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setClass(MainYaYaAct.this, MainAct.class);
                        startActivity(intent);
                        menuWindow.dismiss(); //响应点击事件之后关闭Menu
                    }
                });
                menu_display = true;
            } else {
                //如果当前已经为显示状态，则隐藏起来
                menuWindow.dismiss();
                menu_display = false;
            }

            return false;
        }
        return false;
    }
    //设置标题栏右侧按钮的作用
//	public void btnmainright(View v) {
//		Intent intent = new Intent (MainWeixin.this,MainTopRightDialog.class);
//		startActivity(intent);
//		//Toast.makeText(getApplicationContext(), "点击了功能按钮", Toast.LENGTH_LONG).show();
//	}
//	public void startchat(View v) {      //小黑  对话界面
//		Intent intent = new Intent (MainWeixin.this,ChatActivity.class);
//		startActivity(intent);
//		//Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
//	}
//	public void exit_settings(View v) {                           //退出  伪“对话框”，其实是一个activity
//		Intent intent = new Intent (MainWeixin.this,ExitFromSettings.class);
//		startActivity(intent);
//	}
//	public void btn_shake(View v) {                                   //手机摇一摇
//		Intent intent = new Intent (MainWeixin.this,ShakeActivity.class);
//		startActivity(intent);
//	}
}
    
    

