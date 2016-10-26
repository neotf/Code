$(function() {
	$("#logsub").click(function() {
		$.ajax({
			url: 'auth',
			type: "POST",
			data: $('#login').serialize(),
			error: function(request) {
				$("#result").text("Connection error");
			},
			success: function(data) {
				$("#result").text(JSON.stringify(data));
			}
		});
	})
	$("#btn_reg").click(function() {
		$.ajax({
			url: 'reg',
			type: "POST",
			data: $('#register').serialize(),
			error: function(request) {
				$("#result").text("Connection error");
			},
			success: function(data) {
				$("#result").text(JSON.stringify(data));
			}
		});
	})
	
	$("#btn_add").click(function () {
      $("#myModalLabel").text("注册");
      $('#myModal').modal();
    });
});

function checkuser(){
    var user = {
    		username:$("#username").val(),
        };
	$.ajax({
		url: 'reg',
		type: "GET",
		dataType:'text',
		data: user,
		error: function(request) {
			$("#result").text("Connection error");
		},
		success: function(data) {
			$("#result").text(JSON.stringify(data));
		}
	});
}