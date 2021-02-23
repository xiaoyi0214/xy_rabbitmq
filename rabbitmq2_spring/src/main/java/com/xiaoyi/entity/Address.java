package com.xiaoyi.entity;

/**
 * Created on 2021/2/22.
 *
 * @author 小逸
 * @description
 */
public class Address {

    @Override
    public String toString() {
        return "Address{" +
                "provinces='" + provinces + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                '}';
    }

    private String provinces;

    private String city;

    private String area;

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
