package wily.betterfurnaces.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class InventoryTileEntity extends TileEntity implements IInventoryTileEntity, ISidedInventory, INamedContainerProvider, INameable {

    public NonNullList<ItemStack> inventory;
    public int invLastIndex;

    protected ITextComponent name;

    public InventoryTileEntity(TileEntityType<?> tileEntityTypeIn, int sizeInventory) {
        super(tileEntityTypeIn);
        invLastIndex = sizeInventory;
        inventory = NonNullList.withSize(sizeInventory, ItemStack.EMPTY);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        this.setChanged();
        return new SUpdateTileEntityPacket(getBlockPos(), -1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT tag = pkt.getTag();
        this.load(level.getBlockState(worldPosition), tag);
        this.setChanged();
        level.markAndNotifyBlock(worldPosition, level.getChunkAt(worldPosition), level.getBlockState(worldPosition).getBlock().defaultBlockState(), level.getBlockState(worldPosition), 2, 3);
    }

    @Override
    public CompoundNBT getUpdateTag() {

        CompoundNBT tag = new CompoundNBT();
        return this.save(tag);
    }

    @Override
    public ITextComponent getName() {
        return (this.name != null ? this.name : new TranslationTextComponent(IgetName()));
    }

    @Override
    public String IgetName() {
        return Objects.requireNonNull(getBlockState().getBlock().getDescriptionId());
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return this.IgetSlotsForFace(side);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return IisItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return IisItemValidForSlot(i, itemStack);
    }

    public void updateBlockState() {
        level.sendBlockUpdated(getBlockPos(), level.getBlockState(getBlockPos()), getBlockState(), 2);
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return IcanExtractItem(i, itemStack, direction);
    }

    @Override
    public int getContainerSize() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.inventory) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void breakDurabilityItem(ItemStack stack) {
        if (stack.isDamageableItem()) {
            stack.hurt(1, null, null);
        }
        if (stack.isDamageableItem() && stack.getDamageValue() >= stack.getMaxDamage()) {
            stack.shrink(1);
            this.getLevel().playSound(null, this.getBlockPos(), SoundEvents.ITEM_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        return ItemStackHelper.removeItem(this.inventory, i, i1);
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return ItemStackHelper.takeItem(this.inventory, i);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        ItemStack itemstack = this.inventory.get(index);
        boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);
        this.inventory.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public int getMaxStackSize() {
        return ISidedInventory.super.getMaxStackSize();
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.inventory = NonNullList.withSize(this.getMaxStackSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.inventory);
        if (nbt.contains("CustomName", 8)) {
            this.name = ITextComponent.Serializer.fromJson(nbt.getString("CustomName"));
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        ItemStackHelper.saveAllItems(nbt, this.inventory);
        if (this.name != null) {
            nbt.putString("CustomName", ITextComponent.Serializer.toJson(this.name));
        }
        return nbt;
    }

    @Override
    public boolean stillValid(PlayerEntity playerEntity) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return !(playerEntity.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public boolean hasCustomName() {
        return this.name != null;
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        return this.name;
    }

    public void setCustomName(ITextComponent name) {
        this.name = name;
    }

    @Override
    public ITextComponent getDisplayName() {
        return getName();
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return IcreateMenu(i, playerInventory, playerEntity);
    }

    @Override
    public void clearContent() {
        this.inventory.clear();
    }
}
