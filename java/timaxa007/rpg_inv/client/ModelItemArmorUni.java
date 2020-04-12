package timaxa007.rpg_inv.client;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import timaxa007.rpg_inv.api.ArmorModel;
import timaxa007.rpg_inv.api.IItemArmorRenderer;

public class ModelItemArmorUni implements IItemArmorRenderer {

	@Override
	public boolean hasRender(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return true;
	}

	@Override
	public ArmorModel getModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return timaxa007.rpg_inv.client.Proxy.model_armor_test;
	}

}
