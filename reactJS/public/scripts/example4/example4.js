var React = require('react');
var ReactDOM = require('react-dom');

var AppendableList = require('./modules/appendableList.js');


var rows = [
	{rowVal: "one"},
	{rowVal: "two"},
	{rowVal: "three"}
]

ReactDOM.render(
  <AppendableList rows={rows} />,
  document.getElementById('content')
);