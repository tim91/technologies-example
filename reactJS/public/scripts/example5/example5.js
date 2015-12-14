var React = require('react');
var ReactDOM = require('react-dom');

var MultiStepFormController = require('./modules/multiStepFormController.js');

var FirstAndLastNameForm = require('./modules/firstAndLastNameForm.js');
var QuestionForm = require('./modules/questionsForm.js');

var definedForms = {
		0 : FirstAndLastNameForm,
		1 : QuestionForm
};

ReactDOM.render(
  <MultiStepFormController forms={definedForms} />,
  document.getElementById('content')
);