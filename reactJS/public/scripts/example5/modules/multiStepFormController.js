var React = require('react');
var ReactDOM = require('react-dom');

var MultiStepFormController = React.createClass({
	getInitialState: function(){
		return{
			currentState: 0,
			savedFormsStates: {}
		}
	},
	saveFormState: function(formStateData){
		this.state.savedFormsStates[this.state.currentState] = formStateData;
	},
	nextState: function(formStateData){
		var currState = this.state.currentState;
		var mapSize = Object.keys(this.props.forms).length;
		
		if(currState < mapSize-1){
			
			this.saveFormState(this.refs.currDisplayedForm.state);
		
			this.setState({
				currentState: currState + 1
			})
		}
		
	},
	prevState: function(){
		var currState = this.state.currentState;
		
		if(currState > 0){

			this.saveFormState(this.refs.currDisplayedForm.state);
			
			this.setState({
				currentState: currState - 1
			})
		}
	},
	render: function(){
		var initFormData = null;
		if(this.state.savedFormsStates[this.state.currentState] != null){
			initFormData = this.state.savedFormsStates[this.state.currentState];
		}
		
		var FormToDisplay = this.props.forms[this.state.currentState];
		
		return (
			<div>
				<FormToDisplay ref="currDisplayedForm" initStateData={initFormData}/>
				<br />
				<button type="button" className="btn btn-primary" onClick={this.prevState}>Back</button>
				<button type="button" className="btn btn-success" onClick={this.nextState}>Next</button>
				
			</div>
		);
	}
});


module.exports = MultiStepFormController;