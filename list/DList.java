/* DList.java */

package list;

/**
 *  A DList is a mutable doubly-linked list ADT.  Its implementation is
 *  circularly-linked and employs a sentinel node at the head of the list.
 *
 **/

public class DList extends List {

  /**
   *  (inherited)  size is the number of items in the list.
   *  head references the sentinel node.
   *  Note that the sentinel node does not store an item, and is not included
   *  in the count stored by the "size" field.
   *
   **/

  protected DListNode head;

  /* DList invariants:
   *  1)  head != null.
   *  2)  For every DListNode x in a DList, x.next != null.
   *  3)  For every DListNode x in a DList, x.prev != null.
   *  4)  For every DListNode x in a DList, if x.next == y, then y.prev == x.
   *  5)  For every DListNode x in a DList, if x.prev == y, then y.next == x.
   *  6)  For every DList l, l.head.myList = null.  (Note that l.head is the
   *      sentinel.)
   *  7)  For every DListNode x in a DList l EXCEPT l.head (the sentinel),
   *      x.myList = l.
   *  8)  size is the number of DListNodes, NOT COUNTING the sentinel,
   *      that can be accessed from the sentinel (head) by a sequence of
   *      "next" references.
   **/

  /**
   *  newNode() calls the DListNode constructor.  Use this method to allocate
   *  new DListNodes rather than calling the DListNode constructor directly.
   *  That way, only this method need be overridden if a subclass of DList
   *  wants to use a different kind of node.
   *
   *  @param item the item to store in the node.
   *  @param list the list that owns this node.  (null for sentinels.)
   *  @param prev the node previous to this node.
   *  @param next the node following this node.
   **/
  protected DListNode newNode(Object item, DList list,
                              DListNode prev, DListNode next) {
    return new DListNode(item, list, prev, next);
  }

  /**
   *  DList() constructs for an empty DList.
   **/
  public DList() {
      head = newNode(null, (DList) null, null, null);
      head.next = head;
      head.prev = head;
      size = 0;
  }
  
  /** 
   *  clear() returns a new DList
   *
   **/ 
  public DList clear() {
      return new DList();
  }
  
  /** 
   *  get() returns the xth item in this list
   *
   * @param x is the index of the item
   **/
  public Object get(int x) {
      Object returnItem = null;

      try {
        ListNode currNode = this.front();
        while (x > 0) {
          currNode = currNode.next();
          x--;
        }
        returnItem = currNode.item();
      }
      catch(InvalidNodeException e) {
      }
      return returnItem;
  }
 /** 
   *  getNode() returns the Node at the xth item in this list
   *
   * @param x is the index of the item
   **/ 
  public DListNode getNode(int x) {
      DListNode returnItem = null;
      try {
        ListNode currNode = this.front();
        while (x > 0) {
          currNode = currNode.next();
          x--;
        }
        returnItem = (DListNode) currNode;
      }
      catch(InvalidNodeException e) {
      }
      return returnItem;
  }
    
    

  /**
   *  insertFront() inserts an item at the front of this DList.
   *
   *  @param item is the item to be inserted.
   *
   *  Performance:  runs in O(1) time.
   **/
  public void insertFront(Object item) {
      if (size == 0) {
          head.next = newNode(item, this, head, head);
          head.prev = head.next;
      } else {
          DListNode node = newNode(item, this, head, head.next);
          head.next.prev = node;
          head.next = node;
      }
      size++;
  }

  /**
   *  insertBack() inserts an item at the back of this DList.
   *
   *  @param item is the item to be inserted.
   *
   *  Performance:  runs in O(1) time.
   **/
  public void insertBack(Object item) {
      if (size == 0) {
          head.next = newNode(item, this, head, head);
          head.prev = head.next;
      } else {
          DListNode node = newNode(item, this, head.prev, head);
          head.prev.next = node;
          head.prev = node;
      }
      size++;
  }

  /**
   *  add() inserts an item at the back of this DList.
   *
   *  @param item is the item to be inserted.
   *
   *  Performance:  runs in O(1) time.
   **/
  public void add(Object item) {
      if (size == 0) {
          head.next = newNode(item, this, head, head);
          head.prev = head.next;
      } else {
          DListNode node = newNode(item, this, head.prev, head);
          head.prev.next = node;
          head.prev = node;
      }
      size++;
  }
  
  /**
   *  front() returns the node at the front of this DList.  If the DList is
   *  empty, return an "invalid" node--a node with the property that any
   *  attempt to use it will cause an exception.  (The sentinel is "invalid".)
   *
   *  @return a ListNode at the front of this DList.
   *
   *  Performance:  runs in O(1) time.
   */
  public ListNode front() {
    return head.next;
  }

  /**
   *  back() returns the node at the back of this DList.  If the DList is
   *  empty, return an "invalid" node--a node with the property that any
   *  attempt to use it will cause an exception.  (The sentinel is "invalid".)
   *
   *  @return a ListNode at the back of this DList.
   *
   *  Performance:  runs in O(1) time.
   */
  public ListNode back() {
    return head.prev;
  }

  /**
   *  toString() returns a String representation of this DList.
   *
   *  @return a String representation of this DList.
   *
   *  Performance:  runs in O(n) time, where n is the length of the list.
   */
  public String toString() {
    String result = "[  ";
    DListNode current = head.next;
    while (current != head) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }
}
