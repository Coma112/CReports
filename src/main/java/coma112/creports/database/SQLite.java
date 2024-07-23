package coma112.creports.database;

import coma112.creports.CReports;
import coma112.creports.events.ReportClaimedEvent;
import coma112.creports.events.ReportCreatedEvent;
import coma112.creports.managers.Report;
import coma112.creports.utils.ReportLogger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class SQLite extends AbstractDatabase {
    private final Connection connection;

    public SQLite() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        File dataFolder = new File(CReports.getInstance().getDataFolder(), "reports.db");
        String url = "jdbc:sqlite:" + dataFolder;
        connection = DriverManager.getConnection(url);
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
                ReportLogger.error(exception.getMessage());
            }
        }
    }

    public void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS reports (ID INTEGER PRIMARY KEY, PLAYER VARCHAR(255) NOT NULL, TARGET VARCHAR(255) NOT NULL, REPORT_TEXT VARCHAR(255) NOT NULL, REPORT_DATE VARCHAR(255) NOT NULL, CLAIMER VARCHAR(255) DEFAULT NULL)";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException exception) {
            ReportLogger.error(exception.getMessage());
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
                CReports.getInstance().getServer().getPluginManager().callEvent(new ReportCreatedEvent(player, target, reportText, reportDate));
            }
        } catch (SQLException exception) {
            ReportLogger.error(exception.getMessage());
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
            ReportLogger.error(exception.getMessage());
        }

        return reports;
    }

    @Override
    public void claimReport(@NotNull Player player, @NotNull Report report) {
        String query = "UPDATE reports SET CLAIMER = ? WHERE ID = ?";

        try {
            try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
                preparedStatement.setString(1,player.getName());
                preparedStatement.setInt(2, report.id());
                preparedStatement.executeUpdate();
                CReports.getInstance().getServer().getPluginManager().callEvent(new ReportClaimedEvent(Bukkit.getPlayerExact(report.player()), Objects.requireNonNull(Bukkit.getPlayerExact(report.target())), player));
            }
        } catch (SQLException exception) {
            ReportLogger.error(exception.getMessage());
        }
    }

    @Override
    public String getClaimer(@NotNull Report report) {
        String query = "SELECT CLAIMER FROM reports WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, report.id());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String claimer = resultSet.getString("CLAIMER");
                return (claimer == null || claimer.isEmpty()) ? null : claimer;
            }
        } catch (SQLException exception) {
            ReportLogger.error(exception.getMessage());
        }

        return null;
    }

    @Override
    public boolean alreadyReported(@NotNull OfflinePlayer player) {
        String query = "SELECT COUNT(*) AS count FROM reports WHERE TARGET = ? AND (CLAIMER IS NULL OR CLAIMER = '')";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, player.getName());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) return resultSet.getInt("count") > 0;
        } catch (SQLException exception) {
            ReportLogger.error(exception.getMessage());
        }

        return false;
    }

    @Override
    public String getPlayer(@NotNull Report report) {
        String query = "SELECT PLAYER FROM reports WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, report.id());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) return resultSet.getString("PLAYER");
        } catch (SQLException exception) {
            ReportLogger.error(exception.getMessage());
        }

        return "";
    }

    @Override
    public int getClaimedReports(@NotNull OfflinePlayer player) {
        String query = "SELECT COUNT(*) AS count FROM reports WHERE CLAIMER = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, player.getName());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) return resultSet.getInt("count");
        } catch (SQLException exception) {
            ReportLogger.error(exception.getMessage());
        }

        return 0;
    }

    @Override
    public void reconnect() {
        try {
            if (getConnection() != null && !getConnection().isClosed()) getConnection().close();
            new SQLite();
        } catch (SQLException | ClassNotFoundException exception) {
            ReportLogger.error(exception.getMessage());
        }
    }
}