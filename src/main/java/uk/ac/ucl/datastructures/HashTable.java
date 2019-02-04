package uk.ac.ucl.datastructures;

/**
 *  General interface for generic hash tables.
 *  Copyright (c) 2019
 *  Dept. of Computer Science, University College London
 *  @author Graham Roberts
1 */

public interface HashTable<K,V>
{

  /**
   *  Put a key/value pair into the table.
   *  If an existing entry with the same key is present then its value is
   *  overwritten.
   *
   * @param  key  key object reference
   * @param  value  value object reference
   */
  void put(final K key, final V value);

  /**
   *  Given a key object, return the corresponding a value object from the table
   *  or null if no entry is found.
   *
   * @param  key  key object reference
   * @return      value object reference or null if no object found
   */
  V get(final K key);

  /**
   * Remove a key/value pair from the table if present, otherwise make no change.
   *
   * @param  key  key value reference
   */
  void remove(final K key);

  /**
   * Return the current list of key values for entries in the table.
   *
   * @return List of key values
   */
  SimpleList<K> keys();
}
