/* Entry.java */

package dict;

/**
 *  A class for dictionary entries.
 *
 *  Part of the interface of the Dictionary ADT.
 **/

public class Entry {

  protected Integer key;
  protected Integer value;

  /** 
   *  @return the Integer key
   **/
  public Integer key() {
    return key;
  }

  /** 
   *  @return the Integer value associated with the key
   **/
  public Integer value() {
    return value;
  }

}
