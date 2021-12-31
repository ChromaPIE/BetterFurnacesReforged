package wily.betterfurnaces.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import wily.betterfurnaces.BetterFurnacesReforged;
import wily.betterfurnaces.Config;

import javax.annotation.Nullable;
import java.util.List;

public class ItemXpTank extends ItemUpgrade {


    public ItemXpTank(Properties properties) {
        super(properties);
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(new TranslatableComponent("tooltip." + BetterFurnacesReforged.MOD_ID + ".upgrade_right_click").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD).withItalic(true)));
        tooltip.add(new TranslatableComponent("tooltip." + BetterFurnacesReforged.MOD_ID + ".upgrade.xp").setStyle(Style.EMPTY.applyFormat((ChatFormatting.GRAY))));
        if (!ModList.get().isLoaded(Config.getLiquidXPMod()))
            tooltip.add(new TranslatableComponent("tooltip." + BetterFurnacesReforged.MOD_ID + ".upgrade.isworking").setStyle(Style.EMPTY.applyFormat((ChatFormatting.RED))));
    }
}
