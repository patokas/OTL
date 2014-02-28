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
@WebServlet(name = "PlaceUpdateServlet", urlPatterns = {"/PlaceUpdateServlet"})
public class PlaceUpdateServlet extends HttpServlet {
    
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
            
            PlaceDAO placeDAO = new PlaceDAO();
            placeDAO.setConexion(conexion);
            
            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);
            
            CategoryDAO categoryDAO = new CategoryDAO();
            categoryDAO.setConexion(conexion);

            /////////////////////////////////////////
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
                }

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", username);
                request.setAttribute("access", access);
                
                try {
                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    /////////////////////////////////////////

                    String sidPlace = request.getParameter("idPlace");
                    String sidCity = request.getParameter("idCity");
                    String snamePlace = request.getParameter("namePlace");
                    String sadress = request.getParameter("address");
                    String status = request.getParameter("status");
                    String contact = request.getParameter("contact");
                    String description = request.getParameter("description");
                    String urlImage = request.getParameter("urlImage");
                    String urlLogo = request.getParameter("urlLogo");
                    String sidCategory = request.getParameter("idCategory");
                    
                    Place place = new Place();
                    
                    boolean error = false;

                    /* comprobar id place */
                    if (sidPlace == null || sidPlace.trim().equals("")) {
                        request.setAttribute("msgErrorIdPlace", "Error al recibir id plaza. ");
                        error = true;
                    } else {
                        try {
                            place.setIdPlace(Integer.parseInt(sidPlace));
                        } catch (NumberFormatException n) {
                            request.setAttribute("msgErrorIdPlace", "Error al recibir id plaza. ");
                            error = true;
                        }
                    }

                    /* comprobar id city */
                    if (sidCity == null || sidCity.trim().equals("")) {
                        request.setAttribute("msgErrorIdCity", "Error al recibir id ciudad. ");
                        error = true;
                    } else {
                        try {
                            place.setIdCity(Integer.parseInt(sidCity));
                        } catch (NumberFormatException n) {
                            request.setAttribute("msgErrorIdCity", "Error al recibir id ciudad. ");
                            error = true;
                        }
                    }

                    /* comprobar id category */
                    if (sidCategory == null || sidCategory.trim().equals("")) {
                        request.setAttribute("msgErrorIdCategory", "Error al recibir id categoría");
                        error = true;
                    } else {
                        try {
                            place.setIdCategory(Integer.parseInt(sidCategory));
                        } catch (NumberFormatException n) {
                            request.setAttribute("msgErrorIdCategory", "Error al recibir id categoría");
                            error = true;
                        }
                    }

                    /* comprobar name place */
                    if (snamePlace == null || snamePlace.trim().equals("")) {
                        request.setAttribute("msgErrorNamePlace", "Error: Debe ingresar un nombre para la Plaza. ");
                        error = true;
                    } else {
                        place.setNamePlace(snamePlace);
                    }

                    /* comprobar address */
                    if (sadress == null || sadress.trim().equals("")) {
                        request.setAttribute("msgErrorAddress", "Error: Debe ingresar dirección de la plaza");
                        error = true;
                    } else {
                        place.setAddress(sadress);
                    }

                    /* comprobar status*/
                    if (status == null || status.trim().equals("")) {
                        request.setAttribute("msgErrorStatus", "Error: deber ingresar un estado de servicio válido (0: de Alta; 1: de Baja) ");
                        error = true;
                    } else {
                        try {
                            place.setStatus(Integer.parseInt(status));
                        } catch (NumberFormatException n) {
                            request.setAttribute("msgErrorStatus", "Error: deber ingresar un estado de servicio válido (0: de Alta; 1: de Baja) ");
                            error = true;
                        }
                    }

                    /* comprobar contact */
                    if (contact == null || contact.trim().equals("")) {
                    } else {
                        try {
                            place.setContact(Integer.parseInt(contact));
                        } catch (NumberFormatException n) {
                            request.setAttribute("msgErrorContact", "Error: El número de contacto debe ser numérico. ");
                            error = true;
                        }
                    }

                    /* comprobar description */
                    if (description == null || description.trim().equals("")) {
                        request.setAttribute("msgErrorDes", "Error: Debe ingresar descripción de la plaza.");
                        error = true;
                    } else {
                        place.setDescription(description);
                    }

                    /* comprobar url image */
                    if (urlImage == null || urlImage.trim().equals("")) {
                        request.setAttribute("msgErrorUrlImage", "Error: Debe ingresar la url de la imagen.");
                        error = true;
                    } else {
                        place.setUrlImage(urlImage);
                    }
                    
                    /* comprobar url logo */
                    if(urlLogo == null || urlLogo.trim().equals("")) {
                        request.setAttribute("msgErrorUrlLogo", "Error: Debe ingresar la url del logo.");
                        error = true;
                    } else {
                        place.setUrlLogo(urlLogo);
                    }
                    
                    if (!error) {
                        /* comprobar duplicaciones */
                        Collection<Place> list = placeDAO.findByName(place.getNamePlace());
                        if (list.size() > 0) {
                            for (Place aux : list) {
                                if (aux.getIdPlace() != place.getIdPlace()) {
                                    request.setAttribute("msgErrorDup", "Error: ya existe esta plaza. ");
                                    error = true;
                                }
                            }
                        }
                    }
                    if (!error) {
                        /* comprobar existencia */
                        Collection list = placeDAO.findById(place.getIdPlace());
                        if (list.size() > 0) {
                            placeDAO.update(place);
                            request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                        } else {
                            request.setAttribute("msgErrorFound", "Error: No existe la plaza o ha sido eliminada mientras se actualizaba. ");
                        }
                    }

                    /////////////////////////////////////////
                    // ESTABLECER ATRIBUTOS AL REQUEST
                    /////////////////////////////////////////

                    Collection<City> listCity = cityDAO.getAll();
                    request.setAttribute("listCity", listCity);
                    
                    Collection<Category> listCategory = categoryDAO.getAll();
                    request.setAttribute("listCategory", listCategory);
                    
                    request.setAttribute("place", place);
                    
                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("/place/placeUpdate.jsp").forward(request, response);
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
