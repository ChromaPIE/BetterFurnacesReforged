package wily.betterfurnaces.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import wily.betterfurnaces.blockentity.AbstractCobblestoneGeneratorBlockEntity;
import wily.betterfurnaces.blockentity.AbstractSmeltingBlockEntity;
import wily.betterfurnaces.init.Registration;
import wily.betterfurnaces.items.FuelEfficiencyUpgradeItem;
import wily.betterfurnaces.items.UpgradeItem;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CobblestoneGeneratorBlock extends Block implements EntityBlock {
    public static final String COBBLESTONE_GENERATOR = "cobblestone_generator";


    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final IntegerProperty TYPE = IntegerProperty.create("state", 0, 3);

    public CobblestoneGeneratorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(TYPE, 0));
    }
    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        int s = state.getValue(TYPE);
        return s == 1 || s == 3 ? 9 : 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }


    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult p_225533_6_) {
        ItemStack stack = player.getItemInHand(handIn).copy();
        ItemStack hand = player.getItemInHand(handIn);
        AbstractCobblestoneGeneratorBlockEntity be = (AbstractCobblestoneGeneratorBlockEntity) world.getBlockEntity(pos);

        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if ((hand.getItem() == Items.LAVA_BUCKET) || (hand.getItem() == Items.WATER_BUCKET) || (hand.getItem() instanceof UpgradeItem upg && (upg.upgradeType == 3 || upg.upgradeType == 2))) {
                interactInsert(world, pos, player, handIn, stack);
            } else this.interactWith(world, pos, player);
        }

        return InteractionResult.SUCCESS;
    }

    private void interactWith(Level world, BlockPos pos, Player player) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof MenuProvider) {
            NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider) tileEntity, tileEntity.getBlockPos());
            player.awardStat(Stats.INTERACT_WITH_FURNACE);
        }
    }
    private InteractionResult interactInsert(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack stack) {
        ItemStack hand = player.getItemInHand(handIn);
        if (!((hand.getItem() == Items.LAVA_BUCKET) || (hand.getItem() == Items.WATER_BUCKET) || (hand.getItem() instanceof UpgradeItem upg && (upg.upgradeType == 3 || upg.upgradeType == 2)))){
            return InteractionResult.SUCCESS;
        }

        if (!(world.getBlockEntity(pos) instanceof AbstractCobblestoneGeneratorBlockEntity be)) {
            return InteractionResult.SUCCESS;
        }
        ItemStack newStack = new ItemStack(stack.getItem(), 1);
        newStack.setTag(stack.getTag());

        for (int i : new int[]{0,1,3,4}) {
            if(!stack.isEmpty() && be.IisItemValidForSlot(i, stack)) {
                if ((!(((Container) be).getItem(i).isEmpty())) && (!player.isCreative())) {
                    Containers.dropItemStack(world, pos.getX(), pos.getY() + 1, pos.getZ(), ((Container) be).getItem(i));
                }
                ((Container) be).setItem(i, newStack);
            }
        }
        world.playSound(null, be.getBlockPos(), SoundEvents.ARMOR_EQUIP_IRON, SoundSource.BLOCKS, 1.0F, 1.0F);
        if (!player.isCreative()) {
            player.getItemInHand(handIn).shrink(1);
        }
        be.onUpdateSent();
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean p_196243_5_) {
        if (state.getBlock() != oldState.getBlock()) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof AbstractCobblestoneGeneratorBlockEntity) {
                Containers.dropContents(world, pos, (AbstractCobblestoneGeneratorBlockEntity) be);
                world.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, world, pos, oldState, p_196243_5_);
        }
    }


    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING, TYPE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new AbstractCobblestoneGeneratorBlockEntity.CobblestoneGeneratorBlockEntity(p_153215_, p_153216_);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (level1, pos, state1, tile) -> {
            if (tile instanceof AbstractCobblestoneGeneratorBlockEntity.CobblestoneGeneratorBlockEntity be) {
                be.tick(state1);
            }
        };
    }

}
