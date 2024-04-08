package coma112.creports.config;

import coma112.creports.CReports;
import coma112.creports.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;

public class ReportsYML extends ConfigUtils {
    public ReportsYML() {
        super(CReports.getInstance().getDataFolder().getPath(), "config");

        YamlConfiguration yml = getYml();

        yml.addDefault("database.mysql.host", "localhost");
        yml.addDefault("database.mysql.port", 3306);
        yml.addDefault("database.mysql.database", "coma112");
        yml.addDefault("database.mysql.username", "root");
        yml.addDefault("database.mysql.password", "");
        yml.addDefault("database.mysql.ssl", false);
        yml.addDefault("database.mysql.certificateverification", false);
        yml.addDefault("database.mysql.poolsize", 10);
        yml.addDefault("database.mysql.lifetime", 1800000);

        yml.options().copyDefaults(true);
        save();
    }
}

