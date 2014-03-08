/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univesity;

import city.City;
import city.CityDAO;
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
 * @author patricio
 */
@WebServlet(name = "UniversityMainServlet", urlPatterns = {"/UniversityMainServlet"})
public class UniversityMainServlet extends HttpServlet {

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

        /////////////////////////////////////////
        // ESTABLECER CONEXION
        /////////////////////////////////////////
        try {
            conexion = ds.getConnection();

            UniversityDAO universityDAO = new UniversityDAO();
            universityDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener los valores de session */
                String userJsp = (String) session.getAttribute("username");
                String sAccess = (String) session.getAttribute("access");
                int access = Integer.parseInt(sAccess);

                /* asignar valores a la jsp */
                request.setAttribute("userJsp", userJsp);
                request.setAttribute("access", access);

                //////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                //////////////////////////////////////
                try {
                    String btnDelRow = request.getParameter("btnDelRow");
                    String btnDelCol = request.getParameter("btnDelCol");

                    University university = new University();

                    //////////////////////////////////////////
                    // ELIMINAR POR REGISTRO
                    //////////////////////////////////////////
                    if (btnDelRow != null) {
                        /* recibir parametros */
                        university.setIdUniversity(Integer.parseInt(request.getParameter("idUniversity")));
                        /* eliminar registro */
                        try {
                            universityDAO.delete(university.getIdUniversity());
                            request.setAttribute("msgDel", "Un registro ha sido eliminado.");
                        } catch (Exception referenceException) {
                            request.setAttribute("msgErrorReference", "Error: El registro posee referencias y no puede ser eliminado.");
                        }
                    }

                    //////////////////////////////////////////
                    // ELIMINAR VARIOS REGISTROS
                    //////////////////////////////////////////
                    if (btnDelCol != null) {
                        /* recibir parametros*/
                        String[] outerArray = request.getParameterValues("chk");
                        try {
                            int i = 0;
                            int cont = 0;
                            while (outerArray[i] != null) {
                                try {
                                    universityDAO.delete(Integer.parseInt(outerArray[i]));
                                    cont++;
                                    if (cont == 1) {
                                        request.setAttribute("msgDel", "Un registro ha sido eliminado.");
                                    } else if (cont > 1) {
                                        request.setAttribute("msgDel", cont + " registros han sido eliminados.");
                                    }
                                } catch (Exception ex) {
                                    request.setAttribute("msgErrorReference", "Error: No puede eliminar el registro con ID: " + outerArray[i] + ", existen referencias asociadas.");
                                }
                                i++;
                            }
                        } catch (Exception ex) {
                        }
                    }

                    /* obtener list de universidades */
                    try {
                        Collection<University> list = universityDAO.getAll();
                        request.setAttribute("list", list);

                        if (list.size() == 1) {
                            request.setAttribute("msg", "1 registro encontrado en la base de datos.");
                        } else if (list.size() > 1) {
                            request.setAttribute("msg", list.size() + " registros encontrados en la base de datos.");
                        } else if (list.isEmpty()) {
                            request.setAttribute("msg", "No hay registros encontrado en la base de datos.");
                        }
                    } catch (Exception ex) {
                    }

                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("/university/university.jsp").forward(request, response);
                }
            } catch (Exception sessionException) {
                /* enviar a la vista de login */
                System.out.println("no ha iniciado session");
                request.getRequestDispatcher("/login/login.jsp").forward(request, response);
            }
        } catch (Exception connectionException) {
            connectionException.printStackTrace();
        } finally {
            /* cerrar conexion */
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
