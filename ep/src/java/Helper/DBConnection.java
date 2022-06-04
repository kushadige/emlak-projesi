package Helper;
import Model.User;
import java.sql.*;

/**
 * @author kushadige
 */
public class DBConnection {
    
    Connection c = null;
	
    public DBConnection() {}
	
    String url = "jdbc:mysql://localhost:3306/dbcyberians";
    String username = "root";
    String password = "";
    
    public Connection connect() {
        try {
            this.c = DriverManager.getConnection(url, username, password);
            // System.out.println("Baglandi");
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
            // System.out.println("baglanmadi");
        }
        return c;
    }
    
    /*
    public static void main(String args[]) throws SQLException{
        DBConnection db = new DBConnection();
        
        Connection con = db.connect();
        
        
        PreparedStatement ps = con.prepareStatement("SELECT * FROM user WHERE email = ? AND sifre = ?");
        ps.setString(1, "ok@gmail.com");
        ps.setString(2, "88888");
        ResultSet result = ps.executeQuery();

        if (result.next()) {
            String ad = result.getString("ad");
            String soyad = result.getString("soyad");
            String mail = result.getString("email");
            String telefon = result.getString("telefon");
            String sifre = result.getString("sifre");

            User tempUser = new User(ad, soyad, mail, telefon, sifre);
            
            System.out.println("Ad: " + tempUser.getAd() + " Soyad: " + tempUser.getSoyad());
        } else {
            System.out.println("Kullanici bulunamadi...");
        }
    } */
    
}
