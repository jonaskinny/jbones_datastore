package org.jbones.datastore;

import org.jbones.core.*;
import org.jbones.core.dao.*;
import org.jbones.core.util.*;
import java.sql.Connection;
import java.util.*;

/**
	Class for deleting JDBC data transfer objects in the abstract sence.
*/
   public abstract class BridgeDAOJDBC extends DDAOJDBC implements BridgeDAO {

   public abstract List listChildren(Connection conn, long arg) throws ListException;
   public abstract List listParents(Connection conn, long arg) throws ListException;

   public List listChildren(long arg) throws ListException {
      Connection conn = null;
      try {
         conn = getConnection();
         return listChildren(conn, arg);
         // improve exception throwing here
      } catch (CoreException ce) {
         throw new ListException(ce.getMessage());
      } finally {
         JDBCUtil.release(conn,null,null,null);
      }
   }
   public List listParents(long arg) throws ListException {
      Connection conn = null;
      try {
         conn = getConnection();
         return listParents(conn, arg);
         // improve exception throwing here
      } catch (CoreException ce) {
         throw new ListException(ce.getMessage());
      } finally {
         JDBCUtil.release(conn,null,null,null);
      }
   }

}
