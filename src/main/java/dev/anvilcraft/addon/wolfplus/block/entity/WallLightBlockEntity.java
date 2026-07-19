package dev.anvilcraft.addon.wolfplus.block.entity;

import dev.dubhe.anvilcraft.api.power.IPowerConsumer;
import dev.dubhe.anvilcraft.api.power.PowerGrid;
import dev.dubhe.anvilcraft.block.laser.RubyLaserBlock;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class WallLightBlockEntity extends BlockEntity implements IPowerConsumer {
    @Getter
    @Setter
    private @Nullable PowerGrid grid;

    public WallLightBlockEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Override
    public @Nullable Level getCurrentLevel() {
        return this.getLevel();
    }

    @Override
    public BlockPos getPos() {
        return this.getBlockPos();
    }

    @Override
    public int getInputPower() {
        return 1;
    }

    @Override
    public void gridTick() {
        if (this.level == null) return;
        if (this.getGrid() != null && this.getBlockState().getValue(RubyLaserBlock.OVERLOAD) == this.getGrid().isWorking()) {
            this.level.setBlock(this.getPos(), this.getBlockState().setValue(OVERLOAD, !this.getGrid().isWorking()), 2);
        }
    }
}
