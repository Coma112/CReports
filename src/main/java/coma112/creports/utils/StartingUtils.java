package coma112.creports.utils;

import coma112.creports.CReports;
import coma112.creports.update.UpdateChecker;
import coma112.creports.version.MinecraftVersion;
import coma112.creports.version.ServerVersionSupport;
import coma112.creports.version.VersionSupport;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("deprecation")
public class StartingUtils {
    private static boolean isSupported;

    public static void registerListenersAndCommands() {
        RegisterUtils.registerEvents();
        RegisterUtils.registerCommands();
    }


    public static void checkVM() {
        if (getVMVersion() < 11) {
            Bukkit.getPluginManager().disablePlugin(CReports.getInstance());
            return;
        }

        if (!isSupported) {
            ReportLogger.error("This version of CReports is not supported on this server version.");
            ReportLogger.error("Please consider updating your server version to a newer version.");
            CReports.getInstance().getServer().getPluginManager().disablePlugin(CReports.getInstance());
        }
    }

    public static void checkVersion() {
        VersionSupport support;

        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (Exception ignored) {
            isSupported = false;
            return;
        }

        try {
            String[] classParts = Bukkit.getServer().getClass().getName().split("\\.");

            if (classParts.length < 4) {
                ReportLogger.error("Unexpected server class name format: " + Bukkit.getServer().getClass().getName());
                isSupported = false;
                return;
            }

            String[] versionParts = classParts[3].split("_");

            if (versionParts.length < 2) {
                ReportLogger.error("Unexpected version format in class name: " + classParts[3]);
                isSupported = false;
                return;
            }

            int midVersion = Integer.parseInt(versionParts[1]);

            if (midVersion <= 12) {
                isSupported = false;
                return;
            }

            ReportLogger.info("Found everything moving onto VersionSupport...");
            support = new VersionSupport(CReports.getInstance(), MinecraftVersion.getCurrentVersion());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            ReportLogger.error(exception.getMessage());
            isSupported = false;
            return;
        }

        ServerVersionSupport nms = support.getVersionSupport();
        isSupported = true;
    }


    public static void checkUpdates() {
        new UpdateChecker(116859).getVersion(version -> {
            ReportLogger.info(CReports.getInstance().getDescription().getVersion().equals(version) ? "Everything is up to date" : "You are using an outdated version! Please download the new version so that your server is always fresh! The newest version: " + version);
        });
    }

    public static void saveResourceIfNotExists(@NotNull String resourcePath) {
        if (!new File(CReports.getInstance().getDataFolder(), resourcePath).exists()) CReports.getInstance().saveResource(resourcePath, false);
    }


    static int getVMVersion() {
        String javaVersion = System.getProperty("java.version");
        Matcher matcher = Pattern.compile("(?:1\\.)?(\\d+)").matcher(javaVersion);
        if (!matcher.find()) return -1;
        String version = matcher.group(1);

        try {
            return Integer.parseInt(version);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }
}
