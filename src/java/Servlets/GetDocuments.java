/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlets;

import DBConexion.Conexion;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javax.servlet.http.HttpSession;
import jsonRepository.*;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author oc333
 */
public class GetDocuments extends HttpServlet {

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
    Data[] data;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        JsonRepository jsonRepository = new JsonRepository();
        try {
            System.out.println("Consiguiendo Lista Documentos");
            conn = Conexion.getDS().getConnection();
            conn.setAutoCommit(false);
            jsonRepository.setData(obtenerDocs());
            mapper = new ObjectMapper();
            conn.commit();
        } catch (SQLException sqlEx) {
            System.out.println("GetDocuments::processRequest SQLException: " + sqlEx);
        } catch (Exception e) {
            System.out.println("GetDocuments::processRequest Exception: " + e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.print(e);
                }
            }
        }
        out.println(mapper.writeValueAsString(jsonRepository));
    }
    
    public ArrayList<Data> obtenerDocs() throws Exception {
        ArrayList<Data> alListaDocs = new ArrayList<Data>();
        CallableStatement csObtenerDocs = null;
        ResultSet rsListaDocs = null;
        Data data;
        System.out.println("Ejecutando Procedimiento Conseguir Documentos");
        csObtenerDocs = conn.prepareCall("{ call ZMKT.ZMKT7257_MAP_SAD_PKG.OBTENER_LISTA_REGIO(?)}");
        csObtenerDocs.registerOutParameter(1, OracleTypes.CURSOR);
        csObtenerDocs.execute();
        rsListaDocs = (ResultSet) csObtenerDocs.getObject(1);
        while (rsListaDocs.next()) {
            data = new Data();
            data.setIdDoc(rsListaDocs.getString("SALESREP_NUMBER"));
            data.setDescripcion(rsListaDocs.getString("wwid_regional"));
            data.setNombre(rsListaDocs.getString("nombre"));
            alListaDocs.add(data);
        }
        csObtenerDocs.close();
        return alListaDocs;
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
