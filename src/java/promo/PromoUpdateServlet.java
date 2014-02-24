/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package promo;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author patricio alberto
 */
@WebServlet(name = "PromoUpdateServlet", urlPatterns = {"/PromoUpdateServlet"})
public class PromoUpdateServlet extends HttpServlet {

    @Resource(name = "jdbc/OTL")
    private DataSource ds;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Connection conexion = null;

        try {
            //////////////////////////////////////////
            // ESTABLECER CONEXION
            /////////////////////////////////////////

            conexion = ds.getConnection();

            PromoDAO promoDAO = new PromoDAO();
            promoDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String username = (String) session.getAttribute("username");

                /* comprobar permisos de usuario */
                if (access != 555 && access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {

                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", username);
                    request.setAttribute("access", access);

                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        /////////////////////////////////////////
                        String sidPlace = request.getParameter("idPlace");
                        String sidPromo = request.getParameter("idPromo");
                        String namePlace = request.getParameter("namePlace");
                        String tittle = request.getParameter("tittle");
                        String details = request.getParameter("details");
                        String date1 = request.getParameter("dateBegin");
                        String date2 = request.getParameter("dateEnd");
                        String urlImage = request.getParameter("urlImage");
                        String spoints = request.getParameter("points");

                        Promo promo = new Promo();

                        boolean error = false;

                        /* comprobar id place */
                        if (sidPlace == null || sidPlace.trim().equals("")) {
                            request.setAttribute("msgErrorIdPlace", "Error al recibir id plaza.");
                            error = true;
                        } else {
                            try {
                                promo.setIdPlace(Integer.parseInt(sidPlace));
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorIdPlace", "Error al recibir id plaza.");
                                error = true;
                            }
                        }

                        /* comprobar id promo */
                        if (sidPromo == null || sidPromo.trim().equals("")) {
                            request.setAttribute("msgErrorIdPromo", "Error al recibir id promo.");
                            error = true;
                        } else {
                            try {
                                promo.setIdPromo(Integer.parseInt(sidPromo));
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorIdPromo", "Error al recibir id promo.");
                                error = true;
                            }
                        }

                        /* comprobar name place */
                        if (namePlace == null || namePlace.trim().equals("")) {
                            request.setAttribute("msgErrorNamePlace", "Error al recibir nombre de plaza.");
                            error = true;
                        } else {
                            promo.setNamePlace(namePlace);
                        }

                        /* comprobar tittle */
                        if (tittle == null || tittle.trim().equals("")) {
                            request.setAttribute("msgErrorTittle", "Error: Debe ingresar un título para la promo o regalo.");
                            error = true;
                        } else {
                            promo.setTittle(tittle);
                        }

                        /* comprobar details */
                        if (details == null || details.trim().equals("")) {
                            request.setAttribute("msgErrorDetails", "Error: Debe ingresar detalles para la promo o regalo.");
                            error = true;
                        } else {
                            promo.setDetails(details);
                        }

                        /* comprobar url image */
                        if (urlImage == null || urlImage.trim().equals("")) {
                            request.setAttribute("msgErrorUrlImage", "Error: Debe ingresar url de imagen.");
                            error = true;
                        } else {
                            promo.setUrlImage(urlImage);
                        }

                        /* comprobar date begin */
                        if (date1 == null || date1.trim().equals("")) {
                            request.setAttribute("msgErrorDate", "Error al recibir fecha de inicio.");
                            error = true;
                        } else {
                            promo.setDateBegin(date1);
                            /* comprobar date end */
                            if (date2 == null || date2.trim().equals("")) {
                                request.setAttribute("msgErrorDate", "Error al recibir fecha de término.");
                                error = true;
                            } else {
                                /* comparar fechas */
                                promo.setDateBegin(date1);
                                promo.setDateEnd(date2);
                                System.out.println("comparar fecha inicio fecha fin: " + promo.getDateBegin().compareTo(promo.getDateEnd()));
                                if (promo.getDateBegin().compareTo(promo.getDateEnd()) >= 0) {
                                    request.setAttribute("msgErrorDate", "Error: La fecha de término deber ser mayor que la fecha de inicio.");
                                    error = true;
                                }
                            }
                        }

                        /* comprobar points */
                        if (spoints == null || spoints.trim().equals("")) {
                            request.setAttribute("msgErrorPoints", "Error: Debe ingresar puntos que acumula esta promoción.");
                            error = true;
                        } else {
                            try {
                                promo.setPoints(Integer.parseInt(spoints));
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorPoints", "Error: Los puntos deben ser numéricos.");
                                error = true;
                            }
                        }

                        if (!error) {
                            /* traer todos los registros con el mismo tittle */
                            Collection<Promo> list = promoDAO.findByTittle(promo);
                            if (list.size() > 0) {
                                System.out.println("existe titulo repetido");
                                /* guardar fecha antes de editar */
                                String d = promo.getDateBegin();
                                for (Promo aux : list) {
                                    /* editar fechas para comparar */
                                    aux.setDateEnd(aux.getDateEnd().replace(".0", ""));
                                    promo.setDateBegin(promo.getDateBegin().replace("T", " "));
                                    /*  si pertenecen al mismo place y poseen idPromo diferentes, error */
                                    System.out.println("id place: " + promo.getIdPlace() + " id promo: " + promo.getIdPromo() + " fechas comparación: " + promo.getDateBegin().compareTo(aux.getDateEnd()));
                                    if (promo.getDateBegin().compareTo(aux.getDateEnd()) != 1 && aux.getIdPlace() == promo.getIdPlace() && aux.getIdPromo() != promo.getIdPromo()) {
                                        error = true;
                                        request.setAttribute("msgErrorDup", "Error: ya existe esta promo o regalo. Compruebe utilizando otro título u otro rango de fechas.");
                                    }
                                }
                                promo.setDateBegin(d);
                            }
                        }
                        if (!error) {
                            Promo aux = promoDAO.findbyPromo(promo);
                            if (aux.getIdPlace() > 0) {
                                promoDAO.update(promo);
                                request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                            } else {
                                request.setAttribute("msgErrorFound", "Error: El registro no existe o ha sido mientras se actualizaba.");
                            }
                        }
                        request.setAttribute("promo", promo);
                    } catch (Exception ex) {
                    } finally {
                        request.getRequestDispatcher("/promo/promoUpdate.jsp").forward(request, response);
                    }
                }
            } catch (Exception sessionException) {
                /* enviar a la vista de login */
                System.out.println("no ha iniciado session");
                request.getRequestDispatcher("/login/login.jsp").forward(request, response);
            }
        } catch (Exception connectionException) {
            connectionException.printStackTrace();
        } finally {
            try {
                conexion.close();
            } catch (Exception noGestionar) {
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
