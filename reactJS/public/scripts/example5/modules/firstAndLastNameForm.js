var React = require('react');
var ReactDOM = require('react-dom');

var BaseForm = require("./baseForm.js");

var FirstAndLastNameForm = React.createClass({
	mixins: [BaseForm], //inheritance of method initMethod
	getInitialState: function(){
		return {
			firstName: "",
			lastName: ""
		}
	},
	componentDidMount: function(){//executed on initlization
		this.initMethod();
	},
	onFirstNameChange: function(e){
		this.setState({
			firstName: e.target.value
		})
	},
	onLastNameChange: function(e){
		this.setState({
			lastName: e.target.value
		})
	},
	render: function(){
		return (
			<form>
				<div>
					<input className="form-control"
						type="text"
						placeholder="First Name"
						value={this.state.firstName}
						onChange={this.onFirstNameChange}
					/>
				</div>
				<div>
					<input className="form-control"
						type="text"
						placeholder="Last Name"
						value={this.state.lastName}
						onChange={this.onLastNameChange}
					/>
				</div>
			</form>
		);
	}
});


module.exports = FirstAndLastNameForm;