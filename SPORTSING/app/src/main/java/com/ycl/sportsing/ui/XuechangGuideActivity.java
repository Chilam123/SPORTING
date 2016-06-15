package com.ycl.sportsing.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ycl.sportsing.R;
import com.ycl.sportsing.domain.XueChang;

public class XuechangGuideActivity extends Activity implements View.OnClickListener {

    private Intent intent;
    private ImageView imageViewBack,imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8;
    private XueChang nanshan,jundushan,wanlong,yabuli,miyuan,duolemei,wanke,wanlong2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xue_chang_guide);


        imageViewBack=(ImageView)this.findViewById(R.id.imageview_back);
        imageView1=(ImageView)this.findViewById(R.id.ImageView1);
        imageView2=(ImageView)this.findViewById(R.id.ImageView2);
        imageView3=(ImageView)this.findViewById(R.id.ImageView3);
        imageView4=(ImageView)this.findViewById(R.id.ImageView4);
        imageView5=(ImageView)this.findViewById(R.id.ImageView5);
        imageView6=(ImageView)this.findViewById(R.id.ImageView6);
        imageView7=(ImageView)this.findViewById(R.id.ImageView7);
        imageView8=(ImageView)this.findViewById(R.id.ImageView8);
        imageViewBack.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);
        imageView7.setOnClickListener(this);
        imageView8.setOnClickListener(this);

        initXueChang();

    }

    public void initXueChang(){
        nanshan=new XueChang();
        nanshan.setTitle("南山（北京）滑雪场");
        nanshan.setDetails("位于北京市区东北方向的密云县,雪质很好，是北京周边最早开业的滑雪场，每天慕名前往的游客数不胜数。南山拥有中国最具人气的滑雪公园，也拥有最为丰富的初、中级雪道，缆车、拖牵、魔毯运力较强。南山不光滑雪雪道做的好，也拥有大片的儿童戏雪区、美食众多。因为每周的周末人都非常多，建议滑雪爱好者平日前往，体验者可周末前往，提前热闹的气氛。");
        nanshan.setLocation("北京市密云县河南寨镇圣水头村");
        nanshan.setPhone("010-8909 1909");
        nanshan.setPrice("平日 \n" +
                "门市价 4小时210元/人 全天310元/人\n" +
                "优惠价 4小时165元/人 全天185元/人 \n" +
                "周末/节假日 \n" +
                "门市价 4小时350元/人 全天450元/人 \n" +
                "优惠价 4小时255元/人 全天295元/人\n");
        nanshan.setTransportation("2015-16雪季 南山滑雪直通巴士 往返45元\n" +
                "周一至周日天天发车，乘车需提前一天预定，咨询及班车预定请致电：010-89091909 \n" +
                "发车时间：早8:30分 三元桥/五道口出发，17:30分从南山返回。\n" +
                "正式营业期间，每周一、周二特惠往返25元（节假日除外）。\n" +
                "\n" +
                "线路一：三元桥站\n" +
                "发车地点：三元桥时间国际链家地产门口停车场（曙光西里甲6号院一号楼）\n" +
                "线路二：五道口站\n" +
                "发车地点：华清嘉园东门，五道口地铁站B出口向西第一个红绿灯（东源大厦）左转，距五道口地铁站100米\n" +
                "：线路三：芍药居站（2015年12月26日起）\n" +
                "芍药居京承高速辅路。芍药居地铁站G口出，向左后方延便道步行至京承高速辅路即可到达（距离芍药居地铁G口步行5分钟，约300米 ）。\n" +
                "发车时间：早8:30分 芍药居出发，17:30分从南山返回。\n");


        jundushan=new XueChang();
        jundushan.setTitle("军都山滑雪场");
        jundushan.setDetails("位于北京市区北方的昌平区，是一家以“服务”服人的滑雪场，其滑雪学校、餐厅、冬令营均备受好评。军都山雪道并不太多，有一条超难的7号道（江湖人称七爷）、两条不长的中级道和数条初级道，以流畅的流程+良好的口碑+夜场经营成为北京周边排名前三的滑雪场。建议：中高级的滑雪爱好者可在雪季中期去挑战七爷，道陡人少；初学者如果想好好学习滑雪，军都山滑雪学校绝对是你的不二选择。没有滑过夜场的，不妨去军都山体验一下，物美又价廉。");
        jundushan.setLocation("北京市昌平区崔村镇真顺村588号");
        jundushan.setPhone("010-60725888");
        jundushan.setPrice("平日\n" +
                "4小时160元/人 优惠价150元/人 8小时240元/人 优惠价180元/人\n" +
                "周末\n" +
                "4小时240元/人 优惠价230元/人 8小时390元/人 优惠价270元/人\n" +
                "节假日\n" +
                "4小时280元/人 优惠价260元/人 8小时420元/人 优惠价320元/人\n" +
                "夜场 100元/人\n");
        jundushan.setTransportation("路线1：德胜门公交站坐888路，886路，345（快）路至昌平东关站，换乘昌21路到研发中心下，沿路标行驶至军都山滑雪场；\n" +
                "\n" +
                "路线2：地铁13号线西二旗站换乘地铁昌平线至终点站南邵下车，出站换乘昌11路至北邵洼下，再过马路对面换乘昌21路到研发中心下，沿路标行驶至军都山滑雪场。\n");


        wanlong=new XueChang();
        wanlong.setTitle("万龙八易滑雪场");
        wanlong.setDetails("距离北京市区最近的滑雪场，是石景山乃至全北京滑雪爱好者的乐园，也是为数不多可以乘坐公共交通到达的滑雪场。万龙八易的雪道不是很多，胜在距离北京近，加上开放夜场，在北京拥有十足的人气，适合滑雪爱好者偶尔解馋，也适合初学者前去体验。");
        wanlong.setLocation("丰台区长辛店射击场路12号八一军体大队内");
        wanlong.setPhone("010-52219999");
        wanlong.setPrice("租板\n" +
                "平日 4小时185元/人 6小时220元/人 130元/人（夜场）\n" +
                "周末 4小时285元/人 6小时330元/人 130元/人（夜场）\n" +
                "节假日 4小时300元/人 6小时385元/人 160元/人（夜场）\n" +
                "自带板\n" +
                "平日 4小时165元/人 6小时190元/人 110元/人（夜场）\n" +
                "周末 4小时240元/人 6小时265元/人 110元/人（夜场）\n" +
                "节假日 4小时260元/人 6小时320元/人 140元/人（夜场）\n");
        wanlong.setTransportation("公交路线：乘坐地铁1号线“古城站”C出口,换乘327或385路公交车“八一射击场站”50米即到 　　\n" +
                "自驾线路：\n" +
                "1.莲石路方向:莲石东路西行过西五环-水屯路芦井路出口-沿芦井路指示标行驶-回民公墓-豪特湾酒店红绿灯右拐即到\n" +
                "2.京原路方向:石景山医院-八角桥左转-京原路-衙门口村-京原路芦井方向-燕山水泥厂-漫水桥红绿灯左拐-回民公墓-豪特湾酒店红绿灯处右拐即到\n" +
                "3.京石高速方向:京石高速-杜家坎出口-杜家坎环岛-大灰厂东路方向-槐树岭-过八一射击场即到 4.门头沟方向:石门营环岛-莲石路北京城区方向-芦井路出口-回民公墓-豪特湾酒店红绿灯处右拐即到\n");


        yabuli=nanshan;
        yabuli.setTitle("北京亚布力滑雪场");

        miyuan=nanshan;
        yabuli.setTitle("北京密苑云顶滑雪场");

        duolemei=nanshan;
        yabuli.setTitle("北京多乐美地滑雪场");

        wanke=nanshan;
        yabuli.setTitle("北京万科松花湖滑雪场");

        wanlong2=nanshan;
        yabuli.setTitle("北京万龙滑雪场");

    }

    @Override
    public void onClick(View v) {

        Bundle bundle=new Bundle();
        switch (v.getId()){
            case R.id.imageview_back:
                XuechangGuideActivity.this.finish();
                break;
            case R.id.ImageView1:
                intent=new Intent(XuechangGuideActivity.this, XuechangActivity.class);
                bundle.putSerializable("xuechang",nanshan);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ImageView2:
                intent=new Intent(XuechangGuideActivity.this, XuechangActivity.class);
                bundle.putSerializable("xuechang",jundushan);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ImageView3:
                intent=new Intent(XuechangGuideActivity.this, XuechangActivity.class);
                bundle.putSerializable("xuechang",wanlong);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ImageView4:
                intent=new Intent(XuechangGuideActivity.this, XuechangActivity.class);
                bundle.putSerializable("xuechang",yabuli);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ImageView5:
                intent=new Intent(XuechangGuideActivity.this, XuechangActivity.class);
                bundle.putSerializable("xuechang",miyuan);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ImageView6:
                intent=new Intent(XuechangGuideActivity.this, XuechangActivity.class);
                bundle.putSerializable("xuechang",duolemei);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ImageView7:
                intent=new Intent(XuechangGuideActivity.this, XuechangActivity.class);
                bundle.putSerializable("xuechang",wanke);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.ImageView8:
                intent=new Intent(XuechangGuideActivity.this, XuechangActivity.class);
                bundle.putSerializable("xuechang",wanlong2);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
