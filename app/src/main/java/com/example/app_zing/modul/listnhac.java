package com.example.app_zing.modul;

public class listnhac  {

    public int id;
    public byte[] hinhcasi;
    public String tenbaihat;
    public String casi;

    public listnhac(int id, byte[] hinhcasi, String tenbaihat, String casi) {
        this.id = id;
        this.hinhcasi = hinhcasi;
        this.tenbaihat = tenbaihat;
        this.casi = casi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getHinhcasi() {
        return hinhcasi;
    }

    public void setHinhcasi(byte[] hinhcasi) {
        this.hinhcasi = hinhcasi;
    }

    public String getTenbaihat() {
        return tenbaihat;
    }

    public void setTenbaihat(String tenbaihat) {
        this.tenbaihat = tenbaihat;
    }

    public String getCasi() {
        return casi;
    }

    public void setCasi(String casi) {
        this.casi = casi;
    }
}
