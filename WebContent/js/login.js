// JavaScript Document

$(document).ready(function(e) {
    
	$("#loginForm").submit(function(e)
	{		
		var email = $("#uname").val();
		var password = $("#pass").val();
		
		$.ajax({
			type: 'POST',
			url: 'services/login/checkLogin/',
			data: '{"username":"'+email+'", "password":"'+password+'"}', // or JSON.stringify ({name: 'jonas'}),
			success: function(data) { 
				loginMessage(data);
				//alert('data: ' + data); 
			},
			contentType: "application/json",
			dataType: 'json'
		});		
		
		e.preventDefault();
	});
});

function loginMessage(data) {
	if(data == "1") {
		window.location.href = "shopping.jsp";
	}
	else if(data == "2") {
		/*$("#loginMessage").text("Pogrešno korisničko ime/lozinka!");
		$("#loginMessage").css('visibility','visible');*/
		$().toastmessage('showErrorToast','Lozinka nije validna!');
		$("#pass").val("");
	} else if(data == "3") {
		/*$("#loginMessage").text("Korisnički nalog ne postoji!");
		$("#loginMessage").css('visibility','visible');*/
		$("#uname").val("");
		$("#pass").val("");
		$().toastmessage('showErrorToast', 'Korisnički nalog ne postoji!');
	}
}

 function logOut() {
		$.ajax({
			type: 'GET',
			url: 'services/login/logOut', 
			async: false,
			success: function(data) { 
				//alert("uspesno odlogovan");
				window.location.href = './';
			}
		});		
 }
