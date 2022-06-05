package Model;
import Helper.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kushadige
 */
public class DBController {
    
    DBConnection db = new DBConnection();
    Connection con = null;
    private User user = new User();
    private Ilan ilan = new Ilan();
    
    // TUM ILANLAR
    public List tumIlanlar() throws SQLException {
        List<Ilan> tempList = new ArrayList<Ilan>();
        Ilan tempIlan;

        con = db.connect();
        
        try {
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ilanlar");
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

                tempIlan = new Ilan(ilan_id, kategori, fiyat, baslik, aciklama, gorsel, konum, findUser(ilansahibi_email));
                tempList.add(tempIlan);   
            }
        } finally {
            con.close();
        } 
        return tempList;
    }
    public List tumDanismanlar() throws SQLException {
        List<Danisman> tempList = new ArrayList<Danisman>();
        Danisman temp;

        con = db.connect();
        
        try {
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM danismanlar");
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                
                String adi = result.getString("adi");
                String telefon = result.getString("telefon");
                String gorev = result.getString("gorev");
                String fotograf = result.getString("fotograf");
                
                temp = new Danisman(adi, telefon, gorev, fotograf);
                tempList.add(temp);   
            }
        } finally {
            con.close();
        } 
        return tempList;
    }
    
    // FIND USER
    public User findUser(String ilansahibi_email) throws SQLException {
        user = new User();

        con = db.connect();
        
        try {
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE email = ?");
            ps.setString(1, ilansahibi_email);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                String name = result.getString("name");
                String email = result.getString("email");
                String phone = result.getString("phone");
                String password = result.getString("password");
                
                user = new User(name, email, phone, password);
            }

        } finally {
            con.close();
        } 

        return user;
    }
    
    // Create new User
    public User createUser(String name, String email, String phone, String password) {

        con = db.connect();
        
        try {
            // Email - telefon Kayıtlı mı kontrolü 
            PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE email = ? OR phone = ?");
            ps.setString(1, email);
            ps.setString(2, phone);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                
                user = new User("","","","");
                
            } else {
                // Create new User
                ps = con.prepareStatement("INSERT INTO user VALUES(?, ?, ?, ?)");
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setString(4, password);
                ps.executeUpdate();
                
                user = new User(name, email, phone, password);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(user.getEmail().equals("")){
            return null;
        }
        
        return user;
    }
    
    
    public User checkUser(String email, String password) {
        
        // System.out.println("MAIL: " + email + "SIFRE: " + password);
        
        con = db.connect();
        
        try {
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet result = ps.executeQuery();
            
            if (result.next()) {
                String name = result.getString("name");
                email = result.getString("email");
                String phone = result.getString("phone");
                password = result.getString("password");
                
                user = new User(name, email, phone, password);
                
            } else {
                
                user = new User("","","","");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        if(user.getEmail().equals("")){
            return null;
        }
        
        return user;
    }
    
    public int ilanSayisi() throws SQLException{
        List<Ilan> tempList = this.tumIlanlar();
        return tempList.size();
    }
    
    public void createIlan(String kategori, String fiyat, String baslik, String aciklama, String gorsel, String konum, User ilanSahibi) throws SQLException{
        int ilan_id = this.ilanSayisi()+1;
        
        Ilan tempIlan = new Ilan();
        
        con = db.connect();
        
        PreparedStatement ps = con.prepareStatement("INSERT INTO ilanlar VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        ps.setInt(1, ilan_id);
        ps.setString(2, kategori);
        ps.setString(3, fiyat);
        ps.setString(4, baslik);
        ps.setString(5, aciklama);
        ps.setString(6, gorsel);
        ps.setString(7, konum);
        ps.setString(8, ilanSahibi.getEmail());
        ps.executeUpdate();

        tempIlan = new Ilan(ilan_id, kategori, fiyat, baslik, aciklama, gorsel, konum, ilanSahibi);
    }
    
    public void updateIlan(String kategori, String fiyat, String baslik, String aciklama, String gorsel, String konum, int ilan_id) throws SQLException{
        con = db.connect();
        
        PreparedStatement ps = con.prepareStatement("UPDATE ilanlar SET kategori = ?, fiyat = ?, baslik = ?, aciklama = ?, gorsel = ?, konum = ? WHERE ilan_id = ?");
        ps.setString(1, kategori);
        ps.setString(2, fiyat);
        ps.setString(3, baslik);
        ps.setString(4, aciklama);
        ps.setString(5, gorsel);
        ps.setString(6, konum);
        ps.setInt(7, ilan_id);
        ps.executeUpdate();
    }
    
    public boolean checkUserFavorites(int ilan_id, User u){
        
        con = db.connect();
        
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM favorites WHERE ilan_id = ? AND email = ?");
            ps.setInt(1, ilan_id);
            ps.setString(2, u.getEmail());
            ResultSet result = ps.executeQuery();
            
            if(result.next()) {    
                
                return true;
            } else {    
                return false;
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public void addFavorite(int ilan_id, User u){
        
        con = db.connect();
        try {
            
            PreparedStatement ps = con.prepareStatement("INSERT INTO favorites VALUES(?, ?)");
            ps.setInt(1, ilan_id);
            ps.setString(2, u.getEmail());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    public void deleteFavorite(int ilan_id, User u){
        
        con = db.connect();
        try {
            
            PreparedStatement ps = con.prepareStatement("DELETE FROM favorites WHERE ilan_id = ? AND email = ?");
            ps.setInt(1, ilan_id);
            ps.setString(2, u.getEmail());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteIlan(int ilan_id){
        con = db.connect();
        try {
            
            PreparedStatement ps = con.prepareStatement("DELETE FROM ilanlar WHERE ilan_id = ?");
            ps.setInt(1, ilan_id);
            ps.executeUpdate();
            
            ps = con.prepareStatement("UPDATE ilanlar SET ilan_id = ? WHERE ilan_id IN (SELECT MAX(ilan_id) FROM ilanlar)");
            ps.setInt(1, ilan_id);
            ps.executeUpdate();
            
            ps = con.prepareStatement("DELETE FROM favorites WHERE ilan_id = ?");
            ps.setInt(1, ilan_id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
