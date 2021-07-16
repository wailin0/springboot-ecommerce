function format ( d ) {
    return  '<img src="https://gamingage.s3.amazonaws.com/product/'+d.id+'.png" width="150" height="30" class="img-responsive" /> <br>'+d.description;
}

$(document).ready(function() {
    var dt = $('#table').DataTable( {
    	"ajax": {
            "url": "rest/product/giftCard",
            "dataSrc": ""
        },
        "columns": [
        	{
                "class":          "details-control",
                "orderable":      false,
                "data":           null,
                "defaultContent": ""
            },
            { "data": "name" },
            { "data": "type" },
            { "data": "price" },
            { "data": "stock" },
            {
            	
            	"defaultContent": "<button>update</button>"
            }
            
        ]
    } );
 
    //injection data into html form input value 
    $('#table tbody').on( 'click', 'button', function () {
        var data = dt.row( $(this).parents('tr') ).data();
        $("#editProduct").modal();
        $("input[name='name']").val(data.name);
        $("select[name='type']").val(data.type);
        $("input[name='price']").val(data.price);
        $("input[name='stock']").val(data.stock);
        $("textarea[name='description']").val(data.description);
        
        $("#edit").on("click", function(e){
            $('#editForm').attr('action', "/admin/editProduct/"+data.id).submit();
        });
        $("#delete").on("click", function(e){
        	if(confirm("Are u sure u wanna do this?")){
        		 $('#deleteLink').attr('href', "/admin/deleteProduct/"+data.id).submit();
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