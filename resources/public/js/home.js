/*
 *
 */
(function() {
var margin = {top: 40, right: 20, bottom: 20, left: 20},
    width = 300 - margin.left - margin.right,
    height = 140 - margin.top - margin.bottom;
//http://bl.ocks.org/mbostock/3884955


})();
/* Reading bar graph
 *
 */
(function() {
var margin = {top: 40, right: 20, bottom: 20, left: 20},
    width = 300 - margin.left - margin.right,
    height = 140 - margin.top - margin.bottom;

var parseDate = d3.time.format("%Y").parse;

var x = d3.scale.ordinal()
    .rangeRoundBands([0, width], 0.1);

var y = d3.scale.linear()
    .range([height, 0]);

var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom")
    .tickFormat(d3.time.format("%Y"));

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left")
    .ticks(5);

var svg = d3.select(".dashboard > .row > div.reading")
            .append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

d3.json("/api/books-by-year", function(error, data) {
  data.sort(function(x1, x2) {
      return Object.keys(x1)[0] - Object.keys(x2)[0];});
  x.domain(data.map(function(d) { return parseDate(Object.keys(d)[0]); }));
  y.domain([0,
            d3.max(data, function(d) {
                            return parseInt(d[Object.keys(d)[0]], 10); })
            ]);

  svg.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + height + ")")
      .call(xAxis);
        

  svg.append("g")
      .attr("class", "y axis")
      .call(yAxis);
/*
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 6)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Frequency");
      */

  svg.selectAll(".bar")
      .data(data)
    .enter().append("rect")
      .attr("class", "bar")
      .attr("x", function(d) {
          return x(parseDate(Object.keys(d)[0]));
      })
      .attr("width", function() {return 26;})
      .attr("y", function(d) {
          return y(parseInt(d[Object.keys(d)[0]]));
      })
      .attr("height", function(d) {
          return height - y(parseInt(d[Object.keys(d)[0]]));
      });

    svg.append("text")
        .attr("x", (width / 2))             
        .attr("y", 0 - (margin.top / 2))
        .attr("text-anchor", "middle")  
        .style("font-size", "14px") 
        .text("Books Read Each Year");

});

function type(d) {
  d.frequency = +d.frequency;
  return d;
}
})();
