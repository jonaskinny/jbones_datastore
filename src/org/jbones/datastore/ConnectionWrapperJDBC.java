package org.jbones.datastore;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

import org.jbones.core.*;

/**
   * Simple database connection wrapper.
   */
public class ConnectionWrapperJDBC implements Connection, org.jbones.core.Wrapper {

	private Connection conn;
	private boolean available = true;
   /**
   * Marks this Connection Object as unavailable.
   * A connection management system should check this
   * Connection object to see if available()== true before 
   * returning it to the caller.  A connection management system
   * should set this objects availability to false by calling makeUnavailable()
   * on this object prior to returning it to the caller.
   */
	protected void makeUnavailable() throws SQLException {
		setDefaults();
      available = false;
	}
	protected void makeAvailable() throws SQLException {
      setDefaults();
		available = true;
	}
	public boolean available() {
		return available;
	}
	public void setDefaults() throws SQLException {
      conn.setAutoCommit(true); // connpool default
   }
	public void close() throws SQLException {
      this.makeAvailable();
      // do not close the connection as the pool will attempt to re-use this connection
      // and jbones apps will use JDBCUtil to release the connection resources.
   }
	public void clearWarnings() throws SQLException {
		conn.clearWarnings();
	}
   public void commit() throws SQLException {
      conn.commit();
	}
   public Statement createStatement() throws SQLException {
		return conn.createStatement();
	}
   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		return conn.createStatement(resultSetType, resultSetConcurrency);
	}
   public boolean getAutoCommit() throws SQLException {
		return conn.getAutoCommit();
	}
   public String getCatalog() throws SQLException {
		return conn.getCatalog();
	}
	public DatabaseMetaData getMetaData() throws SQLException {
		return conn.getMetaData();
	}
   public int getTransactionIsolation() throws SQLException {
		return conn.getTransactionIsolation();
	}
   public Map getTypeMap() throws SQLException {
		return conn.getTypeMap();
	}
   public SQLWarning getWarnings() throws SQLException {
		return conn.getWarnings();
	}
   public boolean isClosed() throws SQLException {
		return conn.isClosed();
	}
   public boolean isReadOnly() throws SQLException {
		return conn.isReadOnly();
	}
   public String nativeSQL(String sql) throws SQLException {
		return (String)conn.nativeSQL(sql);
	}
	public CallableStatement prepareCall(String sql) throws SQLException {
		return conn.prepareCall(sql);
	}
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return conn.prepareCall(sql,resultSetType,resultSetConcurrency);
	}
   public PreparedStatement prepareStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		return conn.prepareStatement(sql,resultSetType,resultSetConcurrency);
	}
   public void rollback() throws SQLException {
		conn.rollback();
	}
   public void setAutoCommit(boolean autoCommit) throws SQLException {
		conn.setAutoCommit(autoCommit);
	}
   public void setCatalog(String catalog) throws SQLException {
		conn.setCatalog(catalog);
	}
   public void setReadOnly(boolean readOnly) throws SQLException {
		conn.setReadOnly(readOnly);
	}
   public void setTransactionIsolation(int level) throws SQLException {
		conn.setTransactionIsolation(level);
	}
   public void setTypeMap(Map map) throws SQLException {
		conn.setTypeMap(map);
   }
   public Struct createStruct(String typeName, Object[] attributes) throws SQLException, SQLFeatureNotSupportedException {
      return conn.createStruct(typeName,attributes);
   }
   public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
      return conn.createArrayOf(typeName, elements);
   }
   public Properties getClientInfo() throws SQLException {
      return conn.getClientInfo();
   }
   public void setClientInfo(Properties p) throws SQLClientInfoException {
      conn.setClientInfo(p);
   }
   public void setClientInfo(String name, String value) throws SQLClientInfoException {
      conn.setClientInfo(name,value);
   }
   public String getClientInfo(String name) throws SQLException {
      return conn.getClientInfo(name);
   }
   public boolean isValid(int timeout) throws SQLException {
      return conn.isValid(timeout);
   }
   public SQLXML createSQLXML() throws SQLException {
      return conn.createSQLXML();
   }
   public NClob createNClob() throws SQLException {
      return conn.createNClob();
   }
   public Blob createBlob() throws SQLException {
      return conn.createBlob();
   }
   public Clob createClob() throws SQLException {
      return conn.createClob();
   }
   public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
      return conn.prepareStatement(sql,columnNames);
   }
   public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
      return conn.prepareStatement(sql,columnIndexes);
   }
   public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
      return conn.prepareStatement(sql,autoGeneratedKeys);
   }
   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      return conn.prepareCall(sql,resultSetType,resultSetConcurrency,resultSetHoldability);
   }
   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      return conn.prepareStatement(sql,resultSetType,resultSetConcurrency,resultSetHoldability);
   }
   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      return conn.createStatement(resultSetType,resultSetConcurrency,resultSetHoldability);
   }
   public void releaseSavepoint(Savepoint savepoint) throws SQLException {
      conn.releaseSavepoint(savepoint);
   }
   public void rollback(Savepoint savepoint) throws SQLException {
      conn.rollback(savepoint);
   }
   public Savepoint setSavepoint(String name) throws SQLException {
      return conn.setSavepoint(name);
   }
   public Savepoint setSavepoint() throws SQLException {
      return conn.setSavepoint();
   }
   public int getHoldability() throws SQLException {
      return conn.getHoldability();
   }
   public void setHoldability(int holdability) throws SQLException {
      conn.setHoldability(holdability);
   }
   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return conn.isWrapperFor(iface);
   }
   public <T> T unwrap(Class<T> iface) throws SQLException {
      return conn.unwrap(iface);
   }
   public ConnectionWrapperJDBC(Connection c){
		conn = c;
	}
	public int getNetworkTimeout() throws SQLException {
      return conn.getNetworkTimeout();
   }
   public void setNetworkTimeout(Executor executor,int networkTimeout) throws SQLException {
      conn.setNetworkTimeout(executor,networkTimeout);
   }
   public void abort(Executor executor) throws SQLException {
      conn.abort(executor);
   }
   public String getSchema() throws SQLException {
      return conn.getSchema();
   }
   public void setSchema(String schema) throws SQLException {
		conn.setCatalog(schema);
	}

}
