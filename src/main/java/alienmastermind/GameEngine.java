package alienmastermind;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GameEngine {

    private Goal target;
    private List<Peg> currentPegSeq;
    private List<Rocket> currentRocketSeq;
    private int currentRow;
    private int currentCol;
    private int winOrLose;

    public static final int WIN = 1;
    public static final int LOSE = 2;
    public static final int INPROGRESS = 3;
    public static final int DEFAULT_LEVEL = 1;

    public GameEngine(int level) {
        target = new Goal(level);
        currentPegSeq = new ArrayList<>(GameEngine.NUM_PEGS);
        currentRocketSeq = new ArrayList<>(GameEngine.NUM_PEGS);
        currentRow = 9;
        currentCol = 0;
        winOrLose = INPROGRESS;
    }

    public void addPegToSeq(int color) {
        if (currentPegSeq.size() < GameEngine.NUM_PEGS) {
            currentPegSeq.add(new Peg(color));
            currentCol++;
        }
    }

    public void submitPegSeq() {
        if (currentPegSeq.size() == GameEngine.NUM_PEGS) {
            int numBlueRockets = 0;
            int numWhiteRockets = 0;

            for (int i = 0; i < GameEngine.NUM_PEGS; i++) // go through each peg in sequence
            {
                if (((Peg) currentPegSeq.get(i)).getColor() == (target.getSequence(i).getColor())) {
                    numBlueRockets++;
                    target.setPegCount(i, Goal.COUNTEDBLUE);
                }
            }
            for (int i = 0; i < GameEngine.NUM_PEGS; i++) // go through each peg again to test for white rockets
            {
                goThruTarget: for (int j = 0; j < GameEngine.NUM_PEGS; j++) // go through each peg in target until there is a match or
                                                          // target
                                                          // sequence has been exhaustively searched
                {
                    if ((((Peg) currentPegSeq.get(i)).getColor() == (target.getSequence(j).getColor()))
                            && (target.getPegCount(i) != Goal.COUNTEDBLUE)
                            && (target.getPegCount(j) == Goal.NOTCOUNTED)) {
                        numWhiteRockets++;
                        target.setPegCount(j, Goal.COUNTEDWHITE);
                        break goThruTarget;
                    }
                }
            }
            if (numBlueRockets == GameEngine.NUM_PEGS) {
                winOrLose = WIN;
            }
            if ((numBlueRockets != GameEngine.NUM_PEGS) && currentRow == 0) {
                winOrLose = LOSE;
            }
            for (int i = 0; i < GameEngine.NUM_PEGS; i++) {
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
            currentPegSeq.clear();
            currentRow--;
            currentCol = 0;
            target.clearAllCounts();
        }
    }

    public Goal getGoal() {
        return target;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    // @JsonIgnore
    public Peg getLastPeg() {
        if (currentPegSeq.size() > 0) {
            return (Peg) currentPegSeq.get(currentPegSeq.size() - 1);
        } else {
            return new NullPeg();
        }
    }

    public int getCurrentSeqSize() {
        return currentPegSeq.size();
    }

    public Rocket getCurrentRocketSeq(int index) {
        return currentRocketSeq.get(index);
    }

    public List<Rocket> getCurrentRocketSeq() {
        return currentRocketSeq;
    }

    public void clearCurrentPegSeq() {
        if (currentPegSeq.size() > 0) {
            currentPegSeq.clear();
            currentCol = 0;
        }
    }

    public int getPlayerStatus() {
        return winOrLose;
    }

    public int getLevel() {
        return target.getLevel();
    }
}