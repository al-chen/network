/* Stack.java */

package player;

import list.*;

/**
 *  A Stack is a DList whose methods manipulate 
 *  the item at the front
 *
 **/
public class Stack {

    protected DList list;
    
    /**
     *  Stack() constructs for an empty DList.
     **/
    public Stack(){
        list = new DList();
    }
    
   /**
    *  isEmpty() returns true if this Stack is empty, false otherwise.
    *
    *  @return true if this Stack is empty, false otherwise. 
    *
    **/
    protected boolean isEmpty(){
        if(list.length() == 0){
        return true;
        }
        return false;
    }
 
   /** 
    *  pop() removes the front Edge item from list and returns it
    *
    * @return an Edge object
    **/
    protected Edge pop() {
        try {
            if(list.front().isValidNode()){
                Edge edge = (Edge) ((DListNode)list.front()).item();
                list.front().remove();
                return edge;
            }
        } catch (InvalidNodeException e) {}
        return null;  
    } 
   /** 
    *  push() places a new Edge item in the front of list and returns it
    *
    * @return an Edge object
    **/
    protected Edge push(Edge e){
        list.insertFront(e);
        return e;
    }
    
   /** 
    *  clear() assigns a new DList to list
    *
    **/
    protected void clear() {
        list = new DList();
    }
    
   /**
    *  toString() returns a String representation of this Stack.
    *
    *  @return a String representation of this Stack.
    */
    public String toString() {
        return " " + "Stack: " + list.toString();
    }
}