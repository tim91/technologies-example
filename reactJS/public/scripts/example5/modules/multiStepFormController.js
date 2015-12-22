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
	nextState: function(){
		var currStateNumber = this.state.currentState;
		
		if(currStateNumber < this.getNumberOfRegisteredForms()-1){
			
			this.saveFormState(this.refs.currDisplayedForm.state);
		
			this.setState({
				currentState: currState + 1
			})
		}
		
	},
	getNumberOfRegisteredForms: function(){
		return Object.keys(this.props.forms).length;
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
	getSavedStateOfForm: function(formIndex){
		this.state.savedFormsStates[this.state.currentState];
	},
	render: function(){
		var initFormData = null;
		
		if(this.getSavedStateOfForm(this.state.currentState) != null){
			initFormData = this.getSavedStateOfForm(this.state.currentState);
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