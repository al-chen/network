/* Edge.java  */

package player;

/**
 *  An edge is an unimpeded link between two cells of the same color
 *
 */
public class Edge implements Comparable<Object>{
    
    protected Cell from;
    protected Cell to;
    
    /**
     * private Edge constructor to prevent the user from using this no parameter constructor
     *
     * @param none
     */
    private Edge() {
    }
    
    /**
     * Edge constructor that makes sure both from and to cells are not null
     *
     * @param from: cell on one end of edge link
     * @param to: cell on other end of edge link
     */
    public Edge(Cell from, Cell to) {
        if (from != null && to != null) {
            this.from = from;
            this.to = to;
        }
    }
    
    /*
     * @return from Cell
     */
    public Cell from() {
        return from;
    }
    
    /*
     * @return to Cell
     */
    public Cell to() {
        return to;
    }
    
    /**
     * Imposes a total ordering of edges by comparing the id's of their from and to cells.
     *
     * @return int 0, 1, or -1
     */
    public int compareTo(Object o) {
        Edge edge = (Edge) o;
        if (from.compareTo(edge.from()) == 0 && to.compareTo(edge.to()) == 0) {
            return 0;
        }
        if (from.compareTo(edge.from()) == 0) {
            return to.compareTo(edge.to());
        } else {
            return from.compareTo(edge.from());
        }
    }
    
   /**
    *  toString() returns a String representation of this Edge.
    *
    *  @return a String representation of this Edge.
    */
    public String toString() {
        return " " + to.toString() + " " + from.toString();
    }
}
