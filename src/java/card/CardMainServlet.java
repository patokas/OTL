/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package card;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "CardMainServlet", urlPatterns = {"/CardMainServlet"})
public class CardMainServlet extends HttpServlet {

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
            /////////////////////////////////////
            // ESTABLECER CONEXION
            /////////////////////////////////////

            conexion = ds.getConnection();

            CardDAO cardDAO = new CardDAO();
            cardDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String user = (String) session.getAttribute("username");

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", user);
                request.setAttribute("access", access);

                try {
                    //////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    //////////////////////////////////////////

                    String btnDelRow = request.getParameter("btnDelRow");
                    String btnDelCol = request.getParameter("btnDelCol");

                    Card card = new Card();

                    //////////////////////////////////////////
                    // ELIMINAR POR REGISTRO
                    //////////////////////////////////////////
                    if (btnDelRow != null) {
                        try {
                            /* recibir parámetros */
                            int barcode = Integer.parseInt(request.getParameter("barCode"));
                            try {
                                cardDAO.delete(barcode);
                                request.setAttribute("msgDel", "Un registro ha sido eliminado.");
                            } catch (Exception deleteException) {
                            }
                        } catch (Exception parameterException) {
                        }
                    }
                    //////////////////////////////////////////
                    // ELIMINAR VARIOS REGISTROS
                    //////////////////////////////////////////
                    if (btnDelCol != null) {
                        try {
                            String[] outerArray = request.getParameterValues("chk");
                            int cont = 0;
                            int i = 0;
                            while (outerArray[i] != null) {
                                try {
                                    cardDAO.delete(Integer.parseInt(outerArray[i]));
                                    cont++;
                                    request.setAttribute("msgDel", cont + " registro(s) han sido eliminado(s).");
                                } catch (Exception deleteException) {
                                }
                                i++;
                            }
                        } catch (Exception parameterException) {
                        }
                    }

                    /* obtener todos los registros */
                    Collection<Card> listCard = cardDAO.getAll();

                    /* mensajes de entradas */
                    if (listCard.size() == 1) {
                        request.setAttribute("msg", "1 registro encontrado en la base de datos.");
                    } else if (listCard.size() > 1) {
                        request.setAttribute("msg", listCard.size() + " registros encontrados en la base de datos.");
                    } else if (listCard.isEmpty()) {
                        request.setAttribute("msg", "No hay registros encontrado en la base de datos.");
                    }
                    request.setAttribute("list", listCard);

                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("/card/card.jsp").forward(request, response);
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
