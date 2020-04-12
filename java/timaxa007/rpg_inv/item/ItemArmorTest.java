package timaxa007.rpg_inv.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import timaxa007.rpg_inv.api.ArmorModel;
import timaxa007.rpg_inv.api.IItemArmorModel;

public class ItemArmorTest extends ItemArmor implements IItemArmorModel {

	public ItemArmorTest(ArmorMaterial armorMaterial, int armorType) {
		super(armorMaterial, 0, armorType);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ArmorModel getModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return timaxa007.rpg_inv.client.Proxy.model_armor_test;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		return timaxa007.rpg_inv.client.Proxy.modelEpmty;
	}

}
