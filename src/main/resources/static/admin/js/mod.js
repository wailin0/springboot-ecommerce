function format ( d ) {
    return 'Addtional Details<br>Created Date : '+'<br>'+'<br>'+
    'Address : '+d.address;
}

//for datatable 
$(document).ready(function() {
    var dt = $('#table').DataTable( {
    	"ajax": {
            "url": "rest/user/MOD",
            "dataSrc": ""
        },
        "columns": [
        	{
                "class":          "details-control",
                "orderable":      false,
                "data":           "username",
                "defaultContent": ""
            },
            { "data": "email" },
            { "data": "phone" },
            { "data": "enabled" },
            {
            	
            	"defaultContent": "<button>update</button>"
            }
            
        ]
    } );
 
    //injection data into html form input value 
    $('#table tbody').on( 'click', 'button', function () {
        var data = dt.row( $(this).parents('tr') ).data();
        $("#editUser").modal();
        $("input[name='username']").val(data.username);
        $("input[name='password']").val(data.password);
        $("input[name='email']").val(data.email);
        $("input[name='phone']").val(data.phone);
        $("input[name='address']").val(data.address);
        
        //for setting default radio button based on user role
        if(data.enabled){
        	$("#enable").attr('checked','checked');
        }
        else{
        	$("#disable").attr('checked','checked');
        }
        
        //edit
        $("#edit").on("click", function(e){
            $('#editForm').attr('action', "/admin/editUser/"+data.id).submit();
        });
        
        //delete
        $("#delete").on("click", function(e){
        	if(confirm("Are u sure u wanna do this?")){
        		 $('#deleteLink').attr('href', "/admin/deleteUser/"+data.id).submit();
        	}
        	else{
                return false;
            } 
        });
    } );
    
    
    
 var detailRows = [];
    
    $('#table tbody').on( 'click', 'tr td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = dt.row( tr );
        var idx = $.inArray( tr.attr('id'), detailRows );
 
        if ( row.child.isShown() ) {
            tr.removeClass( 'details' );
            row.child.hide();
 
            // Remove from the 'open' array
            detailRows.splice( idx, 1 );
        }
        else {
            tr.addClass( 'details' );
            row.child( format( row.data() ) ).show();
 
            // Add to the 'open' array
            if ( idx === -1 ) {
                detailRows.push( tr.attr('id') );
            }
        }
    } );
    
    dt.on( 'draw', function () {
        $.each( detailRows, function ( i, id ) {
            $('#'+id+' td.details-control').trigger( 'click' );
        } );
    } );
    
    
} );