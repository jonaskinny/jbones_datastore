package org.jbones.datastore;

import org.jbones.core.dao.Criteria;

import java.io.Serializable;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
   Class that represents a Criteria object.
*/
public final class JDBCCriteria extends Criteria implements Serializable, Cloneable {
   // jcole remove tried to make map final but cant when statically initializing
   private static Map<String, String> criterionMap = null;
   static {
	  criterionMap = loadMap();
   }
   private static Map loadMap() {
      criterionMap = new HashMap(13);
      /*
      public static final String EQUAL = "=";
      public static final String GREATER = ">";
      public static final String GREATER_EQUAL = ">=";
      public static final String LESS = "<";
      public static final String LESS_EQUAL = "<=";
      public static final String NOT_EQUAL = "<>";
      public static final String IN = "in";
      public static final String NOT_IN = "not in";
      public static final String BETWEEN = "between";
      public static final String LIKE = "like";
      public static final String NOT_LIKE = "not like";
      public static final String ORDER_BY = "order by";
      public static final String NULL = "null";
      public static final String ASC = "ASC";
      public static final String DSC = "DSC";
      */
      return criterionMap;
   }
   
   /**
      returns the value of the key as a string.
   */
   public List getCriterion() {
      return null;
   }
   
   /**
      returns the value of the key as a string.
   */
   public List getCriterionString() {
      return null;
   }

   /**
      returns true if the string value of this object's key value
      is equal to the string value of the parameter's key value.
      functions as string.equals(string2).
   */
   public boolean equals(Object o){
      return false;
   }

   /**
      will return the same hashcode value for any Key or Key subclass
      with same key value;  this class and its subclasses are safe to be
      used as key values in collections that operate on hashcode uniqueness.
   */
   public int hashCode(){
      return -1;
   }

   //inner class for individual criterion to be used as 'line items'
   protected class Criterion {
      protected String attribute;
      protected String comparitor;
      protected String value;
      
      public Criterion(String attribute,String comparitor,String value) {
             Criterion c = new Criterion();
             c.attribute = attribute;
             c.comparitor = comparitor;
             c.value = value;
      }
      private Criterion () {

      }
   }
   private JDBCCriteria () {
      
   }
}
