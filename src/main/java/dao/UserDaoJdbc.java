package dao;

import domain.User;
import exception.DuplicateUserIdException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private static final Log log = LogFactory.getLog(UserDaoJdbc.class);
    private JdbcContext jdbcContext;
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
        }
    };

    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(User user) throws DuplicateUserIdException {
            jdbcTemplate.update("insert into users values(?, ?, ?)", user.getId(), user.getName(), user.getPassword());
    }



    public User get(String id)  {
        return jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, this.rowMapper);
    }
    public void deleteAll()  {
        this.jdbcTemplate.update(
                "delete from users"
        );

    }
    public List<User> getAll()  {
        return jdbcTemplate.query("select * from users;", this.rowMapper);
    }
    public int getCount() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }
}
