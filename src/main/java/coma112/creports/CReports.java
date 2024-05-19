package coma112.creports;

import coma112.creports.config.Config;
import coma112.creports.database.AbstractDatabase;
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
    @Getter private static AbstractDatabase databaseManager;
    private static Language language;
    private static Config config;

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

    public Language getLanguage() {
        return language;
    }

    public Config getConfiguration() {
        return config;
    }

    private void registerListenersAndCommands() {
        ListenerRegister.registerEvents();
        CommandRegister.registerCommands();
    }

    private void initializeComponents() {
        language = new Language();
        config = new Config();
    }

    private void initializeDatabaseManager() {
        try {
            databaseManager = new MySQL(Objects.requireNonNull(getConfiguration().getSection("database.mysql")));
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
