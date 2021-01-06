package org.jbones.datastore;

import org.jbones.core.*;
import org.jbones.core.util.*;

import java.util.Properties;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


/** 
	Class that represents an sql sequence object.
*/
public class SequenceJDBC extends Sequence {
   private String schemaName;
   public String getSchemaName() {
      return schemaName;
   }
   public void initialize(Properties p) throws CoreException {
         try {
            schemaName = p.getProperty("connectionpool.schemaname");

         } catch (Exception e) {
            Log.getLog(Log.ERR).log("problem loading SequenceJDBC:"+schemaName);
            Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
            throw new CoreException(CoreException.getStackTrace(e));
         }
      }
   /**
	* this function returns the next value in the sequence.
	* @param objectName the object that is associated with the 
   * sequence object.
   */
   public long nextVal(String objectName, Connection connection) throws SequenceException {

      PreparedStatement psSelect = null;
      PreparedStatement psUpdate = null;
      ResultSet rst = null;
      long value = -1;
      boolean autoCommit = false;
		
      try {
         if (connection.getAutoCommit()){
				connection.setAutoCommit(false); // dont reset to true ... let the conn manager/pool handle defaults
			}
         psUpdate = connection.prepareStatement("update " + schemaName + "." + objectName + "_SEQUENCE set NEXT_VALUE=NEXT_VALUE + 1");
         if (psUpdate.executeUpdate() < 1) {
            try {
					connection.rollback();
				} catch (Exception e) {
					Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
					throw new SequenceException(e);
            }
         }
         psSelect = connection.prepareStatement("select NEXT_VALUE from " + schemaName + "." + objectName + "_SEQUENCE");
         rst = psSelect.executeQuery();
         if (rst.next() == false){
            try {
					connection.rollback();
				} catch (Exception e) {
					Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
					throw new SequenceException(e);
            }
         } else {
            value = rst.getLong("NEXT_VALUE");
            connection.commit();
   		}
         return value;
		} catch (Exception e) {
         Log.getLog(Log.ERR).log(CoreException.getStackTrace(e));
         try {
			   connection.rollback();
			} catch (Exception e2) {
            Log.getLog(Log.ERR).log(CoreException.getStackTrace(e2));
				throw new SequenceException(e2);
			}
         throw new SequenceException(e);
		   } finally {
            JDBCUtil.release(connection,rst,null,psUpdate);
            JDBCUtil.release(psUpdate);
         }
   }
}
