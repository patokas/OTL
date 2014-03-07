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
            String sql = "select * from client_promo cp, place pl, promo pr where pr.id_promo = cp.id_promo and pr.id_place = pl.id_place order by cp.id_promo desc";
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

    public ClientPromo findbyRutIdPromo(ClientPromo clientPromo) {

        Statement sentence = null;
        ResultSet result = null;

        ClientPromo reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from client_promo cp, place pl, promo pr where cp.id_promo = " + clientPromo.getIdPromo() + " and cp.rut = " + clientPromo.getRut() + " and cp.id_promo = pr.id_promo and pl.id_place = pr.id_place";
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
            System.out.println("Error de sintaxis en ClientPromoDAO, findbyRutIdPromo() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ClientPromoDAO, findbyRutIdPromo() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ClientPromoDAO, findbyRutIdPromo() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ClientPromoDAO, findbyRutIdPromo() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ClientPromoDAO, findbyRutIdPromo() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ClientPromoDAO, findbyRutIdPromo() : " + ex);
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

            String sql = "insert into client_promo (id_promo, rut, dv) values (?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdPromo());            
            sentence.setInt(2, reg.getRut());
            sentence.setString(3, reg.getDv());

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
            String sql = "delete from client_promo where id_promo = ? and rut = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdPromo());
            sentence.setInt(2, reg.getRut());

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
}
