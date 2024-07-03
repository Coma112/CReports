package coma112.creports.hooks;

import coma112.creports.CReports;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class PlaceholderAPI extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "cr";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Coma112";
    }

    @Override
    public @NotNull String getVersion() {
        return CReports.getInstance().getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(@NotNull Player player, @NotNull String params) {
        if (params.equals("claimed")) return String.valueOf(CReports.getDatabaseManager().getClaimedReports(player));

        if (params.startsWith("claimed_")) {
            try {
                OfflinePlayer playerToGet = Bukkit.getOfflinePlayer(params.split("_")[1]);
                return String.valueOf(CReports.getDatabaseManager().getClaimedReports(playerToGet));
            } catch (Exception exception) {
                return "---";
            }
        }
        return null;
    }


    public static void registerHook() {
        new PlaceholderAPI().register();
    }
}
