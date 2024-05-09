package roomescape.dao;

import java.util.Optional;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.LoginMember;

@Repository
public class LoginMemberJDBCRepository implements LoginMemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public LoginMemberJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<LoginMember> rowMapper = (resultSet, rowNum) -> new LoginMember(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getString("email"),
            resultSet.getString("password")
    );

    @Override
    public Optional<LoginMember> findByEmail(String email) {
        String sql = "SELECT id, name, email, password FROM login_member WHERE email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, email));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
