package dev.anvilcraft.addon.wolfplus;

import dev.anvilcraft.addon.wolfplus.init.AddonBlockEntities;
import dev.anvilcraft.addon.wolfplus.init.AddonBlocks;
import dev.anvilcraft.addon.wolfplus.init.AddonCreativeModeTabs;
import dev.anvilcraft.lib.v2.registrum.Registrum;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(AnvilCraftWolfPlus.MOD_ID)
public class AnvilCraftWolfPlus {
    public static final String MOD_ID = "anvilcraft_wolfplus";
    public static final Registrum REGISTRUM = Registrum.create(AnvilCraftWolfPlus.MOD_ID);

    public AnvilCraftWolfPlus(IEventBus modEventBus, ModContainer modContainer) {
        AddonBlocks.register();
        AddonBlockEntities.register();
        AddonCreativeModeTabs.register();
    }

    public static Identifier of(String path) {
        return Identifier.fromNamespaceAndPath(AnvilCraftWolfPlus.MOD_ID, path);
    }
}
