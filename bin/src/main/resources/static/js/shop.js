//I used pagination plugin
      var app = angular.module('ShopApp', ['angularUtils.directives.dirPagination']);
      
      app.controller('ProductsController', function($scope, $http) {
    	  $scope.currentPage = 1;     //current page for paging plugin
    	  $scope.selectCategory="";
    	  
    	  //for showing all products
    	  $http.get('/rest/products/').
          then(function(response) {
              $scope.products = response.data;
          });
    	  
    	  //for showing selected products by category
    	  $scope.category = function(){
          $http.get('/rest/products/category/'+$scope.selectCategory).
              then(function(response) {
                  $scope.products = response.data;
              });
    	  };
    	  
    	  
    	  $scope.type = function(type){
          $http.get('/rest/products/type/'+type).
              then(function(response) {
                   $scope.products = response.data;
              });
          };
      });
      

      //for pagination plugin
      function PagingController($scope) {
        $scope.pageChangeHandler = function(num) {
        	
        };
      }
      
      app.controller('PagingController', PagingController);
      