package submissions;
import checkers.AbstractEvaluator;

/**
 * student skeleton
 * change Class name to first initial, last name
 * of one of the students in the team
 * e.g. student Bob Smith -> BSmith
 */
public class TestName2 extends AbstractEvaluator {

    /*
     * Helper methods:
     * Each method listed here take in one spot on the board (a char), and
     * returns a boolean (true or false) based on if the info checking is
     * valid for that specific tile
     * 
     * methodName (parameters):  information checking
     * ownChecker (char tile):   own regular checker pieces
     * ownKing    (char tile):   own king checker pieces
     * oppChecker (char tile):   opponent's regular checker pieces
     * oppKing    (char tile):   opponent's king checker pieces
     */

    public TestName2() { //also change constructor name

        //change theses values
        super.name = "Jackie"; //set equal to your team name
        super.section = 3;  //set equal to lab section
    }

    /*
     * Board layout
     * Rows close to row 0 is Evaluator's
     * side of the board, while close to row 9 is the opponenent's
     */
    public boolean isEmpty(char [][] board, int c, int r) {
    	if (r>8 || r<1 || c>8 || c<1) {
    		return false;
    	}
    	if (ownChecker(board[r][c]) || oppChecker(board[r][c]) || ownKing(board[r][c]) || oppKing(board[r][c])){
    		return false;
    	}
    	return true;
    }
    
    public boolean canBeJumped(char [][] board, int r, int c) {

    	if (r==0 || r==8 || c==0 || c==8) {
    		return false;
    	}
    	// from the bottom right
    	if ((oppChecker(board[r+1][c+1]) || oppKing(board[r+1][c+1])) && isEmpty(board,r-1,c-1)) {
    		return true;
    	}
    	// from the bottom left
    	else if ((oppChecker(board[r+1][c-1]) || oppKing(board[r+1][c-1])) && isEmpty(board, r-1, c+1)) {
    		return true;
    	}
    	// from the top right
    	else if (oppKing(board[r-1][c+1]) && isEmpty(board, r+1, c-1)) {
    		return true;
    	}
    	// from the top left
    	else if (oppKing(board[r-1][c-1]) && isEmpty(board, r+1, c+1)) {
    		return true;
    	}
    	return false;
    }
    
    /*
     * Board layout
     * Rows close to row 0 is Evaluator's
     * side of the board, while close to row 9 is the opponenent's
     */

    @Override
    public int evaluateBoard(char [][] board) {
    	int score = 0;
    	int own = 0;
    	int opp = 0;
    	int row = 1;
    	int column = 1;
    	for(row = 1; row < 9; row++) {
        	for(column = 1; column < 9; column++) {

        		if (ownChecker(board[row][column])) {
        			own++;
        		}
        		if (ownKing(board[row][column])) {
        			score += 3;
        			own++;
        		}
        		if (oppChecker(board[row][column])) {
        			opp++;
        		}
        		if (oppKing(board[row][column])) {
        			score -= 3;
        			opp++;
        		}
        	}
    	}
    	score = (own-opp)*250;
    	
    	for(row = 1; row < 9; row++) {
        	for(column = 1; column < 9; column++) {

            	if (ownChecker(board[row][column]) && !canBeJumped(board,row,column)) {
        			score+= 40*row;
        		}
        		if (ownChecker(board[row][column]) && row==1) {
        			score += 20;
        		}
        		if (ownChecker(board[row][column]) && (column==1 || column==8)) {
        			score += 20*row;
        		}
        		if (ownKing(board[row][column])) {
        			if (canBeJumped(board, row, column)) {
        				score -=300;
        			}
        			if (row>4) {
        				score+= (8-row)*5;
        			}
        			else {
        				score += row*5;
        			}
        			if (column>4) {
        				score+= (8-column)*5;
        			}
        			else {
        				score += column*5;
        			}
        		}
        	}
    	}
        //System.out.println("SCORE: "+score);
    	return score; //return will be your final board score 
    }
}