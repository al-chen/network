/* MachinePlayer.java */

package player;

import list.*;
import dict.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

    protected int myColor;
    protected int oppColor;
    protected int searchDepth;
    protected Board board;
    
    protected static final int DEFAULTDEPTH = 2;
    
    private HashTableChained currentRoute;
    private int[] visited;
    private Stack stack;
    private Cell[] possibleNetwork;
    
  /**
   * In Board.java
   * Creates a machine player with the given color.  Color is either 0 (black) or 1 (white).  (White has the first move.)
   * @param color: the MachinePlayer's color
   */
  public MachinePlayer(int color) {
    this(color, DEFAULTDEPTH);
  }
  
  /**
   * In MachinePlayer.java
   * Creates a machine player with the given color and search depth.  Color is either 0 (black) or 1 (white).  (White has the first move). 
   *
   * @param color: the MachinePlayer's color
   * @param searchDepth: the maximum search depth for minimax
   */
  public MachinePlayer(int color, int searchDepth) {
    if (color == Board.BLACK || color == Board.WHITE) {
      myColor = color;
      oppColor = 1 - color;
      this.searchDepth = searchDepth;
      board = new Board(color);
      
      currentRoute = new HashTableChained(78);
      visited = new int[78];
      stack = new Stack();
      possibleNetwork = new Cell[10];
    } else {
      System.exit(0);
    }
  }
 
  /**
   * In MachinePlayer.java
   * Returns a new move by "this" player.  Internally records the move (updates the internal game board) as a move by "this" player.
   * The move returned is the move optimal for winning
   *
   * @return myBestMove: a new move 
   */
  public Move chooseMove(){
    int color = this.myColor;
    int alpha = (Integer.MAX_VALUE * -1);
    int beta  = Integer.MAX_VALUE;
    int depth    = 0;
    int maxDepth = this.searchDepth;
    
    BestMove myBestMove = null;
    
    if (board.getMySteps() >= 5 && maxDepth != 1) {
        myBestMove = miniMax(this.board, color, alpha, beta, depth, 1);
        if (myBestMove.score == Integer.MAX_VALUE) {
            forceMove(myBestMove.move);
            return myBestMove.move;
        }
    }
  
    myBestMove = miniMax(this.board, color, alpha, beta, depth, maxDepth);
    forceMove(myBestMove.move);
    return myBestMove.move;
  }
  
  /** 
   * 
   * @param gboard: board to perform minimax on
   * @param color: color to maximize chances of winning
   * @param alpha: highest value in alpha-beta pruning
   * @param beta: lowest value in alpha-beta pruning
   * @param depth: the depth that minimax is currently at
   * @param maxDepth: the maximum depth that minimax will go to
   * @return the best move
   */
  private BestMove miniMax(Board gboard, int color, int alpha, int beta, int depth, int maxDepth) {

    BestMove myBestMove = new BestMove();
    BestMove replyMove = null;
    
    //only applicable if miniMax is called on a Board in which color already has a win
    if (depth == 0 && findNetwork(gboard, color)) {
      if (myColor == color) {
        myBestMove.score = Integer.MAX_VALUE;
      } else {
        myBestMove.score = (Integer.MAX_VALUE * -1);
      }
      return myBestMove;
    }
    
    if (depth > 0 && findNetwork(gboard, 1-color)) {
      if (myColor == 1-color) {
        myBestMove.score = Integer.MAX_VALUE;
      } else {
        myBestMove.score = (Integer.MAX_VALUE * -1);
      }
      return myBestMove;
    }
    
    if (depth > maxDepth) {
      myBestMove.score = heuristicEvaluation(gboard, depth, color);
      return myBestMove;
    }
    
    if (color == gboard.myColor) {
      myBestMove.score = alpha;
    } else {
      myBestMove.score = beta;
    }
    
    DList moves = gboard.availableMoves(color);
    myBestMove.move = (Move) moves.get(0);
    
    for (int a = 0; a < moves.size(); a++) {
      Move m = (Move) moves.get(a);
      Board newBoard = null;
      try {
        newBoard = (Board) gboard.clone();
      } catch (CloneNotSupportedException cnsEx) {
      }
      newBoard.applyMove(m, color);
      replyMove = miniMax(newBoard, 1-color, alpha, beta, depth+1, maxDepth);
      newBoard = null;
      
      if ((gboard.myColor == color) && (replyMove.score > myBestMove.score)) {
        myBestMove.move = m;
        myBestMove.score = replyMove.score;
        alpha = replyMove.score;
      } else if ((gboard.oppColor == color) && (replyMove.score < myBestMove.score)) {
        myBestMove.move = m;
        myBestMove.score = replyMove.score;
        beta = replyMove.score;
      }
      if (alpha >= beta) {
        return myBestMove;
      }
    }
    return myBestMove;
  }
  
   /**
   * In MachinePlayer.java
   * An evaluation function that assigns a score to a board.
   ** The score is positive if the board favors the current player; negative if it favors the opponent
   ** If either player has a winning board, they are assigned a maximum score of (-)1000
   *
   * @param board: a board
   * @param depth: what depth we're at in the minimax method
   * @param color: what color the current player is
   * @return an integer score of the board
   */
  private int heuristicEvaluation(Board gboard, int depth, int color) {
    int myScore  = 0;
    int oppScore = 0;
    
    int othercolor;
    if (color == Board.BLACK) {
      othercolor = Board.WHITE;
    }
    else {
      othercolor = Board.BLACK;
    }
      myScore = board.visibleCells(color) - board.visibleCells(othercolor);
      if (board.inGoal(color, 0)) {
        myScore += 4;
      }
      if (board.inGoal(color, 1)) {
        myScore +=4;
      }
      if (board.inGoal(othercolor, 0)) {
        myScore -= 2;
      }
      if (board.inGoal(othercolor,1)) {
        myScore -= 2;
      }
      for (int i = 0; i < Board.GAMESIZE; i++) {
        for (int j = 0; j < Board.GAMESIZE; j++) {
          if (gboard.board[i][j] != null) {
            if ((gboard.board[i][j]).getColor() == this.myColor) {
              myScore  += (gboard.getEdgeList(gboard.board[i][j])).size();
            } else {
              oppScore += (gboard.getEdgeList(gboard.board[i][j])).size();
            }
          }
        }
      }
    return myScore - oppScore;
  }
  
  /**
   * In MachinePlayer.java
   * If the Move m is legal, records the move as a move by the opponent (updates the internal game board) and returns true.  If the move is illegal, 
   * returns false without modifying the internal state of "this" player.  This method allows your opponents to inform you of their moves.
   *
   * @param m: move for the opponent to perform
   * @return boolean: returns true if move is a valid move
   */
  public boolean opponentMove(Move m) {
    if (m == null || m.moveKind == Move.QUIT) {
        return false;
    }
    Move nm = null;
    if (board.isValidMove(m, oppColor)) {
      if (m.moveKind == Move.STEP && board.getOppSteps() >= Board.MAXSTEPS) {
        board.remove(m.x2, m.y2);
        board.setCell(new Cell(m.x1, m.y1, oppColor));
        board.setOppSteps(board.getOppSteps() + 1);
        return true;
      } else if (m.moveKind == Move.ADD && board.getOppSteps() < Board.MAXSTEPS) {
        board.setCell(new Cell(m.x1, m.y1, oppColor));
        board.setOppSteps(board.getOppSteps() + 1);
        return true;
      } else {
        return false;
      }
    }
    return false;
  }

  /**
  * In MachinePlayer.java
  * If the Move m is legal, records the move as a move by "this" player (updates the internal game board) and returns true.  If the move is
  * illegal, returns false without modifying the internal state of "this" player.  This method is used to help set up "Network problems" for your
  * player to solve.
  *
  * @param m: move to force
  * @param boolean: true if m is a valid move, false otherwise
  */
  public boolean forceMove(Move m) {
    if (m == null || m.moveKind == Move.QUIT) {
        return false;
    }
    if (board.isValidMove(m, myColor)) {
      if (m.moveKind == Move.STEP && board.getMySteps() >= Board.MAXSTEPS) {
        board.remove(m.x2, m.y2);
        board.setCell(new Cell(m.x1, m.y1, myColor));
        board.setMySteps(board.getMySteps() + 1);
        return true;
      } else if (m.moveKind == Move.ADD && board.getMySteps() < Board.MAXSTEPS) {
        board.setCell(new Cell(m.x1, m.y1, myColor));
        board.setMySteps(board.getMySteps() + 1);
        return true;
      } else {
        return false;
      }      
    }
    return false;
  }
  
  /**
   * In MachinePlayer.java
   * Clears currentRoute, visited, stack, possibleNetwork instance variables
   */ 
  private void clearAll() {
    currentRoute.makeEmpty();
    visited = new int[78];
    stack.clear();
    possibleNetwork = new Cell[10];
  }
  
  /** 
  * In MachinePlayer.java
  * Returns a list of all the cells comprising a newtork if there is one.
  * Uses depth first search.
  *
  * @param startId: the ID of the cell in the starting goal area
  * @param targetId: the ID of the cell in the ending goal area
  * @param gboard: board to check
  */
  private Cell[] search(int startId, int targetId, Board gboard) {
        clearAll();
        DList nextEdges;
        Edge next = null;
        Cell start = gboard.getCell(startId);
        Cell target = gboard.getCell(targetId);
        if (start == null || target == null) {
          return null;
        }
        Edge placeholder = new Edge(start, start);
        stack.push(placeholder);
        int loopCnt = 0;
        
        while (!stack.isEmpty()) {
            int oldParent = 0;
            if (next != null) {
                oldParent = next.to.id();
            }

            next = stack.pop();
            if (oldParent != 0) {
                removeOld(next, oldParent);
            }
            
            currentRoute.insert(next.to().id(), next.from().id());
            visited[next.to().id()] = next.to().id();

            loopCnt++;
            //Target reached - found one network
            while (next.to().id() == targetId) {
                int index = 0;
                Cell[] temp = new Cell[10];
                temp[index] = target;
                index++;
                int parent = target.id();
                do {
                    parent = currentRoute.find(parent).value();
                    temp[index] = gboard.getCell(parent);
                    index++;
                } while (parent != startId);
                
                int idx = 0;
                for (int i = 9; i >= 0; i--) {
                    if (temp[i] != null) {
                        possibleNetwork[idx] = temp[i];
                        idx++;
                    }
                }
                
                if (isValidRoute(possibleNetwork, start.getColor())) {
                    return possibleNetwork;
                } else {
                    if (stack.isEmpty()) {
                        return null;
                    }
                    parent = next.to().id();
                    next = stack.pop();
                    removeOld(next, parent);

                    currentRoute.insert(next.to().id(), next.from().id());
                    visited[next.to().id()] = next.to().id();
                    possibleNetwork = new Cell[10];
                }
            }

            nextEdges = gboard.getEdgeList(next.to().id());
            for (int b = 0; b < nextEdges.size(); b++) {
                Edge e = (Edge) nextEdges.get(b);
                if (visited[e.to().id()] == 0) {
                    stack.push(e);
                }
            }
        }
        return null;
    }

  /** 
   * In MachinePlayer.java
   * If search finds a possible network, but it is not a valid network (Ex. it's not more than 5 connections long), removeOld removes all the edges in the invalid 
   * network that produced the invalid network up to a point so that search can continue to look for other options
   *
   * @param next: the next edge to be searched on the stack
   * @param parent: the ID of the from-cell in the lowest edge current route
   */
  private void removeOld(Edge next, int parent) {
        int toRemove = -1;
        int oldSize = currentRoute.size();
        boolean showDiff = false;
        while (parent != next.from().id()) {
            toRemove = parent;
            parent = currentRoute.find(parent).value();
            currentRoute.remove(toRemove);
            visited[toRemove] = 0;
        }
        if (currentRoute.size() != oldSize) {
            showDiff = true;
        }
    }
    
  /** 
   * In MachinePlayer.java
   * If search finds a possible network, isValidRoute determines if it is a valid network.
   * Checks if this possible network is at least 6 cells long, if it turns a corner at every cell, and if it only has two chips in the goal areas.
   *
   * @param possibleNetwork: Cell array that contains connected cells that may possibly form a network
   * @param color: color
   */
  private boolean isValidRoute(Cell[] possibleNetwork, int color) {
    int csize = 0;
    for (Cell c : possibleNetwork) {
        if (c != null) {
            csize++;
        }
    }
    if (csize < 6) {
        return false;
    }
    Cell c1, c2, c3 = null;
    int x1, y1, x2, y2, x3, y3 = 0;
    for (int i = 1; i < csize - 1; i++) {
      c1 = possibleNetwork[i-1];
      c2 = possibleNetwork[i];
      c3 = possibleNetwork[i+1];
      x1 = c1.x();
      y1 = c1.y();
      x2 = c2.x();
      y2 = c2.y();
      x3 = c3.x();
      y3 = c3.y();
      if (x1 == x2 && x2 == x3) {
        return false;
      }
      if (y1 == y2 && y2 == y3) {
        return false;
      }
      if ((x1 + y1) == (x2 + y2) && (x2 + y2) == (x3 + y3)) {
        return false;
      }
      if ((x2 - x1) == (y2 - y1) && (x3 - x2) == (y3 - y2)) {
        return false;
      }
      if (x2 == 0 || x2 == 7 || y2 == 0 || y2 == 7) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * In MachinePlayer.java
   * A function that determines if a particular colored network was formed in the game board
   *
   * @param gboard: a game board
   * @param color: color of the chip
   * @return a boolean value indicate if a network was formed for the color chip
   */
  private boolean findNetwork(Board gboard, int color) {
      if (gboard == null || (color != Board.WHITE && color != Board.BLACK)) {
          return false;
      }
      if (color == gboard.myColor) {
          if (gboard.getMySteps() < 6) {
              return false;
          }
      } else {
          if (gboard.getOppSteps() < 6) {
              return false;
          }
      }
      
      DList starts  = gboard.getStartCells(color);
      DList targets = gboard.getTargetCells(color);
      
      Cell[] result = null;
      for (int i = 0; i < starts.size(); i++) {
          Cell start = (Cell) starts.get(i);
          for (int j = 0; j < targets.size(); j++) {
              Cell target = (Cell) targets.get(j);
              result = search(start.id(), target.id(), gboard);
              if (result != null) {
                return true;
              }
          }
      }
      return false;
  }
  
  public static void main (String[] args) {
    MachinePlayer mp = new MachinePlayer(Board.WHITE);
    MachinePlayer mp2 = new MachinePlayer(Board.BLACK);
    
    //readme scenario 1 - valid route 
    //60 - 65 - 55 - 33 - 35 - 57
    mp2.forceMove(new Move(6, 0));
    mp2.forceMove(new Move(6, 5));
    mp2.forceMove(new Move(5, 5));
    mp2.forceMove(new Move(3, 3));
    mp2.forceMove(new Move(3, 5));
    mp2.forceMove(new Move(5, 7));
    System.out.println("board:\n" + mp2.board);
    Cell[] r = mp2.search(60, 57, mp2.board);
    if (r!=null) {System.out.println("Found network");}
    else {System.out.println("no network found");}
    }
}

