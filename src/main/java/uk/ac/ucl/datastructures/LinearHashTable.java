package uk.ac.ucl.datastructures;

/**
 *  A simple hash table class implementation using linear probing.
 *  Copyright (c) 2019
 *  Dept. of Computer Science, University College London
 *  @author Graham Roberts
 */

import java.lang.reflect.Array;

public class LinearHashTable<K,V> implements HashTable<K,V>
{
  /**
   * Helper class used to store a hash table entry (a key/value pair).
   * As this is a private helper class it is acceptable to have
   * public instance variables. Instances of this
   * class are never made available to client code of the hash table.
   */
  private static class Entry<S,T>
  {
    /**
     *  Key object.
     */
    public S key;
    /**
     *  Data object.
     */
    public T value;

    /**
     *  Constructor for an Entry object
     *
     *@param  key   key object reference
     *@param  value   data object reference
     */
    public Entry(final S key, final T value)
    {
      this.key = key;
      this.value = value;
    }
  }

  /**
   * Array of Entry references used as the hash table data structure.
   * By default all elements are initialised to null.
   */
  private Entry<K,V>[] data;

  /**
   * Special entry used to mark a deleted entry in the array.
   * Entries have to be marked deleted in order to manage the
   * probing correctly, if not then a probe for an entry would
   * not be able to determine whether a null marks the end of the
   * probe or whether it is a previously valid entry that has been
   * removed and the entry being searched for appears later on.
   */
  private final Entry<K,V> DELETED = new Entry<K,V>(null, null);

  /**
   * Size of array and, hence, number of entries allowed in table.
   */
  private int tableSize;

  /**
   * Default table size.
   */
  private static final int TABLESIZE = 100;

  /**
   * Construct table with default array size.
   */
  public LinearHashTable()
  {
    this(TABLESIZE);
  }

  /**
   * Construct table with given array size. The size fixes the maximum
   * number of values that can be stored in the table.
   *
   * @param size  size of table array
   */
  @SuppressWarnings("unchecked")
  public LinearHashTable(final int size)
  {
    tableSize = size;
    data = (Entry<K,V>[]) Array.newInstance(Entry.class,tableSize);
  }

  /**
   *  Put a key/value pair into the table by hashing the key to get the array
   *  index at which the search for an empty slot (probing) should start.
   *  Probing is actually carried out by calling a private helper method.
   *  If an existing element with the same key is present then overwrite its value,
   *  otherwise add element in an empty slot (either holding null or the DELETED
   *  entry).
   *
   *@param  key  key object reference
   *@param  val  data object reference
   */
  public void put(final K key, final V val)
  {
    int index = findEntry(key, true);
    Entry<K,V> entry = data[index];
    if ((entry == null) || (entry == DELETED))
    {
      data[index] = new Entry<K,V>(key, val);
    }
    else
    {
      entry.value = val;
    }
  }

  /**
   *  Given a key object, return a data object from the table or
   *  null if it is not found.
   *
   *@param  key  key object reference
   *@return      data object reference or null if no object found
   */
  public V get(final K key)
  {
    int index = findEntry(key, false);
    if ((index != -1) && (data[index] != null))
    {
      return data[index].value;
    }
    else
    {
      return null;
    }
  }

  /**
   * Remove a key/value pair from table if present, otherwise make no change.
   * Removing is actually done by marking the relevant array element as
   * DELETED, by storing a reference to the DELETED entry into it. The array
   * element cannot be simply set to null to ensure that following elements can still
   * be found by probing (when probing, finding a null value terminates the
   * probe).
   *
   *@param  key  key object reference
   */
  public void remove(final K key)
  {
    int index = findEntry(key, false);
    if ((index != -1) && (data[index] != null))
    {
      data[index] = DELETED;
    }
  }

  /**
   * Return the current list of key values for entries in the table.
   *
   * @return List of key values
   */
  public SimpleList<K> keys()
  {
    SimpleList<K> keys = new LinkedList<K>();
    for (Entry<K,V> entry : data)
    {
      if ((entry != null) && (entry != DELETED))
      {
        keys.insertAtHead(entry.key);
      }
    }
    return keys;
  }

  /**
   *  Given a key, use a hash function to obtain the array index
   *  corresponding to the key.
   *  The hashCode method inherited (and possibly overridden)
   *  from class Object is called to do the hashing, with the returned
   *  value constrained to the hash table array bounds.
   *  The distribution of objects in the hash table will depend
   *  on the quality of the hash function implemented by hashCode.
   *
   *@param  key  reference to object to be hashed
   *@return      array index of element corresponding to key
   */
  private int getIndex(final Object key)
  {
    return Math.abs(key.hashCode() % tableSize);
  }

  /**
   * Helper method to find (probe for) an entry in the table array and
   * return its index.
   * Probing stops when either null or an entry with a matching key
   * is found. If stopAtDeleted is true then the search additionally
   * stops when a DELETED entry is found. This allows the method to be
   * used by all of the get/put/remove methods, avoiding duplication of
   * code and keeping all probing code in a single method.
   * While probing, the array is treated as a circular array. Probing moves
   * forward one array element at a time. Alternative strategies such as
   * quadratic probing could be implemented.
   *
   * @param key  key object reference
   * @param stopAtDeleted flag to determine whether search should stop
   * when a DELETED entry is found.
   *
   * @return index of array element where search stops, or -1 if search
   * wraps around to start and no unused or not DELETED entry found.
   */
  private int findEntry(final K key, final boolean stopAtDeleted)
  {
    int index = getIndex(key);
    for (int i = 0; i < tableSize; i++)
    {
      if (data[index] == null)
      {
        return index;
      }
      if (stopAtDeleted && (data[index] == DELETED))
      {
        return index;
      }
      if (data[index] != DELETED)
      {
        if (data[index].key.equals(key))
        {
          return index;
        }
      }
      index = (index + 1) % tableSize;
    }
    return -1;
  }
}