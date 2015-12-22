var React = require('react');
var ReactDOM = require('react-dom');

var BaseForm = require("./baseForm.js");

var QuestionsForm = React.createClass({
	mixins: [BaseForm],  //inheritance from BaseForm
	getInitialState: function(){
		return {
			age: null
		}
	},
	componentDidMount: function(){//executed on initlization
		this.initMethod();
	},
	onUpdateAge: function(e){
		this.setState({
			age : e.target.value
		})
	},
	render: function(){
		return (
			<div>
				<form>
					<div>
						<label>How old are you?:</label>
						<input className="form-control"
							type="number"
							value={this.state.age}
							onChange={this.onUpdateAge}
						/>
					</div>
				</form>
			</div>
		);
	}
});


module.exports = QuestionsForm;