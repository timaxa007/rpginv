package timaxa007.rpg_inv.client.registry;

import java.util.IdentityHashMap;

import com.google.common.collect.Maps;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import timaxa007.rpg_inv.api.IItemArmorRenderer;

public class RenderRegistryItemArmor {

	private static final IdentityHashMap<Item, IItemArmorRenderer> models = Maps.newIdentityHashMap();

	public static void registerItemArmorModel(Item item, IItemArmorRenderer renderer) {
		models.put(item, renderer);
	}

	public static IItemArmorRenderer getItemArmorModel(EntityPlayer entityPlayer, ItemStack itemStack, int slot) {
		IItemArmorRenderer renderer = models.get(itemStack.getItem());
		if (renderer != null && renderer.hasRender(entityPlayer, itemStack, slot))
			return renderer;
		return null;
	}

}
