/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.user.web;

import com.user.web.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author bleezy
 */
@ManagedBean
@SessionScoped
public class Karyawan {

    /**
     * Creates a new instance of Karyawan
     */
    private String IDKaryawan;
    public void setIDKaryawan(String IDKaryawan) {
      this.IDKaryawan = IDKaryawan;
    }
    public String getIDKaryawan() {
        return IDKaryawan;
    }

    private String NAMA;
    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }
    public String getNAMA() {
        return NAMA;
    }
    
    private String IDProfesi;
    public void setIDProfesi(String IDProfesi) {
        this.IDProfesi = IDProfesi;
    }
    public String getIDProfesi() {
        return IDProfesi;
    }
   
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 

public String Edit_Karyawan(){
     FacesContext fc = FacesContext.getCurrentInstance();
     Map<String,String > params = fc.getExternalContext().getRequestParameterMap();
     String Field_ID_karyawan = params.get("action");
     try {
          Koneksi obj_koneksi = new Koneksi();
          Connection connection = obj_koneksi.get_connection();
          Statement st = connection.createStatement();
          ResultSet rs = st.executeQuery("select * from karyawan where ID_karyawan="+Field_ID_karyawan);
          Karyawan obj_Karyawan = new Karyawan();
          rs.next();
          obj_Karyawan.setIDKaryawan(rs.getString("ID_karyawan"));
          obj_Karyawan.setNAMA(rs.getString("Nama"));
          obj_Karyawan.setIDProfesi(rs.getString("ID_Profesi"));
          sessionMap.put("EditKaryawan", obj_Karyawan);  
      } catch (Exception e) {
            System.out.println(e);
      }
     return "/EditKaryawan.xhtml?faces-redirect=true";   
}

public String Delete_Karyawan(){
      FacesContext fc = FacesContext.getCurrentInstance();
      Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
      String Field_ID_karyawan = params.get("action");
      try {
         Koneksi obj_koneksi = new Koneksi();
         Connection connection = obj_koneksi.get_connection();
         PreparedStatement ps = connection.prepareStatement("delete from karyawan where ID_karyawan=?");
         ps.setString(1, Field_ID_karyawan);
         System.out.println(ps);
         ps.executeUpdate();
        } catch (Exception e) {
         System.out.println(e);
        }
       return "/Karyawan.xhtml?faces-redirect=true";   
}

public String Update_Karyawan(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	String Update_ID_karyawan= params.get("Update_ID_karyawan");
        try {
            Koneksi obj_koneksi = new Koneksi();
            Connection connection = obj_koneksi.get_connection();
            PreparedStatement ps = connection.prepareStatement("update karyawan set ID_karyawan=?, Nama=?, ID_Profesi=? where ID_karyawan=?");
            ps.setString(1, IDKaryawan);
            ps.setString(2, NAMA);
            ps.setString(3, IDProfesi);
            ps.setString(4, Update_ID_karyawan);
            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
       return "/Karyawan.xhtml?faces-redirect=true";   
}
    
    public ArrayList getGet_all_karyawan() throws Exception{
        ArrayList list_of_karyawan=new ArrayList();
             Connection connection=null;
        try {
            Koneksi obj_koneksi = new Koneksi();
            connection = obj_koneksi.get_connection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("Select * from karyawan");
            while(rs.next()){
                Karyawan obj_Karyawan = new Karyawan();
                obj_Karyawan.setIDKaryawan(rs.getString("ID_karyawan"));
                obj_Karyawan.setNAMA(rs.getString("Nama"));
                obj_Karyawan.setIDProfesi(rs.getString("ID_Profesi"));
                list_of_karyawan.add(obj_Karyawan);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
        return list_of_karyawan;
}
    
public String Tambah_Karyawan(){
        try {
            Connection connection=null;
            Koneksi obj_koneksi = new Koneksi();
            connection = obj_koneksi.get_connection();
            PreparedStatement ps=connection.prepareStatement("insert into karyawan(ID_karyawan, Nama, ID_Profesi) value('"+IDKaryawan+"','"+NAMA+"','"+IDProfesi+"')");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/Karyawan.xhtml?faces-redirect=true";
    }
    public Karyawan() {
    }
    
}
