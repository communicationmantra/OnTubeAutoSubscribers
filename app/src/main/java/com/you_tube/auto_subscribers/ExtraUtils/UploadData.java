package com.you_tube.auto_subscribers.ExtraUtils;

/**
 * Created by 91982 on 16-04-2018.
 */

public class UploadData {
    String id,uniqueid,title,usernameid,cpc,country,earnpoint,totalclick,yorn;

    public UploadData() {
    }

    public UploadData(String id, String uniqueid, String title, String usernameid, String cpc, String country, String earnpoint, String totalclick, String yorn) {
        this.id = id;
        this.uniqueid = uniqueid;
        this.title = title;
        this.usernameid = usernameid;
        this.cpc = cpc;
        this.country = country;
        this.earnpoint = earnpoint;
        this.totalclick = totalclick;
        this.yorn = yorn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsernameid() {
        return usernameid;
    }

    public void setUsernameid(String usernameid) {
        this.usernameid = usernameid;
    }

    public String getCpc() {
        return cpc;
    }

    public void setCpc(String cpc) {
        this.cpc = cpc;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEarnpoint() {
        return earnpoint;
    }

    public void setEarnpoint(String earnpoint) {
        this.earnpoint = earnpoint;
    }

    public String getTotalclick() {
        return totalclick;
    }

    public void setTotalclick(String totalclick) {
        this.totalclick = totalclick;
    }

    public String getYorn() {
        return yorn;
    }

    public void setYorn(String yorn) {
        this.yorn = yorn;
    }
}
