package com.ycl.sportsing.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.ycl.sportsing.R;

public class Weather implements Parcelable{
      private String xuechang;
	private String tianqi;
	private String wendu;
	private String jianshuiliang;
	private String imageweather;

	public String getXuechang() {
		return xuechang;
	}

	public void setXuechang(String xuechang) {
		this.xuechang = xuechang;
	}

	public String getTianqi() {
		return tianqi;
	}

	public void setTianqi(String tianqi) {
		this.tianqi = tianqi;
	}

	public String getWendu() {
		return wendu;
	}

	public void setWendu(String wendu) {
		this.wendu = wendu;
	}

	public String getJianshuiliang() {
		return jianshuiliang;
	}

	public void setJianshuiliang(String jianshuiliang) {
		this.jianshuiliang = jianshuiliang;
	}

	public int getImageweather() {
		if(imageweather.equals("qing"))
			return R.mipmap.ic_weather_qing;
		if(imageweather.equals("duoyun"))
			return R.mipmap.ic_weather_duoyun;
		if(imageweather.equals("xiayu"))
			return R.mipmap.ic_weather_xiayu;
		return R.mipmap.ic_weather_qing;
	}

	public void setImageweather(String imageweather) {
		this.imageweather = imageweather;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
         dest.writeString(xuechang);
		dest.writeString(tianqi);
		dest.writeString(wendu);
		dest.writeString(jianshuiliang);
		dest.writeString(imageweather);

	}

	public static final Parcelable.Creator<Weather> CREATOR=new Creator<Weather>() {
		@Override
		public Weather createFromParcel(Parcel source) {
			return new Weather(source);
		}

		@Override
		public Weather[] newArray(int size) {
			return new Weather[size];
		}
	};

	public Weather(Parcel source){
		xuechang=source.readString();
		tianqi=source.readString();
		wendu=source.readString();
		jianshuiliang=source.readString();
		imageweather=source.readString();
	}

	public Weather(){}
}
