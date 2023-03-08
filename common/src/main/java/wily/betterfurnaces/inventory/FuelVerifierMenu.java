package wily.betterfurnaces.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import wily.betterfurnaces.blockentity.FuelVerifierBlockEntity;
import wily.betterfurnaces.init.Registration;


public class FuelVerifierMenu extends AbstractInventoryMenu<FuelVerifierBlockEntity> {


    public FuelVerifierMenu(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        this( windowId, world, pos, playerInventory, player, new SimpleContainerData(1));
    }

    public FuelVerifierMenu( int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player, ContainerData fields) {
        super(Registration.FUEL_VERIFIER_CONTAINER.get(), windowId, world, pos, playerInventory, player, fields);
        checkContainerDataCount(this.fields, 1);
    }

    @Override
    public void addInventorySlots() {
        this.addSlot(new SlotFuel(be, 0, 80, 48));
    }


    public BlockPos getPos() {
        return this.be.getBlockPos();
    }

    public int getBurnTimeScaled(int pixels) {
        int i = 20000;

        return this.fields.get(0) * pixels / i;
    }
    public float getBurnTime() {
        return (float) this.fields.get(0) / 200;
    }


}
