
package Model;

/**
 * @author kushadige
 */
public class Favorites {
    private int ilan_id;
    private String email;
    
    public Favorites () {}
    
    public Favorites (int ilan_id, String email) {
        this.ilan_id = ilan_id;
        this.email = email;
    }
    
    public void setIlan_id(int ilan_id){
        this.ilan_id = ilan_id;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public int getIlan_id(){
        return ilan_id;
    }
    public String getEmail(){
        return email;
    }
}
