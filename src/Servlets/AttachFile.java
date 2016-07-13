/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlets;

import com.cummins.DBConexion.Conexion;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.CallableStatement;
import java.util.Iterator;
import java.util.List;



import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author nc784
 */
public class AttachFile extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
     String file_name="";
     FileItem file;
     String descripcion="";
     String modulo="";
     String subModulo="";
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            conn = Conexion.getDS().getConnection();
            conn.setAutoCommit(false);
            System.out.println("Guardando Archivo");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sfu  = new ServletFileUpload(factory);
            if (! ServletFileUpload.isMultipartContent(request)) {
                System.out.println("sorry. No file uploaded");
                return;
            }
            // parse request
            List<?> items = sfu.parseRequest(request);
	         Iterator<?> iter = items.iterator();
	         int currentPos =0;
	         while (iter.hasNext()) {
	              FileItem item = (FileItem) iter.next();
	              if (item.isFormField()) {
	                 if (item.getFieldName().equals("descripcion")){
	                	 descripcion= item.getString();
	                 } else if (item.getFieldName().equals("selMod")){
	                	 modulo= item.getString();
	                 } else if (item.getFieldName().equals("selSubMod")){
	                	 subModulo= item.getString();
	                 }
	              } else {
	            	  file = (FileItem) items.get(currentPos);
	                  file_name =file.getName();
	              }
	              currentPos++;
             }
	         guardarDocumento();
            conn.commit();
            out.print("La informacion fue guardada exitosamente");
        }catch (SQLException ex) {
            out.print("Ocurrio un error al guardar la informacion");
            System.out.println(ex);
        } catch (Exception ex) {
            out.print("Ocurrio un error al guardar la informacion");
            System.out.println(ex);
        } finally {
            if(conn != null){
                try{
                    conn.close();
                }catch(SQLException e){
                    System.out.println(e);
                }
            }
            out.close();
        }
    }

   
    public void guardarDocumento() throws Exception{
    	System.out.println("Ejecutando Procedimiento Guardar Documento");
    	CallableStatement cs = conn.prepareCall("{ call LOTUS.ZMKT7352_DES_PKG.GUARDAR_BLOB(?,?,?,?,?)}");
    	cs.setString(1, descripcion);
    	cs.setBinaryStream(2, file.getInputStream(), (int) file.getSize());
    	cs.setString(3, file_name);
    	cs.setString(4, modulo);
    	cs.setString(5, subModulo);
    	cs.execute();
    	cs.close();
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
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
