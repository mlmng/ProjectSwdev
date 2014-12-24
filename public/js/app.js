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
			//console.log(data);
			$scope.subjects = data;
		})
		$scope.datas = [[{day:'Monday',strTime:'08:00',endTime:'08:50'},{day:'Monday',strTime:'09:00',endTime:'09:50'},
						{day:'Monday',strTime:'10:00',endTime:'10:50'},{day:'Monday',strTime:'11:00',endTime:'11:50'},
						{day:'Monday',strTime:'12:00',endTime:'12:50'},{day:'Monday',strTime:'13:00',endTime:'13:50'},
						{day:'Monday',strTime:'14:00',endTime:'14:50'},{day:'Monday',strTime:'15:00',endTime:'15:50'},
						{day:'Monday',strTime:'16:00',endTime:'16:50'}],
						[{day:'Thusday',strTime:'08:00',endTime:'08:50'},{day:'Thusday',strTime:'09:00',endTime:'09:50'},{},{},{},{},{},{},{}],
						[{day:'Wednesday',strTime:'08:00',endTime:'08:50'},{day:'Wednesday',strTime:'09:00',endTime:'09:50'},{},{},{},{},{},{},{}],
						[{day:'Tueday',strTime:'08:00',endTime:'08:50'},{day:'Tueday',strTime:'09:00',endTime:'09:50'},{},{},{},{},{},{},{}],
						[{day:'Friday',strTime:'08:00',endTime:'08:50'},{day:'Friday',strTime:'09:00',endTime:'09:50'},{},{},{},{},{},{},{}]];
		
	}
	$scope.getSubject = function() {
		console.log($scope.roomDay1);
		// $http.post('/api/student', {StdID : $scope.roomDay1}).success(function(data){
		//  	console.log(data);
		//  	$scope.studentInstances = data;
		// });
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