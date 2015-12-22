var React = require('react');
var ReactDOM = require('react-dom');

ReactDOM.render(React.createElement(
  'h1',
  {
	  className: "helloClass",
	  id: "1"
  },
  'Hello, world!'
), document.getElementById('content'));

ReactDOM.render(
  <h1 className="helloClass" id="2" >Hello, world by JSX!</h1>,
  document.getElementById('contentjsx')
);