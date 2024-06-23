package coma112.creports.version;

import coma112.creports.utils.ReportLogger;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

@Getter
public class VersionSupport {
    private final ServerVersionSupport versionSupport;

    public VersionSupport(@NotNull Plugin plugin, @NotNull MinecraftVersion version) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (version == MinecraftVersion.UNKNOWN) throw new IllegalArgumentException("VERSION NOT FOUND!!!");


        Class<?> clazz = Class.forName("coma112.creports.version.nms." + version.name() + ".Version");
        versionSupport = (ServerVersionSupport) clazz.getConstructor(Plugin.class).newInstance(plugin);

        if (!versionSupport.isSupported()) {
            ReportLogger.warn("---   VERSION IS SUPPORTED BUT,   ---");
            ReportLogger.warn("The version you are using is badly");
            ReportLogger.warn("implemented. Many features won't work.");
            ReportLogger.warn("Please consider updating your server ");
            ReportLogger.warn("version to a newer version. (like 1.19_R2)");
            ReportLogger.warn("---   PLEASE READ THIS MESSAGE!   ---");
        }

        ReportLogger.info("Version support for {} loaded!", version);
    }
}
