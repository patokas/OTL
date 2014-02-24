package category;

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
@WebServlet(name = "CategoryMainServlet", urlPatterns = {"/CategoryMainServlet"})
public class CategoryMainServlet extends HttpServlet {

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
            /////////////////////////////////////////

            conexion = ds.getConnection();

            CategoryDAO categoryDAO = new CategoryDAO();
            categoryDAO.setConexion(conexion);

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

                try {
                    //////////////////////////////////////
                    // RECIBIR Y COMPROBAR PARAMETROS
                    //////////////////////////////////////

                    String btnDelRow = request.getParameter("btnDelRow");
                    String btnDelCol = request.getParameter("btnDelCol");

                    Category category = new Category();

                    //////////////////////////////////////////
                    // ELIMINAR POR REGISTRO
                    //////////////////////////////////////////
                    if (btnDelRow != null) {
                        /* recibir parametros */
                        category.setIdCategory(Integer.parseInt(request.getParameter("idCategory")));
                        try {
                            categoryDAO.delete(category.getIdCategory());
                            request.setAttribute("msgDel", "Una categoria ha sido eliminada.");
                        } catch (Exception referenceException) {
                            request.setAttribute("msgErrorReference", "Error: Existen referencias asociadas y no puede eliminar.");
                        }
                    }

                    //////////////////////////////////////////
                    // ELIMINAR VARIOS REGISTROS
                    //////////////////////////////////////////
                    if (btnDelCol != null) {
                        /* recibir parametros*/
                        String[] outerArray = request.getParameterValues("chk");
                        try {
                            int i = 0;
                            int cont = 0;
                            while (outerArray[i] != null) {
                                try {
                                    categoryDAO.delete(Integer.parseInt(outerArray[i]));
                                    cont++;
                                    if (cont == 1) {
                                        request.setAttribute("msgDel", "Un registro ha sido eliminado.");
                                    } else if (cont > 1) {
                                        request.setAttribute("msgDel", cont + " registros han sido eliminados.");
                                    }
                                } catch (Exception ex) {
                                    request.setAttribute("msgErrorReference", "Error: No puede eliminar la ciudad con ID: " + outerArray[i] + ", existen referencias asociadas.");
                                }
                                i++;
                            }
                        } catch (Exception ex) {
                        }
                    }

                    //////////////////////////////////////////
                    // OBTENER TOTAL DE REGISTROS
                    //////////////////////////////////////////
                    Collection<Category> list = categoryDAO.getAll();

                    if (list.size() == 1) {
                        request.setAttribute("msg", "1 registro encontrado en la base de datos.");
                    } else if (list.size() > 1) {
                        request.setAttribute("msg", list.size() + " registros encontrados en la base de datos.");
                    } else if (list.isEmpty()) {
                        request.setAttribute("msg", "No hay registros encontrado en la base de datos.");
                    }

                    request.setAttribute("list", list);

                } catch (Exception parameterException) {
                } finally {
                    request.getRequestDispatcher("category/category.jsp").forward(request, response);
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
