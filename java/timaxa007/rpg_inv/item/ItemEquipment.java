package timaxa007.rpg_inv.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import timaxa007.rpg_inv.api.EquipmentModel;
import timaxa007.rpg_inv.api.IItemEquipment;
import timaxa007.rpg_inv.api.IItemEquipmentModel;
import timaxa007.rpg_inv.registry.EnumEquipmentPart;

public class ItemEquipment extends Item
implements IItemEquipment, IItemEquipmentModel {

	private final EnumEquipmentPart equipmentPart;

	public ItemEquipment(EnumEquipmentPart equipmentPart) {
		this.equipmentPart = equipmentPart;
	}

	@Override
	public void onUpdateItemEquipment(Entity entity, ItemStack itemStack) {

	}

	@Override
	public EnumEquipmentPart isEnumEquipmentPart(ItemStack itemStack, EnumEquipmentPart slot, Entity entity) {
		return equipmentPart;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public EquipmentModel getModel(EntityPlayer entityPlayer, ItemStack itemStack, EnumEquipmentPart part) {
		return timaxa007.rpg_inv.client.Proxy.model_test;
	}

}
