package dev.anvilcraft.addon.wolfplus.block;

import com.mojang.serialization.MapCodec;
import dev.anvilcraft.addon.wolfplus.init.AddonBlockEntities;
import dev.dubhe.anvilcraft.api.power.IPowerComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class WallLightBlock extends DirectionalBlock implements EntityBlock {
    public static final Map<Direction, VoxelShape> SHAPES = Shapes.rotateAll(Block.box(0, 0, 15, 16, 16, 16));

    public static final BooleanProperty OVERLOAD = IPowerComponent.OVERLOAD;
    public static final EnumProperty<IPowerComponent.Switch> SWITCH = IPowerComponent.SWITCH;

    public WallLightBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition
            .any()
            .setValue(FACING, Direction.UP)
            .setValue(OVERLOAD, true)
            .setValue(SWITCH, IPowerComponent.Switch.ON)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(OVERLOAD).add(SWITCH);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return WallLightBlock.SHAPES.get(state.getValue(WallLightBlock.FACING));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    @Override
    protected void neighborChanged(
        BlockState state,
        Level level,
        BlockPos pos,
        Block block,
        @Nullable Orientation orientation,
        boolean movedByPiston
    ) {
        if (level.isClientSide()) return;
        boolean isOff = state.getValue(SWITCH) == IPowerComponent.Switch.OFF;
        if (isOff == level.hasNeighborSignal(pos)) return;
        if (isOff) {
            level.scheduleTick(pos, this, 4);
        } else {
            level.setBlock(pos, state.cycle(SWITCH), 2);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        boolean isOff = state.getValue(SWITCH) == IPowerComponent.Switch.OFF;
        if (isOff && !level.hasNeighborSignal(pos)) {
            level.setBlock(pos, state.cycle(SWITCH), 2);
        }
    }

    @Override
    protected MapCodec<WallLightBlock> codec() {
        return Block.simpleCodec(WallLightBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return AddonBlockEntities.WALL_LIGHT.create(worldPosition, blockState);
    }

    public static int lightLevel(BlockState state) {
        if (state.getValue(OVERLOAD)) return 0;
        return state.getValue(SWITCH) == IPowerComponent.Switch.ON ? 15 : 0;
    }
}
