//code in server
var express = require('express')
var mongojs = require('mongojs')
var format = require('date-format')
var bodyParser = require('body-parser')
var app = express()
var db = mongojs('project', ['student', 'rfid']);


app.use(express.static(__dirname + '/public'));
app.use(bodyParser.json());

app.post('/api/verify_rfid', function(req, res){
	// console.log(req.body);
	// console.log("======================");
	// res.json(req.body);
	var rfid_code = req.body.rfid;
	db.student.findOne({
   		RFID: rfid_code
	}, function(err, doc) {
		console.log(doc);
    // doc._id.toString() === '523209c4561c640000000001'
	    db.rfid.insert({ 
	    	datetime: format.asString(new Date()), 
	    	student_id: doc._id 
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
app.post('/api/student', function(req, res){
	db.student.insert(req.body, function(err, docs){
		res.send(docs);
	});
});

var server = app.listen(3000, function () {

  	console.log("server is running")

});