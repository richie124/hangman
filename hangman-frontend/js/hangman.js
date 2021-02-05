$(document).ready(function () {
  startGame();
});

var id;

function startGame() {
  var currentAnswer = $('#currentAnswer');

  $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/hangman/begin',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        'dataType': 'json',
        success: function(messageObject, status, jqXHR) {
          alert(messageObject.answer);

          currentAnswer.append(createAnswer(messageObject.answer));
          id = messageObject.id;
        },
        error: function() {
        
        }
    })
}

function createAnswer(answer) {
  var currentAnswer = "";

  for(i=0; i<answer.length; i++) {
    currentAnswer = currentAnswer.concat("_ ");
  }
  return currentAnswer;
}
