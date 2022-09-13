package wily.betterfurnaces.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import wily.betterfurnaces.blockentity.AbstractSmeltingBlockEntity;

import javax.annotation.Nullable;

public class BlockColorsHandler implements BlockColor {
    public static final BlockColor COLOR = new BlockColorsHandler();

    @SubscribeEvent
    public static void registerBlockColors() {
        System.out.println("Starting Better Furnaces BlockColorsHandler");
        Minecraft.getInstance().getBlockColors().register(COLOR, Registration.IRON_FURNACE.get());
        Minecraft.getInstance().getBlockColors().register(COLOR, Registration.GOLD_FURNACE.get());
        Minecraft.getInstance().getBlockColors().register(COLOR, Registration.DIAMOND_FURNACE.get());
        Minecraft.getInstance().getBlockColors().register(COLOR, Registration.NETHERHOT_FURNACE.get());
        Minecraft.getInstance().getBlockColors().register(COLOR, Registration.EXTREME_FURNACE.get());
        Minecraft.getInstance().getBlockColors().register(COLOR, Registration.EXTREME_FORGE.get());
    }

    @Override
    public int getColor(BlockState blockState, @Nullable BlockAndTintGetter iBlockDisplayReader, @Nullable BlockPos blockPos, int i) {
        if (iBlockDisplayReader.getBlockEntity(blockPos) instanceof AbstractSmeltingBlockEntity) {
            AbstractSmeltingBlockEntity be = (AbstractSmeltingBlockEntity) iBlockDisplayReader.getBlockEntity(blockPos);
            if (be.hasUpgrade(Registration.COLOR.get()) && (be.getUpgradeSlotItem(Registration.COLOR.get()).getTag() != null)) {
                return be.hex();
            }
        }
        return 0xFFFFFF;
    }
}
