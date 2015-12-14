/**
	Simple module example
*/

var React = require('react');
var ReactDOM = require('react-dom');

var LoginForm = require("./modules/loginForm.js");

ReactDOM.render(
  <LoginForm submitUrl="user/login" />,
  document.getElementById('content')
);