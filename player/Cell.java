/* Cell.java  */

package player;

/**
 *  A cell is a chip of either black or white color that is 
 *  placed on a board in the game Network at a specific position.
 *
 */
public class Cell implements Comparable<Object>, Cloneable {

    private int x;
    private int y;
    private int color;
    private int id;
    
    /**
     * private Cell constructor to prevent the user from using this no parameter constructor
     *
     * @param none
     */
    private Cell() {
    }
    
    /**
     * Checks for illegal positions/colors before constructing a Cell
     *
     * @param x: column number
     * @param y: row number
     * @param color: color of the cell
     */
    public Cell(int x, int y, int color) {
        if (x >= 0 && x < Board.GAMESIZE &&
            y >= 0 && y < Board.GAMESIZE && 
            (color == Board.BLACK || color == Board.WHITE || color == Board.CORNER)) {
                this.x = x;
                this.y = y;
                this.color = color;
                id = x * Board.OFFSET + y;
        }
    }
    
    /**
     * protected clone() method that clones this cell
     *
     * @param none
     * @return a cloned Object of this Cell
     */
    protected Object clone() throws CloneNotSupportedException {
        Cell cloned = (Cell) super.clone();
        return cloned;
    }
    
    /**
     * id() method that returns the cell's id, which is a 
     * unique int for each position on the board
     *
     * @return id of this cell
     */
    public int id() {
        return id;
    }
    
    /**
     * x() method
     *
     * @return column position
     */
    public int x() {
        return x;
    }
    
    /**
     * getX() method
     *
     * @return column position
     */
    public int getX() {
        return x;
    }
    
    /**
     * y() method
     *
     * @return row position
     */
    public int y() {
        return y;
    }
    
    /**
     * getY() method
     *
     * @return row position
     */
    public int getY() {
        return y;
    }
    
    /**
     * color() method
     *
     * @return color of cell
     */
    public int color() {
        return color;
    }
    
    /**
     * getColor() method
     *
     * @return color of cell
     */
    public int getColor() {
        return color;
    }
    
    /**
     * Imposes a total ordering of cells by comparing their id's.
     * Returns 0 if cells are equal, -1 if other cell's id is greater than this cell's id,
     * and 1 if this cell's id is greater than other cell's id
     *
     * @return int 0, 1, or -1
     */
    public int compareTo(Object o) {
        Cell otherCell = (Cell)o;
        if (id == otherCell.id()) {
            return 0;
        } else {
            if (id < otherCell.id()) {
                return -1;
            } else {
                return 1;
            }
        }
    }
    
   /**
    *  toString() returns a String representation of this Cell.
    *
    *  @return a String representation of this Cell.
    */
    public String toString() {
        return " " + id + " " + color;
    }
    
}
