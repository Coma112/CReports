package coma112.creports.storage;

import coma112.creports.item.IItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStorage {
    public static final ItemStack BACK = IItemBuilder.create(Material.RED_STAINED_GLASS)
            .setName("&cVissza")
            .finish();

    public static final ItemStack FORWARD = IItemBuilder.create(Material.GREEN_STAINED_GLASS)
            .setName("&aElőre")
            .finish();
}
