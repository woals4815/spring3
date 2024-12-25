package dao;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface ConnectionMaker {
    public abstract DataSource makeConnection() throws ClassNotFoundException,
            SQLException;
}
