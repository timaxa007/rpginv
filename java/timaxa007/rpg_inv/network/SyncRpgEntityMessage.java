package timaxa007.rpg_inv.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import timaxa007.rpg_inv.registry.RpgEntityIEEP;

public class SyncRpgEntityMessage implements IMessage {

	public NBTTagCompound nbt;
	public int entityID = -1;

	public SyncRpgEntityMessage() {}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, nbt);
		if (entityID != -1)
			buf.writeInt(entityID);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		nbt = ByteBufUtils.readTag(buf);
		if (buf.readableBytes() > 1)
			entityID = buf.readInt();
	}

	public static class Handler implements IMessageHandler<SyncRpgEntityMessage, IMessage> {

		@Override
		public IMessage onMessage(SyncRpgEntityMessage packet, MessageContext message) {
			if (message.side.isClient())
				act(packet);
			else
				act(message.getServerHandler().playerEntity, packet);
			return null;
		}

		@SideOnly(Side.CLIENT)
		private void act(SyncRpgEntityMessage packet) {
			Minecraft mc = Minecraft.getMinecraft();
			RpgEntityIEEP specialPlayer = null;
			if (packet.entityID == -1)
				specialPlayer = RpgEntityIEEP.get(mc.thePlayer);
			else {
				Entity entity = mc.theWorld.getEntityByID(packet.entityID);
				if (entity instanceof EntityPlayer)
					specialPlayer = RpgEntityIEEP.get((EntityPlayer)entity);
			}
			if (specialPlayer == null) return;
			specialPlayer.loadNBTData(packet.nbt);
		}

		private void act(EntityPlayerMP player, SyncRpgEntityMessage packet) {
			RpgEntityIEEP specialPlayer = null;
			if (packet.entityID == -1)
				specialPlayer = RpgEntityIEEP.get(player);
			else {
				Entity entity = player.worldObj.getEntityByID(packet.entityID);
				if (entity instanceof EntityPlayer)
					specialPlayer = RpgEntityIEEP.get((EntityPlayer)entity);
			}
			if (specialPlayer == null) return;
			specialPlayer.loadNBTData(packet.nbt);
		}

	}

}
