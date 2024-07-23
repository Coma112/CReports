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

import static coma112.creports.version.MinecraftVersion.determineVersion;


@SuppressWarnings("deprecation")
public class StartingUtils {
    private static boolean isSupported;

    public static void registerListenersAndCommands() {
        RegisterUtils.registerEvents();
        RegisterUtils.registerCommands();
    }


    public static void checkVM() {
        int vmVersion = getVMVersion();
        if (vmVersion < 11) {
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
        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (Exception ignored) {
            isSupported = false;
            return;
        }

        try {
            String bukkitVersion = Bukkit.getVersion();

            Pattern pattern = Pattern.compile("\\(MC: (\\d+)\\.(\\d+)(?:\\.(\\d+))?\\)");
            Matcher matcher = pattern.matcher(bukkitVersion);

            if (matcher.find()) {
                int majorVersion = Integer.parseInt(matcher.group(1));
                int minorVersion = Integer.parseInt(matcher.group(2));
                int patchVersion = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;

                MinecraftVersion version = determineVersion(majorVersion, minorVersion, patchVersion);
                if (version == MinecraftVersion.UNKNOWN) {
                    isSupported = false;
                    return;
                }

                VersionSupport support = new VersionSupport(CReports.getInstance(), version);
                ServerVersionSupport nms = support.getVersionSupport();
                isSupported = nms != null;

            } else {
                isSupported = false;
            }
        } catch (Exception exception) {
            isSupported = false;
        }
    }


    public static void checkUpdates() {
        new UpdateChecker(117713).getVersion(version -> {
            ReportLogger.info(CReports.getInstance().getDescription().getVersion().equals(version) ? "Everything is up to date" : "You are using an outdated version! Please download the new version so that your server is always fresh! The newest version: " + version);
        });
    }

    public static void saveResourceIfNotExists(@NotNull String resourcePath) {
        if (!new File(CReports.getInstance().getDataFolder(), resourcePath).exists())
            CReports.getInstance().saveResource(resourcePath, false);
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
