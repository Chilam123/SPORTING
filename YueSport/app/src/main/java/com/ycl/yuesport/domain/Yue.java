package com.ycl.yuesport.domain;

public class Yue {

   private String yue_id;
    private String publisher_id;
    private String publisher_nickname;
    private String publisher_sex;
    private String publisher_touxiang;
    private String publish_time; //发布的时间，默认按发布时间来排序
    private String yue_time; //约的时间
    private String yue_date;//约的日期
    private String yue_jianshenfang_name; //健身房名称
    private String yue_jianshenfang_area; //健身房区域
    private String yue_jianshenfang_details_address; //健身房详细地址
    private String yue_jianshenfang_pic; //健身房图片
    private int yue_price;
    private int yue_need_people;
    private int yue_now_people;
    private String yue_intent;
    private int cart_state; //0为无卡，1为有卡
    private String participant_list;//参与者ID列表,以&&符号连接

    public String getYue_id() {
        return yue_id;
    }

    public void setYue_id(String yue_id) {
        this.yue_id = yue_id;
    }

    public String getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(String publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getPublisher_nickname() {
        return publisher_nickname;
    }

    public void setPublisher_nickname(String publisher_nickname) {
        this.publisher_nickname = publisher_nickname;
    }

    public String getPublisher_sex() {
        return publisher_sex;
    }

    public void setPublisher_sex(String publisher_sex) {
        this.publisher_sex = publisher_sex;
    }

    public String getPublisher_touxiang() {
        return publisher_touxiang;
    }

    public void setPublisher_touxiang(String publisher_touxiang) {
        this.publisher_touxiang = publisher_touxiang;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getYue_time() {
        return yue_time;
    }

    public void setYue_time(String yue_time) {
        this.yue_time = yue_time;
    }

    public String getYue_jianshenfang_name() {
        return yue_jianshenfang_name;
    }

    public void setYue_jianshenfang_name(String yue_jianshenfang_name) {
        this.yue_jianshenfang_name = yue_jianshenfang_name;
    }

    public String getYue_jianshenfang_area() {
        return yue_jianshenfang_area;
    }

    public void setYue_jianshenfang_area(String yue_jianshenfang_area) {
        this.yue_jianshenfang_area = yue_jianshenfang_area;
    }

    public String getYue_jianshenfang_details_address() {
        return yue_jianshenfang_details_address;
    }

    public void setYue_jianshenfang_details_address(String yue_jianshenfang_details_address) {
        this.yue_jianshenfang_details_address = yue_jianshenfang_details_address;
    }

    public int getYue_price() {
        return yue_price;
    }

    public void setYue_price(int yue_price) {
        this.yue_price = yue_price;
    }

    public int getYue_need_people() {
        return yue_need_people;
    }

    public void setYue_need_people(int yue_need_people) {
        this.yue_need_people = yue_need_people;
    }

    public int getYue_now_people() {
        return yue_now_people;
    }

    public void setYue_now_people(int yue_now_people) {
        this.yue_now_people = yue_now_people;
    }

    public String getYue_intent() {
        return yue_intent;
    }

    public void setYue_intent(String yue_intent) {
        this.yue_intent = yue_intent;
    }

    public String getYue_jianshenfang_pic() {
        return yue_jianshenfang_pic;
    }

    public void setYue_jianshenfang_pic(String yue_jianshenfang_pic) {
        this.yue_jianshenfang_pic = yue_jianshenfang_pic;
    }

    public String getYue_date() {
        return yue_date;
    }

    public void setYue_date(String yue_date) {
        this.yue_date = yue_date;
    }

    public int getCart_state() {
        return cart_state;
    }

    public void setCart_state(int cart_state) {
        this.cart_state = cart_state;
    }

    public String getParticipant_list() {
        return participant_list;
    }

    public void setParticipant_list(String participant_list) {
        this.participant_list = participant_list;
    }
}
