package timaxa007.rpg_inv.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import timaxa007.rpg_inv.api.EquipmentModel;
import timaxa007.rpg_inv.api.IItemEquipmentRenderer;
import timaxa007.rpg_inv.registry.EnumEquipmentPart;

public class ModelItemUniEquipment implements IItemEquipmentRenderer {

	@Override
	public boolean hasRender(EntityPlayer entityPlayer, ItemStack itemStack, EnumEquipmentPart part) {
		return true;
	}

	@Override
	public EquipmentModel getModel(EntityPlayer entityPlayer, ItemStack itemStack, EnumEquipmentPart part) {
		return timaxa007.rpg_inv.client.Proxy.model_test;
	}

}
