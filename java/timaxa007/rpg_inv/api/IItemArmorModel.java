package timaxa007.rpg_inv.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IItemArmorModel {

	ArmorModel getModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot);

}
