$(document).ready(function () {
  startGame();
  guess();
  newGame()
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
          //alert(messageObject.answer);

          currentAnswer.append(createAnswer(messageObject.answer));
          id = messageObject.id;
        },
        error: function() {
        
        }
    })
}

function newGame() {
  $('#newGame').click(function(){
    $('#hangmanImage').attr("src","img/0.jpg");
    $("#guesses").empty();
    $("#currentAnswer").empty();
    startGame();
  })
}

function createAnswer(answer) {
  var currentAnswer = "";

  for(i=0; i<answer.length; i++) {
    currentAnswer = currentAnswer.concat("_ ");
  }
  return currentAnswer;
}

function guess() {
  var submitButton = $('#submit');
  var currentAnswer = $('#currentAnswer');

  submitButton.click(function(event) {
    $.ajax({
           type: 'POST',
           url: 'http://localhost:8080/hangman/guess',
           data: JSON.stringify({
                guess: $('#userInput').val(),
                gameId: id
           }),
           headers: {
               'Accept': 'application/json',
               'Content-Type': 'application/json'
           },
           'dataType': 'json',
           success: function(round) {
             const guess = $('#userInput').val();
             $('#guesses').append(" " + guess);
             $('#userInput').val('');
             if(round.currentAnswer != "") {
              currentAnswer.empty();
              currentAnswer.append(round.currentAnswer);
             }
             loadGame();
           },
           error: function () {
           }
        })
    });
}

function loadGame() {
  var image = $('#hangmanImage');

  $.ajax({
           type: 'GET',
           url: 'http://localhost:8080/hangman/game/'.concat(id),
           headers: {
               'Accept': 'application/json',
               'Content-Type': 'application/json'
           },
           'dataType': 'json',
           success: function(game) {
            image.attr("src","img/".concat(game.wrongGuess).concat(".jpg"));
           },
           error: function () {
           }
        });
}