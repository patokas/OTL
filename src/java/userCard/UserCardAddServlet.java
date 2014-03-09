/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userCard;

import Helpers.Format;
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
import univesity.University;
import univesity.UniversityDAO;

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

            UserCardDAO userCardDAO = new UserCardDAO();
            userCardDAO.setConexion(conexion);

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

            UniversityDAO universityDAO = new UniversityDAO();
            universityDAO.setConexion(conexion);

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
                // RECIBIR Y COMPROBAR PARAMETROS
                ////////////////////////////////////////
                try {
                    String btnAdd = request.getParameter("add");
                    String srut = request.getParameter("rut");
                    String email = request.getParameter("email");
                    String firstname = request.getParameter("firstName");
                    String lastname = request.getParameter("lastName");
                    String gender = request.getParameter("gender");
                    String sidCity = request.getParameter("idCity");
                    String telephone = request.getParameter("telephone");
                    String facebook = request.getParameter("facebook");
                    String dateBirth = request.getParameter("dateBirth");
                    String sidUniversity = request.getParameter("idUniversity");

                    UserCard reg = new UserCard();

                    boolean error = false;

                    /* verificar si presiono boton */
                    if (btnAdd == null) {
                        request.setAttribute("msg", "Ingrese un nuevo cliente.");
                    } else {
                        /* comprobar rut */
                        if (srut == null || srut.trim().equals("")) {
                            request.setAttribute("msgErrorRut", "Error: Rut inválido. ");
                            error = true;
                        } else {
                            request.setAttribute("rut", srut);
                            try {
                                if (!ValidationRut.validateRut(srut)) {
                                    request.setAttribute("msgErrorRut", "Error: Rut inválido. ");
                                    error = true;
                                } else {
                                    reg.setRut(Format.getRut(srut));
                                    reg.setDv(Format.getDv(srut));

                                    /* buscar cliente */
                                    UserCard aux = userCardDAO.findByRut(reg.getRut());
                                    /* comprobar si existen duplicaciones */
                                    if (aux != null) {
                                        request.setAttribute("msgErrorRut", "Error: ya existe un usuario con ese rut. ");
                                        error = true;
                                    } else {
                                        /* encriptar password y setear */
                                        reg.setPassword(StringMD.getStringMessageDigest(srut, StringMD.MD5));
                                    }
                                }
                            } catch (Exception ex) {
                                request.setAttribute("msgErrorRut", "Error: Rut inválido. ");
                                error = true;
                            }
                        }

                        /* comprobar email */
                        if (email == null || email.trim().equals("")) {
                            request.setAttribute("msgErrorEmail", "Error al recibir email.");
                            error = true;
                        } else {
                            reg.setEmail(email);
                            boolean find = userCardDAO.validateDuplicateEmail(reg);
                            if (find) {
                                request.setAttribute("msgErrorEmail", "Error: ya existe un usuario con ese email. ");
                                error = true;
                            }
                        }

                        /* comprobar firstname */
                        if (firstname == null || firstname.trim().equals("")) {
                            request.setAttribute("msgErrorFirstName", "Error al recibir nombre.");
                            error = true;
                        } else {
                            reg.setFirstName(firstname);

                        }

                        /* comprobar lastname */
                        if (lastname == null || lastname.trim().equals("")) {
                            request.setAttribute("msgErrorLastName", "Error al recibir apellido.");
                            error = true;
                        } else {
                            reg.setLastName(lastname);
                        }

                        if (gender == null || gender.trim().equals("")) {
                            error = true;
                        } else {
                            try {
                                reg.setGender(Integer.parseInt(gender));
                            } catch (NumberFormatException n) {
                                error = true;
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
                                request.setAttribute("msgErrorTelephone", "Error al recibir telefono, debe ser numérico.");
                                error = true;
                            }
                        }

                        /* comprobar facebook */
                        if (facebook == null || facebook.trim().equals("")) {
                            request.setAttribute("msgErrorFacebook", "Error: Debe ingresar facebook.");
                            error = true;
                        } else {
                            reg.setFacebook(facebook);
                        }

                        /* comprobar fecha de nacimiento */
                        if (dateBirth == null || dateBirth.trim().equals("")) {
                            request.setAttribute("msgErrorDateBirth", "Error: Debe ingresar fecha de nacimiento");
                            error = true;
                        } else {
                            reg.setDateBirth(dateBirth);
                            /* validar que la fecha de nacimiento no sea mayor que la fecha actual */                           
                            if (dateBirth.compareTo(Format.currentDate()) > -1) {
                                request.setAttribute("msgErrorDateBirth", "Error: La fecha de nacimiento no puede ser mayor que la fecha actual.");
                                error = true;
                            }
                        }

                        /* comprobar id university */
                        if (sidUniversity == null || sidUniversity.trim().equals("")) {
                            error = true;
                        } else {
                            try {
                                reg.setIdUniversity(Integer.parseInt(sidUniversity));
                            } catch (NumberFormatException n) {
                                error = true;
                            }
                        }

                        //////////////////////////////////////
                        // INSERTAR REGISTRO
                        //////////////////////////////////////

                        if (!error) {
                            try {
                                userCardDAO.insert(reg);
                                request.setAttribute("msgAdd", "Registro ingresado exitosamente! ");
                            } catch (Exception ex) {
                                request.setAttribute("msgErrorDup", "Error: ya existe este registro.");
                            }
                        }
                    }

                    /* obtener lista de ciudades */
                    try {
                        Collection<City> listCity = cityDAO.getAll();
                        request.setAttribute("listCity", listCity);
                    } catch (Exception ex) {
                    }

                    /* obtener lista de universidades */
                    try {
                        Collection<University> listUniversity = universityDAO.getAll();
                        request.setAttribute("listUniversity", listUniversity);
                    } catch (Exception ex) {
                    }

                    request.setAttribute("reg", reg);

                } catch (Exception parameterException) {
                } finally {
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
