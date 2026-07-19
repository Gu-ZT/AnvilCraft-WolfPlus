package dev.anvilcraft.addon.wolfplus.init;

import dev.anvilcraft.addon.wolfplus.AnvilCraftWolfPlus;
import dev.anvilcraft.lib.v2.registrum.util.entry.RegistryEntry;
import dev.dubhe.anvilcraft.AnvilCraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import static dev.anvilcraft.addon.wolfplus.AnvilCraftWolfPlus.REGISTRUM;

@SuppressWarnings({
    "Convert2MethodRef",
    "FunctionalExpressionCanBeFolded"
})
public class AddonCreativeModeTabs {
    public static final RegistryEntry<CreativeModeTab, CreativeModeTab> MAIN = REGISTRUM
        .creativeTab("main", () -> AddonBlocks.WALL_LIGHT.asItem())
        .lang(_ -> AnvilCraftWolfPlus.of("main").toLanguageKey("itemGroup"), "AnvilCraft: Wolf+")
        .title(Component.translatable(AnvilCraftWolfPlus.of("main").toLanguageKey("itemGroup")))
        .withTabsBefore(AnvilCraft.of("building_blocks"))
        .register();

    public static void register() {
    }
}
