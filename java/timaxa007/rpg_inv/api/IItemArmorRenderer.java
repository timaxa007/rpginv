package timaxa007.rpg_inv.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IItemArmorRenderer {

	boolean hasRender(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot);
	ArmorModel getModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot);

}
