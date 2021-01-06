package org.jbones.datastore;

import org.jbones.core.*;

/** 
	Class that represents an exception occured during a process on an object
  that implements some interface that contains a method of this exception's name.
*/
public class ConnectionException extends CoreException {
static long serialVersionUID=-7652414822624381208L;
   public ConnectionException(String s) {
      super(s);
   }
   
   public ConnectionException(Exception e) {
      super(getStackTrace(e));
   }
}
