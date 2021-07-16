function format ( d ) {
    return  'answer : '+ d.answer;
}

$(document).ready(function() {
    var dt = $('#table').DataTable( {
    	"ajax": {
            "url": "/rest/faq",
            "dataSrc": ""
        },
        "columns": [
        	{
                "class":          "details-control",
                "orderable":      false,
                "data":           null,
                "defaultContent": ""
            },
            { "data": "question" },
            {
            	
            	"defaultContent": "<button>update</button>"
            }
            
        ]
    } );
 
    //injection data into html form input value 
    $('#table tbody').on( 'click', 'button', function () {
        var data = dt.row( $(this).parents('tr') ).data();
        $("#editFAQ").modal();
        $("input[name='question']").val(data.question);
        $("textarea[name='answer']").val(data.answer);
        
        $("#edit").on("click", function(e){
            $('#editForm').attr('action', "/admin/editFAQ/"+data.id).submit();
        });
        $("#delete").on("click", function(e){
        	if(confirm("Are u sure u wanna do this?")){
        		 $('#deleteLink').attr('href', "/admin/deleteFAQ/"+data.id).submit();
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