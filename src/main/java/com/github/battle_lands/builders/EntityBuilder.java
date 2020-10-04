package com.github.battle_lands.builders;

import lombok.Getter;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class EntityBuilder {

    private final EntityType entityType;
    private Location location;
    private boolean ai;
    private final List<PotionEffect> potionEffects;

    public EntityBuilder(EntityType entityType) {
        this.entityType = entityType;
        this.ai = true;
        this.potionEffects = new ArrayList<>();
    }

    public EntityBuilder(EntityType entityType, int amount) {
        this.entityType = entityType;
        this.ai = true;
        this.potionEffects = new ArrayList<>();
    }

    public EntityBuilder ai(boolean ai) {
        this.ai = ai;
        return this;
    }

    public EntityBuilder withPotionEffect(PotionEffect... effects) {
        this.potionEffects.addAll(Arrays.asList(effects));
        return this;
    }

    public EntityBuilder location(Location location) {
        this.location = location;
        return this;
    }

    public Entity build() {
        Entity entity = location.getWorld().spawnEntity(location, entityType);
        if (entity instanceof LivingEntity) {
            EntityLiving entityLiving = ((CraftLivingEntity) entity).getHandle();
            entityLiving.getDataWatcher().watch(15, (byte) (ai ? 0 : 1));
            for (PotionEffect potionEffect : potionEffects) {
                ((CraftLivingEntity) entity).addPotionEffect(potionEffect);
            }
        }
        return entity;
    }

}
