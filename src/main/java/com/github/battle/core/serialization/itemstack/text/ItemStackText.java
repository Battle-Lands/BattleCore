package com.github.battle.core.serialization.itemstack.text;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            String[] stringMeta = new String[5];

            if (itemMeta.hasDisplayName())
                stringMeta[0] = itemMeta.getDisplayName();

            if (itemMeta.hasLore())
                stringMeta[1] = String.join(",", itemMeta.getLore());

            return Stream.of(stringMeta)
              .filter(s -> s != null && !s.isEmpty())
              .collect(Collectors.joining(";"));
        }
        return "";
    }

    private static String serializeSimpleItemStack(ItemStack itemStack) {
        return String.join(";", new String[]{
          itemStack.getType().name(),
          convertString(itemStack.getDurability()),
          convertString(itemStack.getAmount()),
        });
    }

    private static String convertString(Object object) {
        return String.valueOf(object);
    }

}
