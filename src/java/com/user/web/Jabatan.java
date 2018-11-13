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
public class Jabatan {

    /**
     * Creates a new instance of Jabatan
     */
    private String IDJabatan;
    public void setIDJabatan(String IDJabatan) {
    this.IDJabatan = IDJabatan;
    }
    public String getIDJabatan() {
        return IDJabatan;
    }

    private String NAMAJbtn;
    public void setNAMAJbtn(String NAMAJbtn) {
        this.NAMAJbtn = NAMAJbtn;
    }
    public String getNAMAJbtn() {
        return NAMAJbtn;
    }
    
    private String GAJI;
    public void setGAJI(String GAJI) {
        this.GAJI = GAJI;
    }
    public String getGAJI() {
        return GAJI;
    }
   
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap(); 

public String Edit_Jabatan(){
     FacesContext fc = FacesContext.getCurrentInstance();
     Map<String,String > params = fc.getExternalContext().getRequestParameterMap();
     String Field_ID_profesi = params.get("action");
     try {
          Koneksi obj_koneksi = new Koneksi();
          Connection connection = obj_koneksi.get_connection();
          Statement st = connection.createStatement();
          ResultSet rs = st.executeQuery("select * from jabatan where ID_Profesi="+Field_ID_profesi);
          Jabatan obj_Jabatan = new Jabatan();
          rs.next();
          obj_Jabatan.setIDJabatan(rs.getString("ID_Profesi"));
          obj_Jabatan.setNAMAJbtn(rs.getString("Nama_Jabatan"));
          obj_Jabatan.setGAJI(rs.getString("Gaji"));
          sessionMap.put("EditJabatan", obj_Jabatan);  
      } catch (Exception e) {
            System.out.println(e);
      }
     return "/EditJabatan.xhtml?faces-redirect=true";   
}

public String Delete_Jabatan(){
      FacesContext fc = FacesContext.getCurrentInstance();
      Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
      String Field_ID_profesi = params.get("action");
      try {
         Koneksi obj_koneksi = new Koneksi();
         Connection connection = obj_koneksi.get_connection();
         PreparedStatement ps = connection.prepareStatement("delete from jabatan where ID_Profesi=?");
         ps.setString(1, Field_ID_profesi);
         System.out.println(ps);
         ps.executeUpdate();
        } catch (Exception e) {
         System.out.println(e);
        }
       return "/Jabatan.xhtml?faces-redirect=true";   
}

public String Update_Jabatan(){
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	String Update_ID_profesi= params.get("Update_ID_profesi");
        try {
            Koneksi obj_koneksi = new Koneksi();
            Connection connection = obj_koneksi.get_connection();
            PreparedStatement ps = connection.prepareStatement("update jabatan set ID_Profesi=?, Nama_Jabatan=?, GAJI=? where ID_Profesi=?");
            ps.setString(1, IDJabatan);
            ps.setString(2, NAMAJbtn);
            ps.setString(3, GAJI);
            ps.setString(4, Update_ID_profesi);
            System.out.println(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
       return "/Jabatan.xhtml?faces-redirect=true";   
}
    
    public ArrayList getGet_all_jabatan() throws Exception{
        ArrayList list_of_jabatan=new ArrayList();
             Connection connection=null;
        try {
            Koneksi obj_koneksi = new Koneksi();
            connection = obj_koneksi.get_connection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("Select * from jabatan");
            while(rs.next()){
                Jabatan obj_Jabatan = new Jabatan();
                obj_Jabatan.setIDJabatan(rs.getString("ID_Profesi"));
                obj_Jabatan.setNAMAJbtn(rs.getString("Nama_Jabatan"));
                obj_Jabatan.setGAJI(rs.getString("Gaji"));
                list_of_jabatan.add(obj_Jabatan);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
        return list_of_jabatan;
}
    
public String Tambah_Jabatan(){
        try {
            Connection connection=null;
            Koneksi obj_koneksi = new Koneksi();
            connection = obj_koneksi.get_connection();
            PreparedStatement ps=connection.prepareStatement("insert into jabatan(ID_Profesi, Nama_Jabatan, GAJI) value('"+IDJabatan+"','"+NAMAJbtn+"','"+GAJI+"')");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "/Jabatan.xhtml?faces-redirect=true";
    }
    public Jabatan() {
    }
    
}
