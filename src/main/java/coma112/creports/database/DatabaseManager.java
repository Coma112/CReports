package coma112.creports.database;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public abstract class DatabaseManager {
    public abstract void createTable();
    public abstract void createReport(@NotNull OfflinePlayer player, @NotNull OfflinePlayer target, String reportText, String reportDate);
    public abstract boolean isConnected();
    public abstract void disconnect();
    public abstract void reconnect(@NotNull ConfigurationSection section);
}
