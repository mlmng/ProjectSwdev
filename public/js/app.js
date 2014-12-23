//app.js
angular.module("myApp", [])
.controller('mainCtrl', function($scope, $http){
	$scope.name = [];
	$scope.studentInstance ={};

	$http.get('/api/student').success(function(data){
		$scope.name = data;
	})
	
	$scope.save = function(){
		$http.post('/api/student',$scope.studentInstance).success(function(data){
	    	console.log($scope.studentInstance);
	    	$scope.name.push(data);
	    	console.log(data);
	    	$scope.studentInstance = {};
	  	});
	}
})
.controller('studentCtrl', function($scope, $http){
	$scope.getStudent = function() {

		console.log($scope.stdId);

		$http.get('/api/student').success(function(data){
			console.log(data);
			$scope.studentInstance = data;
		})
	}
})