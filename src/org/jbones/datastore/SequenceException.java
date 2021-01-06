package org.jbones.datastore;

import org.jbones.core.*;

/** 
	Class that represents an exception occured during a process on an object
  that implements some interface that contains a method of this exception's name.
*/
public class SequenceException extends CoreException {
static long serialVersionUID=0L;
   public SequenceException(String s) {
      super(s);
   }
   
   public SequenceException(Exception e) {
      super(getStackTrace(e));
   }
}
