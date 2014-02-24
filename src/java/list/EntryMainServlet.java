/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package list;

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
import place.Place;
import place.PlaceDAO;

/**
 *
 * @author patricio alberto
 */
@WebServlet(name = "EntryMainServlet", urlPatterns = {"/EntryMainServlet"})
public class EntryMainServlet extends HttpServlet {

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

            ListDAO entryDAO = new ListDAO();
            entryDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            //////////////////////////////////////////
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
                        //////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        //////////////////////////////////////////

                        String btnDelRow = request.getParameter("btnDelRow");
                        String btnDelCol = request.getParameter("btnDelCol");

                        List entry = new List();

                        //////////////////////////////////////////
                        // ELIMINAR POR REGISTRO
                        //////////////////////////////////////////
                        if (btnDelRow != null) {
                            /* recibir parametros */
                            try {
                                entry.setIdList(Integer.parseInt(request.getParameter("idList")));
                                try {
                                    entryDAO.delete(entry);
                                    request.setAttribute("msgDel", "Un Registro ha sido eliminado.");
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
                                /* recibir parametros */
                                String[] outerArray = request.getParameterValues("chk");

                                int cont = 0;
                                int i = 0;
                                while (outerArray[i] != null) {
                                    entry.setIdList(Integer.parseInt(outerArray[i]));
                                    try {
                                        entryDAO.delete(entry);
                                        cont++;
                                        if (cont > 1) {
                                            request.setAttribute("msgDel", cont + " registos han sido eliminados.");
                                        } else if (cont == 1) {
                                            request.setAttribute("msgDel", " Un registro ha sido eliminado.");
                                        }
                                    } catch (Exception deleteException) {
                                    }
                                    i++;
                                }
                            } catch (Exception paramemterException) {
                            }
                        }
                        //////////////////////////////////////////
                        // ESTABLECER ATRIBUTOS AL REQUEST
                        //////////////////////////////////////////

                        Collection<List> listEntry = entryDAO.getAll();
                        request.setAttribute("msg", listEntry.size() + " registros encontrados en la base de datos.");

                        request.setAttribute("list", listEntry);
                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("list/list.jsp").forward(request, response);
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
