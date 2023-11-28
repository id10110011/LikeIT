package pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BasicConnectionPool implements ConnectionPool {
    private final List<Connection> pool = new ArrayList<>();
    private final List<Connection> usedConnections = new ArrayList<>();
    private static class SingletonHandler {
        private static final BasicConnectionPool instance = new BasicConnectionPool();
    }

    public static BasicConnectionPool getInstance() {
        return SingletonHandler.instance;
    }

    private BasicConnectionPool() {
        ResourceBundle bundle = ResourceBundle.getBundle("database");
        final String url = bundle.getString("db.url");
        final String user = bundle.getString("db.user");
        final String password = bundle.getString("db.password");
        try {
            create(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void create(
            String url, String user,
            String password) throws SQLException, ClassNotFoundException {
        final int INITIAL_POOL_SIZE = 5;
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
    }

    @Override
    public Connection getConnection() {
        Connection connection = pool
                .remove(pool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        pool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(
            String url, String user, String password)
            throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}
