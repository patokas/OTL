/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package point;

import Helpers.Format;
import Helpers.ValidationRut;
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
import place.Place;
import place.PlaceDAO;

/**
 *
 * @author patricio alberto
 */
@WebServlet(name = "PointAddServlet", urlPatterns = {"/PointAddServlet"})
public class PointAddServlet extends HttpServlet {

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

            PointDAO pointDAO = new PointDAO();
            pointDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

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
                    // RECIBIR PARAMETROS
                    //////////////////////////////////////// 
                    try {

                        String btnAdd = request.getParameter("add");
                        String sidPlace = request.getParameter("idPlace");
                        String rut = request.getParameter("rut");
                        String spoints = request.getParameter("points");

                        Point point = new Point();

                        boolean error = false;

                        /* comprobar si recibe formulario */
                        if (btnAdd == null) {
                            request.setAttribute("msg", "Ingrese puntos.");
                        } else {
                            /* comprobar id place */
                            if (sidPlace == null || sidPlace.trim().equals("")) {
                                request.setAttribute("msgErrorIdPlace", "Error al recibir id Plaza.");
                                error = true;
                            } else {
                                point.setIdPlace(Integer.parseInt(sidPlace));
                            }

                            /* comprobar rut */
                            if (rut == null || rut.trim().equals("") || rut.length() < 3) {
                                request.setAttribute("msgErrorRut", "Error: Debe ingresar RUT.");
                                error = true;
                            } else {
                                request.setAttribute("rut", rut);
                                try {
                                    if (!ValidationRut.validateRut(rut)) {
                                        request.setAttribute("msgErrorRut", "Error: RUT inválido.");
                                        error = true;
                                    } else {
                                        point.setRut(Format.getRut(rut));
                                        point.setDv(Format.getDv(rut));
                                    }
                                } catch (Exception ex) {
                                    request.setAttribute("msgErrorRut", "Error: RUT inválido.");
                                    error = true;
                                }
                            }

                            /* comprobar points */
                            if (spoints == null || spoints.trim().equals("")) {
                                request.setAttribute("msgErrorPoints", "Error: Debe ingresar puntos acumulables por asistencia.");
                                error = true;
                            } else {
                                try {
                                    point.setPoints(Integer.parseInt(spoints));
                                    if (point.getPoints() < 0) {
                                        request.setAttribute("msgErrorPoints", "Error: El valor de puntos no puede ser negativo.");
                                        error = true;
                                    }
                                } catch (NumberFormatException n) {
                                    request.setAttribute("msgErrorPoints", "Error: Debe ingresar un valor numérico para puntos.");
                                    error = true;
                                }
                            }
                            ///////////////////////////////////
                            // EJECUTAR LOGICA DE NEGOCIO
                            ///////////////////////////////////
                            if (!error) {
                                /* insertar nuevo evento */
                                try {
                                    pointDAO.insert(point);
                                    request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                } catch (Exception ex) {
                                    request.setAttribute("msgErrorInsert", "Error: ya existe este registro.");
                                    ex.printStackTrace();
                                }
                            }
                        }

                        /* obtener lista de lugares */
                        try {
                            Collection<Place> listPlace = placeDAO.getAll();
                            request.setAttribute("listPlace", listPlace);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        /* asignar valores de point a la vista */
                        request.setAttribute("point", point);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/point/pointAdd.jsp").forward(request, response);
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
