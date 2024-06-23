package coma112.creports.database;

import coma112.creports.managers.Report;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractDatabase {
    public abstract void createReport(@NotNull OfflinePlayer player, @NotNull OfflinePlayer target, String reportText, String reportDate);
    public abstract boolean isConnected();
    public abstract void disconnect();
    public abstract List<Report> getReports();

    public abstract boolean alreadyReported(@NotNull OfflinePlayer player);

    public abstract void claimReport(@NotNull Player player, @NotNull Report report);

    public abstract String getClaimer(@NotNull Report report);

    public abstract void reconnect();
}
