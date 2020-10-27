package com.github.battle.core.serialization.itemstack.text;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * A converter text ItemStack for {@link org.bukkit.inventory.ItemStack}.
 *
 * @author Kewilleen Gomes
 */
public class ItemStackText {

    public static String serialize(ItemStack itemStack) {
        String[] stringData = {
          serializeSimpleItemStack(itemStack),
          serializeItemMeta(itemStack),
        };
        return String.join(";", stringData);
    }

    private static String serializeItemMeta(ItemStack itemStack) {
        if (itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            String[] stringMeta = new String[4];

            if (itemMeta.hasDisplayName())
                stringMeta[0] = itemMeta.getDisplayName();

            if (itemMeta.hasLore())
                stringMeta[1] = String.join(",", itemMeta.getLore());

            if (itemMeta.hasEnchants()) {
                StringBuilder enchantments = new StringBuilder();
                for (Enchantment enchantment : itemMeta.getEnchants().keySet()) {
                    String name = enchantment.getName();
                    int level = itemMeta.getEnchants().get(enchantment);
                    enchantments.append(String.join(":", name, convertString(level))).append(",");
                }
                if (enchantments.length() > 0)
                    enchantments.delete(enchantments.length() - 1, enchantments.length());
                stringMeta[2] = enchantments.toString();
            }
            if (!itemMeta.getItemFlags().isEmpty()) {
                StringBuilder flags = new StringBuilder();
                for (ItemFlag flag : itemMeta.getItemFlags())
                    flags.append(flag.name()).append(",");
                if (flags.length() > 0)
                    flags.delete(flags.length() - 1, flags.length());
                stringMeta[3] = flags.toString();
            }
            return String.join(";", stringMeta);
        }
        return "";
    }

    public static String serializeSimpleItemStack(ItemStack itemStack) {
        return String.join(";", new String[]{
          itemStack.getType().name(),
          convertString(itemStack.getDurability()),
          convertString(itemStack.getAmount()),
        });
    }

    private static String convertString(Object object) {
        return String.valueOf(object);
    }

    public static ItemStack deserialize(String serialize) {
        return new ItemStack(Material.AIR);
    }
}
