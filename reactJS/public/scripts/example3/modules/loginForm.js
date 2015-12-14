var React = require('react');

var LoginForm = React.createClass({
	render: function(){
		return (
			<form action={this.props.submitUrl} method="post">
				<FormInput inputName="firstname" label="First Name" />
				<FormInput inputName="lastname" label="Last Name" />
				<button type="submit">submit</button>
			</form>
		);
	}
});


var FormInput = React.createClass({
	render: function(){
		return (
			<div>
				{this.props.label}:
				<input name={this.props.inputName}></input>
			</div>
		);
	}
});

module.exports = LoginForm;