package timaxa007.rpg_inv.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import timaxa007.rpg_inv.RpgInventoryMod;
import timaxa007.rpg_inv.api.ArmorModel;
import timaxa007.rpg_inv.api.EquipmentModel;
import timaxa007.rpg_inv.api.IItemArmorModel;
import timaxa007.rpg_inv.api.IItemArmorRenderer;
import timaxa007.rpg_inv.api.IItemEquipmentModel;
import timaxa007.rpg_inv.api.IItemEquipmentRenderer;
import timaxa007.rpg_inv.client.registry.RenderRegistryItemArmor;
import timaxa007.rpg_inv.client.registry.RenderRegistryItemEquipment;
import timaxa007.rpg_inv.network.OpenGuiMessage;
import timaxa007.rpg_inv.registry.RpgEntityIEEP;

public class Events {

	@SubscribeEvent
	public void openGuinventory(GuiOpenEvent event)  {
		if (event.gui instanceof GuiInventory) {
			if (!Minecraft.getMinecraft().playerController.isInCreativeMode()) {
				//RpgInventoryMod.proxy.openGui((byte)0, Minecraft.getMinecraft().thePlayer);
				OpenGuiMessage message = new OpenGuiMessage();
				message.id = 0;
				RpgInventoryMod.network.sendToServer(message);
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onRenderArmomForPlayer(RenderPlayerEvent.Specials.Post event) {
		RpgEntityIEEP ieep = RpgEntityIEEP.get(event.entityPlayer);
		if (ieep == null) return;

		if (event.entityPlayer.getCurrentEquippedItem() != null) GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		for (int i = 0; i < ieep.getInventory().getSizeInventory(); ++i) {
			ItemStack slot = ieep.getInventory().getStackInSlot(i);
			if (slot == null) continue;

			EquipmentModel acm = null;

			IItemEquipmentRenderer iier = RenderRegistryItemEquipment.getItemEquipmentModel(event.entityPlayer, slot, RpgEntityIEEP.eep[i]);
			if (iier != null && iier.hasRender(event.entityPlayer, slot, RpgEntityIEEP.eep[i])) {
				acm = iier.getModel(event.entityPlayer, slot, RpgEntityIEEP.eep[i]);
			}

			else if (slot.getItem() instanceof IItemEquipmentModel) {
				acm = ((IItemEquipmentModel)slot.getItem()).getModel(event.entityPlayer, slot, RpgEntityIEEP.eep[i]);
			}

			if (acm != null) {
				acm.render(event.renderer.modelBipedMain, RpgEntityIEEP.eep[i], slot, event.entityPlayer, event.partialRenderTick);
			}
		}

		if (event.entityPlayer.getCurrentEquippedItem() != null) GL11.glDisable(GL12.GL_RESCALE_NORMAL);

	}

	@SubscribeEvent
	public void onRenderArmomForPlayer(RenderPlayerEvent.SetArmorModel event) {

		if (event.stack == null) return;

		ArmorModel acm = null;

		IItemArmorRenderer iier = RenderRegistryItemArmor.getItemArmorModel(event.entityPlayer, event.stack, event.slot);
		if (iier != null && iier.hasRender(event.entityPlayer, event.stack, event.slot)) {
			acm = iier.getModel(event.entityPlayer, event.stack, event.slot);
		}

		else if (event.stack.getItem() instanceof IItemArmorModel) {
			acm = ((IItemArmorModel)event.stack.getItem()).getModel(event.entityPlayer, event.stack, event.slot);
		}

		if (acm != null) {
			acm.render(event.renderer.modelBipedMain, event.slot, event.stack, event.entityPlayer, event.partialRenderTick);
		}
	}

}
