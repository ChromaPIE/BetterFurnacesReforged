package wily.betterfurnaces.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import wily.betterfurnaces.Config;
import wily.betterfurnaces.init.Registration;
import wily.betterfurnaces.inventory.ExtremeFurnaceMenu;

public class ExtremeFurnaceBlockEntity extends AbstractSmeltingBlockEntity {
    public ExtremeFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.EXTREME_FURNACE_TILE.get(), pos, state,6);
    }

    @Override
    public int getCookTimeConfig() {
        return Config.extremeTierSpeed;
    }

    @Override
    public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new ExtremeFurnaceMenu(i, level, worldPosition, playerInventory, playerEntity, this.fields);
    }

}
