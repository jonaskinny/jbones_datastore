package org.jbones.datastore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;

import org.jbones.core.*;
/**
	Basic JDBC Connection Pool ... intended for testing only ... not production-ready code.
*/
public class ConnectionPoolJDBC extends ConnectionPool {
   private String schemaName;
   private String drivername;
   private String datastore;
   private String url;
   private String username;
   private String password;
   private String conncount;;
   private String conntype;
   public void initialize(Properties p) throws CoreException {
         try {
            schemaName = p.getProperty("connectionpool.schemaname");
            datastore = p.getProperty("connectionpool.datastore");
            drivername = p.getProperty(datastore + ".database_driver_class");
            url = p.getProperty(datastore + ".database_url");
            conntype = p.getProperty("connectionpool.connection_type");
            conncount = p.getProperty("connectionpool.connection_count");
            username = p.getProperty("connectionpool.username");
            password = p.getProperty("connectionpool.password");
            int count = Integer.parseInt(conncount);
            
            conns = new Vector(count);
            
            Class.forName(drivername);
            for ( int x = 0; x < count; x ++ ) {
               conns.add(x,new ConnectionWrapperJDBC(
               DriverManager.getConnection(url,username, password)));
            }
            index = 0;
         } catch (Exception e) {
            Log.getLog(Log.ERR).log("problem loading ConnectionPoolJDBC:"+datastore+":"+schemaName);
            Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
            throw new CoreException(CoreException.getStackTrace(e));
         }
      }
  
      public Connection getConnection() {
	    synchronized (syncObject) {
         ConnectionWrapperJDBC conn;
         try {
            do {
               conn = (ConnectionWrapperJDBC)conns.elementAt(index);
               index ++;
               if (index >= conns.size()) {
                  index = 0;
               }
               // dont execute isValid() incase this conn is unavailable.
               // we dont want to execute a query on a busy conn.
               if (null != conn && ! conn.available() && ! conn.isClosed()) {
                  // busy conn ... skip to next one
                  Thread.sleep(10);
               } else if (null != conn && conn.available() && ! conn.isClosed() && conn.isValid(0)) {
                  // available valid conn
                  conn.makeUnavailable();
                  return conn;
               } else if (null == conn || conn.isClosed() || ! conn.isValid(0)) {
                  // bad conn
                  if (null == conn) {
                     Log.getLog(Log.OUT).log("in ConnectionPoolJDBC connection is null.");
                  } else if (conn.isClosed()) {
                     Log.getLog(Log.OUT).log("in ConnectionPoolJDBC connection is closed but should not be.");
                  } else if (! conn.isValid(0)) {
                     Log.getLog(Log.OUT).log("in ConnectionPoolJDBC connection is invalid.");
                  }
                  Log.getLog(Log.OUT).log("in ConnectionPoolJDBC attempting to replace the connection.");
                  conns.remove(index);
                  conn = new ConnectionWrapperJDBC(DriverManager.getConnection(url,username, password));
                  conns.add(index,conn);
		            Thread.sleep(10);
		         }
	       } while (true);  // need to configure a timeout here or the application could hang forever
         } catch(Exception e) {
	        Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
	        index = 0;
	        throw new RuntimeException(CoreException.getStackTrace(e)); // if we ever remove runtime may want to nullify the erroring conn
	        }
	     }
      }
}

