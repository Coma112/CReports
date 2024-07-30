package coma112.creports.utils;

import coma112.creports.CReports;
import coma112.creports.commands.CommandReport;
import coma112.creports.listeners.ReportClaimedListener;
import coma112.creports.listeners.ReportCreatedListener;
import coma112.creports.menu.MenuListener;
import org.bukkit.event.Listener;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("deprecation")
public class RegisterUtils {
    public static void registerEvents() {
        getListenerClasses().forEach(clazz -> {
            try {
                CReports.getInstance().getServer().getPluginManager().registerEvents(clazz.newInstance(), CReports.getInstance());
            } catch (InstantiationException | IllegalAccessException exception) {
                ReportLogger.error(exception.getMessage());
            }
        });
    }

    public static void registerCommands() {
        BukkitCommandHandler handler = BukkitCommandHandler.create(CReports.getInstance());

        handler.register(new CommandReport());
    }

    private static Set<Class<? extends Listener>> getListenerClasses() {
        Set<Class<? extends Listener>> listenerClasses = new HashSet<>();

        listenerClasses.add(MenuListener.class);
        listenerClasses.add(ReportClaimedListener.class);
        listenerClasses.add(ReportCreatedListener.class);
        return listenerClasses;
    }
}
