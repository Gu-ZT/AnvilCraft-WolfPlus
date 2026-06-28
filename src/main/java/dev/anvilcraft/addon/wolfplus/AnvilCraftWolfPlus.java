package dev.anvilcraft.addon.wolfplus;

import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(AnvilCraftWolfPlus.MOD_ID)
public class AnvilCraftWolfPlus {
    public static final String MOD_ID = "anvilcraft_wolfplus";

    public AnvilCraftWolfPlus(IEventBus modEventBus, ModContainer modContainer) {
    }

    public static Identifier of(String path) {
        return Identifier.fromNamespaceAndPath(AnvilCraftWolfPlus.MOD_ID, path);
    }
}
