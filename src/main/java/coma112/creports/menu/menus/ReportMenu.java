package coma112.creports.menu.menus;

import coma112.creports.CReports;
import coma112.creports.config.ConfigKeys;
import coma112.creports.item.IItemBuilder;
import coma112.creports.item.ItemBuilder;
import coma112.creports.language.MessageKeys;
import coma112.creports.managers.Report;
import coma112.creports.menu.PaginatedMenu;
import coma112.creports.utils.MenuUtils;
import coma112.creports.storage.ItemStorage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ReportMenu extends PaginatedMenu {

    public ReportMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

    @Override
    public String getMenuName() {
        return "&cREPORTS";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        List<Report> reports = CReports.getDatabaseManager().getReports();

        if (Objects.equals(event.getCurrentItem(), ItemStorage.BACK)) {
            if (page == 0) {
                player.sendMessage(MessageKeys.FIRST_PAGE);
            } else {
                page--;
                super.open();
            }

            return;
        }

        if (Objects.equals(event.getCurrentItem(), ItemStorage.FORWARD)) {
            int nextPageIndex = page + 1;
            int totalPages = (int) Math.ceil((double) reports.size() / getMaxItemsPerPage());

            if (nextPageIndex >= totalPages) {
                player.sendMessage(MessageKeys.LAST_PAGE);
            } else {
                page++;
                super.open();
            }

            return;
        }

        String targetName = Objects.requireNonNull(event.getCurrentItem()).getItemMeta().getLocalizedName();
        Player target = player.getServer().getPlayerExact(targetName);

        if (target == null) {
            player.sendMessage(MessageKeys.OFFLINE_PLAYER);
            return;
        }

        player.teleport(target.getLocation());
        inventory.close();

        Report reportToRemove = null;

        for (Report report : reports) {
            if (report.target().equals(targetName)) {
                reportToRemove = report;
                break;
            }
        }

        if (reportToRemove != null) CReports.getDatabaseManager().removeReport(reportToRemove);
    }

    @Override
    public void setMenuItems() {
        List<Report> reports = CReports.getDatabaseManager().getReports();
        inventory.clear();
        addMenuBorder();

        int startIndex = page * getMaxItemsPerPage();
        int endIndex = Math.min(startIndex + getMaxItemsPerPage(), reports.size());

        for (int i = startIndex; i < endIndex; i++) {
            Report report = reports.get(i);
            ItemStack item = createReportItem(report);
            inventory.addItem(item);
        }
    }

    private static ItemStack createReportItem(@NotNull Report report) {
        ItemBuilder itemBuilder = IItemBuilder.create(Material.valueOf(ConfigKeys.REPORT_ITEM_MATERIAL))
                .setName(ConfigKeys.REPORT_ITEM_NAME
                        .replace("{player}", report.player())
                        .replace("{id}", String.valueOf(report.id())))
                .setLocalizedName(report.target());

        for (String lore : ConfigKeys.REPORT_ITEM_LORE) {
            itemBuilder.addLore(lore
                    .replace("{target}", report.target())
                    .replace("{reason}", report.reason())
                    .replace("{date}", report.date()));
        }

        return itemBuilder.finish();
    }
}
