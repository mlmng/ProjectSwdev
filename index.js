//code in server
var express = require('express')
var mongojs = require('mongojs')
var format = require('date-format')
var bodyParser = require('body-parser')
var app = express()
var db = mongojs('project', ['student', 'rfid','room']);


app.use(express.static(__dirname + '/public'));
app.use(bodyParser.json());

app.post('/api/verify_rfid', function(req, res){
	//console.log(req.body);
	// console.log("======================");
	// res.json(req.body);
	var rfid_code = req.body.rfid;
	var rfid_room = req.body.room;
	db.student.findOne({
   		RFID: rfid_code
	}, function(err, doc) {
		console.log(doc);
    // doc._id.toString() === '523209c4561c640000000001'
	    db.rfid.insert({ 
	    	datetime: format.asString('yyyy-mm-dd',new Date()), 
	    	time: format.asString('hh:mm:ss',new Date()),
	    	student_id: doc._id ,
	    	rfid_id: rfid_code,
	    	room : rfid_room
	    }, function(err, doc1) {
			res.json(
				{ 
					"StdID" : doc.StdID,
					"FName" : doc.FName,
					"LName" : doc.LName
				}
			);
		});
    });	
});

app.get('/api/student',function(req,res){
	db.student.find({}, function(err,docs){
		res.send(docs);
	});
});

app.post('/api/student',function(req, res){
	console.log(req.body);
	var query = "";
	var count = 0;
	var data_stdId= req.body.StdID
	if(data_stdId != null ){
		query = query+"StdID :"+ data_stdId;
		count++;
	}
	var data_Fname = req.body.FName
	if(data_Fname != null && count >0){
		query = query+",FName :"+ data_Fname;
		count++;
	}
	else if(data_Fname != null && count <0){
		query = query+"FName :"+ data_Fname;
	}
	var data_Lname = req.body.LName
	if(data_Lname != null && count >0){
		query = query+",LName :"+ data_Lname;
		count++;
	}else if(data_Lname != null && count <0){
		query = query+"LName :"+ data_Fname;
	}
	// console.log(query);
	var temp_date  = req.body.date
	//var data_date = temp_date.toString();
	//data_date = data_date.substring(0,10);
	//console.log(data_date);
	// var data_start_time = req.body.STime
	// var data_end_time = req.body.ETime
	db.student.find({
		StdID : data_stdId,
		FName : data_Fname,
		LName : data_Lname
	},function(err,docs){
			console.log(docs);
			res.json(docs);
			console.log(docs.FName);
    });
    // db.rfid.find({
    // 		rfid_id : doc.RFIDS
    // 		datetime : "2014-12-24"
    // 	},function(err,doc1){
    // 	console.log(doc1);
    // 	res.json(doc1);
    // });	
});

app.get('/api/room',function(req,res){
	db.room.find({}, function(err,docs){
		res.send(docs);
	});
});
// app.post('/api/room',function(req,res){
// 	var Room_day = req.body.Dayroom
// 	db.room.find({
// 		room:Room_day
// 	},function(err,doc){
// 		res.json(doc);
// 	});
// });

var server = app.listen(3000, function () {
  	console.log("server is running")
});