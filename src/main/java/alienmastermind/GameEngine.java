/**
 * Engine for Alien Mastermind
 */
package alienmastermind;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

public class GameEngine {

    private int level;
    private Goal target;
    private List<List<Peg>> pegSequences; // saves all the peg sequences from the game
    private List<List<Rocket>> rocketSequences; // saves all the rocket sequences from the game
    private List<Peg> currentPegSeq;
    private List<Rocket> currentRocketSeq;
    private int currentRow;
    private int currentCol;
    private int winOrLose;
    
    @Id
    private String gameID;

    private static final int NUM_ROWS = 10;

    public static final int WIN = 1;
    public static final int LOSE = 2;
    public static final int INPROGRESS = 3;


    /**
     * Constructs a new GameEngine with the given ID and level
     * 
     * @param gameID    ID of the game
     * @param level     Level of the game
     */
    public GameEngine(String gameID, int level) {
        this.level = level;
        target = new Goal(level);
        pegSequences = new ArrayList<>();
        rocketSequences = new ArrayList<>();
        currentPegSeq = new ArrayList<>(Goal.NUM_PEGS);
        currentRocketSeq = new ArrayList<>(Goal.NUM_PEGS);
        currentRow = NUM_ROWS - 1;
        currentCol = 0;
        winOrLose = INPROGRESS;
        this.gameID = gameID;
    }


    @PersistenceConstructor
    public GameEngine(String gameID, int level, Goal target, List<List<Peg>> pegSequences, 
            List<List<Rocket>> rocketSequences, List<Peg> currentPegSeq, List<Rocket> currentRocketSeq, 
            int currentRow, int currentCol, int winOrLose) {
        this.gameID = gameID;
        this.level = level;
        this.target = target;
        this.pegSequences = pegSequences;
        this.rocketSequences = rocketSequences;
        this.currentPegSeq = currentPegSeq;
        this.currentRocketSeq = currentRocketSeq;
        this.currentRow = currentRow;
        this.currentCol = currentCol;
        this.winOrLose = winOrLose;
    }


    /**
     * Adds a peg to the current guess sequence
     * 
     * @param color color of the peg to add to sequence
     */
    public void addPegToSeq(int color) {
        if (currentPegSeq.size() < Goal.NUM_PEGS) {
            currentPegSeq.add(new Peg(color));
            currentCol++;
        }
    }


    /**
     * Submits the current guess sequence, setting the rockets accordingly, and saving the peg and rocket sequences
     */
    public void submitPegSeq() {
        currentRocketSeq.clear();
        if (currentPegSeq.size() == Goal.NUM_PEGS) {
            int numBlueRockets = 0;
            int numWhiteRockets = 0;

            for (int i = 0; i < Goal.NUM_PEGS; i++) // go through each peg in sequence
            {
                if (currentPegSeq.get(i).getColor() == target.getSequence(i).getColor()) { // right color, right place
                    numBlueRockets++;
                    target.setPegCount(i, Goal.COUNTEDBLUE);
                }
            }
            for (int i = 0; i < Goal.NUM_PEGS; i++) // go through each peg again to test for white rockets
            {
                goThruTarget: for (int j = 0; j < Goal.NUM_PEGS; j++) // go through each peg in target until there is a match or
                                                          // target
                                                          // sequence has been exhaustively searched
                {
                    if ((currentPegSeq.get(i).getColor() == target.getSequence(j).getColor())
                            && (target.getPegCount(i) != Goal.COUNTEDBLUE)
                            && (target.getPegCount(j) == Goal.NOTCOUNTED)) { // right color, wrong place
                        numWhiteRockets++;
                        target.setPegCount(j, Goal.COUNTEDWHITE);
                        break goThruTarget;
                    }
                }
            }
            if (numBlueRockets == Goal.NUM_PEGS) { // guess contains all right colors, right spots
                winOrLose = WIN;
            }
            if ((numBlueRockets != Goal.NUM_PEGS) && currentRow == 0) { // guess wrong, out of guesses
                winOrLose = LOSE;
            }

            // add the rockets to the rocket sequence:
            for (int i = 0; i < Goal.NUM_PEGS; i++) {
                if (numBlueRockets > 0) {
                    Rocket r = new Rocket(Rocket.BLUE);
                    currentRocketSeq.add(i, r);
                    numBlueRockets--;
                } else {
                    if (numWhiteRockets > 0) {
                        Rocket r = new Rocket(Rocket.WHITE);
                        currentRocketSeq.add(i, r);
                        numWhiteRockets--;
                    } else {
                        Rocket r = new Rocket(Rocket.EMPTY);
                        currentRocketSeq.add(i, r);
                    }
                }
            }

            // save the peg/rocket sequences:
            pegSequences.add(new ArrayList<>(currentPegSeq));
            rocketSequences.add(new ArrayList<>(currentRocketSeq));

            // reset everything for the next guess:
            currentPegSeq.clear();
            currentRow--;
            currentCol = 0;
            target.clearAllCounts();
        }
    }


    /**
     * Returns the hidden goal sequence
     * 
     * @return  the hidden goal sequence
     */
    public Goal getGoal() {
        return target;
    }


    /**
     * Returns which row of guesses the user is currently on 
     * (starts at 9 on the bottom, goes to 0 on the top) 
     * 
     * @return  which row of guesses the user is currently on 
     */
    public int getCurrentRow() {
        return currentRow;
    }


    /**
     * Returns which column of guesses the user is currently on 
     * (starts at 0 to the left, 3 to the right) 
     * 
     * @return  which column of guesses the user is currently on 
     */
    public int getCurrentCol() {
        return currentCol;
    }


    /**
     * Returns which peg was last guessed in the current sequence
     * 
     * @return  which peg was last guessed in the current sequence
     */
    // @JsonIgnore
    public Peg getLastPeg() {
        if (currentPegSeq.size() > 0) {
            return (Peg) currentPegSeq.get(currentPegSeq.size() - 1);
        } else {
            return new NullPeg();
        }
    }


    /**
     * Returns how many pegs are in the current guess sequence
     * 
     * @return  how many pegs are in the current guess sequence
     */
    public int getCurrentSeqSize() {
        return currentPegSeq.size();
    }


    /**
     * Returns the sequence of rockets for the current guess
     * 
     * @return  the sequence of rockets for the current guess
     */
    public List<Rocket> getCurrentRocketSeq() {
        return currentRocketSeq;
    }


    /**
     * Clears the current guess sequence
     */
    public void clearCurrentPegSeq() {
        if (currentPegSeq.size() > 0) {
            currentPegSeq.clear();
            currentCol = 0;
        }
    }


    /**
     * Returns GameEngine.WIN, GameEngine.LOSE, or GameEngine.INPROGRESS
     * 
     * @return  GameEngine.WIN, GameEngine.LOSE, or GameEngine.INPROGRESS
     */
    public int getWinOrLose() {
        return winOrLose;
    }


    /**
     * Returns the current level being played
     * 
     * @return  the current level being played
     */
    public int getLevel() {
        return target.getLevel();
    }


    /**
     * Returns the ID of this game
     * 
     * @return  the ID of this game
     */
    public String getGameID() {
        return gameID;
    }


    /**
     * Returns the saved peg sequences from this game
     * 
     * @return the saved peg sequences from this game
     */
    public List<List<Peg>> getPegSequences() {
        return pegSequences;
    }


    /**
     * Returns the saved rocket sequences from this game
     * 
     * @return the saved rocket sequences from this game
     */
    public List<List<Rocket>> getRocketSequences() {
        return rocketSequences;
    }
}