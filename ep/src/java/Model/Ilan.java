/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * @author kushadige
 */
public class Ilan {
    private int ilan_id;
    private String kategori;
    private String fiyat;
    private String baslik;
    private String aciklama;
    private String gorsel;
    private String konum;
    private User ilanSahibi;
    
    public Ilan() {}

    public Ilan(int ilan_id, String kategori, String fiyat, String baslik, String aciklama, String gorsel, String konum, User ilanSahibi) {
        this.ilan_id = ilan_id;
        this.kategori = kategori;
        this.fiyat = fiyat;
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.gorsel = gorsel;
        this.konum = konum;
        this.ilanSahibi = ilanSahibi;
    }

    public int getIlan_id() {
        return ilan_id;
    }
    public void setIlan_id(int ilan_id) {
        this.ilan_id = ilan_id;
    }
    public String getKategori() {
        return kategori;
    }
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    public String getFiyat() {
        return fiyat;
    }
    public void setFiyat(String fiyat) {
        this.fiyat = fiyat;
    }
    public String getBaslik() {
        return baslik;
    }
    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }
    public String getAciklama() {
        return aciklama;
    }
    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
    public String getGorsel() {
        return gorsel;
    }
    public void setGorsel(String gorsel) {
        this.gorsel = gorsel;
    }
    public String getKonum(){
        return konum;
    }
    public void setKonum(String konum){
        this.konum = konum;
    }
    public User getIlanSahibi() {
        return ilanSahibi;
    }
    public void setIlanSahibi(User ilanSahibi) {
        this.ilanSahibi = ilanSahibi;
    }
    
}
