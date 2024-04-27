package coma112.creports.utils;

import coma112.creports.CReports;
import coma112.creports.commands.CommandReload;
import coma112.creports.commands.CommandReport;
import coma112.creports.commands.CommandReportMenu;
import coma112.creports.subcommand.PluginCommand;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CommandRegister {
    @SuppressWarnings("deprecation")
    public static void registerCommands() {

        for (Class<? extends PluginCommand> clazz : getCommandClasses()) {
            try {
                PluginCommand commandInstance = clazz.getDeclaredConstructor().newInstance();
                Objects.requireNonNull(Bukkit.getCommandMap()).register(CReports.getInstance().getDescription().getName(), commandInstance);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private static Set<Class<? extends PluginCommand>> getCommandClasses() {
        Set<Class<? extends PluginCommand>> commandClasses = new HashSet<>();
        commandClasses.add(CommandReload.class);
        commandClasses.add(CommandReport.class);
        commandClasses.add(CommandReportMenu.class);
        return commandClasses;
    }
}

