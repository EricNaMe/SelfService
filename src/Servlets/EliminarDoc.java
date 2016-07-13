package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsonRepository.Data;
import jsonRepository.JsonRepository;
import oracle.jdbc.OracleTypes;

import com.cummins.DBConexion.Conexion;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class EliminarDoc
 */
public class EliminarDoc extends HttpServlet {
	/**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private Connection conn;
    String strId;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            System.out.println("Eliminando Documento");
            conn = Conexion.getDS().getConnection();
            conn.setAutoCommit(false);
            strId = (String) request.getParameter("id");
            eliminarDoc();
            conn.commit();
            out.println("El registro fue eliminado exitosamente");
        } catch (SQLException sqlEx) {
            System.out.println("EliminarDoc::processRequest SQLException: " + sqlEx);
        } catch (Exception e) {
            System.out.println("EliminarDoc::processRequest Exception: " + e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.print(e);
                }
            }
        }
    }
    
    public void eliminarDoc() throws Exception {
        CallableStatement csObtenerDocs = null;
        System.out.println("Ejecutando Procedimiento Eliminar Docuemnto");
        csObtenerDocs = conn.prepareCall("{ call LOTUS.ZMKT7352_DES_PKG.ELIMINAR_DOC(?)}");
        csObtenerDocs.setString(1, strId);
        csObtenerDocs.execute();
        csObtenerDocs.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
