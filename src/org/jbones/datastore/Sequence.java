package org.jbones.datastore;

import java.sql.Connection;
import java.util.Properties;

import org.jbones.core.*;
 
/** 
	Class that represents a sequence object in the abstract sense.
*/
public abstract class Sequence {
   public abstract void initialize(Properties p) throws CoreException;
   
   public abstract long nextVal(String objectName, Connection connection) throws SequenceException;

}
