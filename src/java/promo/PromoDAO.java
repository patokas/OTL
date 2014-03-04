/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package promo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import userCard.UserCard;

/**
 *
 * @author patricio alberto
 */
public class PromoDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<Promo> findByTittle(Promo promo) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Promo> list = new ArrayList<Promo>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift pr, place pl where tittle like '" + promo.getTittle() + "%' and pr.id_place = pl.id_place ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Promo reg = new Promo();
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                reg.setPoints(result.getInt("points"));

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

    public Collection<Promo> findByPlace(Promo promo) {

        Statement sentence = null;
        ResultSet result = null;
        Collection<Promo> list = new ArrayList<Promo>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift pr, place pl where pr.id_place = pl.id_place and pr.id_place = " + promo.getIdPlace() + " order by pr.id_promo desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Promo reg = new Promo();
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                reg.setPoints(result.getInt("points"));

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

    public Collection<Promo> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Promo> list = new ArrayList<Promo>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift pr, place pl where pr.id_place = pl.id_place order by id_promo desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Promo reg = new Promo();
                reg.setIdPlace(result.getInt("id_place"));
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                reg.setPoints(result.getInt("points"));
                reg.setRequest(result.getInt("request"));

                list.add(reg);
            }

        } catch (SQLException ex) {
            // Gestionar mejor esta exception
            ex.printStackTrace();
        } finally {
            /* liberar los recursos */
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

    public void delete(Promo promo) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from promo_gift where id_place = ? and id_promo = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, promo.getIdPlace());
            sentence.setInt(2, promo.getIdPromo());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.eliminar: " + promo, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void insert(Promo promo) {

        PreparedStatement sentence = null;

        try {

            String sql = "insert into promo_gift (id_place, tittle, details, date_begin, date_end, url_image, points, request) values (?, ?, ?, ?, ?, ?, ?, ?)";
            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, promo.getIdPlace());
            sentence.setString(2, promo.getTittle());
            sentence.setString(3, promo.getDetails());
            sentence.setString(4, promo.getDateBegin());
            sentence.setString(5, promo.getDateEnd());
            sentence.setString(6, promo.getUrlImage());
            sentence.setInt(7, promo.getPoints());
            sentence.setInt(8, promo.getRequest());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.add: " + promo, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void update(Promo promo) {

        PreparedStatement sentence = null;

        try {
            String sql = "update promo_gift set tittle = ?, details = ?, date_begin = ?, date_end = ?, url_image = ?, points = ?, request = ? where id_place = ? and id_promo = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, promo.getTittle());
            sentence.setString(2, promo.getDetails());
            sentence.setString(3, promo.getDateBegin());
            sentence.setString(4, promo.getDateEnd());
            sentence.setString(5, promo.getUrlImage());
            sentence.setInt(6, promo.getPoints());
            sentence.setInt(7, promo.getRequest());
            sentence.setInt(8, promo.getIdPlace());
            sentence.setInt(9, promo.getIdPromo());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("RegDAO.update: " + promo, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public Promo findbyPromo(Promo promo) {

        Statement sentence = null;
        ResultSet result = null;

        Promo reg = new Promo();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift pr, place pl where pr.id_place = pl.id_place and pr.id_promo = " + promo.getIdPromo() + " and pr.id_place = " + promo.getIdPlace() + "";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                reg.setUrlImage(result.getString("pr.url_image"));
                reg.setPoints(result.getInt("points"));
                reg.setRequest(result.getInt("request"));
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
        return reg;
    }

    void insert(Promo promo, Collection<UserCard> listUC) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into promo_gift (id_place, tittle, details, date_begin, date_end, url_image, points, request) values (?, ?, ?, ?, ?, ?, ?, ?)";
            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, promo.getIdPlace());
            sentence.setString(2, promo.getTittle());
            sentence.setString(3, promo.getDetails());
            sentence.setString(4, promo.getDateBegin());
            sentence.setString(5, promo.getDateEnd());
            sentence.setString(6, promo.getUrlImage());
            sentence.setInt(7, promo.getPoints());
            sentence.setInt(8, promo.getRequest());

            sentence.executeUpdate();

            try {
                if (listUC.size() > 0) {
                    for (UserCard aux : listUC) {
                        String sqlUser = "insert into promo_gift_list (rut, dv, id_place, id_promo) values (?, ?, ?, (select max(id_promo) from promo_gift))";

                        sentence = conexion.prepareStatement(sqlUser);

                        sentence.setInt(1, aux.getRut());
                        sentence.setString(2, aux.getDv());
                        sentence.setInt(3, promo.getIdPlace());

                        sentence.executeUpdate();
                    }
                }
            } catch (SQLException ex) {
                // todo Gestionar mejor esta exception
                ex.printStackTrace();
                throw new RuntimeException("regDAO.add: " + listUC, ex);
            }

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("regDAO.add: " + promo, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}