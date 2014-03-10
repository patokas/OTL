/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package placeCategory;

import category.Category;
import category.CategoryDAO;
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
 * @author patricio
 */
@WebServlet(name = "PlaceCategoryAddServlet", urlPatterns = {"/PlaceCategoryAddServlet"})
public class PlaceCategoryAddServlet extends HttpServlet {

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

            PlaceCategoryDAO placeCategoryDAO = new PlaceCategoryDAO();
            placeCategoryDAO.setConexion(conexion);

            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);

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
                if (access != 555 && access != 777) {
                    request.getRequestDispatcher("/ForbiddenServlet").forward(request, response);
                } else {
                    /* obtener los valores de session y asignar valores a la jsp */
                    request.setAttribute("userJsp", username);
                    request.setAttribute("access", access);

                    try {
                        /////////////////////////////////////////
                        // RECIBIR Y COMPROBAR PARAMETROS
                        /////////////////////////////////////////

                        String btnAdd = request.getParameter("add");
                        String sidPlace = request.getParameter("idPlace");
                        String sidCategory = request.getParameter("idCategory");

                        PlaceCategory placeCategory = new PlaceCategory();

                        boolean error = false;

                        if (btnAdd == null) {
                            request.setAttribute("msg", "Ingrese una promoción o regalo para un cliente.");
                        } else {
                            /* comprobar id place */
                            if (sidPlace == null || sidPlace.trim().equals("")) {
                                error = true;
                            } else {
                                try {
                                    placeCategory.setIdPlace(Integer.parseInt(sidPlace));
                                } catch (NumberFormatException n) {
                                    error = true;
                                }
                            }

                            /* comprobar id category */
                            if (sidCategory == null || sidCategory.trim().equals("")) {
                                error = true;
                            } else {
                                try {
                                    placeCategory.setIdCategory(Integer.parseInt(sidCategory));
                                } catch (NumberFormatException n) {
                                    error = true;
                                }
                            }

                            if (!error) {
                                /* verificar si existe place_category duplicada */
                                PlaceCategory aux = placeCategoryDAO.findById(placeCategory.getIdPlace(), placeCategory.getIdCategory());

                                if (aux != null) {
                                    request.setAttribute("msgErrorDup", "Error: ya existe esta categoría para el lugar.");
                                } else {
                                    try {
                                        placeCategoryDAO.insert(placeCategory);
                                        request.setAttribute("msgOk", "Registro ingresado exitosamente! ");
                                    } catch (Exception sqlException) {
                                        request.setAttribute("msgErrorDup", "Error: ya existe esta categoría para el lugar.");
                                    }
                                }
                            }
                        }

                        /* obtener lista de lugares */
                        Collection<Place> listPlace = placeDAO.getAll();
                        request.setAttribute("listPlace", listPlace);

                        /* obtener lista de categorias */
                        Collection<Category> listCategory = categoryDAO.getAll();
                        request.setAttribute("listCategory", listCategory);

                        /* enviar registro a la vista */
                        request.setAttribute("placeCategory", placeCategory);

                    } catch (Exception parameterException) {
                    } finally {
                        request.getRequestDispatcher("/placeCategory/placeCategoryAdd.jsp").forward(request, response);
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
