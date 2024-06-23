package coma112.creports.config;

import coma112.creports.CReports;
import coma112.creports.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class Config extends ConfigUtils {
    public Config() {
        super(CReports.getInstance().getDataFolder().getPath(), "config");
        save();
    }
}

