package coma112.creports.utils;

import coma112.creports.CReports;
import coma112.creports.commands.CommandReport;
import revxrsal.commands.bukkit.BukkitCommandHandler;

public class CommandRegister {
    public static void registerCommands() {
        BukkitCommandHandler handler = BukkitCommandHandler.create(CReports.getInstance());
        handler.register(new CommandReport());
    }
}

