function login(){
	$.ajax({
		url : 'auth',
		type : "POST",
		data : $('#form').serialize(),
		error : function(request) {
			alert("Connection error");
		},
		success : function(data) {
			$("#result").text(JSON.stringify(data));
		}
	});
}
