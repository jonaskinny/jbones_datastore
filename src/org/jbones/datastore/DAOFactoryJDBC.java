package org.jbones.datastore;

import org.jbones.core.*;
import org.jbones.core.dao.*;
import org.jbones.core.util.*;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

public class DAOFactoryJDBC extends DAOFactory {
   private ConnectionPool connectionPool = null;
   private Sequence sequence = null;
   protected String schemaName;
   public void initialize() throws CoreException {
      Object object = null;
      Object name = null;
      Properties p = PropertiesUtil.loadPropertiesFileFromClassPath("/org/jbones/core/dao.properties");
      schemaName = p.getProperty("connectionpool.schemaname");
      try {
               connectionPool = (ConnectionPool)Class.forName(p.getProperty("connectionpool.class_imp")).newInstance();
               connectionPool.initialize(p);
      } catch (Exception e) {
               setUnsafeToUse();
               Log.getLog(Log.ERR).log("problem loading connection pool");
               Log.getLog(Log.ERR).log(e.getMessage());
               Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
               throw new RuntimeException("FATAL EXCEPTION IN CORE loading connection pool : " + CoreException.getStackTrace(e));
      }
      try {
               sequence = (Sequence)Class.forName(p.getProperty("sequence.class_imp")).newInstance();
               sequence.initialize(p);
      } catch (Exception e2) {
               setUnsafeToUse();
               Log.getLog(Log.ERR).log("problem loading sequence");
               Log.getLog(Log.ERR).log(e2.getMessage());
               Log.getLog(Log.ERR).log(CoreException.getStackTrace(e2));
               throw new RuntimeException("FATAL EXCEPTION IN CORE loading sequence : " + CoreException.getStackTrace(e2));
      }
      Enumeration keys = objects.keys();
      if (null != keys) {
        while (keys.hasMoreElements()) {
           name = keys.nextElement();
           object = objects.get(name);
           if (object instanceof DAOJDBC) {
              try {
                  Log.getLog(Log.OUT).log("initializing dao:" + name);
                  ((DAOJDBC)object).setConnectionPool(connectionPool);
                  ((DAOJDBC)object).setSequence(sequence);
                  ((DAOJDBC)object).setSchemaName(schemaName);
                  if (object instanceof ADAOJDBC) {
                     ((ADAOJDBC)object).setArchiveSequence(sequence);
                  }
              } catch(Exception e2) {
                setUnsafeToUse();
                Log.getLog(Log.ERR).log("problem initializing dao:" + name);
                throw new RuntimeException(CoreException.getStackTrace(e2));
              }
           }
        }
     }
     setSafeToUse();
  }
  public DAO getDAO(String DAOName) throws CoreException {
     return (DAO)objects.get(DAOName);
  }
}
