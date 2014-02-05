<!doctype html>
<html>
	<head>
		<title>Doughnut Chart</title>
		<script src="resources/js/Chart.js"></script>
		<meta name = "viewport" content = "initial-scale = 1, user-scalable = no">
		<style>
			canvas{
			}
		</style>
	</head>
	<body>
		<canvas id="canvas" height="250" width="250"></canvas>
	<script>
		var doughnutData = [
				{
					value: 30,
					name: "GLU",
					color:"#F7464A"
				},
				{
					value : 50,
					name: "Experiment",
					color : "#46BFBD"
				},
				{
					value : 100,
					name: "Experiment",
					color : "#FDB45C"
				},
				{
					value : 40,
					name: "Experiment",
					color : "#949FB1"
				},
				{
					value : 120,
					
					color : "#4D5360"
				}
			
			];

	var myDoughnut = new Chart(document.getElementById("canvas").getContext("2d")).Doughnut(doughnutData);
	
	</script>
	</body>
</html>