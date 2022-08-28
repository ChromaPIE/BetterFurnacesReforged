package wily.betterfurnaces.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import wily.betterfurnaces.inventory.AbstractFuelVerifierMenu;
import wily.betterfurnaces.init.Registration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractFuelVerifierBlockEntity extends InventoryBlockEntity {

    @Override
    public int[] IgetSlotsForFace(Direction side) {
        return new int[0];
    }

    @Override
    public boolean IcanExtractItem(int index, ItemStack stack, Direction direction) {
        return false;
    }

    public final ContainerData fields = new ContainerData() {
        public int get(int index) {
            if (index == 0)
            return AbstractFuelVerifierBlockEntity.this.burnTime;
            else return 0;
        }

        public void set(int index, int value) {
            AbstractFuelVerifierBlockEntity.this.burnTime = value;
        }

        @Override
        public int getCount() {
            return 1;
        }
    };
    @Override
    public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new AbstractFuelVerifierMenu.FuelVerifierMenu(i, level, worldPosition, playerInventory, playerEntity, this.fields);
    }

    /**
     * The number of ticks that the furnace will keep burning
     */
    private int burnTime;

    public static class FuelVerifierBlockEntity extends AbstractFuelVerifierBlockEntity {
        public FuelVerifierBlockEntity(BlockPos pos, BlockState state) {
            super(Registration.FUEL_VERIFIER_TILE.get(), pos, state);
        }

    }
    public AbstractFuelVerifierBlockEntity(BlockEntityType<?> tileentitytypeIn, BlockPos pos, BlockState state) {
        super(tileentitytypeIn, pos, state, 1);

    }

    public void tick(BlockState blockState) {
        if (!level.isClientSide) {
            ItemStack fuel = getItem(0);
            if (!(fuel.isEmpty()))
            burnTime = getBurnTime(fuel);
            else burnTime = 0;
        }

    }
    @Override
    public void load(CompoundTag tag) {
        if (tag.getCompound("inventory").isEmpty() && !tag.getList("Items",10).isEmpty()) {
            if (isEmpty())
                getInv().deserializeNBT(tag);
        }else
        getInv().deserializeNBT(tag.getCompound("inventory"));
        this.burnTime = tag.getInt("BurnTime");
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.putInt("BurnTime", this.burnTime);
        super.saveAdditional(tag);
    }

    protected static int getBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            int ret = stack.getBurnTime(RecipeType.SMELTING);
            return net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack, ret == -1 ? AbstractFurnaceBlockEntity.getFuel().getOrDefault(item, 0) : ret, RecipeType.SMELTING);
        }
    }


    public static boolean isItemFuel(ItemStack stack) {
        return getBurnTime(stack) > 0;
    }

    @Nonnull
    @Override
    public <
            T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {

        if (!this.isRemoved() && facing != null && capability == ForgeCapabilities.ITEM_HANDLER) {
            return LazyOptional.of(this::getInv).cast();
        }
        return super.getCapability(capability, facing);
    }


    @Override
    public boolean IisItemValidForSlot(int index, ItemStack stack) {
        if (index == 0) {
            return isItemFuel(stack);
        }
        return false;
    }


}
