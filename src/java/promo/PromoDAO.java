/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package promo;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
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

    public Collection<Promo> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Promo> list = new ArrayList<Promo>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift pr, place pl where pr.id_place = pl.id_place order by id_promo desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                Promo reg = new Promo();
                /* obtener resultSet */
                reg.setIdPlace(result.getInt("id_place"));
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                reg.setPoints(result.getInt("points"));
                reg.setRequest(result.getInt("request"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PromoDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PromoDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PromoDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PromoDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PromoDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PromoDAO, getAll() : " + ex);
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

    public boolean findDuplicate(Promo reg) {

        Statement sentence = null;
        ResultSet result = null;

        boolean find = false;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift where id_place = " + reg.getIdPlace() + " and id_promo <> " + reg.getIdPromo() + " and tittle = '" + reg.getTittle() + "' and date_end > '" + reg.getDateBegin() + "'";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* obtener resultSet */
                find = true;
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PromoDAO, findDuplicate : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PromoDAO, findDuplicate : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PromoDAO, findDuplicate : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PromoDAO, findDuplicate : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PromoDAO, findDuplicate : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PromoDAO, findDuplicate : " + ex);
        } finally {
            /* liberar recursos */
            try {
                result.close();
            } catch (Exception noGestionar) {
            }
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
        return find;
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
                /* instanciar objeto */
                Promo reg = new Promo();
                /* obtener resultSet */
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setDetails(result.getString("details"));
                reg.setDateBegin(result.getString("date_begin"));
                reg.setDateEnd(result.getString("date_end"));
                reg.setPoints(result.getInt("points"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PromoDAO, findByTittle() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PromoDAO, findByTittle() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PromoDAO, findByTittle() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PromoDAO, findByTittle() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PromoDAO, findByTittle() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PromoDAO, findByTittle() : " + ex);
        } finally {
            /* liberar recursos */
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

    public Promo findbyPromo(Promo promo) {

        Statement sentence = null;
        ResultSet result = null;

        Promo reg = new Promo();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift pr, place pl where pr.id_place = pl.id_place and pr.id_promo = " + promo.getIdPromo() + " and pr.id_place = " + promo.getIdPlace() + "";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* obtener resultSet */
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

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PromoDAO, findbyPromo() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PromoDAO, findbyPromo() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PromoDAO, findbyPromo() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PromoDAO, findbyPromo() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PromoDAO, findbyPromo() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PromoDAO, findbyPromo() : " + ex);
        } finally {
            /* liberar recursos */
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
            } catch (MySQLSyntaxErrorException ex) {
                System.out.println("Error de sintaxis en PromoDAO, insert(), insert into client_promo : " + ex);
                throw new RuntimeException("MySQL Syntax Exception en PromoDAO, insert(), insert into client_promo  : " + ex);
            } catch (MySQLIntegrityConstraintViolationException ex) {
                System.out.println("MySQL Excepción de integridad en PromoDAO, insert(), insert into client_promo  : " + ex);
                throw new RuntimeException("MySQL Excepción de integridad en PromoDAO, insert(), insert into client_promo : " + ex);
            } catch (SQLException ex) {
                System.out.println("MySQL Excepción inesperada en PromoDAO, PromoDAO, insert(), insert into client_promo  : " + ex);
                throw new RuntimeException("MySQL Excepción inesperada en PromoDAO, insert(), insert into client_promo : " + ex);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PromoDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PromoDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PromoDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PromoDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PromoDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PromoDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void delete(Promo promo) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from promo_gift where id_place = ? and id_promo = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, promo.getIdPlace());
            sentence.setInt(2, promo.getIdPromo());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PromoDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PromoDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PromoDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PromoDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PromoDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PromoDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
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

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PromoDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PromoDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PromoDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PromoDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PromoDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PromoDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
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

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en PromoDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en PromoDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en PromoDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en PromoDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en PromoDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en PromoDAO, update() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}