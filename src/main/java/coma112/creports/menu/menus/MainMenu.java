package coma112.creports.menu.menus;

import coma112.creports.enums.keys.ConfigKeys;
import coma112.creports.item.IItemBuilder;
import coma112.creports.menu.Menu;
import coma112.creports.utils.MenuUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

public class MainMenu extends Menu implements Listener {
    public MainMenu(@NotNull MenuUtils menuUtils) { super(menuUtils); }

    @Override
    public String getMenuName() { return ConfigKeys.MAIN_MENU_TITLE.getString(); }

    @Override
    public int getSlots() { return ConfigKeys.MAIN_MENU_SIZE.getInt(); }

    @Override
    public boolean enableFillerGlass() {
        return ConfigKeys.MAIN_MENU_FILLER_GLASS.getBoolean();
    }

    @Override
    public int getMenuTick() {
        return 1;
    }

    @Override
    public void handleMenu(final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!event.getInventory().equals(inventory)) return;

        event.setCancelled(true);

        if (event.getSlot() == ConfigKeys.MAIN_UNCLAIMED_MENU_SLOT.getInt()) {
            inventory.close();
            new UnclaimedMenu(MenuUtils.getMenuUtils(player)).open();
        }

        if (event.getSlot() == ConfigKeys.MAIN_CLAIMED_MENU_SLOT.getInt()) {
            inventory.close();
            new ClaimedMenu(MenuUtils.getMenuUtils(player)).open();
        }

        if (event.getSlot() == ConfigKeys.MAIN_COMBINED_MENU_SLOT.getInt()) {
            inventory.close();
            new CombinedMenu(MenuUtils.getMenuUtils(player)).open();
        }
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(ConfigKeys.MAIN_CLAIMED_MENU_SLOT.getInt(), IItemBuilder.createItemFromSection("main-menu.claimed-menu-item"));
        inventory.setItem(ConfigKeys.MAIN_UNCLAIMED_MENU_SLOT.getInt(), IItemBuilder.createItemFromSection("main-menu.unclaimed-menu-item"));
        inventory.setItem(ConfigKeys.MAIN_COMBINED_MENU_SLOT.getInt(), IItemBuilder.createItemFromSection("main-menu.combined-menu-item"));
        setFillerGlass();
    }

    @EventHandler
    public void onClose(final InventoryCloseEvent event) {
        if (event.getInventory().equals(inventory)) close();
    }
}
