package com.ycl.sportsing.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.ycl.sportsing.R;
import com.ycl.sportsing.adapter.ShouyeMenuAdapter;
import com.ycl.sportsing.domain.ADInfo;
import com.ycl.sportsing.myview.MyGridView;
import com.ycl.sportsing.utils.CycleViewPager;
import com.ycl.sportsing.utils.CycleViewPager.ImageCycleViewListener;
import com.ycl.sportsing.utils.ViewFactory;

import java.util.ArrayList;
import java.util.List;

public class ShouYeActivity extends Activity implements View.OnClickListener{

    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;
    private ShouyeMenuAdapter shouyeMenuAdapter;
    private MyGridView myShouYeMenuGridView;

    private ImageView img_xuechang;
    private ImageView img_xuepiao;
    private ImageView img_tianqi;
    private TextView text_xuechang;
    private TextView text_xuepiao;
    private TextView  text_tianqi;
    private LinearLayout linearlayout_xuechang;
    private LinearLayout linearlayout_xuepiao;
    private LinearLayout linearlayout_tianqi;

    private String[] imageUrls = {"http://pic4.nipic.com/20090920/3315621_103454041272_2.jpg",
            "http://img.taopic.com/uploads/allimg/110305/2136-11030513443995.jpg",
            "http://pic3.40017.cn/scenery/destination/2015/05/18/15/BimdHf.jpg",
            "http://www.people.com.cn/mediafile/pic/20141126/70/15506572524836385226.jpg",
            "http://photo.66diqiu.com/uploads/749/ue/image/20150126/1422236023769525.jpg"};

    // shouye中gridView菜单数据源
    private int[] i = new int[] { R.mipmap.shouye_menu_1,R.mipmap.shouye_menu_2,
            R.mipmap.shouye_menu_3,R.mipmap.shouye_menu_4,
            R.mipmap.shouye_menu_5,R.mipmap.shouye_menu_6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_ye);
        configImageLoader();
        initialize();
        initialize2();


    }

    /**
     * 初始化组件
     */
    private void initialize2(){
        myShouYeMenuGridView=(MyGridView)this.findViewById(R.id.gridview_menu);
        shouyeMenuAdapter=new ShouyeMenuAdapter(this,i);
        myShouYeMenuGridView.setAdapter(shouyeMenuAdapter);
        myShouYeMenuGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                // TODO Auto-generated method stub
                switch (position) {

                    case 0:// 雪友小视频
                        Intent intent0 = new Intent(ShouYeActivity.this,
                                ShipinActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:// 新人驾到
                        Intent intent1 = new Intent(ShouYeActivity.this,
                                ForTestActivity.class);
                        intent1.putExtra("flag", 2);
                        startActivity(intent1);
                        break;
                    case 2:// 滑雪新手指南
                        Intent intent2 = new Intent(ShouYeActivity.this,
                                NewBieGuideActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:// 雪场攻略
                        Intent intent3 = new Intent(ShouYeActivity.this,
                                WebViewActivity.class);
                        intent3.putExtra("title", "雪场攻略");
                        intent3.putExtra("webUrl","http://jingyan.baidu.com/article/a65957f48c2ab224e77f9b5b.html");
                        startActivity(intent3);
                        break;
                    case 4:// 雪场动态
                        Intent intent4 = new Intent(ShouYeActivity.this,
                                DongTaiActivity.class);
                        intent4.putExtra("flag", 1);
                        startActivity(intent4);
                        break;
                    case 5:// 二手交易
                        Intent intent5 = new Intent(ShouYeActivity.this,
                                SecondHandActivity.class);
                        intent5.putExtra("flag", 7);
                        startActivity(intent5);
                        break;


                    default:
                        break;
                }
            }
        });

        img_xuechang =(ImageView) findViewById(R.id.img_xuechang);
        img_xuepiao=(ImageView) findViewById(R.id.img_xuepiao);
        img_tianqi=(ImageView) findViewById(R.id.img_tianqi);
        img_xuechang.setOnClickListener(this);
        img_xuepiao.setOnClickListener(this);
        img_tianqi.setOnClickListener(this);

        linearlayout_xuechang =(LinearLayout) findViewById(R.id.linearlayout_xuechang);
        linearlayout_xuepiao=(LinearLayout) findViewById(R.id.linearlayout_xuepiao);
        linearlayout_tianqi=(LinearLayout) findViewById(R.id.linearlayout_tianqi);

        linearlayout_xuechang.setOnClickListener(this);
        linearlayout_xuepiao.setOnClickListener(this);
        linearlayout_tianqi.setOnClickListener(this);
    }

    /**
     * 初始化循环Banner组件
     */
    @SuppressLint("NewApi")
    private void initialize() {

        cycleViewPager = (CycleViewPager) getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);

        for(int i = 0; i < imageUrls.length; i ++){
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i );
            infos.add(info);
        }

        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        //设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }

    private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
                Toast.makeText(ShouYeActivity.this,
                        "position-->" + info.getContent(), Toast.LENGTH_SHORT)
                        .show();
            }

        }

    };

    /**
     * 配置ImageLoder
     */
    private void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.mipmap.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearlayout_xuechang:
            case R.id.img_xuechang:
                startActivity(new Intent(ShouYeActivity.this,XuechangGuideActivity.class));
                break;

            case R.id.linearlayout_xuepiao:
            case R.id.img_xuepiao:
                startActivity(new Intent(ShouYeActivity.this,XuePiaoActivity.class));
                break;

            case R.id.linearlayout_tianqi:
            case R.id.img_tianqi:
                startActivity(new Intent(ShouYeActivity.this,XueChangWeatherActivity.class));
                break;

            default:
                break;
        }
    }
}
