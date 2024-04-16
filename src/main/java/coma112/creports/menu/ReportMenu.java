package coma112.creports.menu;
import coma112.creports.CReports;
import coma112.creports.item.IItemBuilder;
import coma112.creports.managers.Report;
import coma112.creports.processor.MessageProcessor;
import coma112.creports.storage.ItemStorage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class ReportMenu {
    private static final Map<UUID, Inventory> playerInventories = new HashMap<>();
    private static final Map<Inventory, Integer> refreshTasks = new HashMap<>();

    public static void close(@NotNull Player player) {
        Inventory inventory = playerInventories.remove(player.getUniqueId());
        if (inventory != null) Bukkit.getScheduler().cancelTask(refreshTasks.remove(inventory));
    }

    public static Inventory getSession(@NotNull Player player) {
        return playerInventories.get(player.getUniqueId());
    }

    public static void open(@NotNull Player player) {
        if (playerInventories.containsKey(player.getUniqueId())) return;

        Inventory inventory = Bukkit.createInventory(player, 54, MessageProcessor.process("&cREPORTS"));
        playerInventories.put(player.getUniqueId(), inventory);

        refresh(player);
        run(player, inventory);
        inventory.setItem(45, ItemStorage.BACK);
        inventory.setItem(53, ItemStorage.FORWARD);
        player.openInventory(inventory);
    }

    public static void refresh(@NotNull Player player) {
        Inventory inventory = playerInventories.get(player.getUniqueId());

        if (inventory != null) {
            List<Report> reports = CReports.getDatabaseManager().getReports();
            int slot = 0;

            for (Report report : reports) {
                ItemStack item = createReportItem(report);

                if (item != null) {
                    inventory.setItem(slot, item);
                    slot++;
                    if (slot >= 54) break;
                }
            }
        }
    }


    private static ItemStack createReportItem(@NotNull Report report) {
        return IItemBuilder.create(Material.MOJANG_BANNER_PATTERN)
                .setName("&2" + report.player() + "&a's report (#" + report.id() + ")")
                .addLore("")
                .addLore("&bTARGET: &a" + report.target())
                .addLore("&bREASON: &a" + report.reason())
                .addLore("&bDATE: &a" + report.date())
                .addLore("")
                .addLore("&aClick if you want to teleport to the target!")
                .setLocalizedName(report.target())
                .finish();
    }

    private static void run(@NotNull Player player, @NotNull Inventory inventory) {
        refreshTasks.put(inventory, Bukkit.getScheduler().scheduleSyncRepeatingTask(CReports.getInstance(), () -> {
            if (!playerInventories.containsKey(player.getUniqueId()))
                Bukkit.getScheduler().cancelTask(refreshTasks.remove(inventory));
            refresh(player);
        }, 20L, 5L));
    }
}


