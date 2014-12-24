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

		//console.log($scope.stdId,$scope.fname,$scope.lname);

		// $http.get('/api/student').success(function(data){
		// 	console.log(data);
		// 	$scope.studentInstances = data;
		// })
		// $scope.studentInstances = [];
		// $http.post('/api/student', {StdID : $scope.stdId}).success(function(data){
		// 	console.log(data);
		// 	for (property in data){
  // 				$scope.studentInstances.push(property); 
		// 	}
		// });
		// console.log($scope.studentInstances);

		$http.post('/api/student', {StdID : $scope.stdId,FName : $scope.fname,LName : $scope.lname,date : $scope.date,STime : $scope.starttime,ETime : $scope.endtime}).success(function(data){
			console.log(data);
    		$scope.studentInstances = data;
    		console.log($scope.studentInstances);
		});
	}
})
.controller('teacherCtrl', function($scope, $http){
	$scope.init = function() {
		$http.get('/api/room').success(function(data){
			//console.log(data[0].sid);
			console.log(data);
			$scope.subjects = data;
		})
		//var test = $scope.subjects[0].sid;
		//console.log("ROOM:"+data[0].sid);
		//$scope.datas[0][0]={test};
		$scope.datas = [[{sid:'101-101'},{},{},{},{},{},{},{},{}],
						[{},{},{},{},{},{},{},{},{}],
						[{},{},{},{},{},{},{},{},{}],
						[{},{},{},{},{},{},{},{},{}],
						[{},{},{},{},{},{},{},{},{}]];
		var subjects = $scope.subjects;
			//console.log(subjects);
		
	}
	$scope.getSubject = function() {

		//console.log($scope.stdId);

		
	}
})
.controller('showCtrl', function($scope, $http){
	$scope.getShow = function() {

		console.log($scope.stdId);

		$http.get('/api/student').success(function(data){
			console.log(data);
			$scope.studentInstance = data;
		})
	}
})