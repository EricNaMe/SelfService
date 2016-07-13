/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
    $.ajax({
        dataType: "text",
        method: "POST",
        url: 'GetDocuments?mod=LOTUS&subMod=Manual',
        beforeSend: function() {
            $('#headerModal').html('Solicitando informacion...');
            $('#closeModal').css('display', 'none');
            $('#myModal').modal({keyboard: false, backdrop: 'static'}, 'show');
        }
    }).done(function(e) {
        json = $.parseJSON(e);
        $('#tableBody').bootstrapTable({
            dataType: 'json',
            emptytext: '-',
            data: json.data,
            search: true,
            idField: 'idDoc',
            uniqueId: 'idDoc',
            formatLoadingMessage: function() {
                return 'Cargando...';
            },
            formatRecordsPerPage: function(pageNumber) {
                return pageNumber + ' registros por pagina';
            },
            formatShowingRows: function(pageFrom, pageTo, totalRows) {
                return 'Mostrando ' + /*pageFrom + ' a ' + */pageTo + ' de ' + totalRows + ' registros';
            },
            formatSearch: function() {
                return 'Buscar';
            },
            formatNoMatches: function() {
                return 'No se encontraron registros';
            },
            formatPaginationSwitch: function() {
                return 'Ocultar/Mostrar paginacioon';
            },
            formatRefresh: function() {
                return 'Actualizar';
            },
            formatToggle: function() {
                return 'Toggle';
            },
            formatColumns: function() {
                return 'Columnas';
            },
            formatAllRows: function() {
                return 'Todos';
            },
            columns: [{
                    field: 'idDoc',
                    title: 'ID Doc',
                    visible: false
                }, {
                    field: 'descripcion',
                    title: 'Descripcion',
                    type: 'text',
                    visible: true
                }, {
                    field: 'nombre',
                    title: 'Nombre',
                    type: 'text'
                }, {
                    field: 'fecha',
                    title: 'Fecha de Creacion',
                    type: 'text'
                }, {
                    field: 'eliminar',
                    title: 'Eliminar',
                    type: 'text',
                    align: 'center',
                    valign: 'middle'
                }]
        });
        $('#myModal').modal('hide');
    }).fail(function(e) {
        $('#closeModal').css('display', '');
        $('#headerModal').html('Ocurrio un error al cargar los datos');
    });
    
    $('#saveFile').click(function(){
		    var form = document.getElementById('formUploadFile');
		    var formData = new FormData(form);
		    $.ajax({
		        url: 'AttachFile',
		        data: formData,
		        contentType: false,
		        processData: false,
		        type: 'POST',
		        beforeSend: function() {
	                $('#headerModal').html('Guardando Documento...');
	                $('#closeModal').css('display', 'none');
	                $('#myModal').modal({keyboard: false, backdrop: 'static'}, 'show');
	            }
		    }).done(function(e) {
		    	$('#tableBody').bootstrapTable('refresh');
	            $('#myModal').modal('hide');
	        }).fail(function(e) {
	            $('#myModal').modal('hide');
	        });
	    	$('#tableBody').bootstrapTable('refresh');
		    $('#modalUploadFile').modal('hide');
		    $('#descripcion').val('');
	});
    
    $('#attach').click(function(){
    	$("#modalUploadFile").modal();
    });
});

function eliminarRegistro(id) {
	if(confirm("¿Esta seguro que deseas eliminar el registro?")) {
	    $('#tableBody').bootstrapTable('removeByUniqueId', id);
	    $.ajax({
	        dataType: "text",
	        url: "EliminarDoc?id=" + id,
	        method: "GET",
	        beforeSend: function() {
	            $('#headerModal').html('Eliminando registro...');
	            $('#closeModal').css('display', 'none');
	            $('#closeModalEliminar').css('display', 'none');
	            $('#myModal').modal({keyboard: false, backdrop: 'static'}, 'show');
	        }
	    }).done(function(e) {
	        $('#headerModal').html(e);
	        $('#closeModalEliminar').css('display', '');
	    }).fail(function(e) {
	        $('#headerModal').html('Ocurrio un error al eliminar el registro');
	        $('#closeModalEliminar').css('display', '');
	
	    });
	}
}
