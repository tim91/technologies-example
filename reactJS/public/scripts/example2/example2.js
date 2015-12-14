/**
	Simple component example
*/

var React = require('react');
var ReactDOM = require('react-dom');

var ParametrizedInput = React.createClass({
	render: function(){
		return(
			<input id={this.props.inputId} placeholder={this.props.defaultValue}></input>
		);
	}
});

ReactDOM.render(
  <ParametrizedInput inputId="100" defaultValue="write something" />,
  document.getElementById('content')
);