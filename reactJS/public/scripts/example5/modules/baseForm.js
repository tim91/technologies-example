var BaseFormMethods = {
	initMethod: function(){
		if(this.props.initStateData != null){
			this.setState(this.props.initStateData);
		}
	}
}

module.exports = BaseFormMethods;