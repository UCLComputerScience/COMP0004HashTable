package uk.ac.ucl;

/*
 *  Copyright (c) 2019
 *  Dept. of Computer Science, University College London
 *  @author Graham Roberts
 */


import org.junit.Before;
import uk.ac.ucl.datastructures.ChainedHashTable;

public class ChainedHashTableTest extends HashTableTest
{
  @Before
  public void setUp()
  {
    empty = new ChainedHashTable<String,Integer>() ;
    one = new ChainedHashTable<String,Integer>() ;
    one.put("One",1) ;
    several = new ChainedHashTable<String,Integer>() ;
    several.put("One",1) ;
    several.put("Two",2) ;
    several.put("Three",3) ;
    many = new ChainedHashTable<String,Integer>() ;
    for (int i = 0 ; i < MANYSIZE ; i++)
    {
      many.put(""+i,i) ;
    }
  }
}