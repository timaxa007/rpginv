package timaxa007.rpg_inv.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import timaxa007.rpg_inv.registry.EnumEquipmentPart;

public interface IItemEquipment {

	void onUpdateItemEquipment(Entity entity, ItemStack itemStack);
	EnumEquipmentPart isEnumEquipmentPart(ItemStack itemStack, EnumEquipmentPart slot, Entity entity);

}
