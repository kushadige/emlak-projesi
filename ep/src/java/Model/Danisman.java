
package Model;

/**
 * @author kushadige
 */
public class Danisman {
    private String adi;
    private String telefon;
    private String gorev;
    private String fotograf;

    public Danisman(String adi, String telefon, String gorev, String fotograf) {
        this.adi = adi;
        this.telefon = telefon;
        this.gorev = gorev;
        this.fotograf = fotograf;
    }
    
    public String getAdi() {
        return adi;
    }
    public void setAdi(String adi) {
        this.adi = adi;
    }
    public String getTelefon() {
        return telefon;
    }
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
    public String getGorev() {
        return gorev;
    }
    public void setGorev(String gorev) {
        this.gorev = gorev;
    }
    public String getFotograf() {
        return fotograf;
    }
    public void setFotograf(String fotograf) {
        this.fotograf = fotograf;
    }
}
