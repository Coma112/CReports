package coma112.creports.config;

import coma112.creports.CReports;
import coma112.creports.utils.ConfigUtils;

public class Config extends ConfigUtils {
    public Config() {
        super(CReports.getInstance().getDataFolder().getPath(), "config");
        save();
    }
}

