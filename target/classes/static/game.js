// Peg constants: =========================================================
const RED_PEG = 1, YELLOW_PEG = 2, GREEN_PEG = 3, PURPLE_PEG = 4, BLUE_PEG = 5, ORANGE_PEG = 6,
  PINK_PEG = 7, AQUA_PEG = 8, EMPTY_PEG = 9;

const RED_PEG_IMG = "images/redAlien.gif", YELLOW_PEG_IMG = "images/yellowAlien.gif", GREEN_PEG_IMG = "images/greenAlien.gif",
  PURPLE_PEG_IMG = "images/purpleAlien.gif", BLUE_PEG_IMG = "images/blueAlien.gif", ORANGE_PEG_IMG = "images/orangeAlien.gif",
  PINK_PEG_IMG = "images/pinkAlien.gif", AQUA_PEG_IMG = "images/aquaAlien.gif", EMPTY_PEG_IMG = "images/emptyPeg.gif";

const pegImageSources = new Map([
  [RED_PEG, RED_PEG_IMG],
  [YELLOW_PEG, YELLOW_PEG_IMG],
  [GREEN_PEG, GREEN_PEG_IMG],
  [PURPLE_PEG, PURPLE_PEG_IMG],
  [BLUE_PEG, BLUE_PEG_IMG],
  [ORANGE_PEG, ORANGE_PEG_IMG],
  [PINK_PEG, PINK_PEG_IMG],
  [AQUA_PEG, AQUA_PEG_IMG],
  [EMPTY_PEG, EMPTY_PEG_IMG],
]);


// Rocket constants: =========================================================
const BLUE_ROCKET = 1, WHITE_ROCKET = 2, EMPTY_ROCKET = 3;
const BLUE_ROCKET_IMG = "images/blueRocket.gif", WHITE_ROCKET_IMG = "images/whiteRocket.gif",
  EMPTY_ROCKET_IMG = "images/emptyRocket.gif";

const rocketImageSources = new Map([
  [BLUE_ROCKET, BLUE_ROCKET_IMG],
  [WHITE_ROCKET, WHITE_ROCKET_IMG],
  [EMPTY_ROCKET, EMPTY_ROCKET_IMG],
]);


// Goal constants: =========================================================
const NUM_PEGS = 4;
const G_IMG = "images/g.gif", O_IMG = "images/o.gif", A_IMG = "images/a.gif", L_IMG = "images/l.gif";


// GameEngine constants: =========================================================
const WIN = 1, LOSE = 2, IN_PROGRESS = 3;



// Main: =========================================================
const DEFAULT_LEVEL = 1;
const RED_X_IMG = "images/x.png";

let gameID = "";

// start a new game:
newGame(DEFAULT_LEVEL);


// add button/dropdown handlers:
$("#btn-submit").click(function () {
  submit();
});

$("#btn-clear").click(function () {
  clear();
});

$("#btn-instructions").click(function () {
  instructions();
});

$("#btn-new-game").click(function () {
  if (confirm("Are you sure you want to start a new game?")) {
    $.get("./games/game/" + gameID, function (gameEngine) {
      newGame(gameEngine.level);
    });
  }
});

$('.dropdown-menu a').click(function () {
  changeLevel(parseInt($(this).text()));
});


// submit function (what happens when the submit button is clicked):
function submit() {

  // get the current game engine to ensure that the current guess has NUM_PEGS pegs:
  $.get("./games/game/" + gameID, function (gameEngineGET) {
    if (gameEngineGET.currentSeqSize == NUM_PEGS) {

      // send submit request to server: 
      $.post("./games/" + gameID + "/peg-seq/" + false, function (gameEngine) {
        allBlank = true;

        // show the rockets:
        for (i = 0; i < NUM_PEGS; i++) {
          rocketFill = gameEngine.currentRocketSeq[i].fill;
          $("#rocket" + (gameEngine.currentRow + 1) + "-" + i).attr("src", rocketImageSources.get(rocketFill));
          if (rocketFill != EMPTY_ROCKET) {
            allBlank = false;
          }
        }

        // if no rockets, blank, show red x:
        if (allBlank) {
          $("#x-" + (gameEngine.currentRow + 1)).attr("src", RED_X_IMG);
          $("#x-" + (gameEngine.currentRow + 1)).show();
        }
        disableSubmitButton();

        // check for win/loss:
        if (gameEngine.playerStatus == WIN) {
          win();
        }
        if (gameEngine.playerStatus == LOSE) {
          lose();
        }
      });
    }
    else {
      alert("Each guess must contain " + NUM_PEGS + " aliens");
    }
  });
}


// clear function (what happens when the clear button is clicked):
function clear() {
  // clear all the pegs:
  $.post("./games/" + gameID + "/peg-seq/" + true, function (gameEngine) {
    for (i = 0; i < NUM_PEGS; i++) {
      $("#peg" + gameEngine.currentRow + "-" + i).attr("src", pegImageSources.get(EMPTY_PEG));
    }
    disableSubmitButton();
  });
}


// instructions function (what happens when the instructions button is clicked):
function instructions() {
  $.get("./games/game/" + gameID, function (gameEngine) {
    alert("Four aliens have arranged themselves in a secret order and are hiding behind the sign marked GOAL."
      + '\n' + "There are " + (gameEngine.level + 3) + " different possible colors of aliens."
      + '\n'
      + "There may be more than one alien of the same color, and there may be no alien of a particular color."
      + '\n'
      + "Try to guess the order in which the four aliens are arranged before you reach the end of the board."
      + '\n'
      + "After each guess, zero to four rockets will appear on the rocket panel on the left."
      + '\n'
      + "Each blue rocket means that you have placed an alien of the right color in the right position."
      + '\n'
      + "Each white rocket means that you have placed an alien of the right color in the wrong position."
      + '\n' + '\n' + "You are currently playing level " + gameEngine.level);
  });
}


function win() {
  showGoal();
  alert("Congratulations -- You win!");
  disableButtons();
}


function lose() {
  showGoal();
  alert("Sorry -- You lose.");
  disableButtons();
}


function showGoal() {
  $.get("./games/game/" + gameID, function (gameEngine) {
    for (i = 0; i < NUM_PEGS; i++) {
      $("#goal-" + i).attr("src", pegImageSources.get(gameEngine.goal.sequence[i].color));
    }
  });
}


function disableButtons() {
  $("#btn-red").prop("disabled", true);
  $("#btn-yellow").prop("disabled", true);
  $("#btn-green").prop("disabled", true);
  $("#btn-purple").prop("disabled", true);
  $("#btn-blue").prop("disabled", true);
  $("#btn-orange").prop("disabled", true);
  $("#btn-pink").prop("disabled", true);
  $("#btn-aqua").prop("disabled", true);
  disableSubmitButton();
  $("#btn-clear").prop("disabled", true);
  $("#btn-change-level").prop("disabled", true);
}


// puts all the buttons in the game-starting state:
function resetButtons() {
  $("#btn-red").prop("disabled", false);
  $("#btn-yellow").prop("disabled", false);
  $("#btn-green").prop("disabled", false);
  $("#btn-purple").prop("disabled", false);
  $("#btn-blue").prop("disabled", false);
  $("#btn-orange").prop("disabled", false);
  $("#btn-pink").prop("disabled", false);
  $("#btn-aqua").prop("disabled", false);
  disableSubmitButton();
  $("#btn-clear").prop("disabled", false);
  $("#btn-change-level").prop("disabled", false);
}


function newGame(level) {
  // call the new-game server endpoint:
  $.post(("./games/new-game/" + level), resetUI);
}


// resets the UI to a new game state:
function resetUI(gameEngine) {

  if (gameID.length > 0) { // gameID is not empty
    // delete the old game:
    $.ajax({
      url: "./games/" + gameID,
      type: 'DELETE',
    });
  }

  // set the new gameID:
  gameID = gameEngine.gameID;

  // clear all peg images:
  $(".peg-img").attr("src", pegImageSources.get(EMPTY_PEG));

  // clear all rocket images:
  $(".rocket-img").attr("src", rocketImageSources.get(EMPTY_ROCKET));

  // hide red x images:
  $(".x").hide();

  // cover goal:
  $("#goal-0").attr("src", G_IMG);
  $("#goal-1").attr("src", O_IMG);
  $("#goal-2").attr("src", A_IMG);
  $("#goal-3").attr("src", L_IMG);

  // clear all color buttons:
  $(".color-buttons").empty();

  // add color buttons:
  // all levels:
  $(".color-buttons").append(generateColorButtonHtml("red"), generateColorButtonHtml("yellow"), generateColorButtonHtml("green"),
    generateColorButtonHtml("purple"));

  // level 2:
  if (gameEngine.level > 1) {
    $(".color-buttons").append(generateColorButtonHtml("blue"));
    // decrease button/font size to make them all fit in the same space:
    $(".btn-color").css("fontSize", "0.7rem");

    // level 3:
    if (gameEngine.level > 2) {
      $(".color-buttons").append(generateColorButtonHtml("orange"));
      $(".btn-color").css("fontSize", "0.6rem");

      // level 4:
      if (gameEngine.level > 3) {
        $(".color-buttons").append(generateColorButtonHtml("pink"));
        $(".btn-color").css("fontSize", "0.5rem");

        // level 5:
        if (gameEngine.level > 4) {
          $(".color-buttons").append(generateColorButtonHtml("aqua"));
          $(".btn-color").css("fontSize", "0.4rem");
        }
      }
    }
  }

  // reset buttons:
  resetButtons();

  // set text on level dropdown:
  $("#level-dropdown").text("Level: " + gameEngine.level);

  addColorButtonHandlers();
}


function addColorButtonHandlers() {

  // add handlers to color buttons:
  $(".btn-color").on("click", function () {
    var source = $(this).attr("id");
    $.get("./games/game/" + gameID, function (gameEngine) {

      // only respond if the current sequence isn't already full:
      if (gameEngine.currentSeqSize < NUM_PEGS) {
        if (source == "btn-red") {
          addPeg(RED_PEG);
        } else if (source == "btn-yellow") {
          addPeg(YELLOW_PEG);
        } else if (source == "btn-green") {
          addPeg(GREEN_PEG);
        } else if (source == "btn-purple") {
          addPeg(PURPLE_PEG);
        } else if (source == "btn-blue") {
          addPeg(BLUE_PEG);
        } else if (source == "btn-orange") {
          addPeg(ORANGE_PEG);
        } else if (source == "btn-pink") {
          addPeg(PINK_PEG);
        } else if (source == "btn-aqua") {
          addPeg(AQUA_PEG);
        }
      }
    });
  });
}


// adds a peg to the current sequence:
function addPeg(color) {
  $.post("./games/" + gameID + "/peg/" + color, function (gameEngine) {
    $("#peg" + gameEngine.currentRow + "-" + (gameEngine.currentCol - 1)).attr("src", pegImageSources.get(gameEngine.lastPeg.color));

    // enable submit button only if current guess contains NUM_PEGS pegs:
    if (gameEngine.currentSeqSize == NUM_PEGS) {
      enableSubmitButton();
    } else {
      disableSubmitButton();
    }
  });
}


function changeLevel(level) {
  if (confirm("Are you sure you want to start a new game?")) {
    newGame(level);
  }
}


function disableSubmitButton() {
  $("#btn-submit").prop("disabled", true);
  $("#btn-submit").attr("title", "Each guess must contain " + NUM_PEGS + " aliens");
}


function enableSubmitButton() {
  $("#btn-submit").prop("disabled", false);
  $("#btn-submit").attr("title", "Submit your current guess");
}


function generateColorButtonHtml(color) {
  return "<div class=\"row\"><button type=\"button\" class=\"btn btn-primary btn-color\" id=\"btn-" + color.toLowerCase()
    + "\" title=\"Add " + color.toLowerCase() + " alien to your guess\">" + color.substring(0, 1).toUpperCase() + color.substring(1) + "</button></div>";
}

window.onbeforeunload = function () {
  $.ajax({
    url: "./games/" + gameID,
    type: 'DELETE',
  });
}