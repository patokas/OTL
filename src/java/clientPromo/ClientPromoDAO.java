/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientPromo;

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
                ClientPromo reg = new ClientPromo();
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setTittlePromo(result.getString("tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setPoints(result.getInt("points"));

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

    public ClientPromo findbyPromoGiftList(ClientPromo promoGiftList) {

        Statement sentence = null;
        ResultSet result = null;

        ClientPromo reg = null;

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift_list pgl, place pl, promo_gift pg where pgl.id_promo = " + promoGiftList.getIdPromo() + " and pgl.id_place = " + promoGiftList.getIdPlace() + " and pgl.rut = " + promoGiftList.getRut() + " and pgl.id_place = pg.id_place and pgl.id_place = pl.id_place and pgl.id_promo = pg.id_promo";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                reg = new ClientPromo();
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittlePromo(result.getString("tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
                reg.setStatus(result.getInt("status"));
                reg.setPoints(result.getInt("points"));
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

    public void delete(ClientPromo reg) {

        PreparedStatement sentence = null;

        try {
            String sql = "delete from promo_gift_list where id_place = ? and id_promo = ? and rut = ? ";

            sentence = conexion.prepareStatement(sql);

            sentence.setInt(1, reg.getIdPlace());
            sentence.setInt(2, reg.getIdPromo());
            sentence.setInt(3, reg.getRut());

            sentence.executeUpdate();

        } catch (SQLException ex) {
            // todo Gestionar mejor esta exception
            ex.printStackTrace();
            throw new RuntimeException("DAO.eliminar: " + reg, ex);
        } finally {
            try {
                sentence.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    Collection<ClientPromo> findByPlacePromo(ClientPromo promo) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<ClientPromo> list = new ArrayList<ClientPromo>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift_list pgl, place pl, promo_gift pg where pgl.id_place = " + promo.getIdPlace() + " and pgl.id_promo = " + promo.getIdPromo() + " and pgl.id_place = pl.id_place and pgl.id_promo = pg.id_promo and pgl.id_place = pg.id_place group by pgl.id_place";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                ClientPromo reg = new ClientPromo();
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittlePromo(result.getString("tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
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

    Collection<ClientPromo> findByPlace(ClientPromo promo) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<ClientPromo> list = new ArrayList<ClientPromo>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift_list pgl, place pl, promo_gift pg where pgl.id_place = " + promo.getIdPlace() + " and pgl.id_place = pl.id_place and pgl.id_promo = pg.id_promo and pgl.id_place = pg.id_place order by pgl.id_promo desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                ClientPromo reg = new ClientPromo();
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittlePromo(result.getString("tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
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

    Collection<ClientPromo> findByGenre(int genre) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<ClientPromo> list = new ArrayList<ClientPromo>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift_list pgl, place pl, promo_gift pg, user_card uc where uc.genre = " + genre + " and pgl.id_place = pl.id_place and pgl.id_promo = pg.id_promo and pgl.id_place = pg.id_place order by pgl.id_promo desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                ClientPromo reg = new ClientPromo();
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittlePromo(result.getString("tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
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

    Collection<ClientPromo> findByStatus(ClientPromo promo) {

        Statement sentence = null;
        ResultSet result = null;

        Collection<ClientPromo> list = new ArrayList<ClientPromo>();

        try {
            sentence = conexion.createStatement();
            String sql = "select * from promo_gift_list pgl, place pl, promo_gift pg where pgl.status = " + promo.getStatus() + " and pgl.id_place = pl.id_place and pgl.id_promo = pg.id_promo and pgl.id_place = pg.id_place order by pgl.id_promo desc";
            result = sentence.executeQuery(sql);

            while (result.next()) {
                ClientPromo reg = new ClientPromo();
                reg.setIdPromo(result.getInt("id_promo"));
                reg.setIdPlace(result.getInt("id_place"));
                reg.setNamePlace(result.getString("name_place"));
                reg.setTittlePromo(result.getString("tittle"));
                reg.setRut(result.getInt("rut"));
                reg.setDv(result.getString("dv"));
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
}
