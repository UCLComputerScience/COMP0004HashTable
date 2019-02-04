package uk.ac.ucl;

import org.junit.Before;
import uk.ac.ucl.datastructures.LinearHashTable;

/**
 * Copyright (c) 2019
 * Dept. of Computer Science, University College London
 * @author Graham Roberts
 */


public class LinearHashTableTest extends HashTableTest
{
  @Before
  public void setUp()
  {
    empty = new LinearHashTable<String,Integer>() ;
    one = new LinearHashTable<String,Integer>() ;
    one.put("One",1) ;
    several = new LinearHashTable<String,Integer>() ;
    several.put("One",1) ;
    several.put("Two",2) ;
    several.put("Three",3) ;
    many = new LinearHashTable<String,Integer>(MANYSIZE) ;
    for (int i = 0 ; i < MANYSIZE ; i++)
    {
      many.put(""+i,i) ;
    }
  }
}