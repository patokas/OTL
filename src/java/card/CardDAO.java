/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package card;

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
        Collection<Card> list = new ArrayList<Card>();
        Statement sentence = null;
        ResultSet result = null;


        try {
            sentence = conexion.createStatement();
            String sql = "select * from card";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Card reg = new Card();
                reg.setBarCode(result.getInt("bar_code"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setCardType(result.getInt("card_type"));
                reg.setDateBeginCard(result.getString("date_begin_card"));
                reg.setDateEndCard(result.getString("date_end_card"));
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

    public Collection<Card> findByRut(int rut) {
        Statement sentence = null;
        ResultSet result = null;
        Collection<Card> list = new ArrayList<Card>();
        try {
            sentence = conexion.createStatement();
            String sql = "select * from card where rut = " + rut + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Card reg = new Card();
                reg.setBarCode(result.getInt("bar_code"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setCardType(result.getInt("card_type"));
                reg.setDateBeginCard(result.getString("date_begin_card"));
                reg.setDateEndCard(result.getString("date_end_card"));
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

    public Collection<Card> findByBarCode(int barcode) {
        Statement sentence = null;
        ResultSet result = null;
        Collection<Card> list = new ArrayList<Card>();
        try {
            sentence = conexion.createStatement();
            String sql = "select * from card where bar_code = " + barcode + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                Card reg = new Card();
                reg.setBarCode(result.getInt("bar_code"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setCardType(result.getInt("card_type"));
                reg.setDateBeginCard(result.getString("date_begin_card"));
                reg.setDateEndCard(result.getString("date_end_card"));
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

    public Card findOneByBarCode(int barcode) {
        Statement sentence = null;
        ResultSet result = null;
        Card reg = null;
        try {
            sentence = conexion.createStatement();
            String sql = "select * from card where bar_code = " + barcode + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg = new Card();
                reg.setBarCode(result.getInt("bar_code"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setCardType(result.getInt("card_type"));
                reg.setDateBeginCard(result.getString("date_begin_card"));
                reg.setDateEndCard(result.getString("date_end_card"));
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

    public Card findbyBarCodeJoin(Card card) {

        Statement sentence = null;
        ResultSet result = null;

        Card reg = new Card();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from card c, user_card u where c.rut = u.rut and c.bar_code = " + card.getBarCode() + " and u.rut = " + card.getRut() + "";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg.setBarCode(result.getInt("bar_code"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setCardType(result.getInt("card_type"));
                reg.setDateBeginCard(result.getString("date_begin_card"));
                reg.setDateEndCard(result.getString("date_end_card"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
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

    public Card findbyRutJoin(Card card) {

        Statement sentence = null;
        ResultSet result = null;

        Card reg = new Card();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from user_card  where rut = " + card.getRut() + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
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

    public UserCard findbyRutJoin1(int rut) {

        Statement sentence = null;
        ResultSet result = null;
        
        UserCard reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from user_card  where rut = " + rut + " ";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg = new UserCard();
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setFirstName(result.getString("first_name"));
                reg.setLastName(result.getString("last_name"));
            }

        } catch (SQLException ex) {
            // Gestionar mejor esta exception
            ex.printStackTrace();
        } finally {
            try {
                result.close();
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

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("CardDAO.update: " + card, ex);
        } finally {
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

    public void delete(int barcode) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from card where bar_code = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, barcode);

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("CardDAO.eliminar: " + barcode, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }
}
