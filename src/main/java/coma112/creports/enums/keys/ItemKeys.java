package coma112.creports.enums.keys;

import coma112.creports.item.IItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public enum ItemKeys {
    CLAIMED_BACK_ITEM("claimed-menu.back-item"),
    UNCLAIMED_BACK_ITEM("unclaimed-menu.back-item"),
    UNCLAIMED_FORWARD_ITEM("unclaimed-menu.back-item"),
    COMBINED_FORWARD_ITEM("claimed-menu.forward-item"),
    COMBINED_BACK_ITEM("claimed-menu.back-item"),
    MAIN_CLAIMED_MENU_ITEM("main-menu.claimed-menu-item"),
    MAIN_UNCLAIMED_MENU_ITEM("main-menu.unclaimed-menu-item"),
    MAIN_COMBINED_MENU_ITEM("main-menu.combined-menu-item"),
    REPORT_ITEM("report-item"),
    FILLER_GLASS_ITEM("filler-glass-item"),
    CLAIMED_FORWARD_ITEM("claimed-menu.forward-item");

    private final String path;

    ItemKeys(@NotNull final String path) {
        this.path = path;
    }

    public ItemStack getItem() {
        return IItemBuilder.createItemFromString(path);
    };
}
