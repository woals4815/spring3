package dao;

import domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    private static final Log log = LogFactory.getLog(UserDao.class);
    private DataSource dataSource;
    private JdbcContext jdbcContext;
    private JdbcTemplate jdbcTemplate;

    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        jdbcTemplate.update("insert into users values(?, ?, ?)", user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) throws SQLException {
        Connection c = this.dataSource.getConnection();
        PreparedStatement ps = c
                .prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = null;

        if (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        rs.close();
        ps.close();
        c.close();

        return user;
    }
    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update(
                "delete from users"
        );

    }
    public List<User> getAll() throws SQLException {
        return jdbcTemplate.query("select * from users;", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
            }
        });
    }
    public int getCount() throws SQLException {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
//        return this.jdbcTemplate.query(
//                new PreparedStatementCreator() {
//                    @Override
//                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                        return con.prepareStatement("select count(*) from users");
//                    }
//                },
//                new ResultSetExtractor<Integer>() {
//                    @Override
//                    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
//                        rs.next();
//                        return rs.getInt(1);
//                    }
//                }
//        );
    }
    private PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps;
        ps = c.prepareStatement("delete from users");
        return ps;
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        }catch (SQLException e) {
            throw e;
        } finally {

        if (ps != null) {
        ps.close();}
        }
        if (c != null) {
            c.close();
        }
    }
}
