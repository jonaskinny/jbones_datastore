package org.jbones.datastore;

import org.jbones.core.*;
import org.jbones.core.dao.*;
import org.jbones.core.util.*;
import java.sql.Connection;
import java.util.*;

/**
	Class for deleting JDBC data transfer objects in the abstract sence.
*/
   public abstract class DDAOJDBC extends DAOJDBC implements DDAO {
   /**
   * Deletes all data based on the long used.
   * Subclasses should implement this function.
   */
	public abstract boolean delete(long arg, Connection  conn) throws DeleteException;
	/**
   * Simple delete function for a persisted object based on the long used.
   */
	public boolean delete(long arg) throws DeleteException {
      Connection conn = null;
      try {
         conn = getConnection();
         return delete(arg, conn);
      } catch (DeleteException deleteE) {
         throw new DeleteException(deleteE.getMessage());
      } catch (Exception e) {
         throw new DeleteException(e.getMessage());
      } finally {
         JDBCUtil.release(conn,null,null,null);
      }
   }
}
