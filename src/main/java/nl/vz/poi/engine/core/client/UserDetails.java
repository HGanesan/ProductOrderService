package nl.vz.poi.engine.core.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserDetails {

    @JsonProperty
    public int page;

    @JsonProperty
    public int per_page;

    @JsonProperty
    public int total;

    @JsonProperty
    public int total_pages;

    @JsonProperty("data")
    public List<UserData> userDataList;

    @JsonProperty
    public Support support;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<UserData> getUserDataList() {
        return userDataList;
    }

    public void setUserDataList(List<UserData> userDataList) {
        this.userDataList = userDataList;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "page=" + page +
                ", per_page=" + per_page +
                ", total=" + total +
                ", total_pages=" + total_pages +
                ", userDataList=" + userDataList +
                ", support=" + support +
                '}';
    }
}
