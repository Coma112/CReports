package coma112.creports.version.nms.v1_18_R2;

import coma112.creports.utils.ReportLogger;
import coma112.creports.version.ServerVersionSupport;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Version implements ServerVersionSupport {

    @Contract(pure = true)
    public Version(@NotNull Plugin plugin) {
        ReportLogger.info("Loading support for version 1.18.2...");
        ReportLogger.info("Support for 1.18.2 is loaded!");
    }

    @Override
    public String getName() {
        return "1.18_R2";
    }

    @Override
    public boolean isSupported() {
        return true;
    }
}

