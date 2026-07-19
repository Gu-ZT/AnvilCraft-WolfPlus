package dev.anvilcraft.addon.wolfplus.init;

import dev.anvilcraft.addon.wolfplus.block.entity.WallLightBlockEntity;
import dev.anvilcraft.lib.v2.registrum.util.entry.BlockEntityEntry;

import static dev.anvilcraft.addon.wolfplus.AnvilCraftWolfPlus.REGISTRUM;

public class AddonBlockEntities {
    public static final BlockEntityEntry<WallLightBlockEntity> WALL_LIGHT = REGISTRUM
        .blockEntity("wall_light", WallLightBlockEntity::new)
        .validBlock(AddonBlocks.WALL_LIGHT)
        .register();

    public static void register() {
    }
}
