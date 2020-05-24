package timaxa007.rpg_inv;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import timaxa007.rpg_inv.api.IItemEquipment;
import timaxa007.rpg_inv.network.SyncItemEquipmentMessage;
import timaxa007.rpg_inv.network.SyncRpgEntityMessage;
import timaxa007.rpg_inv.registry.RpgEntityIEEP;

public class Events {

	@SubscribeEvent
	public void addEntityConstructing(EntityEvent.EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.entity;
			if (RpgEntityIEEP.get(player) == null) RpgEntityIEEP.reg(player);
		}
	}

	@SubscribeEvent
	public void cloneRpgEntityIEEP(PlayerEvent.Clone event) {
		if (event.original.isDead) return;
		RpgEntityIEEP originalRpgEntityIEEP = RpgEntityIEEP.get(event.original);
		if (originalRpgEntityIEEP == null) return;
		RpgEntityIEEP newRpgEntityIEEP = RpgEntityIEEP.get(event.entityPlayer);
		if (newRpgEntityIEEP == null) return;
		NBTTagCompound nbt = new NBTTagCompound();
		newRpgEntityIEEP.saveNBTData(nbt);
		originalRpgEntityIEEP.loadNBTData(nbt);
	}

	@SubscribeEvent
	public void syncRpgEntityIEEP(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP)event.entity;
			RpgEntityIEEP specialPlayer = RpgEntityIEEP.get(player);
			NBTTagCompound nbt = new NBTTagCompound();
			if (specialPlayer != null) {
				specialPlayer.saveNBTData(nbt);
				SyncRpgEntityMessage message = new SyncRpgEntityMessage();
				message.nbt = nbt;
				message.entityID = player.getEntityId();
				//RpgInventoryMod.network.sendToDimension(message, player.dimension);
				RpgInventoryMod.network.sendToAllAround(message, new NetworkRegistry.TargetPoint(
						player.dimension, player.posX, player.posY, player.posZ, 256));
			}
			//RpgInventoryMod.network.sendTo(message, player);
		}
	}

	@SubscribeEvent
	public void playerStartTracking(PlayerEvent.StartTracking event) {
		if (event.entityPlayer instanceof EntityPlayerMP && event.target instanceof EntityPlayerMP) {
			RpgEntityIEEP specialPlayer = RpgEntityIEEP.get((EntityPlayerMP)event.target);
			if (specialPlayer != null) {
				NBTTagCompound nbt = new NBTTagCompound();
				specialPlayer.saveNBTData(nbt);
				SyncRpgEntityMessage message = new SyncRpgEntityMessage();
				message.nbt = nbt;
				message.entityID = event.target.getEntityId();
				RpgInventoryMod.network.sendTo(message, (EntityPlayerMP)event.entityPlayer);
			}
		}
	}

	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void playerDropsEvent(PlayerDropsEvent event) {
		event.entityPlayer.captureDrops = true;
		RpgEntityIEEP ieep = RpgEntityIEEP.get(event.entityPlayer);
		if (ieep != null) {
			for (int i = 0; i < ieep.getInventory().getSizeInventory(); ++i) {
				event.entityPlayer.func_146097_a(ieep.getInventory().getStackInSlot(i), true, false);
				ieep.getInventory().setInventorySlotContents(i, null);
			}
		}
		event.entityPlayer.captureDrops = false;
	}

	@SubscribeEvent
	public void updatePlayer(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)event.entityLiving;
			//if (player.worldObj.isRemote) return;
			int i = 0;
			ItemStack slot;

			RpgEntityIEEP ieep = RpgEntityIEEP.get(player);
			if (ieep != null) {
				for (i = 0; i < ieep.getInventory().getSizeInventory(); ++i) {
					slot = ieep.getInventory().getStackInSlot(i);

					if (slot != null && slot.getItem() instanceof IItemEquipment)
						((IItemEquipment)slot.getItem()).onUpdateItemEquipment(player, slot);

					if (ieep.oldItemEquipment[i] != slot) {

						checkItemEquipment(player, ieep.oldItemEquipment[i], slot, i);
						ieep.oldItemEquipment[i] = slot;
					}
				}
			}

			for (i = 0; i < player.inventory.armorInventory.length; ++i) {
				slot = player.inventory.armorInventory[i];
				if (ieep.oldItemArmor[i] != slot) {
					checkItemArmor(player, ieep.oldItemArmor[i], slot, i);
					ieep.oldItemArmor[i] = slot;
				}
			}

		}
	}

	private static void checkItemEquipment(EntityPlayer player, ItemStack old, ItemStack current, int slotID) {
		if (player.worldObj.isRemote) return;

		/*if (old == null && current != null) System.out.println("put on " + current.getDisplayName());
		if (old != null && current == null) System.out.println("took off " + old.getDisplayName());
		if (old != null && current != null && old != current)
			System.out.println("changed " + old.getDisplayName() + " on " + current.getDisplayName());*/

		SyncItemEquipmentMessage message = new SyncItemEquipmentMessage();
		message.entityID = player.getEntityId();
		message.slotID = (byte)slotID;
		message.item = current;
		//RpgInventoryMod.network.sendToDimension(message, player.dimension);
		RpgInventoryMod.network.sendToAllAround(message, new NetworkRegistry.TargetPoint(
				player.dimension, player.posX, player.posY, player.posZ, 256));
		//EntityTracker.trackedEntityIDs.get(player.getEntityId()).@EntityTrackerEntry@.blocksDistanceThreshold / 2
	}

	private static void checkItemArmor(EntityPlayer player, ItemStack old, ItemStack current, int slotID) {
		if (player.worldObj.isRemote) return;

		/*if (old == null && current != null) System.out.println("put on " + current.getDisplayName());
		if (old != null && current == null) System.out.println("took off " + old.getDisplayName());
		if (old != null && current != null && old != current)
			System.out.println("changed " + old.getDisplayName() + " on " + current.getDisplayName());*/

	}

	@SubscribeEvent
	public void playerBreakSpeed(PlayerEvent.BreakSpeed event) {
		//System.out.println((event.entityPlayer.worldObj.isRemote ? "[C] " : "[S] ") + event.entityPlayer.getClass());
	}

}
