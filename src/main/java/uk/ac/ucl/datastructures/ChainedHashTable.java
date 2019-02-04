package uk.ac.ucl.datastructures;/*
 *  A simple generic chained hash table class implementation.
 *  This should not be taken as a production quality class.
 *  Copyright (c) 2007
 *  Dept. of Computer Science, University College London
 *  @author Graham Roberts
 *  @version 2.2 2007-03-01
 */

import java.lang.reflect.Array;

public class ChainedHashTable<K,V> implements HashTable<K,V>
{
  /**
   * Helper class used to implement chains. As this is a private helper
   * class it is acceptable to have public instance variables. Instances of this
   * class are never made available to client code of the hash table.
   */
  private static class Entry<S,T>
  {
    /**
     *  Next entry in chain.
     */
    public Entry<S,T> next;
    /**
     *  Key object.
     */
    public S key;
    /**
     *  Value object.
     */
    public T value;

    /**
     *  Constructor for the Entry object
     *
     *@param  next  next entry in chain reference or null
     *@param  key   key object reference
     *@param  value   value object reference
     */
    public Entry(final Entry<S,T> next, final S key, final T value)
    {
      this.next = next;
      this.key = key;
      this.value = value;
    }
  }

  /**
   * Array of entry references used to store the chains.
   * By default all elements are initialised to null.
   */
  private Entry<K,V>[] data;

  /**
   * Default size of array and, hence, number of chains allowed.
   */
  private static final int DEFAULT_SIZE = 100;

  /**
   *  Create a hash table with the given table size.
   *  Note that arrays of a generic type like Entry<K,V> cannot be created directly,
   *  so using new Entry<K,V>[size] is not valid. Instead, the newInstance method of the
   *  Java library class Array is used to create an array of type Entry with the given
   *  size. To fully understand why find out more about generics, arrays and type
   *  erasure.
   *
   *  @param size the size of the array used to store chains.
   */
  @SuppressWarnings("unchecked")
  public ChainedHashTable(final int size)
  {
    data = (Entry<K,V>[]) Array.newInstance(Entry.class,size);
  }

  /**
   *  Create a hash table with the default array size.
   */
  public ChainedHashTable()
  {
    this(DEFAULT_SIZE);
  }

  /**
   *  Put a key/value pair into the table by hashing the key to get the array
   *  index of the chain that should hold the entry containing the pair.
   *  If an existing entry with the same key is present then overwrite its value,
   *  otherwise add a new entry.
   *
   *@param  key  key object reference
   *@param  val  data object reference
   */
  public void put(final K key, final V val)
  {
    // Use helper method to determine if entry with same key is already in
    // chain. If not, add a new entry to head of chain.
    int index = getIndex(key);
    Entry<K,V> node = find(key, data[index]);

    if (node == null)
    {
      data[index] = new Entry<K,V>(data[index], key, val);
    }
    else
    {
      // Otherwise update data value of existing entry.
      node.value = val;
    }
  }

  /**
   *  Given a key object, return a value object from the table or
   *  null if nothing is found.
   *
   *@param  key  key object reference
   *@return      value object reference or null if no object found
   */
  public V get(final K key)
  {
    Entry<K,V> temp = find(key, data[getIndex(key)]);
    if (temp != null)
    {
      return temp.value;
    }
    else
    {
      return null;
    }
  }

  /**
   * Remove a key/value pair from table if present, otherwise make no change.
   *
   *@param  key  key object reference
   */
  public void remove(final K key)
  {
    int index = getIndex(key);
    Entry<K,V> ref = data[index];
    Entry<K,V> previous = null;
    while (ref != null)
    {
      if ((ref.key).equals(key))
      {
        if (previous == null)
        {
          data[index] = ref.next;
        }
        else
        {
          previous.next = ref.next;
        }
        return;
      }
      previous = ref;
      ref = ref.next;
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
    for (Entry<K,V> node : data)
    {
      while (node != null)
      {
        keys.insertAtHead(node.key);
        node = node.next;
      }
    }
    return keys;
  }

  /**
   *  Given a key, use a hash function to obtain the array index of the chain
   *  corresponding to the key.
   *  The hashCode method is inherited from class Object and should be overridden by
   *  subclasses to provide a good hash function for the subclasses used as key values.
   *  See the Java class library documentation for more information about the
   *  hashcode method. The distribution of objects in the hash table will depend
   *  on the quality of the hash function implemented by hashCode.
   *  The value returned by this method must be a valid index in the hash table array.
   *
   *@param  key  reference to object to be hashed
   *@return      array index of chain corresponding to key
   */
  private int getIndex(final K key)
  {
    return Math.abs(key.hashCode() % data.length);
  }

  /**
   *  Find node given a key and a chain to search.
   *
   *@param  key    key object reference
   *@param  ref    entry object reference where search will start
   *@return        entry object holding key or null if not found
   */
  private Entry<K,V> find(final K key, Entry<K,V> ref)
  {
    while (ref != null)
    {
      if ((ref.key).equals(key))
      {
        return ref;
      }
      ref = ref.next;
    }
    return null;
  }
}
