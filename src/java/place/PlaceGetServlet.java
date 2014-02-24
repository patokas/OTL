/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package place;

import category.Category;
import category.CategoryDAO;
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
 * @author patricio alberto
 */
@WebServlet(name = "PlaceGetServlet", urlPatterns = {"/PlaceGetServlet"})
public class PlaceGetServlet extends HttpServlet {

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

            PlaceDAO dao = new PlaceDAO();
            dao.setConexion(conexion);

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

            CategoryDAO categoryDAO = new CategoryDAO();
            categoryDAO.setConexion(conexion);

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
                if (access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {

                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", username);
                    request.setAttribute("access", access);
                    request.setAttribute("su", 777); //superuser                    

                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        /////////////////////////////////////////

                        String sidPlace = request.getParameter("idPlace");

                        boolean error = false;

                        /* comprobar id place */
                        int id = 0;
                        if (sidPlace == null || sidPlace.trim().equals("")) {
                            error = true;
                        } else {
                            id = Integer.parseInt(sidPlace);
                        }

                        if (!error) {
                            /* buscar place */
                            Collection<Place> list = dao.findById(id);
                            if (list.size() > 0) {
                                for (Place reg : list) {
                                    if (reg.getIdPlace() > 0) {
                                        request.setAttribute("place", reg);
                                        request.setAttribute("msgOk", "Se encontró el registro!");
                                    }
                                }
                            } else {
                                request.setAttribute("msgErrorFound", "Error: No se encontró el registro.");
                            }
                        }

                        //////////////////////////////////////////
                        // ESTABLECER ATRIBUTOS AL REQUEST
                        /////////////////////////////////////////

                        Collection<City> listCity = cityDAO.getAll();
                        request.setAttribute("listCity", listCity);

                        Collection<Category> listCategory = categoryDAO.getAll();
                        request.setAttribute("listCategory", listCategory);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/place/placeUpdate.jsp").forward(request, response);
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
