/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package point;

import Helpers.Format;
import Helpers.ValidationRut;
import event.Event;
import event.EventDAO;
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
@WebServlet(name = "PointUpdateServlet", urlPatterns = {"/PointUpdateServlet"})
public class PointUpdateServlet extends HttpServlet {

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

        /* establecer set de caracteres */
        request.setCharacterEncoding("UTF-8");

        /* definir conexion */
        Connection conexion = null;

        try {
            /////////////////////////////////////////
            // ESTABLECER CONEXION
            ///////////////////////////////////////// 

            conexion = ds.getConnection();

            PointDAO pointDAO = new PointDAO();
            pointDAO.setConexion(conexion);

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

                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    ////////////////////////////////////////
                    try {

                        String sidPlace = request.getParameter("idPlace");
                        String namePlace = request.getParameter("namePlace");
                        String srut = request.getParameter("rut");
                        String sdv = request.getParameter("dv");
                        String spoints = request.getParameter("points");

                        Point point = new Point();

                        boolean error = false;

                        /* comprobar id place */
                        if (sidPlace == null || sidPlace.trim().equals("")) {
                            request.setAttribute("msgErrorIdPlace", "Error al recibir id de plaza.");
                            error = true;
                        } else {
                            point.setIdPlace(Integer.parseInt(sidPlace));
                        }

                        /* comprobar namePlace */
                        if (namePlace == null || namePlace.trim().equals("")) {
                            request.setAttribute("msgErrorNamePlace", "Error al recibir nombre de plaza.");
                            error = true;
                        } else {
                            point.setNamePlace(namePlace);
                        }

                        /* comprobar rut */
                        if (srut == null || srut.trim().equals("") || srut.length() < 2) {
                            request.setAttribute("msgErrorRut", "Error al recibir RUT.");
                            error = true;
                        } else {
                            try {
                                point.setRut(Integer.parseInt(srut));
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorRut", "Error: RUT inválido.");
                                error = true;
                            }
                        }
                        
                        /* comprobar dv */
                        if(sdv == null || sdv.trim().equals("")) {
                            request.setAttribute("msgErrorRut", "Error al recibir RUT.");
                            error = true;
                        } else {
                            point.setDv(sdv);
                        }
                        
                        /* comprobar points */
                        if (spoints == null || spoints.trim().equals("")) {
                            request.setAttribute("msgErrorPoints", "Error: Debe ingresar puntos.");
                            error = true;
                        } else {
                            try {
                                point.setPoints(Integer.parseInt(spoints));
                                if (point.getPoints() < 0) {
                                    request.setAttribute("msgErrorPoints", "Error: Los puntos no pueden ser negativos.");
                                    error = true;
                                }
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorPoints", "Error: Los puntos deben ser numéricos.");
                                error = true;
                            }
                        }
                        
                        if (!error) {
                            /* comprobar existencia */
                            Point aux = pointDAO.findByPoint(point);
                            if (aux != null) {
                                pointDAO.update(point);
                                request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                            } else {
                                request.setAttribute("msgErrorFound", "Error: no existe el evento o ha sido eliminado mientras se actualizaba.");
                            }
                        }

                        request.setAttribute("point", point);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/point/pointUpdate.jsp").forward(request, response);
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
