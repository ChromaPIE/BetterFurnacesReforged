package wily.betterfurnaces.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import wily.betterfurnaces.blockentity.AbstractSmeltingBlockEntity;
import wily.betterfurnaces.blocks.AbstractForgeBlock;

import java.util.function.Supplier;

public class PacketOrientationButton {

	private int x;
	private int y;
	private int z;
	private boolean state;

	public PacketOrientationButton(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		state = buf.readBoolean();
	}

	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeBoolean(state);
	}

	public PacketOrientationButton(BlockPos pos, boolean state) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.state = state;
	}

	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			BlockPos pos = new BlockPos(x, y, z);
			AbstractSmeltingBlockEntity be = (AbstractSmeltingBlockEntity) player.getLevel().getBlockEntity(pos);
			if (player.level.isLoaded(pos)) {
				player.level.setBlock(pos, player.level.getBlockState(pos).setValue(AbstractForgeBlock.SHOW_ORIENTATION, state),3);
				be.getLevel().markAndNotifyBlock(pos, player.getLevel().getChunkAt(pos), be.getLevel().getBlockState(pos).getBlock().defaultBlockState(), be.getLevel().getBlockState(pos), 2, 3);
				be.setChanged();
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
