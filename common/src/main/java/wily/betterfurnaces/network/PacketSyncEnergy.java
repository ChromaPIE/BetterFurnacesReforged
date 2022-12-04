package wily.betterfurnaces.network;

import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import wily.betterfurnaces.blockentity.AbstractSmeltingBlockEntity;
import wily.factoryapi.base.Storages;

import java.util.function.Supplier;

public class PacketSyncEnergy {
    private int energy;
    private BlockPos pos;


    public PacketSyncEnergy(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readInt());

    }

    public PacketSyncEnergy(BlockPos pos, int energy) {
        this.pos = pos;
        this.energy = energy;

    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(energy);
    }

    public void apply(Supplier<NetworkManager.PacketContext> ctx) {
        ctx.get().queue(() -> {
            Player player = ctx.get().getPlayer();
            BlockEntity be = player.getLevel().getBlockEntity(pos);
            if (player.level.isLoaded(pos)) {
                ((AbstractSmeltingBlockEntity)be).getStorage(Storages.ENERGY,null).ifPresent(t-> t.setEnergyStored(energy));
                be.setChanged();
            }
        });
    }
}
