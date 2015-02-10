/* HashTableChained.java */

package dict;

import list.DList;
import list.DListNode;
import list.List;
import list.ListNode;
import list.InvalidNodeException;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *
 **/
public class HashTableChained implements Dictionary {

    protected static final int DEFAULTBUCKETS = 78;
    private int size;
    private int numEntries;
    private DList[] table;

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.
   **/
  public HashTableChained(int sizeEstimate) {
      size = sizeEstimate;
      table = new DList[size];
  }

  /** 
   *  Construct a new empty hash table with a default size.
   **/
  public HashTableChained() {
      size = DEFAULTBUCKETS;
      table = new DList[size];
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/
  public int size() {
    return numEntries;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    return numEntries == 0;
  }

  /**
   *  Create a new Entry object referencing the Integer input key and associated Integer value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method runs in O(1) time if the number of collisions is small.
   *
   *  @param key: the Integer key by which the entry can be retrieved.
   *  @param value: an Integer object.
   *  @return an entry containing the key and value.
   **/
  public Entry insert(Integer key, Integer value) {
      int i = key;
      
      Entry entry = new Entry();
      entry.key = key;
      entry.value = value;
      
      if (table[i] == null) {
          table[i] = new DList();
          table[i].insertFront(entry);
      } else {
          table[i].insertBack(entry);
      }
      numEntries++;
      return entry;
  }

  /** 
   *  Search for an entry with the specified Integer key.  If such an entry is found,
   *  return it; otherwise return null.
   *
   *  This method runs in O(1) time if the number of collisions is small.
   *
   *  @param key: the search Integer key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/  
  public Entry find(Integer key) {
      if (key == null) {
          return null;
      }
      int i = key;
      if (table[i] != null && table[i].length() != 0) {
          try {
              ListNode node = table[i].front();
              while (node.isValidNode()) {
                  if (((Entry) node.item()).key() == key) {
                      return (Entry) node.item();
                  }
                  node = node.next();
              }
              return null;
          } catch (InvalidNodeException inEx) {
              return null;
          }
      }
      return null;
  }

  /** 
   *  Remove an entry with the specified Integer key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *
   *  This method runs in O(1) time if the number of collisions is small.
   *
   *  @param key: the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */
  public Entry remove(Integer key) {
    // Replace the following line with your solution.
      if (key == null) {
          return null;
      }
      int i = key;
      if (table[i] != null && table[i].length() != 0) {
          ListNode node = table[i].front();
          
          while (node.isValidNode()) {
              try {
                  if (((Entry) node.item()).key() == key) {
                      Entry e = (Entry) node.item();
                      node.remove();
                      numEntries--;
                      if (table[i].length() == 0) {
                          table[i] = null;
                      }
                      return e;
                  }
                  node = node.next();
              } catch (InvalidNodeException inEx) {
                  return null;
              }
          }
          return null;
      }
      return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    // Your solution here.
      for (int i = 0; i < table.length; i++) {
          if (table[i] != null) {
              table[i] = null;
          }
      }
      numEntries = 0;
  }

  /*
   * Prints out how many entries are in the bucket list
   * 
   */
  public void hashTableHistograph() {
      int[] hist = new int[table.length];
      for (int i = 0; i < table.length; i++) {
          int cnt = 0;
          if (table[i] != null) {
              List l = table[i];
              ListNode n;
              try {
                  for (n = l.front(); n.isValidNode(); n = n.next()) {
                      cnt++;
                  }
              } catch (InvalidNodeException inEx) {
                  System.out.println(inEx.toString());
              }
          }
          hist[i] = cnt;
      }
      for (int j = 0; j < table.length; j++) {
        System.out.println("bucket:" + j + " count:" + hist[j]);
      }
  }

  public static void main(String[] args) {
      /*HashTableChained htc = new HashTableChained(11);
      
      htc.insert(new Integer(1), new Integer(1));
      htc.insert(new Integer(2), new Integer(2));
      htc.insert(new Integer(3), new Integer(3));
      htc.insert(new Integer(4), new Integer(4));
      htc.insert(new Integer(5), new Integer(5));
      htc.insert(new Integer(6), new Integer(6));
      htc.insert(new Integer(7), new Integer(7));
      htc.insert(new Integer(8), new Integer(8));
      htc.insert(new Integer(9), new Integer(9));
      htc.insert(new Integer(10), new Integer(10));
      htc.insert(new Integer(11), new Integer(11));
      htc.insert(new Integer(12), new Integer(12));
      //
      htc.insert(new Integer(13), new Integer(13));
      htc.insert(new Integer(14), new Integer(14));
      htc.insert(new Integer(15), new Integer(15));
      htc.insert(new Integer(16), new Integer(16));
      htc.insert(new Integer(17), new Integer(17));
      htc.insert(new Integer(18), new Integer(18));
      htc.insert(new Integer(19), new Integer(19));
      htc.insert(new Integer(20), new Integer(20));
      htc.insert(new Integer(21), new Integer(21));
      htc.insert(new Integer(22), new Integer(22));
      htc.insert(new Integer(23), new Integer(23));
      htc.insert(new Integer(24), new Integer(24));
      //      
      System.out.println("\nbucket size: " + htc.size);
      System.out.println("hashtable numEntries: " + htc.numEntries);
      
      for (int i = 0; i < htc.table.length; i++) {
          if (htc.table[i] != null) {
              List l = htc.table[i];
              ListNode n;
              try {
                  for (n = l.front(); n.isValidNode(); n = n.next()) {
                      System.out.println("n.item() " + i + ": " + ((Entry) n.item()).key().toString() + " " + ((Entry) n.item()).value().toString());
                  }
              } catch (InvalidNodeException inEx) {
                  System.out.println(inEx.toString());
              }
          } else {
              System.out.println("n.item() " + i + ": null");
          }
      }
      Entry e = htc.find(new Integer(1));
      System.out.println("\nfind Integer(1) Entry: " + e.key() + " " + e.value());
      e = htc.find(new Integer(24));
      System.out.println("find Integer(24) Entry: " + e.key() + " " + e.value());
      
      System.out.println("remove Integer(6)");
      Entry ei8 = htc.remove(new Integer(6));
      if (ei8 != null) {
          System.out.println("removed entry key: " + ei8.key() + " value: " + ei8.value());
      }
      else {
          System.out.println("ei8 null");
      }

      System.out.println("remove Integer(19)");
      Entry ei1 = htc.remove(new Integer(19));
      if (ei1 != null) {
          System.out.println("removed entry key: " + ei1.key() + " value: " + ei1.value());
      }
      else {
          System.out.println("ei1 null");
      }
      
      System.out.println("\nbucket size: " + htc.size);
      System.out.println("hashtable numEntries: " + htc.numEntries);
      
      for (int i = 0; i < htc.table.length; i++) {
          if (htc.table[i] != null) {
              List l = htc.table[i];
              ListNode n;
              try {
                  for (n = l.front(); n.isValidNode(); n = n.next()) {
                      System.out.println("n.item() " + i + ": " + ((Entry) n.item()).key().toString() + " " + ((Entry) n.item()).value().toString());
                  }
              } catch (InvalidNodeException inEx) {
                  System.out.println(inEx.toString());
              }
          } else {
              System.out.println("n.item() " + i + ": null");
          }
      }

      Entry e2 = htc.find(new Integer(1));
      if (e2 != null) {
          System.out.println("\nfind Integer(1) Entry: " + e2.key() + " " + e2.value());
      } else {
          System.out.println("\nfind Integer(1) null");
      }
      Entry e3 = htc.find(new Integer(19));
      if (e3 != null) {
          System.out.println("find Integer(19 Entry: " + e3.key() + " " + e3.value());
      } else {
          System.out.println("find Integer(19) null");
      }
      Entry e4 = htc.find(new Integer(6));
      if (e4 != null) {
          System.out.println("find Integer(6) Entry: " + e4.key() + " " + e4.value());
      } else {
          System.out.println("find Integer(6) null");
      }
      Entry e5 = htc.find(new Integer(21));
      if (e5 != null) {
          System.out.println("find Integer(21) Entry: " + e5.key() + " " + e5.value());
      } else {
          System.out.println("find Integer(21) null");
      }
      Entry e6 = htc.find(new Integer(24));
      if (e6 != null) {
          System.out.println("find Integer(24) Entry: " + e6.key() + " " + e6.value());
      } else {
          System.out.println("find Integer(24) null");
      }
      
      System.out.println("\nmakeEmpty()");
      htc.makeEmpty();
      System.out.println("bucket size: " + htc.size);
      System.out.println("hashtable numEntries: " + htc.numEntries);
      for (int i = 0; i < htc.table.length; i++) {
          if (htc.table[i] != null) {
              List l = htc.table[i];
              ListNode n;
              try {
                  for (n = l.front(); n.isValidNode(); n = n.next()) {
                      System.out.println("n.item() " + i + ": " + ((Entry) n.item()).key().toString() + " " + ((Entry) n.item()).value().toString());
                  }
              } catch (InvalidNodeException inEx) {
                  System.out.println(inEx.toString());
              }
          } else {
              System.out.println("n.item() " + i + ": null");
          }
      }

      System.out.println("\n2 is prime: " + htc.isPrime(2));
      System.out.println("101 is prime: " + htc.isPrime(101));
      System.out.println("7919 is prime: " + htc.isPrime(7919));*/
  }    
}