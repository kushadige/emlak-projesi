/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author kushadige
 */
@RequestScoped
@ManagedBean
public class MainBean {
    
    DBController dbController = new DBController();
    private List<Ilan> ilanList = new ArrayList<Ilan>();
    private List<Danisman> danismanList = new ArrayList<Danisman>();
    private String iframeUrl = "https://www.google.com/maps/d/embed?mid=12E1i2eQ-KbUPv7eD0XPG2HVeh7LkwOg&ehbc=2E312F\" width=\"300\" height=\"300\"";
    
    public List<Ilan> getIlanList() throws SQLException { 
        ilanList = dbController.tumIlanlar();
        return ilanList;
    }
    public void setIlanList(List<Ilan> ilanList) {
        this.ilanList = ilanList;
    }

    public List<Danisman> getDanismanList() throws SQLException {
        danismanList = dbController.tumDanismanlar();
        return danismanList;
    }
    public void setDanismanList(List<Danisman> danismanList) {
        this.danismanList = danismanList;
    }

    public DBController getDbController() {
        return dbController;
    }

    public void setDbController(DBController dbController) {
        this.dbController = dbController;
    }

    public String getIframeUrl() {
        return iframeUrl;
    }
}
