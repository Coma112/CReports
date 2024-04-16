package coma112.creports.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import coma112.creports.CReports;
import coma112.creports.managers.Report;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Getter
public class MySQL extends DatabaseManager {
    private final Connection connection;

    public MySQL(@NotNull ConfigurationSection section) throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();

        String host = section.getString("host");
        String database = section.getString("database");
        String user = section.getString("username");
        String pass = section.getString("password");
        int port = section.getInt("port");
        boolean ssl = section.getBoolean("ssl");
        boolean certificateVerification = section.getBoolean("certificateverification");
        int poolSize = section.getInt("poolsize");
        int maxLifetime = section.getInt("lifetime");

        hikariConfig.setPoolName("ReportsPool");
        hikariConfig.setMaximumPoolSize(poolSize);
        hikariConfig.setMaxLifetime(maxLifetime * 1000L);
        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(pass);
        hikariConfig.addDataSourceProperty("useSSL", String.valueOf(ssl));
        if (!certificateVerification)
            hikariConfig.addDataSourceProperty("verifyServerCertificate", String.valueOf(false));
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("encoding", "UTF-8");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("jdbcCompliantTruncation", "false");
        hikariConfig.addDataSourceProperty("characterEncoding", "utf8");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("socketTimeout", String.valueOf(TimeUnit.SECONDS.toMillis(30)));
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "275");
        hikariConfig.addDataSourceProperty("useUnicode", "true");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        connection = dataSource.getConnection();
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS reports (ID INT AUTO_INCREMENT PRIMARY KEY, PLAYER VARCHAR(255) NOT NULL, TARGET VARCHAR(255) NOT NULL, REPORT_TEXT VARCHAR(255) NOT NULL, REPORT_DATE VARCHAR(255) NOT NULL)";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void createReport(@NotNull OfflinePlayer player, @NotNull OfflinePlayer target, @NotNull String reportText, @NotNull String reportDate) {
        String query = "INSERT INTO reports (PLAYER, TARGET, REPORT_TEXT, REPORT_DATE) VALUES (?, ?, ?, ?)";

        try {
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
                preparedStatement.setString(1, player.getName());
                preparedStatement.setString(2, target.getName());
                preparedStatement.setString(3, reportText);
                preparedStatement.setString(4, reportDate);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public List<Report> getReports() {
        List<Report> reports = new ArrayList<>();

        String query = "SELECT * FROM reports";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String player = resultSet.getString("PLAYER");
                String target = resultSet.getString("TARGET");
                String reason = resultSet.getString("REPORT_TEXT");
                String date = resultSet.getString("REPORT_DATE");
                reports.add(new Report(id, player, target, reason, date));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return reports;
    }

    @Override
    public void removeReport(@NotNull Report report) {
        String query = "DELETE FROM reports WHERE ID = ?";

        try {
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
                preparedStatement.setInt(1, report.id());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void reconnect(@NotNull ConfigurationSection section) {
        try {
            if (getConnection() != null && !getConnection().isClosed()) getConnection().close();
            new MySQL(Objects.requireNonNull(CReports.getInstance().getReportsYML().getSection("database.mysql")));
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to reconnect to the database", exception);
        }
    }


}
