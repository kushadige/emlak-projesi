
package Helper;

import Model.DBController;
import Model.Favorites;
import Model.Ilan;
import Model.User;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * @author kushadige
 */
@ManagedBean
@SessionScoped
public class AuthenticationBean implements Serializable {
    
    DBController dbController = new DBController();
    DBConnection db = new DBConnection();
    Connection con = null;
    private String name = "";
    private String email = "";
    private String phone = "";
    private String password = "";
    private boolean readed_confirmed = false;
    
    private User user = new User();
    private Ilan ilan = new Ilan();
    private String message;
    
    
    // Kullanıcıya özel Listeler - Getter Setterları
    private List<Ilan> ilanlarim = new ArrayList<Ilan>();
    private List<Ilan> favoriIlanlarim = new ArrayList<Ilan>();
    
    public List<Ilan> getIlanlarim() throws SQLException {
        ilanlarim = this.ilanlarim();
        return ilanlarim;
    }
    public void setIlanlarim(List<Ilan> ilanlarim) {
        this.ilanlarim = ilanlarim;
    }
    public List<Ilan> getFavoriIlanlarim() throws SQLException {
        favoriIlanlarim = this.favoriIlanlarim();
        return favoriIlanlarim;
    }
    public void setFavoriIlanlarim(List<Ilan> favoriIlanlarim) {
        this.favoriIlanlarim = favoriIlanlarim;
    }
    ///
    
    public String login() {
        
        if (email.equals("")) {
            setMessage("Mailinizi giriniz.");
            return "login";
        } 
        if (password.equals("")) {
            setMessage("Şifrenizi giriniz.");
            return "login";
        }
        
        // Girilen mail ve şifre geçerli mi kontrolü
        user = dbController.checkUser(email, password);
        if (user == null) {
            
            setMessage("Geçersiz E-mail veya şifre");
            return "/login";
        }
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("valid_user", this.user);
        return "/home?faces-redirect=true";
    }
    
    public String logout(){
        user = new User();
        return "index";
    }
    
    public String register(){
        // AD - SOYAD KONTROL
        if (name.equals("")) {
            setMessage("Adınız giriniz.");
            return "register";
        }
        if (name.length() < 5){
            setMessage("Adınız çok kısa.");
            return "register";
        }
        // MAIL KONTROL
        if (email.equals("")) {
            setMessage("E-Postanızı giriniz.");
            return "register";
        } else if (!email.contains("@") || email.contains("\"") || email.contains(" ") || email.contains("\\")) {
            setMessage("Geçersiz e-posta.");
            return "register";
        }
        // TELEFON KONTROL
        if (phone.equals("")) {
            setMessage("Telefonunuzu giriniz.");
            return "register";
        }
        // ŞİFRE KONTROL
        if (password.equals("")){
            setMessage("Şifrenizi giriniz.");
            return "register";
        } else if (password.length() < 5) {
            setMessage("Şifreniz çok kısa.");
            return "register";
        }

        if (!readed_confirmed) {
            message = "Kullanıcı politikasını onaylayınız.";
            return "register.xhtml";
        }
        
        user = dbController.createUser(name, email, phone, password);
        if (user == null) {
            setMessage("Kayıtlı mail veya telefonla üyelik oluşturamazsınız.");
            return "register";
        }
        setReaded_confirmed(false);
        this.setEmail("");
        this.setName("");
        this.setMessage("");
        this.setPassword("");
        this.setPhone("");
        return "home";
    }

    public String ilanolustur() throws SQLException{
        // AD - SOYAD KONTROL
        boolean control = false;
        if (ilan.getBaslik().equals("")) {
            setMessage("Başlık giriniz.");
            return "ilanver";
        }
        else if (ilan.getBaslik().length() < 5){
            setMessage("Başlık çok kısa.");
            return "ilanver";
        }
        else if (ilan.getKategori().equals("")) {
            setMessage("Kategori giriniz.");
            return "ilanver";
        }
        else if (ilan.getFiyat().equals("")) {
            setMessage("Fiyat giriniz.");
            return "ilanver";
        }
        else if (ilan.getAciklama().equals("")){
            setMessage("Açıklama giriniz.");
            return "ilanver";
        } 
        else if (ilan.getGorsel().equals("")) {
            setMessage("Gorsel giriniz.");
            return "ilanver";
        }
        else if (ilan.getKonum().equals("")) {
            setMessage("Konum giriniz.");
            return "ilanver";
        }
        control = true;
        if(control){
            dbController.createIlan(ilan.getKategori(), ilan.getFiyat(), ilan.getBaslik(), ilan.getAciklama(), ilan.getGorsel(), ilan.getKonum(), user);
            ilan = new Ilan();
            setMessage(" ");
            return "ilanlarim";
        } else {
            setMessage(" ");
            return "ilanver";
        }
    }
    
    public void favorilereEkle(String num){
        int ilan_id = Integer.parseInt(num);
        
        boolean control = dbController.checkUserFavorites(ilan_id, this.getUser());
        
        if(control){
            dbController.deleteFavorite(ilan_id, this.getUser());
            
        }
        else {
            dbController.addFavorite(ilan_id, this.getUser());
            
        }
    }
    
    public void favoriSil(String num){
        int ilan_id = Integer.parseInt(num);
        dbController.deleteFavorite(ilan_id, this.getUser());
    }
    
    public String ilanguncelle(String num) throws SQLException {
        int ilan_id = Integer.parseInt(num);
        boolean control = true;
        if (ilan.getBaslik().equals("")) {
            setMessage("Başlık giriniz.");
            control = false;
        }
        else if (ilan.getBaslik().length() < 5){
            setMessage("Başlık çok kısa.");
            control = false;
        }
        else if (ilan.getKategori().equals("")) {
            setMessage("Kategori giriniz.");
            control = false;
        }
        else if (ilan.getFiyat().equals("")) {
            setMessage("Fiyat giriniz.");
            control = false;
        }
        else if (ilan.getAciklama().equals("")){
            setMessage("Açıklama giriniz.");
            control = false;
        } 
        else if (ilan.getGorsel().equals("")) {
            setMessage("Gorsel giriniz.");
            control = false;
        }
        else if (ilan.getKonum().equals("")) {
            setMessage("Konum giriniz.");
            control = false;
        }
        
        if(control){
            dbController.updateIlan(ilan.getKategori(), ilan.getFiyat(), ilan.getBaslik(), ilan.getAciklama(), ilan.getGorsel(), ilan.getKonum(), ilan_id);
            ilan = new Ilan();
            return "ilanlarim";
        } else {
            return "ilanlarim";
        }
    }
    
    public void ilanSil(String num){
        int ilan_id = Integer.parseInt(num);
        dbController.deleteIlan(ilan_id);
    }
    
    public List ilanlarim() throws SQLException {
        List<Ilan> tempList = new ArrayList<Ilan>();
        Ilan tempIlan;

        con = db.connect();
        
        try {
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ilanlar WHERE ilansahibi_email = ?");
            ps.setString(1, this.getUser().getEmail());
            ResultSet result = ps.executeQuery();

            while (result.next()) {
               
                int ilan_id = result.getInt("ilan_id");
                String kategori = result.getString("kategori");
                String fiyat = result.getString("fiyat");
                String baslik = result.getString("baslik");
                String aciklama = result.getString("aciklama");
                String gorsel = result.getString("gorsel");
                String konum = result.getString("konum");
                String ilansahibi_email = result.getString("ilansahibi_email");

                tempIlan = new Ilan(ilan_id, kategori, fiyat, baslik, aciklama, gorsel, konum, this.getUser());
                tempList.add(tempIlan);   
            }
        } finally {
            con.close();
        } 
        return tempList;
    }
    public List favoriIlanlarim() throws SQLException {
        List<Ilan> tempList = new ArrayList<Ilan>();
        Ilan tempIlan;

        con = db.connect();
        
        try {
            // GELİŞMİŞ SQL SORGUSU
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ilanlar WHERE ilan_id IN (SELECT ilan_id FROM favorites WHERE email = ?)");
            ps.setString(1, this.getUser().getEmail());
            ResultSet result = ps.executeQuery();

            while (result.next()) {
               
                int ilan_id = result.getInt("ilan_id");
                String kategori = result.getString("kategori");
                String fiyat = result.getString("fiyat");
                String baslik = result.getString("baslik");
                String aciklama = result.getString("aciklama");
                String gorsel = result.getString("gorsel");
                String konum = result.getString("konum");
                String ilansahibi_email = result.getString("ilansahibi_email");

                tempIlan = new Ilan(ilan_id, kategori, fiyat, baslik, aciklama, gorsel, konum, this.getUser());
                tempList.add(tempIlan);   
            }
        } finally {
            con.close();
        } 
        return tempList;
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isReaded_confirmed() {
        return readed_confirmed;
    }
    public void setReaded_confirmed(boolean readed_confirmed) {
        this.readed_confirmed = readed_confirmed;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Ilan getIlan() {
        return ilan;
    }
    public void setIlan(Ilan ilan) {
        this.ilan = ilan;
    }
}
