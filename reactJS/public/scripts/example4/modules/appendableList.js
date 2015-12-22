var React = require('react');
var ReactDOM = require('react-dom');


var AppendableList = React.createClass({
	getInitialState: function(){
		return {
			rows: []
		};
	},
	onElementSubmit: function(element){
		//append row list
		var currRows = this.state.rows;
		currRows.push(element);
		this.setState({
			rows: currRows
		})
	},
	render: function(){
		var tableRows = this.state.rows.map(
						function(row, i){
							return <ListRow key={i} rowValue={row.rowVal} />
						});
		return(
			<div>
				<table>
					<thead>
						<tr>
							<th>Value</th>
						</tr>
					</thead>
					<tbody>
						{tableRows}
					</tbody>
				</table>
				<ListController onSubmitData={this.onElementSubmit}/>
			</div>
		)
	}
});

var ListRow = React.createClass({
	render: function(){
	return <tr key={this.props.key}><td key={this.props.key}>{this.props.rowValue}</td></tr>
	}
});

var ListController = React.createClass({
	getInitialState: function(){
		return {
			inputValue: ""
		};
	},
	updateInputState: function(e){
		this.setState({
				inputValue: e.target.value
		})
	},
	onFormSubmit: function(e){
		e.preventDefault();
		
		//execute function in AppendableList
		this.props.onSubmitData({
			rowVal: this.state.inputValue
		})
		
		//clear input
		this.setState({
			inputValue: ""
		})
	},
	render: function(){
		return (
			<form onSubmit={this.onFormSubmit}>
				<input
					type="text"
					value={this.state.inputValue}
					onChange={this.updateInputState}
				/>
				<input type="submit" value="Add" />
			</form>
		);
	}
});

module.exports = AppendableList;

