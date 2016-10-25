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
	$("#regsub").click(function() {
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
      $("#myModalLabel").text("新增");
      $('#myModal').modal();
    });
});