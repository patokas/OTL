/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exchangeable;

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
public class ExchangeableDAO {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public Collection<Exchangeable> getAll() {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Exchangeable> list = new ArrayList<Exchangeable>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from gift g, place p where g.id_place = p.id_place order by g.id_gift desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                Exchangeable reg = new Exchangeable();
                /* obtener resultset */
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setIdExchangeable(result.getInt("id_gift"));
                reg.setTittle(result.getString("tittle"));
                reg.setUrlImage(result.getString("g.url_image"));
                reg.setPoints(result.getInt("points"));
                reg.setRequest(result.getInt("request"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ExchangeableDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ExchangeableDAO, getAll() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ExchangeableDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ExchangeableDAO, getAll() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ExchangeableDAO, getAll() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ExchangeableDAO, getAll() : " + ex);
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

    public void delete(Exchangeable exchange) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from gift where id_place = ? and id_gift = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, exchange.getIdPlace());
            sentence.setInt(2, exchange.getIdExchangeable());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ExchangeableDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ExchangeableDAO, delete() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ExchangeableDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ExchangeableDAO, delete() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ExchangeableDAO, delete() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ExchangeableDAO, delete() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public Collection<Exchangeable> findByTittle(Exchangeable exchange) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<Exchangeable> list = new ArrayList<Exchangeable>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from gift g, place pl where tittle = '" + exchange.getTittle() + "' and g.id_place = pl.id_place ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* instanciar objeto */
                Exchangeable reg = new Exchangeable();
                /* obtener resultset */
                reg.setIdExchangeable(result.getInt("id_gift"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setPoints(result.getInt("points"));
                reg.setRequest(result.getInt("request"));
                /* agregar a la lista */
                list.add(reg);
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ExchangeableDAO, findByTittle() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ExchangeableDAO, findByTittle() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ExchangeableDAO, findByTittle() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ExchangeableDAO, findByTittle() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ExchangeableDAO, findByTittle() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ExchangeableDAO, findByTittle() : " + ex);
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

    public void insert(Exchangeable exchange) {

        PreparedStatement sentence = null;

        try {

            String sql = "insert into gift (id_place, tittle, url_image, points, request) values (?, ?, ?, ?, ?)";
            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, exchange.getIdPlace());
            sentence.setString(2, exchange.getTittle());
            sentence.setString(3, exchange.getUrlImage());
            sentence.setInt(4, exchange.getPoints());
            sentence.setInt(5, exchange.getRequest());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ExchangeableDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ExchangeableDAO, insert() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ExchangeableDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ExchangeableDAO, insert() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ExchangeableDAO, insert() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ExchangeableDAO, insert() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    public Exchangeable findByExchange(Exchangeable exchange) {

        Statement sentence = null;
        ResultSet result = null;

        Exchangeable reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from gift g, place pl where g.id_place = " + exchange.getIdPlace() + " and g.id_gift = " + exchange.getIdExchangeable() + " and g.id_place = pl.id_place ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                /* definir objeto */
                reg = new Exchangeable();
                /* obtener resultset */
                reg.setIdExchangeable(result.getInt("id_gift"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittle(result.getString("tittle"));
                reg.setPoints(result.getInt("points"));
                reg.setUrlImage(result.getString("url_image"));
                reg.setRequest(result.getInt("request"));
            }

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ExchangeableDAO, findByExchangeable() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ExchangeableDAO, findByExchangeable() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ExchangeableDAO, findByExchangeable() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ExchangeableDAO, findByExchangeable() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ExchangeableDAO, findByExchangeable() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ExchangeableDAO, findByExchangeable() : " + ex);
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

    public void update(Exchangeable exchange) {

        PreparedStatement sentence = null;

        try {
            String sql = "update gift set tittle = ?, url_image = ?, points = ?, request = ? where id_place = ? and id_gift = ? ";
            sentence = conexion.prepareStatement(sql);

            sentence.setString(1, exchange.getTittle());
            sentence.setString(2, exchange.getUrlImage());
            sentence.setInt(3, exchange.getPoints());
            sentence.setInt(4, exchange.getRequest());
            sentence.setInt(5, exchange.getIdPlace());
            sentence.setInt(6, exchange.getIdExchangeable());

            sentence.executeUpdate();

        } catch (MySQLSyntaxErrorException ex) {
            System.out.println("Error de sintaxis en ExchangeableDAO, update() : " + ex);
            throw new RuntimeException("MySQL Syntax Exception en ExchangeableDAO, update() : " + ex);
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("MySQL Excepción de integridad en ExchangeableDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción de integridad en ExchangeableDAO, update() : " + ex);
        } catch (SQLException ex) {
            System.out.println("MySQL Excepción inesperada en ExchangeableDAO, update() : " + ex);
            throw new RuntimeException("MySQL Excepción inesperada en ExchangeableDAO, update() : " + ex);
        } finally {
            /* liberar recursos */
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
