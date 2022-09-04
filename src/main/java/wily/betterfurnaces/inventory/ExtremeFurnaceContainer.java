package wily.betterfurnaces.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wily.betterfurnaces.init.Registration;

public class ExtremeFurnaceContainer extends AbstractFurnaceContainer {

    public ExtremeFurnaceContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(Registration.EXTREME_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
    }

    public ExtremeFurnaceContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, IIntArray fields) {
        super(Registration.EXTREME_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player, fields);
    }

}
