//code in server
var express = require('express')
var app = express()
app.use(express.static(__dirname + '/public'));
app.get('/api/book',function(req,res){
	db.book.find({}, function(err, books){
		res.send(books);
	});
	
})

app.post('/api/book', function(req, res){
	db.book.insert(req.body, function(err, books){
			res.send(books);
	});
});

var server = app.listen(3000, function () {

  	console.log("server is running")

})