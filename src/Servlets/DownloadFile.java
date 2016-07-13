/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import com.cummins.DBConexion.Conexion;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.jdbc.OracleTypes;

/**
 *
 * @author nn919
 */
public class DownloadFile extends HttpServlet {

  
	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE = 4096;   
	private Connection conn;
    HttpSession session;
    private String strId;
    Blob blob;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            System.out.println("Descargando Documento");
            session = request.getSession();
            conn = Conexion.getDS().getConnection();
            conn.setAutoCommit(false);
            strId = (String)request.getParameter("id_doc");
            ObtenerCantMec(request,response);
        } catch (SQLException sqlEx) {
            System.out.println("DownloadFile::processRequest SQLException: " + sqlEx);
        } catch (NumberFormatException numEx) {
            System.out.println("DownloadFile::processRequest NumberFormatException: " + numEx);
        } catch (Exception e) {
            System.out.println("DownloadFile::processRequest Exception: " + e);
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

    public void ObtenerCantMec (HttpServletRequest request, HttpServletResponse response) throws Exception {

        CallableStatement csObtenerDocs = null;
        ResultSet rsListaDocs = null;

        System.out.println("Ejecutando Procedimiento Descargar Documento");
        csObtenerDocs = conn.prepareCall("{ call LOTUS.ZMKT7352_DES_PKG.OBTENER_BLOB(?,?)}");
        csObtenerDocs.registerOutParameter(1, OracleTypes.CURSOR);
        csObtenerDocs.setString(2, strId);
        csObtenerDocs.execute();
        rsListaDocs = (ResultSet) csObtenerDocs.getObject(1);
        while (rsListaDocs.next()) {
            Blob blob = rsListaDocs.getBlob("ATTACH");
            String fileName = rsListaDocs.getString("NOMBRE");
            InputStream inputStream = blob.getBinaryStream();
            int fileLength = (int) blob.length();
            System.out.println("fileLength = " + blob.length());
            ServletContext context = getServletContext();
            // sets MIME type for the file download
            String mimeType = context.getMimeType(fileName);
            if (mimeType == null) {        
                mimeType = "application/octet-stream";
            }              
             
            // set content properties and header attributes for the response
            response.setContentType(mimeType);
            response.setContentLength(fileLength);
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", fileName);
            response.setHeader(headerKey, headerValue);

            // writes the file to the client
            OutputStream outStream = response.getOutputStream();
             
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outStream.close();
        }
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
