package uk.ac.ucl;

/*
 *  HashTable unit Tests
 *  Copyright (c) 2019
 *  Dept. of Computer Science, University College London
 *  @author Graham Roberts
s */

import org.junit.Test;
import uk.ac.ucl.datastructures.HashTable;
import uk.ac.ucl.datastructures.SimpleList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class HashTableTest
{
  // These variables should be initialised by a set up method in a subclass.
  protected HashTable<String,Integer> empty ;
  protected HashTable<String,Integer> one ;
  protected HashTable<String,Integer> several ;
  protected HashTable<String,Integer> many ;

  protected static final int MANYSIZE = 500 ;

  @Test
  public void testGet()
  {
    assertEquals(new Integer(1),one.get("One")) ;
    assertEquals(new Integer(1),several.get("One")) ;
    assertEquals(new Integer(2),several.get("Two")) ;
    assertEquals(new Integer(3),several.get("Three")) ;
  }

  @Test
  public void testGetMany()
  {
    for (int i = 0 ; i < MANYSIZE ; i++)
    {
      assertEquals(new Integer(i),many.get(""+i)) ;
    }
  }

  @Test
  public void testGetNotInTable()
  {
    assertNull(empty.get("One")) ;
    assertNull(one.get("Two")) ;
    assertNull(several.get("three")) ;
    assertNull(one.get(""));
    assertNull(one.get(" "));
  }

  @Test
  public void testChangeExistingEntry()
  {
    one.put("One",10) ;
    assertEquals(new Integer(10),one.get("One")) ;
    several.put("One",10) ;
    assertEquals(new Integer(10),several.get("One")) ;
    several.put("Two",20) ;
    assertEquals(new Integer(20),several.get("Two")) ;
    several.put("Three",30) ;
    assertEquals(new Integer(30),several.get("Three")) ;
    for (int i = 0 ; i < MANYSIZE ; i++)
    {
      many.put(""+i,i+1) ;
      assertEquals(new Integer(i+1),many.get(""+i)) ;
    }
  }

  @Test
  public void testRemove()
  {
    empty.remove("One") ;
    assertNull(empty.get("One")) ;
    one.remove("One") ;
    assertNull(one.get("One")) ;
    several.remove("One") ;
    assertNull(several.get("One")) ;
    several.remove("Four") ;
    assertNull(several.get("Four")) ;
    assertEquals(new Integer(2),several.get("Two")) ;
    assertEquals(new Integer(3),several.get("Three")) ;
    several.remove("Two") ;
    assertNull(several.get("Two")) ;
    several.remove("Three") ;
    assertNull(several.get("Three")) ;
  }

  @Test
  public void testRemoveMany()
  {
    for (int i = 0 ; i < MANYSIZE ; i++)
    {
      many.remove(""+i) ;
      assertNull(many.get(""+i)) ;
    }
  }

  @Test
  public void testGetKeys()
  {
     assertTrue(empty.keys().isEmpty());
     checkKeys(one.keys(),new String[]{"One"});
     checkKeys(several.keys(),new String[]{"One","Two","Three"});
     String[] manyKeys = new String[MANYSIZE];
     for(int i = 0 ; i < MANYSIZE ; i++)
     {
       manyKeys[i] = "" + i;
     }
     checkKeys(many.keys(),manyKeys);
  }

  @Test
  public void testRemoveAndAdd()
  {
    for (int i = 0 ; i < MANYSIZE ; i += 2)
    {
      many.remove(""+i) ;
    }
    for (int i = 0 ; i < MANYSIZE ; i += 2)
    {
      many.put(""+i,i) ;
    }
  }

  private void checkKeys(SimpleList<String> actualKeys,
                         String[] expected)
  {
    String[] actual = simpleListToArray(actualKeys);
    Arrays.sort(actual);
    Arrays.sort(expected);
    assertTrue("Keys not correct", Arrays.equals(actual,expected));
  }

  private String[] simpleListToArray(SimpleList<String> actualKeys)
  {
    List<String> temp = new ArrayList<String>();
    for(String s : actualKeys)
    {
      temp.add(s);
    }
    return temp.toArray(new String[1]);
  } 
}