/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userCard;

import Helpers.Format;
import Helpers.StringMD;
import Helpers.ValidationRut;
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
import univesity.University;
import univesity.UniversityDAO;

/**
 *
 * @author alexander
 */
@WebServlet(name = "UserCardUpdateServlet", urlPatterns = {"/UserCardUpdateServlet"})
public class UserCardUpdateServlet extends HttpServlet {

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

            UserCardDAO usercardDAO = new UserCardDAO();
            usercardDAO.setConexion(conexion);

            CityDAO cityDAO = new CityDAO();
            cityDAO.setConexion(conexion);

            UniversityDAO universityDAO = new UniversityDAO();
            universityDAO.setConexion(conexion);

            //////////////////////////////////////////
            // COMPROBAR SESSION
            /////////////////////////////////////////
            try {
                /* recuperar sesion */
                HttpSession session = request.getSession(false);

                /* obtener parametros de session */
                int access = Integer.parseInt((String) session.getAttribute("access"));
                String user = (String) session.getAttribute("username");

                /* obtener los valores de session y asignar valores a la jsp */
                request.setAttribute("userJsp", user);
                request.setAttribute("access", access);

                try {

                    /////////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    ////////////////////////////////////////

                    String srut = request.getParameter("rut"); //rut + dv
                    String firstname = request.getParameter("firstName");
                    String lastname = request.getParameter("lastName");
                    String telephone = request.getParameter("telephone");
                    String sidCity = request.getParameter("idCity");
                    String email = request.getParameter("email");
                    String gender = request.getParameter("gender");
                    String facebook = request.getParameter("facebook");
                    String dateBirth = request.getParameter("dateBirth");
                    String sidUniversity = request.getParameter("idUniversity");

                    /* parametros para actualizar password */
                    String chkPwd = request.getParameter("chk");
                    String pwd1 = request.getParameter("pwd1");
                    String pwd2 = request.getParameter("pwd2");

                    UserCard userCardReg = new UserCard();

                    boolean error = false;

                    /* comprobar rut */
                    if (srut == null || srut.trim().equals("")) {
                        request.setAttribute("msgErrorRut", "Error: Rut inválido. ");
                        error = true;
                    } else {
                        request.setAttribute("rut", srut);
                        try {
                            if (!ValidationRut.validateRut(srut)) {
                                error = true;
                                request.setAttribute("msgErrorRut", "Error: Rut inválido. ");
                            } else {
                                userCardReg.setRut(Format.getRut(srut));
                                userCardReg.setDv(Format.getDv(srut));

                                /* comprobar existencia */
                                UserCard aux = usercardDAO.findByRut(userCardReg.getRut());
                                if (aux == null) {
                                    request.setAttribute("msgErrorFound", "Error: El usuario no ha sido encontrado o ha sido eliminado mientras se actualizaba");
                                    error = true;
                                }
                            }
                        } catch (Exception ex) {
                            request.setAttribute("msgErrorRut", "Error: Rut inválido. ");
                            error = true;
                        }
                    }

                    /* comprobar firstname */
                    if (firstname == null || firstname.trim().equals("")) {
                        request.setAttribute("msgErrorFirstName", "Error al recibir nombre.");
                        error = true;
                    } else {
                        userCardReg.setFirstName(Format.capital(firstname));
                    }

                    /* comprobar lastname */
                    if (lastname == null || lastname.trim().equals("")) {
                        request.setAttribute("msgErrorLastName", "Error al recibir apellido.");
                        error = true;
                    } else {
                        userCardReg.setLastName(Format.capital(lastname));
                    }

                    /* comprobar email */
                    if (email == null || email.trim().equals("")) {
                        request.setAttribute("msgErrorEmail", "Error al recibir email.");
                        error = true;
                    } else {
                        userCardReg.setEmail(email);
                        boolean find = usercardDAO.validateDuplicateEmail(userCardReg);
                        if (find) {
                            request.setAttribute("msgErrorEmail", "Error: ya existe un usuario con ese email. ");
                            error = true;
                        }
                    }

                    /* comprobar genero */
                    if (gender == null || gender.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            userCardReg.setGender(Integer.parseInt(gender));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }

                    /* comprobar id city */
                    if (sidCity == null || sidCity.trim().equals("")) {
                        error = true;
                    } else {
                        try {
                            userCardReg.setIdCity(Integer.parseInt(sidCity));
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
                            userCardReg.setTelephone(Integer.parseInt(telephone));
                        } catch (NumberFormatException n) {
                            request.setAttribute("msgErrorTelephone", "Error al recibir telefono, los valores deben ser numericos.");
                            error = true;
                        }
                    }

                    /* comprobar facebook */
                    if (facebook == null || facebook.trim().equals("")) {
                        request.setAttribute("msgErrorFacebook", "Error: Debe ingresar facebook.");
                        error = true;
                    } else {
                        userCardReg.setFacebook(facebook);
                    }

                    /* comprobar fecha de nacimiento */
                    if (dateBirth == null || dateBirth.trim().equals("")) {
                        request.setAttribute("msgErrorDateBirth", "Error: Debe ingresar fecha de nacimiento");
                        error = true;
                    } else {
                        userCardReg.setDateBirth(dateBirth);
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
                            userCardReg.setIdUniversity(Integer.parseInt(sidUniversity));
                        } catch (NumberFormatException n) {
                            error = true;
                        }
                    }
                    
                    System.out.println("id uni " + userCardReg.getIdUniversity() );

                    /////////////////////////////////////////
                    // ACTUALIZAR REGISTRO 
                    ////////////////////////////////////////

                    /* comprobar checkbox password */
                    if (chkPwd != null) {
                        /* comprobar pwd1 */
                        if (pwd1 == null || pwd1.trim().equals("")) {
                            request.setAttribute("msgErrorPwd1", "Error: Debe ingresar password.");
                        } else {
                            userCardReg.setPwd1(pwd1);
                            /* comprobar pwd2 */
                            if (pwd2 == null || pwd2.trim().equals("")) {
                                request.setAttribute("msgErrorPwd1", "Error: Debe ingresar password.");
                            } else {
                                userCardReg.setPwd2(pwd2);
                                /* comprobar coincidencias */
                                if (!pwd1.equals(pwd2)) {
                                    request.setAttribute("msgErrorPwd1", "Error: Las password's no coinciden");
                                    error = true;
                                }
                                if (pwd1.length() < 6 || pwd2.length() < 6) {
                                    request.setAttribute("msgErrorPwd2", "Error: La password debe poseer al menos 6 caracteres");
                                    error = true;
                                }

                                if (!error) {
                                    /* encriptar password */
                                    userCardReg.setPassword(StringMD.getStringMessageDigest(pwd1, StringMD.MD5));
                                    usercardDAO.updatePassword(userCardReg);
                                    request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                                }
                            }
                        }
                    } else {
                        if (!error) {
                            usercardDAO.update(userCardReg);
                            request.setAttribute("msgOk", "Registro actualizado exitosamente! ");
                        }
                    }

                    /* retornar list city */
                    Collection<City> listCity = cityDAO.getAll();
                    request.setAttribute("listCity", listCity);

                    /* obtener lista de universidades */
                    try {
                        Collection<University> listUniversity = universityDAO.getAll();
                        request.setAttribute("listUniversity", listUniversity);
                    } catch (Exception ex) {
                    }

                    /* retornar registro */
                    request.setAttribute("reg", userCardReg);

                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("/userCard/userCardUpdate.jsp").forward(request, response);
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
