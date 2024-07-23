package coma112.creports;

import com.github.Anon8281.universalScheduler.UniversalScheduler;
import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import coma112.creports.config.Config;
import coma112.creports.database.AbstractDatabase;
import coma112.creports.database.MySQL;
import coma112.creports.database.SQLite;
import coma112.creports.enums.DatabaseType;
import coma112.creports.enums.LanguageType;
import coma112.creports.enums.keys.ConfigKeys;
import coma112.creports.hooks.PlaceholderAPI;
import coma112.creports.language.Language;
import coma112.creports.utils.ReportLogger;
import coma112.creports.utils.StartingUtils;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

import static coma112.creports.utils.StartingUtils.registerListenersAndCommands;
import static coma112.creports.utils.StartingUtils.saveResourceIfNotExists;

public final class CReports extends JavaPlugin {

    @Getter private static CReports instance;
    @Getter private static AbstractDatabase databaseManager;
    private static Language language;
    private static Config config;
    private static TaskScheduler scheduler;

    @Override
    public void onLoad() {
        instance = this;

        StartingUtils.checkVersion();
    }

    @Override
    public void onEnable() {
        StartingUtils.checkVM();
        saveDefaultConfig();

        scheduler = UniversalScheduler.getScheduler(this);

        initializeComponents();
        registerListenersAndCommands();
        initializeDatabaseManager();

        PlaceholderAPI.registerHook();
        StartingUtils.checkUpdates();

        new Metrics(this, 22367);
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

    public TaskScheduler getScheduler() {
        return scheduler;
    }

    private void initializeComponents() {
        config = new Config();

        saveResourceIfNotExists("locales/messages_en.yml");
        saveResourceIfNotExists("locales/messages_hu.yml");
        saveResourceIfNotExists("locales/messages_de.yml");

        language = new Language("messages_" + LanguageType.valueOf(ConfigKeys.LANGUAGE.getString()));
    }

    private void initializeDatabaseManager() {
        try {
            switch (DatabaseType.valueOf(ConfigKeys.DATABASE.getString())) {
                case MYSQL, mysql -> {
                    databaseManager = new MySQL(Objects.requireNonNull(getConfiguration().getSection("database.mysql")));
                    MySQL mySQL = (MySQL) databaseManager;
                    mySQL.createTable();
                }

                case SQLITE, sqlite -> {
                    databaseManager = new SQLite();
                    SQLite sqLite = (SQLite) databaseManager;
                    sqLite.createTable();
                }
            }
        } catch (SQLException | ClassNotFoundException exception) {
            ReportLogger.error(exception.getMessage());
        }
    }
}
