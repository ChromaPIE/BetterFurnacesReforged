package wily.betterfurnaces.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import wily.betterfurnaces.Config;
import wily.betterfurnaces.init.Registration;
import wily.betterfurnaces.inventory.IronFurnaceMenu;

public class IronFurnaceBlockEntity extends AbstractSmeltingBlockEntity {
    public IronFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.IRON_FURNACE_TILE.get(), pos, state,6);
    }

    @Override
    public int getCookTimeConfig() {
        return Config.ironTierSpeed;
    }

    @Override
    public AbstractContainerMenu IcreateMenu(int i, Inventory playerInventory, Player playerEntity) {
        return new IronFurnaceMenu(i, level, worldPosition, playerInventory, playerEntity, this.fields);
    }

}
