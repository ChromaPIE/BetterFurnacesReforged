package wily.ultimatefurnaces.screens;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wily.betterfurnaces.screens.AbstractFurnaceScreen;
import wily.ultimatefurnaces.inventory.PlatinumFurnaceContainer;

@OnlyIn(Dist.CLIENT)
public class PlatinumFurnaceScreen extends AbstractFurnaceScreen<PlatinumFurnaceContainer> {


    public PlatinumFurnaceScreen(PlatinumFurnaceContainer container, PlayerInventory inv, ITextComponent name) {
        super(container, inv, name);
    }

}
