package wily.ultimatefurnaces.screens;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wily.betterfurnaces.screens.AbstractFurnaceScreen;
import wily.ultimatefurnaces.inventory.UltimateFurnaceMenu;

@OnlyIn(Dist.CLIENT)
public class UltimateFurnaceScreen extends AbstractFurnaceScreen<UltimateFurnaceMenu> {


    public UltimateFurnaceScreen(UltimateFurnaceMenu container, Inventory inv, Component name) {
        super(container, inv, name);
    }

}
