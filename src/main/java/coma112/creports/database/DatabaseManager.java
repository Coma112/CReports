package coma112.creports.database;

import coma112.creports.managers.Report;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class DatabaseManager {
    public abstract void createTable();
    public abstract void createReport(@NotNull OfflinePlayer player, @NotNull OfflinePlayer target, String reportText, String reportDate);
    public abstract boolean isConnected();
    public abstract void disconnect();
    public abstract List<Report> getReports();
    public abstract void reconnect(@NotNull ConfigurationSection section);
}
