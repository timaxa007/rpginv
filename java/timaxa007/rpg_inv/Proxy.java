package timaxa007.rpg_inv;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import timaxa007.rpg_inv.inventory.ContainerPlayer;
import timaxa007.rpg_inv.network.OpenGuiMessage;

public class Proxy {

	public void preInit(FMLPreInitializationEvent event) {

	}

	public void init() {

	}

	private static Container getContainer(final int id, final EntityPlayer player) {
		switch (id) {
		case 0:return new ContainerPlayer(player);
		default:return null;
		}
	}

	public void openGui(byte id, EntityPlayer player) {
		if (player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
			Container container = getContainer(id, player);
			if (container == null) return;
			playerMP.closeContainer();
			playerMP.getNextWindowId();
			int windowId = playerMP.currentWindowId;

			OpenGuiMessage message = new OpenGuiMessage();
			message.windowID = windowId;
			message.id = id;
			RpgInventoryMod.network.sendTo(message, playerMP);

			player.openContainer = container;
			playerMP.openContainer.windowId = windowId;
			playerMP.openContainer.addCraftingToCrafters(playerMP);
		}
	}

}
