/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package card;

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
 * @author alexander
 */
public class CardDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<Card> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Card> list = new ArrayList<Card>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from card";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                Card reg = new Card();
                /* obtener resulset */
                reg.setBarCode(result.getInt("bar_code"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setCardType(result.getInt("card_type"));
                reg.setDateBeginCard(result.getString("date_begin_card"));
                reg.setDateEndCard(result.getString("date_end_card"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en CardDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en CardDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en CardDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en CardDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en CardDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en CardDAO, getAll() : " + ex);
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

    public Card findByBarCode(int barcode) {

        Statement sentence = null;
        ResultSet result = null;

        Card reg = null;
        try {
            sentence = conexion.createStatement();
            String sql = "select * from card where bar_code = " + barcode + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objetos */
                reg = new Card();
                /* obtener resultset */
                reg.setBarCode(result.getInt("bar_code"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setCardType(result.getInt("card_type"));
                reg.setDateBeginCard(result.getString("date_begin_card"));
                reg.setDateEndCard(result.getString("date_end_card"));
            }
        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en CardDAO, findByBarCode() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en CardDAO, findByBarCode() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en CardDAO, findByBarCode() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en CardDAO, findByBarCode() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en CardDAO, findByBarCode() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en CardDAO, findByBarCode() : " + ex);
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

    public Card findbyBarCodeJoin(Card card) {

        Statement sentence = null;
        ResultSet result = null;

        Card reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from card c, user_card u where c.rut = u.rut and c.bar_code = " + card.getBarCode() + " and u.rut = " + card.getRut() + "";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                reg = new Card();
                /* obtener resultset */
                reg.setBarCode(result.getInt("bar_code"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setCardType(result.getInt("card_type"));
                reg.setDateBeginCard(result.getString("date_begin_card"));
                reg.setDateEndCard(result.getString("date_end_card"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en CardDAO, findbyBarCodeJoin() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en CardDAO, findbyBarCodeJoin() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en CardDAO, findbyBarCodeJoin() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en CardDAO, findbyBarCodeJoin() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en CardDAO, findbyBarCodeJoin() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en CardDAO, findbyBarCodeJoin() : " + ex);
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

    public Card findbyRutJoin(Card card) {

        Statement sentence = null;
        ResultSet result = null;

        Card reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from user_card  where rut = " + card.getRut() + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                reg = new Card();
                /* obtener resultset */
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en CardDAO, findbyRutJoin() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en CardDAO, findbyRutJoin() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en CardDAO, findbyRutJoin() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en CardDAO, findbyRutJoin() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en CardDAO, findbyRutJoin() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en CardDAO, findbyRutJoin() : " + ex);
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

    public void update(Card card) {

        PreparedStatement sentence = null;

        try {
            String sql = "update card set card_type = ?, date_begin_card = ?, date_end_card = ? where bar_code = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, card.getCardType());
            sentence.setString(2, card.getDateBeginCard());
            sentence.setString(3, card.getDateEndCard());
            sentence.setInt(4, card.getBarCode());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en CardDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en CardDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en CardDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en CardDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en CardDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en CardDAO, update() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void insert(Card reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "insert into card (rut, dv, bar_code, card_type, date_begin_card, date_end_card) values (?, ?, ?, ?, ?, ?)";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getRut());
            sentence.setString(2, reg.getDv());
            sentence.setInt(3, reg.getBarCode());
            sentence.setInt(4, reg.getCardType());
            sentence.setString(5, reg.getDateBeginCard());
            sentence.setString(6, reg.getDateEndCard());
            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en CardDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en CardDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en CardDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en CardDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en CardDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en CardDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public void delete(int barcode) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from card where bar_code = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, barcode);

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en CardDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en CardDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en CardDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en CardDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en CardDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en CardDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
