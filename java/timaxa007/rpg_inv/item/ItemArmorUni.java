package timaxa007.rpg_inv.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemArmorUni extends Item {

	@Override
	public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
		//if (armorType == 0) return true;
		return true;
	}

}
