var margin = {top: 20, right: 20, bottom: 30, left: 40},
    width = 250 - margin.left - margin.right,
    height = 100 - margin.top - margin.bottom;

var parseDate = d3.time.format("%Y").parse;

var x = d3.time.scale()
    .range([0, width]);

var y = d3.scale.linear()
    .range([height, 0]);

var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom")
    .ticks(d3.time.year);

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left");

var svg = d3.select(".dashboard > .row > div:nth-child(1)")
            .append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

d3.json("/api/books-by-year", function(error, data) {
  x.domain(data.map(function(d) { return parseDate(Object.keys(d)[0]); }));
  y.domain([0, d3.max(data, function(d) { return parseInt(d[Object.keys(d)[0]]); })]);

  svg.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + height + ")")
      .call(xAxis);

  svg.append("g")
      .attr("class", "y axis")
      .call(yAxis)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 6)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Frequency");

  svg.selectAll(".bar")
      .data(data)
    .enter().append("rect")
      .attr("class", "bar")
      .attr("x", function(d) { return x(parseDate(Object.keys(d)[0])); })
      .attr("width", function() {return 10;})
      .attr("y", function(d) { return y(parseInt(d[Object.keys(d)[0]])); })
      .attr("height", function(d) { return height - y(parseInt(d[Object.keys(d)[0]])); });

});

function type(d) {
  d.frequency = +d.frequency;
  return d;
}
