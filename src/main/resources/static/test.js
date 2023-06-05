$.ajax({
  method: "POST",
  contentType: 'application/json',
  url: "http://localhost:8000/api/posts/generate-report",
  data: { name: "John", location: "Boston" }
}).done(function( msg ) {
    alert( "Generate report excel: " + msg );
});