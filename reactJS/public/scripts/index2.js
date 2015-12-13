var LinkedStateMixin = require('react-addons-linked-state-mixin');
//asdfasdfasdfasdf
var ProductCategoryRow = React.createClass({
	render: function() {
		return (<tr><th colSpan="2">{this.props.category}</th></tr>);
	}
});

var ProductRow = React.createClass({
    onProductButtonClickHandler: function(e){
		console.log("Otrzymałem wiadomość: " + e);
	},
	render: function() {
        var name = this.props.product.stocked ?
            this.props.product.name :
            <span style={{color: 'red'}}>
                {this.props.product.name}
            </span>;
		
        return (
            <tr>
                <td>{name}</td>
                <td>{this.props.product.price}</td>
				<td><OrderProductButton onOrderButtonClick={this.onProductButtonClickHandler} productName={this.props.product.name}></OrderProductButton></td>
            </tr>
        );
    }
});

var OrderProductButton = React.createClass({
	onButtonClickAction: function(e){
		this.props.onOrderButtonClick("Wiadomość dla CIebie od: " + e.target.id);
	},
	render: function(){
		return (
			<button onClick={this.onButtonClickAction} id={this.props.productName}>Click me!</button>
		);
	}
});

var ProductTable = React.createClass({
    render: function() {
        var rows = [];
        var lastCategory = null;
        this.props.products.forEach(function(product) {
			if (product.name.indexOf(this.props.valueLink.value) === -1 || (!product.stocked && this.props.inStockOnly)) {
                return;
            }
            if (product.category !== lastCategory) {
                rows.push(<ProductCategoryRow category={product.category} key={product.category} />);
            }
            rows.push(<ProductRow product={product} key={product.name} />);
            lastCategory = product.category;
        }.bind(this));
        return (
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>
        );
    }
});

var SearchBar = React.createClass({
    handleChange: function(){
		this.props.onUserInput(
			this.refs.inStockOnlyInput.checked
		);
	},
	render: function() {
        return (
            <form>
                <input 
					type="text" 
					placeholder="Search..." 
					value={this.props.filterText} 
					ref="filterTextInput"
				/>
                <p>
                    <input 
						type="checkbox" 
						checked={this.props.inStockOnly}
						ref="inStockOnlyInput"
                        onChange={this.handleChange}
					/>
                    {' '}
                    Only show products in stock
                </p>
            </form>
        );
    }
});
//mixins: [LinkedStateMixin],
var FilterableProductTable = React.createClass({
	mixins: [LinkedStateMixin],
	getInitialState: function() {
        return {
            filterText: '',
            inStockOnly: false
        };
    },
	handleUserInput: function(stockIsChecked){
		this.setState(
			{inStockOnly: stockIsChecked}
		);
	},
    render: function() {
        return (
            <div>
                <SearchBar 
					filterText={this.state.filterText}
                    inStockOnly={this.state.inStockOnly}
					onUserInput={this.handleUserInput}
				/>
                <ProductTable 
					products={this.props.products} valueLink={this.linkState('filterText')} inStockOnly={this.state.inStockOnly}
				/>
            </div>
        );
    }
});

var PRODUCTS = [
  {category: 'Sporting Goods', price: '$49.99', stocked: true, name: 'Football'},
  {category: 'Sporting Goods', price: '$9.99', stocked: true, name: 'Baseball'},
  {category: 'Sporting Goods', price: '$29.99', stocked: false, name: 'Basketball'},
  {category: 'Electronics', price: '$99.99', stocked: true, name: 'iPod Touch'},
  {category: 'Electronics', price: '$399.99', stocked: false, name: 'iPhone 5'},
  {category: 'Electronics', price: '$199.99', stocked: true, name: 'Nexus 7'}
];

ReactDOM.render(
    <FilterableProductTable products={PRODUCTS} />,
    document.getElementById('content')
);