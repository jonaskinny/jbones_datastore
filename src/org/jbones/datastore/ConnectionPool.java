package org.jbones.datastore;

import java.util.*;
import java.sql.Connection;
import org.jbones.core.*;

   /**
   * Simple connection object pool.  Constructor loads collection of 
   * connections.  Subclasses of this class can implement dynamic pool sizing based
   * on application traffic.  
   */
public abstract class ConnectionPool {
   protected Vector conns;
   protected Object syncObject = new Object();
   protected int index;
   
   public abstract void initialize(Properties p) throws CoreException;
   
   public abstract Connection getConnection();
}

