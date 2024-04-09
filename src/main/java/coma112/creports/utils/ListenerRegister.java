package coma112.creports.utils;

import coma112.creports.CReports;
import coma112.creports.listeners.ReportMenuListener;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class ListenerRegister {
    @SuppressWarnings("deprecation")
    public static void registerEvents() {
        Set<Class<? extends Listener>> listenerClasses = getListenerClasses();

        for (Class<? extends Listener> clazz : listenerClasses) {
            try {
                CReports.getInstance().getServer().getPluginManager().registerEvents(clazz.newInstance(), CReports.getInstance());
            } catch (InstantiationException | IllegalAccessException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private static Set<Class<? extends Listener>> getListenerClasses() {
        Set<Class<? extends Listener>> listenerClasses = new HashSet<>();
        listenerClasses.add(ReportMenuListener.class);
        return listenerClasses;
    }

}
