package coma112.creports.config;

import coma112.creports.CReports;
import coma112.creports.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class Config extends ConfigUtils {
    public Config() {
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
        yml.addDefault("report-item.material", "PAPER");
        yml.addDefault("report-item.name", "&2{player}&a's report (#{id})");
        yml.addDefault("report-item.lore", List.of(
                "",
                "&bTARGET: &a{target}",
                "&bREASON: &a{reason}",
                "&bDATE: &a{date}",
                "",
                "&aClick if you want to teleport to the target!"
        ));

        yml.addDefault("menu.back-item.amount", 1);
        yml.addDefault("menu.back-item.material", "RED_STAINED_GLASS");
        yml.addDefault("menu.back-item.name", "&cBack");
        yml.addDefault("menu.back-item.slot", 45);

        yml.addDefault("menu.forward-item.amount", 1);
        yml.addDefault("menu.forward-item.material", "GREEN_STAINED_GLASS");
        yml.addDefault("menu.forward-item.name", "&aForward");
        yml.addDefault("menu.forward-item.slot", 53);

        yml.addDefault("menu.title", "&cREPORTS");
        yml.addDefault("menu.size", 54);
        yml.addDefault("menu.update-tick", 2);

        yml.options().copyDefaults(true);
        save();
    }
}

