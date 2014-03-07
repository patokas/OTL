/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orderCard;

import java.io.IOException;
import java.sql.Connection;
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
 * @author patricio
 */
@WebServlet(name = "OrderCardUpdateServlet", urlPatterns = {"/OrderCardUpdateServlet"})
public class OrderCardUpdateServlet extends HttpServlet {

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
            /////////////////////////////////////////
            // ESTABLECER CONEXION
            /////////////////////////////////////////

            conexion = ds.getConnection();

            OrderCardDAO orderCardDAO = new OrderCardDAO();
            orderCardDAO.setConexion(conexion);

            /////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String username = (String) session.getAttribute("username");

                /* comprobar permisos de usuario */
                if (access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                }

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", username);
                request.setAttribute("access", access);

                try {
                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    /////////////////////////////////////////

                    String sidOrderCard = request.getParameter("idOrder");
                    String sCardType = request.getParameter("cardType");
                    String srequest = request.getParameter("request");

                    OrderCard orderCard = new OrderCard();

                    boolean error = false;

                    /* comprobar idOrder */
                    if (sidOrderCard == null || sidOrderCard.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            orderCard.setIdOrder(Integer.parseInt(sidOrderCard));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /* comprobar card type */
                    if (sCardType == null || sCardType.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            orderCard.setCardType(Integer.parseInt(sCardType));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /* comprobar request */
                    if (srequest == null || srequest.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            orderCard.setRequest(Integer.parseInt(srequest));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    if (!error) {
                        /* actualizar datos */
                        try {
                            orderCardDAO.update(orderCard);
                            request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                        } catch (Exception ex) {
                            request.setAttribute("msgErrorFound", "Error al actualizar, el registro ya no existe.");
                        }

                        /* despachar datos a la vista */
                        try {
                            OrderCard aux = orderCardDAO.findById(orderCard.getIdOrder());
                            request.setAttribute("orderCard", aux);
                        } catch (Exception ex) {
                        }
                    } else {
                        request.setAttribute("msgErrorFound", "Error: Datos corruptos.");
                    }
                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("/orderCard/orderCardUpdate.jsp").forward(request, response);
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
