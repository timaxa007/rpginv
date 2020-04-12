package timaxa007.rpg_inv.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import timaxa007.rpg_inv.registry.EnumEquipmentPart;

public interface IItemEquipmentModel {

	EquipmentModel getModel(EntityPlayer entityPlayer, ItemStack itemStack, EnumEquipmentPart part);

}
