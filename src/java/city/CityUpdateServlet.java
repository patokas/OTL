/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package city;

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
 * @author patricio alberto
 */
@WebServlet(name = "CityUpdateServlet", urlPatterns = {"/CityUpdateServlet"})
public class CityUpdateServlet extends HttpServlet {

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

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

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
                // DECLARAR VARIABLES DE INSTANCIA
                //////////////////////////////////////// 

                City city = new City();

                try {
                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    //////////////////////////////////////// 

                    String sidCity = request.getParameter("idCity");
                    String nameCity = request.getParameter("nameCity");

                    boolean error = false;

                    /* comprobar id city */
                    if (sidCity == null || sidCity.trim().equals("")) {
                        request.setAttribute("msgErrorId", "Error al recibir id Ciudad. ");
                        error = true;
                    } else {
                        city.setIdCity(Integer.parseInt(sidCity));
                    }
                    /* comprobar name city */
                    if (nameCity == null || nameCity.trim().equals("")) {
                        request.setAttribute("msgErrorNameCity", "Error al recibir nombre Ciudad. ");
                        error = true;
                    } else {
                        city.setNameCity(Format.capital(nameCity));
                    }

                    if (!error) {
                        /* comprobar ciudad duplicada */
                        boolean find = cityDAO.validateDuplicateName(city);
                        if (find) {
                            request.setAttribute("msgErrorDup", "Error: ya existe una ciudad con este nombre. ");
                        } else {
                            /* comprobar existencia */
                            City aux = cityDAO.findbyIdCity(city);
                            if (aux != null) {
                                cityDAO.update(city);
                                request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                            } else {
                                request.setAttribute("msgErrorFound", "Error: La ciudad no existe o ha sido eliminada mientras se actualizaba.");
                            }
                        }
                    }
                } catch (Exception parameterException) {
                } finally {
                    request.setAttribute("city", city);
                    request.getRequestDispatcher("/city/cityUpdate.jsp").forward(request, response);
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
