package coma112.creports.menu;

import coma112.creports.enums.keys.ConfigKeys;
import coma112.creports.item.IItemBuilder;
import coma112.creports.processor.MessageProcessor;
import coma112.creports.utils.MenuUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public abstract class Menu implements InventoryHolder {

    protected MenuUtils menuUtils;
    protected Inventory inventory;

    public Menu(MenuUtils menuUtils) {
        this.menuUtils = menuUtils;
    }

    public abstract void handleMenu(final InventoryClickEvent event);
    public abstract void setMenuItems();

    public abstract String getMenuName();

    public abstract int getSlots();
    public abstract int getMenuTick();

    public abstract boolean enableFillerGlass();


    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), MessageProcessor.process(getMenuName()));

        this.setMenuItems();

        menuUtils.getOwner().openInventory(inventory);
        MenuUpdater menuUpdater = new MenuUpdater(this);
        menuUpdater.start(getMenuTick());
    }

    public void setFillerGlass() {
        if (!enableFillerGlass()) return;

        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null) inventory.setItem(i, IItemBuilder.createItemFromSection("filler-glass-item"));
        }
    }

    public void close() {
        MenuUpdater menuUpdater = new MenuUpdater(this);
        menuUpdater.stop();
        inventory = null;
    }

    public void updateMenuItems() {
        if (inventory != null) {
            setMenuItems();
            menuUtils.getOwner().updateInventory();
        }
    }
}
