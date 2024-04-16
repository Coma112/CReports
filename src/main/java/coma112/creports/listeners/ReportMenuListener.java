package coma112.creports.listeners;

import coma112.creports.CReports;
import coma112.creports.language.MessageKeys;
import coma112.creports.managers.Report;
import coma112.creports.menu.ReportMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

@SuppressWarnings("deprecation")
public class ReportMenuListener implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (!event.getInventory().equals(ReportMenu.getSession(player))) return;

        ReportMenu.close(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Inventory sessionInventory = ReportMenu.getSession(player);
        if (sessionInventory == null || !event.getInventory().equals(sessionInventory)) return;

        event.setCancelled(true);

        if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) return;

        String targetName = event.getCurrentItem().getItemMeta().getLocalizedName();
        Player target = player.getServer().getPlayerExact(targetName);

        if (event.getCurrentItem().getItemMeta().hasLocalizedName()) {
            if (target == null) {
            player.sendMessage(MessageKeys.OFFLINE_PLAYER);
            return;

            }
        }

        player.teleport(target.getLocation());
        ReportMenu.close(player);

        Report reportToRemove = null;

        for (Report report : CReports.getDatabaseManager().getReports()) {
            if (report.target().equals(targetName)) {
                reportToRemove = report;
                break;
            }
        }

        if (reportToRemove != null) {
            CReports.getDatabaseManager().removeReport(reportToRemove);
            ReportMenu.refresh(player);
        }
    }
}


