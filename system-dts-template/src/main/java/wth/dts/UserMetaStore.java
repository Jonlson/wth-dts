package wth.dts;

import com.aliyun.dts.subscribe.clients.metastore.AbstractUserMetaStore;
import com.aliyun.dts.subscribe.clients.metastore.MetaStore;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Future;

@Slf4j
public class UserMetaStore extends AbstractUserMetaStore {

    private MysqlDataSource dataSource;

    public UserMetaStore(String storeUrl, String storeUserName, String storePassword) {
        this.dataSource = new MysqlDataSource();

        this.dataSource.setUrl(storeUrl);

        this.dataSource.setUser(storeUserName);

        this.dataSource.setUrl(storePassword);
    }
    @Override
    protected void saveData(String groupId, String toStorageJson) {
        Connection connection = getConnection();
        String sql = "insert into dts_checkpoint(group_id, checkpoint) values (?, ?) on dulplicate key update checkpoint = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, groupId);
            preparedStatement.setString(2, toStorageJson);
            preparedStatement.setString(3, toStorageJson);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Save data error", e);
        } finally {
            close(null, preparedStatement, connection);
        }
    }


    @Override
    protected String getData(String groupId) {
        Connection connection = getConnection();
        String sql = "select checkpoint from dts_checkpoint where group_id = ?";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, groupId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("checkpoint");
            }

            return "";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            close(resultSet, preparedStatement, connection);
        }

        return null;
    }

    Connection getConnection() {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            log.error("Get connection error", e);
        }

        return null;
    }

    void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
