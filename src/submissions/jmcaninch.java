package submissions;
import checkers.AbstractEvaluator;

/**
 * student skeleton
 * change Class name to first initial, last name
 * of one of the students in the team
 * e.g. student Bob Smith -> BSmith
 */
public class jmcaninch extends AbstractEvaluator {

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

    public jmcaninch() { //also change constructor name

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
    	for(int row = 1; row < board.length; row++) {
        	for(int column = 1+(row%2); column < board.length; column+=2) {
        		
        		if (ownChecker(board[row][column])) {
        			own++;
        			if (!canBeJumped(board,row,column)) {
            			score += 3;
            		}
        			if (canBeJumped(board,row,column)) {
            			score -= 6;
            		}
        			if (row==1) {
            			score += 2;
            		}
        		}
        		if (ownKing(board[row][column])) {
        			score += 3;
        			own++;
        			if (row <= 4) {
        				score += row - 1;
        			}
        			else {
        				score += 8 - row;
        			}
        			if (column <= 4) {
        				score += row - 1;
        			}
        			else {
        				score += 8 - row;
        			}
        			if (canBeJumped(board,row,column)) {
        			score -= 10;
        			}
        			for (int r = row-2; r<=row+2; r+=2) {
        				for (int c=column-2; c<=column+2; c+=2) {
        					if ((r<=8 && r>0 && c<=8 && c>0)&&(oppChecker(board[r][c])||oppKing(board[r][c]))) {
        						score += 1;
        					}
        				}
        			}
        			for (int r = row-1; r<=row+1; r+=1) {
        				for (int c=column-1; c<=column+1; c+=1) {
        					if ((r<=8 && r>0 && c<=8 && c>0)&&(oppChecker(board[r][c])||oppKing(board[r][c]))&& !(canBeJumped(board,r,c))) {
        						score += 1;
        					}
        				}
        			}
        		}
        		if (oppChecker(board[row][column])) {
        			opp++;
        		}
        		if (oppKing(board[row][column])) {
        			opp++;
        			score-=3;
        		}
        		
        		
        	}
        }
    	if (opp==0 && own>1) {
    		score+=100;
    	score += (own-opp)*2;
    	}
        return score; //return will be your final board score 
    }
}