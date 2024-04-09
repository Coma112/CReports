package coma112.creports.listeners;

import coma112.creports.menu.ReportMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ReportMenuListener implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (!event.getInventory().equals(ReportMenu.getSession(player))) return;

        ReportMenu.close(player);
    }
}
