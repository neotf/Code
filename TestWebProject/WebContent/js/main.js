function login(){
	$.ajax({
		url : 'auth',
		type : "GET",
		data : $('#form').serialize(),
		error : function(request) {
			alert("Connection error");
		},
		success : function(data) {
			$("#result").text(data);
		}
	});
}
