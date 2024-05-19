package coma112.creports.menu.menus;

import coma112.creports.CReports;
import coma112.creports.enums.keys.ConfigKeys;
import coma112.creports.enums.keys.MessageKeys;
import coma112.creports.item.IItemBuilder;
import coma112.creports.managers.Report;
import coma112.creports.menu.PaginatedMenu;
import coma112.creports.storage.ItemStorage;
import coma112.creports.utils.MenuUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class ReportMenu extends PaginatedMenu {

    public ReportMenu(MenuUtils menuUtils) {
        super(menuUtils);
    }

    @Override
    public String getMenuName() {
        return ConfigKeys.MENU_TITLE.getString();
    }

    @Override
    public int getSlots() {
        return ConfigKeys.MENU_SIZE.getInt();
    }

    private static ItemStack createReportItem(@NotNull Report report) {
        ItemStack itemStack = IItemBuilder.createItemFromSection("report-item");
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            String displayName = meta.getDisplayName()
                    .replace("{player}", report.player())
                    .replace("{id}", String.valueOf(report.id()));
            meta.setDisplayName(displayName);

            List<String> lore = meta.getLore();
            if (lore != null) {
                List<String> updatedLore = new ArrayList<>();
                for (String line : lore) {
                    String updatedLine = line
                            .replace("{target}", report.target())
                            .replace("{reason}", report.reason())
                            .replace("{date}", report.date());
                    updatedLore.add(updatedLine);
                }
                meta.setLore(updatedLore);
            }
        }

        itemStack.setItemMeta(meta);
        return itemStack;
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

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        List<Report> reports = CReports.getDatabaseManager().getReports();

        if (Objects.equals(event.getCurrentItem(), ItemStorage.BACK)) {
            if (page == 0) {
                player.sendMessage(MessageKeys.FIRST_PAGE.getMessage());
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
                player.sendMessage(MessageKeys.LAST_PAGE.getMessage());
            } else {
                page++;
                super.open();
            }

            return;
        }

        String targetName = Objects.requireNonNull(event.getCurrentItem()).getItemMeta().getLocalizedName();
        Player target = player.getServer().getPlayerExact(targetName);

        if (target == null) {
            player.sendMessage(MessageKeys.OFFLINE_PLAYER.getMessage());
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
}
