package wily.ultimatefurnaces.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import wily.betterfurnaces.inventory.AbstractFurnaceMenu;
import wily.ultimatefurnaces.init.RegistrationUF;

public class CopperFurnaceMenu extends AbstractFurnaceMenu {

    public CopperFurnaceMenu(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
        super(RegistrationUF.COPPER_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
    }

    public CopperFurnaceMenu(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player, ContainerData fields) {
        super(RegistrationUF.COPPER_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player, fields);
    }


    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(be.getLevel(), be.getBlockPos()), playerEntity, RegistrationUF.COPPER_FURNACE.get());
    }
}
