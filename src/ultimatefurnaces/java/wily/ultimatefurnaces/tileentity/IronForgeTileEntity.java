package wily.ultimatefurnaces.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraftforge.common.ForgeConfigSpec;
import wily.betterfurnaces.Config;
import wily.betterfurnaces.tileentity.AbstractForgeTileEntity;
import wily.ultimatefurnaces.inventory.IronForgeContainer;
import wily.ultimatefurnaces.init.RegistrationUF;

public class IronForgeTileEntity extends AbstractForgeTileEntity {
    public IronForgeTileEntity() {
        super(RegistrationUF.IRON_FORGE_TILE.get());
    }

    @Override
    public ForgeConfigSpec.IntValue getCookTimeConfig() {
        return Config.ironTierSpeed;
    }

    @Override
    public String IgetName() {
        return "block.ultimatefurnaces_bfr.iron_forge";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new IronForgeContainer(i, level, worldPosition, playerInventory, playerEntity, this.fields);
    }
}
