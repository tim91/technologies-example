/**
	Simple component example
*/

var React = require('react');
var ReactDOM = require('react-dom');

var InputWithParametrizedPlaceholder = React.createClass({
	render: function(){
		return(
			<input id={this.props.inputId} placeholder={this.props.defaultValue}></input>
		);
	}
});

ReactDOM.render(
  <InputWithParametrizedPlaceholder inputId="100" defaultValue="write something" />,
  document.getElementById('content')
);