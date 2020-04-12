package timaxa007.rpg_inv.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import timaxa007.rpg_inv.registry.EnumEquipmentPart;

public interface IItemEquipmentRenderer {

	boolean hasRender(EntityPlayer entityPlayer, ItemStack itemStack, EnumEquipmentPart part);
	EquipmentModel getModel(EntityPlayer entityPlayer, ItemStack itemStack, EnumEquipmentPart part);

}
