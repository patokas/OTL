/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userCard;

import Helpers.Console;
import Helpers.Format;
import Helpers.Rut;
import Helpers.StringMD;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import Helpers.ValidationRut;
import city.City;
import city.CityDAO;
import javax.servlet.http.HttpSession;

/**
 *
 * @author patricio Alberto
 */
@WebServlet(name = "UserCardAddServlet", urlPatterns = {"/UserCardAddServlet"})
public class UserCardAddServlet extends HttpServlet {

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

            UserCardDAO dao = new UserCardDAO();
            dao.setConexion(conexion);

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            //////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String user = (String) session.getAttribute("username");

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", user); // username
                request.setAttribute("access", access); // nivel de acceso                    

                ////////////////////////////////////////
                // DECLARAR VARIABLES DE INSTANCIA
                ///////////////////////////////////////                 

                UserCard reg = new UserCard();

                try {

                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    ////////////////////////////////////////

                    String btnAdd = request.getParameter("add");
                    String srut = request.getParameter("rut");
                    String email = request.getParameter("email");
                    String firstname = request.getParameter("firstName");
                    String lastname = request.getParameter("lastName");
                    String genre = request.getParameter("genre");
                    String sidCity = request.getParameter("idCity");
                    String telephone = request.getParameter("telephone");

                    boolean error = false;

                    /* verificar si presiono boton */
                    if (btnAdd == null) {
                        request.setAttribute("msg", "Ingrese un nuevo cliente.");
                    } else {
                        /* comprobar rut */
                        if (srut == null || srut.trim().equals("")) {
                            request.setAttribute("msgErrorRut", "Error al recibir rut. Parametro no recibido.");
                            error = true;
                        } else {
                            ValidationRut helper = new ValidationRut();
                            reg.setRut(Format.getRut(srut));
                            reg.setDv(Format.getDv(srut));
                            /* validar rut */
                            if (helper.validarRut(srut)) {

                                Collection<UserCard> list = dao.findByRut(reg.getRut());
                                /* comprobar si existen duplicaciones */
                                if (list.size() > 0) {
                                    request.setAttribute("msgErrorRut", "Error: ya existe un usuario con ese rut. ");
                                    error = true;
                                } else {
                                    /* encriptar password y setear */
                                    reg.setPassword(StringMD.getStringMessageDigest(srut, StringMD.MD5));
                                }
                            } else {
                                request.setAttribute("msgErrorRut", "Error: Rut inválido. ");
                                error = true;
                            }
                        }

                        /* comprobar email*/
                        if (email == null || email.trim().equals("")) {
                            request.setAttribute("msgErrorEmail", "Error al recibir email.");
                            error = true;
                        } else {
                            reg.setEmail(email);
                            Collection<UserCard> list = dao.findByEmail(reg.getEmail());
                            for (UserCard aux : list) {
                                if (aux.getRut() > 0) {
                                    request.setAttribute("msgErrorEmail", "Error: ya existe un usuario con ese email. ");
                                    error = true;
                                }
                            }
                        }

                        /* comprobar firstname */
                        if (firstname == null || firstname.trim().equals("")) {
                            request.setAttribute("msgErrorFirstName", "Error al recibir nombre.");
                            error = true;
                        } else {
                            reg.setFirstName(firstname);

                        }

                        /* comprobar lastname*/
                        if (lastname == null || lastname.trim().equals("")) {
                            request.setAttribute("msgErrorLastName", "Error al recibir apellido.");
                            error = true;
                        } else {
                            reg.setLastName(lastname);
                        }

                        if (genre == null || genre.trim().equals("")) {
                            error = true;
                        } else {
                            try {
                                reg.setGenre(Integer.parseInt(genre));
                            } catch (NumberFormatException n) {
                                error = true;
                                // ERROR AL RECIBIR UN VALOR NO NUMERICO
                            }
                        }
                        /* comprobar ciudad */
                        if (sidCity == null || sidCity.trim().equals("")) {
                            error = true;
                        } else {
                            try {
                                reg.setIdCity(Integer.parseInt(sidCity));
                            } catch (NumberFormatException n) {
                                error = true;
                                // ERROR AL RECIBIR UN VALOR NO NUMERICO
                            }
                        }
                        /* comprobar telefono */
                        if (telephone == null || telephone.trim().equals("")) {
                            request.setAttribute("msgErrorTelephone", "Error al recibir telefono.");
                            error = true;
                        } else {
                            try {
                                reg.setTelephone(Integer.parseInt(telephone));
                            } catch (NumberFormatException n) {
                                request.setAttribute("msgErrorTelephone", "Error al recibir telefono, los valores deben ser numericos.");
                                error = true;
                            }
                        }

                        /////////////////////////////////////////
                        // INSERTAR REGISTRO
                        ////////////////////////////////////////

                        if (!error) {
                            dao.insert(reg);
                            request.setAttribute("msgAdd", "Registro ingresado exitosamente! ");
                        }
                    }
                } catch (Exception parameterException) {
                } finally {
                    Collection<City> listCity = cityDAO.getAll();
                    request.setAttribute("listCity", listCity);
                    request.setAttribute("reg", reg);
                    request.getRequestDispatcher("/userCard/userCardAdd.jsp").forward(request, response);
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
