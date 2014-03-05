/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientPromo;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author patricio alberto
 */
public class ClientPromoDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<ClientPromo> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<ClientPromo> list = new ArrayList<ClientPromo>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift_list pgl, place pl, promo_gift pg where pgl.id_place = pl.id_place and pg.id_promo = pgl.id_promo and pg.id_place = pl.id_place order by pgl.id_promo desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                ClientPromo reg = new ClientPromo();
                /* obtener resultset */
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setTittlePromo(result.getString("tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setPoints(result.getInt("points"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientPromoDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientPromoDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientPromoDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientPromoDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientPromoDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientPromoDAO, getAll() : " + ex);
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

    public ClientPromo findbyPromoGiftList(ClientPromo promoGiftList) {

        Statement sentence = null;
        ResultSet result = null;

        ClientPromo reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift_list pgl, place pl, promo_gift pg where pgl.id_promo = " + promoGiftList.getIdPromo() + " and pgl.id_place = " + promoGiftList.getIdPlace() + " and pgl.rut = " + promoGiftList.getRut() + " and pgl.id_place = pg.id_place and pgl.id_place = pl.id_place and pgl.id_promo = pg.id_promo";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                reg = new ClientPromo();
                /* obtener resultset */
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittlePromo(result.getString("tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setStatus(result.getInt("status"));
                reg.setPoints(result.getInt("points"));
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientPromoDAO, findByClientPromo() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientPromoDAO, findByClientPromo() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientPromoDAO, findByClientPromo() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientPromoDAO, findByClientPromo() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientPromoDAO, findByClientPromo() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientPromoDAO, findByClientPromo() : " + ex);
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

    public void insert(ClientPromo reg) {

        PreparedStatement sentence = null;

        try {

            String sql = "insert into promo_gift_list (id_promo, id_place, rut, dv) values (?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdPromo());
            sentence.setInt(2, reg.getIdPlace());
            sentence.setInt(3, reg.getRut());
            sentence.setString(4, reg.getDv());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientPromoDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientPromoDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientPromoDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientPromoDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientPromoDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientPromoDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void delete(ClientPromo reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from promo_gift_list where id_place = ? and id_promo = ? and rut = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdPlace());
            sentence.setInt(2, reg.getIdPromo());
            sentence.setInt(3, reg.getRut());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientPromoDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientPromoDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientPromoDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientPromoDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientPromoDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientPromoDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    Collection<ClientPromo> findByPlace(ClientPromo promo) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<ClientPromo> list = new ArrayList<ClientPromo>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift_list pgl, place pl, promo_gift pg where pgl.id_place = " + promo.getIdPlace() + " and pgl.id_place = pl.id_place and pgl.id_promo = pg.id_promo and pgl.id_place = pg.id_place order by pgl.id_promo desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                ClientPromo reg = new ClientPromo();
                /* obtener resultset */
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittlePromo(result.getString("tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setPoints(result.getInt("points"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ClientPromoDAO, findByPlace() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientPromoDAO, findByPlace() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientPromoDAO, findByPlace() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientPromoDAO, findByPlace() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientPromoDAO, findByPlace() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientPromoDAO, findByPlace() : " + ex);
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
}
