var x = localStorage.getItem("x"), y = localStorage.getItem("y"), lar = localStorage.getItem("lar"), alt = localStorage.getItem("alt");
var ros = localStorage.getItem("ros");

window.onload = function() {
	var c=document.getElementById('myCanvas');
	var ctx=c.getContext('2d');
	var img=document.getElementById('img');
	ctx.drawImage(img,10,10);
	var canvas = document.getElementById('myCanvas');
	var context = canvas.getContext('2d');
	var gradient = context.createLinearGradient(0, 0, 200, 0);
	
	x = parseInt(x);
	y = parseInt(y);
	lar = parseInt(lar);
	alt = parseInt(alt);
	
	context.strokeStyle = 'red';
	context.lineWidth = 4;
	context.strokeRect(x + 10, y + 10, lar, alt);
	
	gradient.addColorStop(0, "white");
	context.fillStyle = gradient;
	
	if (lar != 0) {
		if (y > 100) {
			context.fillRect(x + 8, y - 25, 98, 27);
			context.font = "24px Arial";
			context.fillStyle = "red";
			context.fillText("Rosto " + ros, x + 10, y - 4);
		} else {
			context.fillRect(x + 8, y + alt + 15, 98, 27);
			context.font = "24px Arial";
			context.fillStyle = "red";
			context.fillText("Rosto " + ros, x + 10, y + alt + 35);
		}
	}
}

function drawFace(xx, yy, xlar, yalt, fotolar, rosto) {
	x = parseInt(xx / (fotolar / 500));
	y = parseInt(yy / (fotolar / 500));
	lar = parseInt(xlar / (fotolar / 500));
	alt = parseInt(yalt / (fotolar / 500));
	ros = rosto;
	
	localStorage.setItem("x", x);
	localStorage.setItem("y", y);
	localStorage.setItem("lar", lar);
	localStorage.setItem("alt", alt);
	localStorage.setItem("ros", ros);
	reload();
}

function nextFace() {
	localStorage.setItem("x", 0);
	localStorage.setItem("y", 0);
	localStorage.setItem("lar", 0);
	localStorage.setItem("alt", 0);
	localStorage.setItem("ros", ' ');
}