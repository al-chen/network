/* BestMove.java */

package player;

public class BestMove {

  public Move move;
  public int score;

  public BestMove() {
    this.move = null;
    this.score = 0;
  }
  
  public BestMove(Move move) {
    this.move = move;
    this.score = 0;
  }
  
  public BestMove(Move move, int score) {
    this.move = move;
    this.score = score;
  }
  
  public Move getMove() {
    return move;
  }
  
  public int getScore() {
    return score;
  }
  
  public void setMove(Move move) {
    this.move = move;
  }
  
  public void setScore(int score) {
    this.score = score;
  }

}
