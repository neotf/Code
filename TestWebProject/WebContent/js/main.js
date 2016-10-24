$(function() {
	$("#submit").click(function() {
		$.ajax({
			url: 'auth',
			type: "POST",
			data: $('#form').serialize(),
			error: function(request) {
				$("#result").text("Connection error");
			},
			success: function(data) {
				$("#result").text(JSON.stringify(data));
			}
		});
	})
	
});