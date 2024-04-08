package coma112.creports;

import coma112.creports.config.ReportsYML;
import coma112.creports.database.DatabaseManager;
import coma112.creports.database.MySQL;
import coma112.creports.language.Language;
import coma112.creports.utils.CommandRegister;
import coma112.creports.utils.ListenerRegister;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

public final class CReports extends JavaPlugin {

    @Getter private static CReports instance;
    @Getter private static DatabaseManager databaseManager;
    private static Language language;
    private static ReportsYML reportsYML;


    @Override
    public void onEnable() {
        instance = this;

        initializeComponents();
        registerListenersAndCommands();
        initializeDatabaseManager();

        MySQL mysql = (MySQL) databaseManager;
        mysql.createTable();

    }

    @Override
    public void onDisable() {
        if (databaseManager != null) databaseManager.disconnect();
    }

    private void registerListenersAndCommands() {
        ListenerRegister.registerEvents();
        CommandRegister.registerCommands();
    }

    private void initializeComponents() {
        language = new Language();
        reportsYML = new ReportsYML();
    }

    private void initializeDatabaseManager() {
        try {
            databaseManager = new MySQL(Objects.requireNonNull(getReportsYML().getSection("database.mysql")));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Language getLanguage() {
        return language;
    }

    public ReportsYML getReportsYML() {
        return reportsYML;
    }
}
