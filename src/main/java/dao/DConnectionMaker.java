package dao;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DConnectionMaker implements ConnectionMaker {
    @Override
    public DataSource makeConnection() throws ClassNotFoundException, SQLException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource ();

        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost/test?characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("woals4815");

        return dataSource;
    }
}
