<style>

.node {
  border: solid 1px #F2625C;
  /*font: 12px; */
  line-height: 31px;
  overflow: hidden;
  position: absolute;
  /*text-indent: 4px; */
  text-align:center;
  background-color: #F7464A;
}

</style>
<div class="bs-example" style="padding-left: 10px;">
    <div class="btn-group">
        <button type="button" class="btn btn-primary btn-xs-custom" id="1">last 2 Days</button>
        <button type="button" class="btn btn-primary btn-xs-custom" id="2">last 1 Day</button>
        <button type="button" class="btn btn-primary btn-xs-custom" id="3">last 12 Hrs</button>
        <button type="button" class="btn btn-primary btn-xs-custom" id="4">last 8 Hrs</button>
        <button type="button" class="btn btn-primary btn-xs-custom" id="5">last 4 Hrs</button>
    </div>
</div>
<script>

var margin = {top: 5, right: 5, bottom: 5, left: 10},
    width = 1360 - margin.left - margin.right,
    height = 50 - margin.top - margin.bottom;

var color = d3.scale.category20c();

var treemap = d3.layout.treemap()
    .size([width, height])
    .sticky(true)
    .value(function(d) { return d.fourHrs; });

var div = d3.select("#treeDiv").append("div")
    .style("position", "relative")
    .style("width", (width + margin.left + margin.right) + "px")
    .style("height", (height + margin.top + margin.bottom) + "px")
    .style("left", margin.left + "px")
    .style("top", margin.top + "px");

d3.json("flare.json", function(error, root) {
	var node = div.datum(root).selectAll(".node")
      .data(treemap.nodes)
	  .enter().append("div")
      .attr("class", "node")
      .call(position)
      .style("background-color", function(d) { return d.bg; })
      .text(function(d) { return d.children ? null : d.name + "[" + d.fourHrs_d + "%]"; });

  d3.selectAll("button").on("click", function change() {
    /*
	  var value = this.id === "1"
        ? function() { return 1; }
        : function(d) { return d.twoDays; };
	*/
        var value = function(d) { return d.fourHrs; };
        if(this.id == 1) 
        	value = function(d) { return d.twoDays; };
        else if(this.id == 2)
        	value = function(d) { return d.oneDay; };
        else if(this.id == 3)
        	value = function(d) { return d.twelveHrs; };
        else if(this.id == 4)
        	value = function(d) { return d.eightHrs; };
        else if(this.id == 5)
        	value = function(d) { return d.fourHrs; };
        
        var nData = function(d) { alert(d.fourHrs_d); return d.fourHrs_d; };
        if(this.id == 1) 
        	nData = function(d) { return d.children ? null : d.name + "[" + d.twoDays_d + "%]"; };
        else if(this.id == 2)
        	nData = function(d) { return d.children ? null : d.name + "[" + d.oneDay_d + "%]"; };
        else if(this.id == 3)
        	nData = function(d) { return d.children ? null : d.name + "[" + d.twelveHrs_d + "%]"; };
        else if(this.id == 4)
        	nData = function(d) { return d.children ? null : d.name + "[" + d.eightHrs_d + "%]"; };
        else if(this.id == 5)
        	nData = function(d) { return d.children ? null : d.name + "[" + d.fourHrs_d + "%]"; };
    	
    node
    	.text(nData)
        .data(treemap.value(value).nodes)
      	.transition()
        .duration(2500)
        .call(position);
  });
});

function position() {
  this.style("left", function(d) { return d.x + "px"; })
      .style("top", function(d) { return d.y + "px"; })
      .style("width", function(d) { return Math.max(0, d.dx - 1) + "px"; })
      .style("height", function(d) { return Math.max(0, d.dy - 1) + "px"; });
  	  
  	//.style("background-color", function(d) { return d.bg; } );
}

</script>

 <!-- <label><input type="radio" name="mode" value="size" checked> Size</label>
 <label><input type="radio" name="mode" value="count"> Count</label>
  -->
