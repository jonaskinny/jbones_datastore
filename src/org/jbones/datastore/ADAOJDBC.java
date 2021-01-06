package org.jbones.datastore;

import org.jbones.core.*;
import org.jbones.core.dao.*;
import org.jbones.core.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
	Class for archiving and unarchiving JDBC data transfer objects in the abstract sence.
*/
public abstract class ADAOJDBC extends DAOJDBC implements ADAO {
   private String archiveSequenceName = getArchiveEntityName();
   private Sequence archiveSequence;

   /**
    subclass should provide the return value for its class
    to construct the name
    */
   protected abstract String getArchiveEntityName();

   public void setArchiveSequence(Sequence archiveSequence) throws CoreException {
          this.archiveSequence = archiveSequence;
   }

	public long getArchiveSequenceNextVal() throws SequenceException, ConnectionException {
          if (null != archiveSequence) {
             return archiveSequence.nextVal(archiveSequenceName,getConnection());
          } else {
            throw new SequenceException("sequence is null");
          }

   }
   /**
   * Simple list function for a List of all persisted objects.
   * Subclasses should implement this function.
   */
	public List listArchive() throws ListException {
      Connection conn = null;
      try {
         conn = getConnection();
         return listArchive(conn);
         // improve exception throwing here
      } catch (CoreException ce) {
         throw new ListException(ce.getMessage());
      } finally {
         JDBCUtil.release(conn,null,null,null);
      }
   }
   /**
   * Simple read function for a persisted object based on the long used.
   */
	public DTO readArchive(long arg) throws ReadException {
      Connection conn = null;
      try {
         conn = getConnection();
         return readArchive(arg, conn);
      } catch (ReadException readE) {
         throw new ReadException(readE.getMessage());
      } catch (Exception e) {
         throw new ReadException(e.getMessage());
      } finally {
         JDBCUtil.release(conn,null,null,null);
      }
   }
   public abstract List listArchive(Connection conn) throws ListException;
   protected abstract DTO loadEntityArchive(ADTO Entity, Connection conn, ResultSet rs) throws ReadException;
   public abstract DTO readArchive(long arg, Connection conn) throws ReadException;
}
