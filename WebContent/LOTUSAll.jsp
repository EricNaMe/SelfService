<%-- 
    Document   : index
    Created on : 28-jun-2016, 18:42:59
    Author     : oc333
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 

<%@include file="EnlacesHeader.jsp"%>
	<script type="text/javascript" src="js/lotusAll.js"></script>
</head>
    <body>
        <div id="myModal" class="modal fade" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="headerModal">Enviando informacion</h4>
                    </div>
                    <div class="modal-body">
                        <div class="progress">
                            <div class="progress-bar progress-bar-striped active" role="progressbar"
                                 aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width:100%">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal" id="closeModal" style="display: none">Close</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal" id="closeModalEliminar" style="display: none">Close</button>
                    </div>
                </div>
            </div>
        </div>
       
 	<%@include file="PlantillaHeader.jsp"%>
 	
 	<style>
 	a {
    color:blue;
    text-decoration: none;
}
a:hover{
color:blue;
}
 	</style>
        
        <div class="modal fade" id="modalUploadFile" role="dialog">
			<div class="modal-dialog">
		      <!-- Modal content-->
		      	<div class="modal-content">
			        <div class="modal-header">
			          <button type="button" class="close" data-dismiss="modal">&times;</button>
			        </div>
			        <div class="modal-body">
			         <form id="formUploadFile" enctype="multipart/form-data"  method="post">
			         	Modulo: <select name="selMod" id="selMod">
			         	<option>LOTUS</option>
			         	<option>SHAREPOINT</option>
			         	<option>ERP</option>
			         	<option>JAVA</option>
			         	</select>
			         	<br><br>
			         	Sub-Modulo: <select name="selSubMod" id="selSubMod">
			         	<option>How to</option>
			         	<option>Manual</option>
			         	<option>Troubleshooting</option>
			         	<option>FAQ</option>
			         	</select>
			         	<br><br>
			         	Descripcion:
		           		<input type="text"  name="descripcion" id="descripcion" />
		           		<br><br>
		           		Seleccione un Archivo:
		           		<input type="file"  name="file" /> 
		           		<br>
			           <input type="button" class="btn btn-default"  value="Adjuntar" id="saveFile"/>
				     </form>
			        </div>
		    	</div>
			</div>
		</div>
		
		<%@include file="LATERALlotus.jsp"%>
		
        <div class="menu-central col-sm-10 col-sm-offset-1" style="margin-bottom:20px; ">
        	<div class="col-md-12" style="margin-bottom:20px;">
     		   <h1 class="titulo-plantilla" style="font-weight:bold; text-align:center;">All LOTUS</h1>
       	    </div>
        	<div class="col-md-12" style="text-align:center;">
        	<input type="button" class="btn btn-default"  value="Adjuntar Archivo" id="attach"/>
        	</div>
        	<div class="col-md-10 col-md-offset-1">
            <table id="tableBody"></table>
            </div>
        </div>
       
    </body>
</html>
