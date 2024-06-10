package me.kaimson.melonclient.database;

import com.google.common.collect.*;
import me.kaimson.melonclient.*;
import java.util.*;
import java.sql.*;

public class Database
{
    public static final Database INSTANCE;
    private final String host = "8.tcp.ngrok.io:13391";
    private final String database = "melonclient";
    private final String url = "jdbc:mysql://8.tcp.ngrok.io:13391/melonclient";
    protected final List<Connection> activeConnections;
    
    public Database() {
        this.activeConnections = (List<Connection>)Lists.newArrayList();
    }
    
    public Connection initConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return new Connection(DriverManager.getConnection("jdbc:mysql://8.tcp.ngrok.io:13391/melonclient", "melonclient-use", "ReadOnly:P"));
        }
        catch (ClassNotFoundException | SQLException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
            Client.error("Connection failed!", new Object[0]);
            return null;
        }
    }
    
    public void close() {
        final Iterator<Connection> connectionIterator = this.activeConnections.iterator();
        Client.info("Trying to close {} connections!", this.activeConnections.size());
        while (connectionIterator.hasNext()) {
            final Connection con = connectionIterator.next();
            try {
                con.connection.close();
                connectionIterator.remove();
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
                Client.error("Error closing connection! Skipping...", new Object[0]);
            }
        }
        Client.info("Connections closed! {} remain!", this.activeConnections.size());
    }
    
    static {
        INSTANCE = new Database();
    }
    
    public static class Connection
    {
        public final java.sql.Connection connection;
        
        public Connection(final java.sql.Connection connection) {
            this.connection = connection;
            Database.INSTANCE.activeConnections.add(this);
        }
        
        public PreparedStatement prepareStatement(final String statement) throws SQLException {
            return this.connection.prepareStatement(statement);
        }
    }
}
