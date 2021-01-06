package org.jbones.datastore;

import org.jbones.core.*;
import org.jbones.core.dao.*;
import org.jbones.core.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
	Class for accessing JDBC datasources to populate data transfer objects with data in the abstract sense.
*/
   public abstract class DAOJDBC implements DAO {
   private String sequenceName = getEntityName();
   private ConnectionPool connectionpool;
   private Sequence sequence;
   //private Audit audit;
   protected String schemaName;

   /**
    subclass should provide the return value for its class
    to construct the name
    */
   protected abstract String getEntityName();
	
   public void setConnectionPool(ConnectionPool connectionPool) throws CoreException {
          this.connectionpool = connectionPool;
   }
   /*
   public void setAudit(Audit audit) throws CoreException {
          this.audit = audit;
   }
   */
	public Connection getConnection() throws ConnectionException {
          if (null != connectionpool) {
             return connectionpool.getConnection();
          } else {
            throw new ConnectionException("connectionpool is null");
          }

   }
   public void setSequence(Sequence sequence) throws CoreException {
          this.sequence = sequence;
   }

	public long getSequenceNextVal() throws SequenceException, ConnectionException {
          if (null != sequence) {
             return sequence.nextVal(sequenceName,getConnection());
          }
          throw new SequenceException("sequence is null");
   }
   public void setSchemaName(String schemaName) {
      if (null != schemaName) {
         this.schemaName = schemaName.toUpperCase();
      }
   }
   public String getSchemaName() {
      return this.schemaName;
   }
   /**
   * Simple create of all properties for this persisted object.
   * Any properties not set prior to a call to this method will
   * result in the default values being used.  Typically this means
   * null for Strings and objects, -1 for BIGINTs etc.
   * Subclasses should implement this function.
   */
	public abstract boolean create(DTO arg) throws CreateException, UniqueException;
   /**
   * Simple read function for a persisted object based on the long used.
   */
	public DTO read(long arg) throws ReadException {
      Connection conn = null;
      try {
         conn = getConnection();
         return read(arg, conn);
         // improve exception throwing here
      } catch (Exception e) {
         throw new ReadException(e.getMessage());
      } finally {
         JDBCUtil.release(conn,null,null,null);
      }
   }
   /**
   * Simple read function for a persisted object based on the long used.
   */
	public DTO readList(long arg) throws ReadException {
      Connection conn = null;
      try {
         conn = getConnection();
         return readList(arg, conn);
         // improve exception throwing here
      } catch (Exception e) {
         throw new ReadException(e.getMessage());
      } finally {
         JDBCUtil.release(conn,null,null,null);
      }
   }
   /**
   * Simple read function for a persisted object based on the long used.
   * Subclasses should implement this function.
   */
	public abstract DTO read(long arg, Connection connection) throws ReadException;
	/**
   * Simple read function for a persisted object based on the long used.
   * Subclasses should implement this function.
   */
	public abstract DTO readList(long arg, Connection connection) throws ReadException;
	/**
   * Simple find function for a List of persisted objects based on the Criteria used.
   * Subclasses should implement this function.
   */
	public abstract List find(Criteria arg) throws FindException;
	/**
   * Simple list function for a List of all persisted objects.
   * Subclasses should implement this function.
   */
	public List list() throws ListException {
      Connection conn = null;
      try {
         conn = getConnection();
         return list(conn);
         // improve exception throwing here
      } catch (CoreException ce) {
         throw new ListException(ce.getMessage());
      } finally {
         JDBCUtil.release(conn,null,null,null);
      }
   }
   public abstract List list(Connection conn) throws ListException;
   /**
   * Simple update of all properties for this persisted object.
   * Any properties not set prior to a call to this method will
   * result in the default values being used.  Typically this means
   * null for Strings and objects, -1 for BIGINTs etc.
   * Subclasses should implement this function.
   */
	public abstract boolean update(DTO arg) throws UpdateException, UniqueException;
	/**
   * Simple create statement for data based on the Criteria used.
   * Any properties not set prior to a call to this method will
   * result in the default values being used.  Typically this means
   * null for Strings and objects, -1 for BIGINTs etc.
   * Subclasses should implement this function.
   */
	public abstract Criteria createCriteria(DTO arg) throws CreateException;
	/**
   * Creates a Criteria Object to be used with this object.
   */
	public abstract Criteria createCriteria() throws CreateException;
	public DAOJDBC getDAO(String arg) throws ServiceLocatorException {
      return (DAOJDBC)ServiceLocator.getDAO(arg);
   }
   protected abstract DTO loadEntity(DTO Entity, Connection conn, ResultSet rs) throws ReadException;
}
