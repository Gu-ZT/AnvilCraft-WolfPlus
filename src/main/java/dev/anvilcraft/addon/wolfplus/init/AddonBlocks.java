package dev.anvilcraft.addon.wolfplus.init;

import dev.anvilcraft.addon.wolfplus.block.WallLightBlock;
import dev.anvilcraft.lib.v2.registrum.providers.DataGenContext;
import dev.anvilcraft.lib.v2.registrum.providers.generators.RegistrumBlockModelGenerator;
import dev.anvilcraft.lib.v2.registrum.util.entry.BlockEntry;
import dev.anvilcraft.lib.v2.util.nullness.NonNullBiConsumer;
import dev.dubhe.anvilcraft.api.power.IPowerComponent;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import static dev.anvilcraft.addon.wolfplus.AnvilCraftWolfPlus.REGISTRUM;

public class AddonBlocks {
    public static final BlockEntry<WallLightBlock> WALL_LIGHT = REGISTRUM.block("wall_light", WallLightBlock::new)
        .properties(properties -> properties.mapColor(MapColor.COLOR_LIGHT_GRAY)
            .lightLevel(WallLightBlock::lightLevel)
            .strength(0.3F)
            .sound(SoundType.GLASS)
            .isValidSpawn(Blocks::never))
        .tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .blockstate(() -> new NonNullBiConsumer<>() {
            @Override
            public void accept(DataGenContext<Block, WallLightBlock> context, RegistrumBlockModelGenerator generator) {
                Identifier normalId = context.getId().withPrefix("block/");
                MultiVariant normal = RegistrumBlockModelGenerator.plainVariant(normalId);
                MultiVariant off = RegistrumBlockModelGenerator.plainVariant(normalId.withSuffix("_off"));
                MultiVariant overload = RegistrumBlockModelGenerator.plainVariant(normalId.withSuffix("_overload"));
                generator.blockStateOutput.accept(createModel(context.get(), normal, off, overload));
                generator.registerSimpleItemModel(context.get(), normalId);
            }

            private static BlockModelDefinitionGenerator createModel(
                Block block,
                MultiVariant normal,
                MultiVariant off,
                MultiVariant overload
            ) {
                return MultiVariantGenerator.dispatch(block)
                    .with(
                        PropertyDispatch.initial(WallLightBlock.OVERLOAD, WallLightBlock.SWITCH)
                            .select(false, IPowerComponent.Switch.ON, normal)
                            .select(true, IPowerComponent.Switch.ON, overload)
                            .select(false, IPowerComponent.Switch.OFF, off)
                            .select(true, IPowerComponent.Switch.OFF, off)
                    )
                    .with(
                        PropertyDispatch.modify(WallLightBlock.FACING)
                            .select(Direction.EAST, RegistrumBlockModelGenerator.Y_ROT_90)
                            .select(Direction.WEST, RegistrumBlockModelGenerator.Y_ROT_270)
                            .select(Direction.SOUTH, RegistrumBlockModelGenerator.Y_ROT_180)
                            .select(Direction.NORTH, RegistrumBlockModelGenerator.NOP)
                            .select(Direction.UP, RegistrumBlockModelGenerator.X_ROT_270)
                            .select(Direction.DOWN, RegistrumBlockModelGenerator.X_ROT_90)
                    );
            }
        })
        .simpleItem()
        .register();

    public static void register() {
    }
}
