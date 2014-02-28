/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orderCard;

import Helpers.Format;
import Helpers.ValidationRut;
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
import userCard.UserCard;
import userCard.UserCardDAO;

/**
 *
 * @author patricio
 */
@WebServlet(name = "OrderCardAddServlet", urlPatterns = {"/OrderCardAddServlet"})
public class OrderCardAddServlet extends HttpServlet {

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
            ////////////////////////////////////////// 

            conexion = ds.getConnection();

            OrderCardDAO orderCardDAO = new OrderCardDAO();
            orderCardDAO.setConexion(conexion);

            UserCardDAO userCardDAO = new UserCardDAO();
            userCardDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
            try {

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

                /////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                /////////////////////////////////////////
                try {

                    String btnAdd = request.getParameter("add");
                    String stypeCard = request.getParameter("typeCard");
                    String srequest = request.getParameter("request");
                    String srut = request.getParameter("rut");

                    OrderCard orderCard = new OrderCard();

                    boolean error = false;

                    if (btnAdd == null) {
                        request.setAttribute("msg", "Ingrese una solicitud de tarjeta.");
                    } else {

                        /* comprobar type card */
                        if (stypeCard == null || stypeCard.trim().equals("")) {
                            error = true;
                        } else {
                            try {
                                orderCard.setTypeCard(Integer.parseInt(stypeCard));
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
System.out.println(error);
                        /* comprobar rut */
                        if (srut == null || srut.trim().equals("")) {
                            request.setAttribute("msgErrorRut", "Debe ingresar un RUT.");
                            error = true;
                        } else {
                            request.setAttribute("rut", srut);
                            try {
                                if (ValidationRut.validateRut(srut)) {
                                    orderCard.setRut(Format.getRut(srut));
                                    orderCard.setDv(Format.getDv(srut));

                                    /* buscar usuario */
                                    UserCard auxUC = userCardDAO.findOneByRut(orderCard.getRut());
                                    if (auxUC == null) {
                                        request.setAttribute("msgErrorExist", "Error: No existe cliente.");
                                        error = true;
                                    }
                                } else {
                                    request.setAttribute("msgErrorRut", "Error: RUT inválido.");
                                    error = true;
                                }
                            } catch (Exception ex) {
                                request.setAttribute("msgErrorRut", "Error: RUT inválido.");
                                error = true;
                            }
                        }

                        
                        if (!error) {
                            try {
                                orderCardDAO.insert(orderCard);
                                request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                            } catch (Exception ex) {
                                request.setAttribute("msgErrorIns", "Error al insertar, verifique existencias relacionales. ");
                            }
                        }
                    }

                    /* enviar datos a la vista */
                    request.setAttribute("orderCard", orderCard);

                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("/orderCard/orderCardAdd.jsp").forward(request, response);
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
