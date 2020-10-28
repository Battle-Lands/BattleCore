package com.github.battle.core.builder;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class Cuboid {

    private final Location location;
    private final int minX, maxX;
    private final int minY, maxY;
    private final int minZ, maxZ;

    public Cuboid(@NonNull Location location, @NonNull Location point) {
        this.location = location;
        this.minX = Math.min(location.getBlockX(), point.getBlockX());
        this.maxX = Math.max(location.getBlockX(), point.getBlockX());

        this.minY = Math.min(location.getBlockY(), point.getBlockY());
        this.maxY = Math.max(location.getBlockY(), point.getBlockY());

        this.minZ = Math.min(location.getBlockZ(), point.getBlockZ());
        this.maxZ = Math.max(location.getBlockZ(), point.getBlockZ());
    }

    public static List<Block> listAroundPlatform(@NonNull Location location, int range) {
        final List<Block> blocks = new ArrayList<>();
        final World world = location.getWorld();
        final int y = location.getBlockY();

        final int blockX = location.getBlockX();
        final int minRangeX = blockX - range;
        final int maxRangeX = blockX + range;

        final int blockZ = location.getBlockZ();
        final int minRangeZ = blockZ - range;
        final int maxRangeZ = blockZ + range;

        for (int x = minRangeX; x <= maxRangeX; x++) {
            for (int z = minRangeZ; z <= maxRangeZ; z++) {
                blocks.add(world.getBlockAt(x, y, z));
            }
        }
        return blocks;
    }

    public List<Block> list() {
        final List<Block> blocks = new ArrayList<>();
        final World world = location.getWorld();

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    blocks.add(world.getBlockAt(x, y, z));
                }
            }
        }

        return blocks;
    }

    public List<Block> listPlatform() {
        final List<Block> blocks = new ArrayList<>();
        final World world = location.getWorld();
        final int y = location.getBlockY();

        for (int x = minX; x < maxX; x++) {
            for (int z = minZ; z < maxZ; z++) {
                blocks.add(world.getBlockAt(x, y, z));
            }
        }

        return blocks;
    }
}