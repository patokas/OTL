/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientNews;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;


/**
 *
 * @author alexander
 */
public class ClientNewsDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<ClientNews> findById(int id) {

        Statement sentence = null;
        ResultSet result = null;
        Collection<ClientNews> list = new ArrayList<ClientNews>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from client_news cn, user_card uc where cn.id_client_news = " + id + " and cn.rut = uc.rut ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                ClientNews reg = new ClientNews();
                reg.setIdClientNews(result.getInt("id_client_news"));
                reg.setTittle(result.getString("tittle"));
                reg.setTypeNews(result.getInt("type_news"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));

                list.add(reg);
            }

        } catch (SQLException ex) {
            // Gestionar mejor esta exception
            ex.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (Exception noGestionar) {
            }
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
        return list;
    }

    public Collection<ClientNews> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<ClientNews> cnewslist = new ArrayList<ClientNews>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from client_news cn, user_card uc where cn.rut = uc.rut";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                ClientNews reg = new ClientNews();

                reg.setIdClientNews(result.getInt("id_client_news"));
                reg.setTittle(result.getString("tittle"));
                reg.setTypeNews(result.getInt("type_news"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));

                cnewslist.add(reg);
            }

            //5 let free resources        
        } catch (SQLException ex) {
            // Gestionar mejor esta exception
            ex.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (Exception noGestionar) {
            }
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
        return cnewslist;
    }

    public Collection<ClientNews> findByTittleDate(String name, int rut, String date1) {

        Statement sentence = null;
        ResultSet result = null;
        Collection<ClientNews> list = new ArrayList<ClientNews>();

        try {

            sentence = conexion.createStatement();
            String sql = "select * from client_news cn where tittle like '" + name + "%' and cn.rut = '" + rut + "' and cn.date_end <= '" + date1 + "' ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                ClientNews reg = new ClientNews();
                reg.setIdClientNews(result.getInt("id_client_news"));
                reg.setTittle(result.getString("tittle"));
                reg.setTypeNews(result.getInt("type_news"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));

                list.add(reg);
            }

            //5 let free resources        
        } catch (SQLException ex) {
            // Gestionar mejor esta exception
            ex.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (Exception noGestionar) {
            }
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
        return list;
    }

    public Collection<ClientNews> findByTittleDateId(String name, String date1, int idclientnews) {

        Statement sentence = null;
        ResultSet result = null;
        Collection<ClientNews> list = new ArrayList<ClientNews>();

        try {

            sentence = conexion.createStatement();
            String sql = "select * from client_news cn, user_card uc where tittle like '" + name + "%' and cn.date_end <= '" + date1 + "' and cn.id_client_news <> '" + idclientnews + "' and cn.rut = uc.rut ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                ClientNews reg = new ClientNews();
                reg.setIdClientNews(result.getInt("id_client_news"));
                reg.setTittle(result.getString("tittle"));
                reg.setTypeNews(result.getInt("type_news"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));

                list.add(reg);
            }

            //5 let free resources        
        } catch (SQLException ex) {
            // Gestionar mejor esta exception
            ex.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (Exception noGestionar) {
            }
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
        return list;
    }

    public Collection<ClientNews> findByRut(int rut) {

        Statement sentence = null;
        ResultSet result = null;
        Collection<ClientNews> list = new ArrayList<ClientNews>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from  user_card uc where uc.rut = " + rut + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                ClientNews reg = new ClientNews();
                reg.setRut(result.getInt("rut"));
                list.add(reg);
            }

        } catch (SQLException ex) {
            // Gestionar mejor esta exception
            ex.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (Exception noGestionar) {
            }
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
        return list;
    }

    public void insert(ClientNews reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into client_news (tittle, type_news, rut, dv, date_begin, date_end) values (?, ?, ?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);


            sentence.setString(1, reg.getTittle());
            sentence.setInt(2, reg.getTypeNews());
            sentence.setInt(3, reg.getRut());
            sentence.setString(4, reg.getDv());
            sentence.setString(5, reg.getDateBegin());
            sentence.setString(6, reg.getDateEnd());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.add: " + reg, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void delete(int id) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from client_news where id_client_news = ?";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, id);

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.eliminar: " + id, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(ClientNews reg) {
        PreparedStatement sentence = null;

        try {
            String sql = "update client_news set tittle = ?, type_news = ?, rut = ?, dv = ?,  date_begin = ?, date_end = ? where id_client_news = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, reg.getTittle());
            sentence.setInt(2, reg.getTypeNews());
            sentence.setInt(3, reg.getRut());
            sentence.setString(4, reg.getDv());
            sentence.setString(5, reg.getDateBegin());
            sentence.setString(6, reg.getDateEnd());
            sentence.setInt(7, reg.getIdClientNews());


            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("RegDAO.update: " + reg, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
