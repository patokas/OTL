/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univesity;

import Helpers.Format;
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
@WebServlet(name = "UniversityUpdateServlet", urlPatterns = {"/UniversityUpdateServlet"})
public class UniversityUpdateServlet extends HttpServlet {

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
            //////////////////////////////////////// 

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

                /////////////////////////////////////////
                // RECIBIR Y COMPROBAR PARAMETROS
                ////////////////////////////////////////
                try {
                    String sidUniversity = request.getParameter("idUniversity");
                    String nameUniversity = request.getParameter("nameUniversity");

                    University university = new University();

                    boolean error = false;

                    /* comprobar id */
                    if (sidUniversity == null || sidUniversity.trim().equals("")) {
                        request.setAttribute("msgErrorId", "Error al recibir ID. ");
                        error = true;
                    } else {
                        university.setIdUniversity(Integer.parseInt(sidUniversity));
                    }
                    /* comprobar name */
                    if (nameUniversity == null || nameUniversity.trim().equals("")) {
                        request.setAttribute("msgErrorName", "Error al recibir nombre. ");
                        error = true;
                    } else {
                        university.setNameUniversity(Format.capital(nameUniversity));
                    }

                    if (!error) {
                        /* comprobar duplicados */
                        boolean find = universityDAO.validateDuplicate(university);
                        if (find) {
                            request.setAttribute("msgErrorDup", "Error: ya existe un registro con este nombre. ");
                        } else {
                            /* comprobar existencia */
                            University aux = universityDAO.findById(university.getIdUniversity());
                            if (aux != null) {
                                universityDAO.update(university);
                                request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                            } else {
                                request.setAttribute("msgErrorFound", "Error: El registro no existe o ha sido eliminado mientras se actualizaba.");
                            }
                        }
                    }
                    request.setAttribute("uni", university);
                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("/university/universityUpdate.jsp").forward(request, response);
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
