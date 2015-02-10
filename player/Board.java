/* Board.java  */

package player;

import list.*;

/**
 *  An implementation of the game board for the game Network.
 *
 *  A network is a sequence of six or more chips that starts in one of the player's goal areas and 
 *  terminates in the other goal area.
 *
 *  It contains 8 x 8 cells (grids, chip locations)
 *  The chip's color can be either BLACK(0) or WHITE(1)
 */
public class Board implements Cloneable{
    
    protected static final int BLACK = 0;
    protected static final int WHITE = 1;
    protected static final int CORNER = 2;
    protected static final int NETWORK_SIZE = 6;
    protected static final int GAMESIZE = 8;
    protected static final int MAXSTEPS = 10;
    protected static final int OFFSET = 10;
    
    protected int mySteps;
    protected int oppSteps;
    protected int myColor;
    protected int oppColor;
    
    protected Cell[][] board;
    
    /**
     * private Board constructor to prevent the user from using this no parameter constructor
     *
     * @param none
     */
    private Board() {
    }

    /**
     * protected Board constructor
     *
     * @param color: my chip's color for this Board
     */
    protected Board(int color) {
        if (color == WHITE || color == BLACK) {
            myColor = color;
            oppColor = 1 - color;
            mySteps = 0;
            oppSteps = 0;
            board = new Cell[GAMESIZE][GAMESIZE];
            board[0][0] = new Cell(0, 0, CORNER);
            board[0][7] = new Cell(0, 7, CORNER);
            board[7][0] = new Cell(7, 0, CORNER);
            board[7][7] = new Cell(7, 7, CORNER);
        }
    }
    
    /**
     * protected clone() method that clones this board
     *
     * @param none
     * @return a cloned Object of this Board
     */
    protected Object clone() throws CloneNotSupportedException {
        Board cloned = (Board) super.clone();
        Cell[][] newCells = new Cell[GAMESIZE][GAMESIZE];

        for (int i = 0; i < GAMESIZE; i++) {
            for (int j = 0; j < GAMESIZE; j++) {
                if (this.board[i][j] != null) {
                  Cell newCell = (Cell)(this.board[i][j]).clone();
                  newCells[i][j] = newCell;
                }
            }
        }
        cloned.setBoard(newCells);
        return cloned;  
    }
  
    /**
     * protected getCell() method
     *
     * @param x: cell's column number
     * @param y: cell's row number
     * @return a Cell object from this Board associated with x column & y row
     */
    protected Cell getCell(int x, int y) {
        if (x >= 0 && x < GAMESIZE && y >= 0 && y < GAMESIZE) {
            return board[x][y];
        }
        return null;
    }
    
    /**
     * protected getCell() method
     *
     * @param id: cell's id number
     * @return a Cell object from this Board associated with the cell id
     */
    protected Cell getCell(int id) {
        if (id >= 0 && id <= (GAMESIZE * OFFSET)) {
            int x = id / OFFSET;
            int y = id % OFFSET;
            if (x >= 0 && x < GAMESIZE &&
                y >= 0 && y < GAMESIZE) {
                return board[x][y];
            }
        }
        return null;
    }
    
    /**
     * protected setCell() method
     *
     * @param cell: a Cell to be placed on this Board
     */
    protected void setCell(Cell cell) {
        if (cell.x() >= 0 && cell.x() < GAMESIZE &&
            cell.y() >= 0 && cell.y() < GAMESIZE) {
            board[cell.x()][cell.y()] = cell;
        }
    }
    
    /**
     * protected setCell() method
     *
     * @param x: cell's column number
     * @param y: cell's row number
     * @param color: cell's color
     */
    protected void setCell(int x, int y, int color) {
        if (x >= 0 && x < GAMESIZE && y >= 0 && y < GAMESIZE &&
            (color == WHITE || color == BLACK)) {
            board[x][y] = new Cell(x, y, color);
        }
    }
    
    /**
     * protected setBoard() method
     *
     * @param newBoard: a Board object to be set to this Board
     */
    protected void setBoard(Cell[][] newBoard) {
        this.board = newBoard;
    }
    
    /**
     * protected getMySteps() method
     *
     * @return my number of moves
     */
    protected int getMySteps() {
        return mySteps;
    }
    
    /**
     * protected getOppSteps() method
     *
     * @return my opponent's number of moves
     */
    protected int getOppSteps() {
        return oppSteps;
    }
    
    /**
     * protected setMySteps() method
     *
     * @param n: my number of moves
     */
    protected void setMySteps(int n) {
        mySteps = n;
    }
    
    /**
     * protected setOppSteps() method
     *
     * @param n: my opponent's number of moves
     */
    protected void setOppSteps(int n) {
        oppSteps = n;
    }
    
    /**
     * protected getMyColor() method
     *
     * @return my color
     */
    protected int getMyColor() {
        return myColor;
    }
    
    /**
     * protected getOppColor() method
     *
     * @return my opponent's color
     */
    protected int getOppColor() {
        return oppColor;
    }
    
    /**
     * protected remove() overloaded method
     *
     * @param c: a Cell to be removed from this Board
     */
    protected void remove(Cell c) {
        int x = c.x();
        int y = c.y();
        remove(x, y);
    }
    
    /**
     * protected remove() method
     *
     * @param x: the column number for the Cell to be removed from this Board
     * @param y: the row number for the Cell to be removed from this Board
     */
    protected void remove(int x, int y) {
        if ((x == 0 && y == 0) || (x == 0 && y == 7) ||
            (x == 7 && y == 0) || (x == 7 && y == 7) ||
            x < 0 || x >= GAMESIZE || y < 0 || y >= GAMESIZE) {
            return;
        }    
        board[x][y] = null;
    }
    
    /**
     * protected getStartCells() method that finds all the cells in the starting goal area for a color
     *
     * @param color: the color of the chip to be reported from the Board
     * @return DList of Cells in the starting goal area of the color
     */
    protected DList getStartCells(int color) {
        DList start = new DList();
        if (color == WHITE) {
            for (int i = 1; i < GAMESIZE-1; i++) {
                if (board[0][i] != null) {
                    start.add(board[0][i]);
                }
            }
        } else if (color == BLACK) {
            for (int i = 1; i < GAMESIZE-1; i++) {
                if (board[i][0] != null) {
                    start.add(board[i][0]);
                }
            }
        }
        return start;
    }
    
    /**
     * protected getTargetCells() method that finds all the cells in the ending goal area for a color
     *
     * @param color: the color of the chip to be reported from the Board
     * @return DList of Cells in the ending goal area of the color
     */
    protected DList getTargetCells(int color) {
        DList target = new DList();
        if (color == WHITE) {
            for (int i = 1; i < GAMESIZE-1; i++) {
                if (board[7][i] != null) {
                    target.add(board[7][i]);
                }
            }
        } else if (color == BLACK) {
            for (int i = 1; i < GAMESIZE-1; i++) {
                if (board[i][7] != null) {
                    target.add(board[i][7]);
                }
            }
        }
        return target;
    }
    
    /**
     * Find connections for a particular cell of color in all directions.
     * Stops when a cell of another color or an invalid cell position is reached.
     *
     * @param cell: a cell
     * @return DList of cells linked to the cell
     */
    protected DList connectionFinder(Cell c) {
        DList cells = new DList();
        int x = c.x();
        int y = c.y();
        int color = c.getColor();
        int nx;
        int ny;
        
        //Check all directions from the specific Cell
        
        //South direction
        nx = x;
        ny = y + 1;
        while (ny < GAMESIZE) {
            if (board[nx][ny] != null) {
                if (board[nx][ny].getColor() == color) {
                    cells.add(new Cell(nx, ny, color));
                }
                break;
            }
            ny++;
        }
        
        //North direction
        nx = x;
        ny = y - 1;
        while (ny >= 0) {
            if (board[nx][ny] != null) {
                if (board[nx][ny].getColor() == color) {
                    cells.add(new Cell(nx, ny, color));
                }
                break;
            }
            ny--;
        }
        
        //East direction
        nx = x + 1;
        ny = y;
        while (nx < GAMESIZE) {
            if (board[nx][ny] != null) {
                if (board[nx][ny].getColor() == color) {
                    cells.add(new Cell(nx, ny, color));
                }
                break;
            }
            nx++;
        }
        
        //West direction
        nx = x - 1;
        ny = y;
        while (nx >= 0) {
            if (board[nx][ny] != null) {
                if (board[nx][ny].getColor() == color) {
                    cells.add(new Cell(nx, ny, color));
                }
                break;
            }
            nx--;
        }
        
        //Northwest direction
        nx = x - 1;
        ny = y - 1;
        while (nx >= 0 && ny >= 0) {
            if (board[nx][ny] != null) {
                if (board[nx][ny].getColor() == color) {
                    cells.add(new Cell(nx, ny, color));
                }
                break;
            }
            nx--;
            ny--;
        }
        
        //Northeast direction
        nx = x + 1;
        ny = y - 1;
        while (nx < GAMESIZE && ny >= 0) {
            if (board[nx][ny] != null) {
                if (board[nx][ny].getColor() == color) {
                    cells.add(new Cell(nx, ny, color));
                }
                break;
            }
            nx++;
            ny--;
        }
        
        //Southwest direction
        nx = x - 1;
        ny = y + 1;
        while (nx >= 0 && ny < GAMESIZE) {
            if (board[nx][ny] != null) {
                if (board[nx][ny].getColor() == color) {
                    cells.add(new Cell(nx, ny, color));
                }
                break;
            }
            nx--;
            ny++;
        }
        
        //Southeast direction
        nx = x + 1;
        ny = y + 1;
        while (nx < GAMESIZE && ny < GAMESIZE) {
            if (board[nx][ny] != null) {
                if (board[nx][ny].getColor() == color) {
                    cells.add(new Cell(nx, ny, color));
                }
                break;
            }
            nx++;
            ny++;
        }
        return cells;
    }
    
    /**
     * protected getEdgeList() method that finds all the Edges connected to a cell
     *
     * @param c: a cell to be used to get all edges starting from it
     * @return DList of edges that all started from this cell
     */
    protected DList getEdgeList(Cell c) {
        return getEdgeList(c.id());
    }
    
    /**
     * protected getEdgeList() method that finds all the Edges connected to a cell using connectionFinder
     *
     * @param id: an id of a cell to be used to get all edges starting from it
     * @return DList of edges that all started from this cell
     */
    protected DList getEdgeList(int id) {
        Cell c = getCell(id);
        DList connections = connectionFinder(c);
        DList edges = new DList();
        for (int i = 0; i < connections.size(); i++) {
            edges.add(new Edge(c, (Cell) connections.get(i)));
        }
        return edges;
    }
    
    /**
     * protected applyMove() checks if the move is valid, and if is, then 
     * performs the move on this Board. Returns true if it's valid and false if not.
     *
     * @param m: a Move to be applied to this Board
     * @param color: the integer color of the chip to be used for this move
     * @return a boolean value representing the success or failure of this move
     */
    protected boolean applyMove(Move m, int color) {
        if (isValidMove(m, color)) {
            if (m.moveKind == Move.ADD) {
                setCell(m.x1, m.y1, color);
            } else {
                remove(m.x2, m.y2);
                setCell(m.x1, m.y1, color);
            }
            if (color == myColor) {
                setMySteps(getMySteps() + 1);
            } else {
                setOppSteps(getOppSteps() + 1);
            }
            return true;
        }
        return false;
    }
    
    /**
     * protected isValidMove() method
     * If the move is an add move, calls isValidAddMove.
     * If the move is a step move, calls isValidStepMove.
     *
     * @param m: a Move to be checked for its validity
     * @param color: the integer color of the chip to be used for this move
     * @return a boolean value representing the success or failure of this move check
     */
    protected boolean isValidMove(Move m, int color) {
        int checkSteps = 0;
        if (color == myColor) {
            checkSteps = mySteps;
        } else if (color == oppColor) {
            checkSteps = oppSteps;
        } else {
            return false;
        }
        if (m.moveKind == Move.ADD && checkSteps < MAXSTEPS) {
            return isValidAddMove(m, color);
        } else if (m.moveKind == Move.STEP && checkSteps >= MAXSTEPS) {
            return isValidStepMove(m, color);
        } else {
            return false;
        }
    }
    
    /**
     * protected isValidStepMove() method
     * Clones the board, removes the cell to be moved, then calls isValidAddMove.
     *
     * @param m: a STEP Move to be checked for its validity
     * @param color: the integer of the chip to be used for this step move
     * @return a boolean value representing the success or failure for this step move check
     */
    protected boolean isValidStepMove(Move m, int color) {
        if (board[m.x1][m.y1] != null || board[m.x2][m.y2] == null || board[m.x2][m.y2].getColor() != color) {
            return false;
        }
        Board newBoard = null;
        try {
            newBoard = (Board) this.clone();
        } catch (CloneNotSupportedException cnsEx) {
        }
        newBoard.remove(m.x2, m.y2);
        boolean isValid = newBoard.isValidAddMove(new Move(m.x1, m.y1), color);
        newBoard = null;
        return isValid;
    }
    
    /**
     * protected isValidAddMove() overloaded method
     *
     * @param m: a ADD Move to be checked for its validity
     * @param color: the integer color of the chip to be used for this add move
     * @return a boolean value represents success or failure of this add move check
     */
    protected boolean isValidAddMove(Move m, int color) {
        if (m.moveKind == Move.QUIT) {
            return false;
        }
        return isValidAddMove(m.x1, m.y1, color);
    }
  
    /**
     * protected isValidAddMove() overloaded method
     *
     * @param cell: a cell whose position on this Board will be checked if an ADD move is valid
     * @return a boolean value representing the success or failure of this add move check
     */
    protected boolean isValidAddMove(Cell cell) {
        return isValidAddMove(cell.x(), cell.y(), cell.getColor());
    }
    
    /**
     * protected isValidAddMove() method
     * Checks if the board piece at target position does not already have a Cell
     * Checks if target position is the goal area of the opposing color
     * Checks for clusters
     *
     * @param x: column number to be checked if an ADD move is valid
     * @param y: row number to be checked if an ADD move is valid
     * @param color: the integer color of the chip to be used for this add move
     * @return a boolean value representing the success or failure of this add move check
     */
    protected boolean isValidAddMove(int x, int y, int color) {
        if (board[x][y] != null) {
            return false;
        }
        if (x < 0 || x >= GAMESIZE ||
            y < 0 || y >= GAMESIZE ||
            (color != BLACK && color != WHITE)) {
            return false;
        }
        if (color == BLACK) {
            //WHITE goal areas
            if (x == 0 || x == 7) {
                return false;
            }
        }
        if (color == WHITE) {
            //BLACK goal areas
            if (y == 0 || y == 7) {
                return false;
            }
        }
        //Checking for clusters
        DList n = neighbors(x, y, color);
        if (n.size() == 0) {
            return true;
        } else if (n.size() >= 2) {
            return false;
        } else {
            for (int i = 0; i < n.size(); i++) {
                DList cList = neighbors((Cell) n.get(i));
                if (cList.size() >= 1) {
                    return false;
                }
            }
            return true;
        }
    }
    
    /**
     * Find the same-color neighbors of a potential cell created by a move
     *
     * @param m: Move that will create a chip on the board
     * @param color: the integer color of the chip to be used for this move
     * @return DList of the neighbor cells of a potential cell created by Move m and color
     */
    protected DList neighbors(Move m, int color) {
        return neighbors(m.x1, m.y1, color);
    }
    
    /**
     * Find the same-color neighbors of a potential cell on this board
     *
     * @param c: Cell whose same-color neighbors will be found
     * @return DList of the neighbor cells of the cell
     */
    protected DList neighbors(Cell c) {
        return neighbors(c.x(), c.y(), c.getColor());
    }
    
    /**
     * Find the same-color neighbors of a potential cell with a given position and color
     * Checks the 8 surrounding positions around the given position for placed cells of the same color
     *
     * @param x: column number of cell whose neighbors will be determined
     * @param y: row number of cell whose neighbors will be determined
     * @param color: the integer color of the chip to be used for this move
     * @return DList of the neighbor cells of a potential cell represented by column x, row y and color
     */
    protected DList neighbors(int x, int y, int color) {
        DList cList = new DList();
        if (x-1 >= 0 && y-1 >= 0) {
            if (board[x-1][y-1] != null && board[x-1][y-1].getColor() == color) {
                cList.add(board[x-1][y-1]);
            }
        }
        if (x-1 >= 0) {
            if (board[x-1][y]   != null && board[x-1][y].getColor() == color) {
                cList.add(board[x-1][y]);
            }
        }
        if (x-1 >= 0 && y+1 < GAMESIZE) {
            if (board[x-1][y+1] != null && board[x-1][y+1].getColor() == color) {
                cList.add(board[x-1][y+1]);
            }
        }
        if (y-1 >= 0) {
            if (board[x][y-1]   != null && board[x][y-1].getColor() == color) {
                cList.add(board[x][y-1]);
            }
        }
        if (y+1 < GAMESIZE) {
            if (board[x][y+1]   != null && board[x][y+1].getColor() == color) {
                cList.add(board[x][y+1]);
            }
        }
        if (x+1 < GAMESIZE && y-1 >= 0) {
            if (board[x+1][y-1] != null && board[x+1][y-1].getColor() == color) {
                cList.add(board[x+1][y-1]);
            }
        }
        if (x+1 < GAMESIZE) {
            if (board[x+1][y]   != null && board[x+1][y].getColor() == color) {
                cList.add(board[x+1][y]);
            }
        }
        if (x+1 < GAMESIZE && y+1 < GAMESIZE) {
            if (board[x+1][y+1] != null && board[x+1][y+1].getColor() == color) {
                cList.add(board[x+1][y+1]);
            }
        }
        return cList;
    }
    
    /**
     * Find the cell positions that chips of a color can be added to.
     * Checks all the open positions on the board for positions that a cell of a certain color can be added to.
     *
     * @param color: the integer color of the chip to be used for this move
     * @return DList of the cells in which an add move to those cells' positions with a color would be valid
     */
    protected DList availableCells(int color) {
        DList legalCells = new DList();
        for (int i = 0; i < GAMESIZE; i++) {
            for (int j = 0; j < GAMESIZE; j++) {
                if (this.board[i][j] == null) {
                    if (isValidAddMove(i, j, color)) {
                        Cell newCell = new Cell(i, j, color);
                        legalCells.add(newCell);
                    }
                }
            }
        }
        return legalCells;
    }
    
    /**
     * Finds all possible add or step moves
     * If steps < 10, availableMoves finds only add moves
     * If steps >= 10, availableMoves finds only step moves
     *
     * @param color: the integer color of the chip
     * @return DList all of possible moves for the given color
     */
    protected DList availableMoves(int color) {
        DList legalMoves = new DList();
        if ((color == myColor && mySteps < MAXSTEPS) || (color == oppColor && oppSteps < MAXSTEPS)) {
            DList ac = availableCells(color);
            for (int i = 0; i < ac.size(); i++) {
                Cell c = (Cell) ac.get(i);
                legalMoves.add(new Move(c.x(), c.y()));
            }
        } else if ((color == myColor && mySteps >= MAXSTEPS) || (color == oppColor && oppSteps >= MAXSTEPS)) {
            DList placedCells = new DList();
            for (int i = 0; i < GAMESIZE; i++) {
                for (int j = 0; j < GAMESIZE; j++) {
                    if (board[i][j] != null && board[i][j].getColor() == color) {
                        placedCells.add(board[i][j]);
                    }
                }
            }
            for (int i = 0; i < placedCells.size(); i++) {
                Board newBoard = null;
                try {
                    newBoard = (Board) this.clone();
                } catch (CloneNotSupportedException cnsEx) {
                }
                Cell c = (Cell) placedCells.get(i);
                newBoard.remove(c);
                DList newList = newBoard.availableCells(color);
                for (int j = 0; j < newList.size(); j++) {
                    Cell cx = (Cell) newList.get(j);
                    if (cx.id() != c.id()) {
                        legalMoves.add(new Move(cx.x(), cx.y(), c.x(), c.y()));
                    }
                }
                newBoard = null;
            }
        }
        return legalMoves;
    }
    
    /**
     * Counts all the pairs of chips of a certain color that are visible to each other
     *
     * @param color: an integer color
     * @return the number of pairs of visible cells
    */
    protected int visibleCells(int color) {
        int pairs = 0;

        int othercolor;
        if (color == WHITE) {
            othercolor = BLACK;
        }
        else {
            othercolor = WHITE;
        }
        DList currentConnections = new DList();
        for (int i = 0; i < GAMESIZE; i++) {
            for (int j = 0; j < GAMESIZE; j++) {
                if (this.getCell(i,j) == null) {
                    continue;
                }
                else if (this.getCell(i,j).getColor() == CORNER) {
                    continue;
                }
                
                else if (this.getCell(i,j).getColor() == othercolor) {
                    continue;
                }
                else {
                    currentConnections = connectionFinder(getCell(i,j));
                    if (currentConnections == null) {
                        continue;
                    }
                    else {
                        pairs += currentConnections.size();
                        
                        for (int x = 0; x < currentConnections.size(); x++) {
                            if (((Cell) currentConnections.get(x)).x() < i) {
                                pairs--;
                                
                            }
                            else if (((Cell) currentConnections.get(x)).x() == i && (((Cell) currentConnections.get(x)).y() < j)) {
                                pairs--;
                                
                            }
                        }
                    }
                }
            }
        }
        return pairs;
    }

    /**
     * Determine if the board has a chip in the goal
     * 
     * @param color: color whose goal we're checking
     * @param side: 0 for up/left, 1 for down/right, depending on color
     * @return true if the color has a chip in the specified goal
     */
    protected boolean inGoal(int color, int side) {
        boolean inGoal = false;
        int coord;
        if (side == 0) {
            coord = 0;
        }
        else {
            coord = GAMESIZE-1;
        }
        if (color == BLACK) {
            for (int y = 0; y < GAMESIZE; y++) {
                if (this.getCell(coord,y) != null) {
                    inGoal = true;
                }
            }
        }
        else {
            for (int x = 0; x < GAMESIZE; x++) {
                if (this.getCell(x,coord) != null) {
                    inGoal = true;
                }
            }
        }
        return inGoal;
    }
    
    /**
     * toString method for Board
     *
     * @return the String representation of this Board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(GAMESIZE * GAMESIZE);
        for (int j = 0; j < GAMESIZE; j++) {
            for (int i = 0; i < GAMESIZE; i++) {
                if (board[i][j] != null) {
                    sb.append((board[i][j]).getColor());
                } else {
                    sb.append("-");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * public main() method used for testing
     *
     * @param args: String array
     */
    public static void main (String[] args) throws CloneNotSupportedException {
    }
}
