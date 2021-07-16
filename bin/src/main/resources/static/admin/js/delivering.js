



$(document).ready(function() {
    var dt = $('#table').DataTable( {
    	"ajax": {
            "url": "rest/order/delivering",
            "dataSrc": ""
        },
        "columns": [
        	
            { "data": "id" },
            { "data": "date" },
            { "data": "status" },
            { "data": "total" },
            {
            	"defaultContent": "<button>view details</button>"
            }
            
        ]
    } );
    
    
 
    //injection data into html form input value 
    $('#table tbody').on( 'click', 'button', function () {
        var data = dt.row( $(this).parents('tr') ).data();
        
        var tr=[];
        $.each(data.cartItemList, function(i, val){
        	tr.push('<tr>');
			tr.push('<td>' + val.product.name + '</td>');
			tr.push('<td>' + val.product.price + '</td>');
			tr.push('<td>' + val.qty + '</td>');
			tr.push('<td>' + val.subtotal + '</td>');
			tr.push('</tr>');
			
        }
        
        );$('#orderTable>tbody').html($(tr.join('')));

        $("#editProduct").modal();
        $("#username").html(data.user.username);
        $("#email").html(data.user.email);
        $("#phone").html(data.user.phone);
        $("#address").html(data.user.address);
        $("#total").html(data.total);
       
        $("#accept").on("click", function(e){
        	if(confirm("Confrim?")){
        		 $('#deliveredLink').attr('href', "/admin/order/delivered/"+data.id).submit();
        	}
        	else{
                return false;
            } 
        });
        
        $("#decline").on("click", function(e){
        	if(confirm("Confirm?")){
        		 $('#declineLink').attr('href', "/admin/order/reject/"+data.id).submit();
        	}
        	else{
                return false;
            } 
        });
      
    } );
    

    
} );