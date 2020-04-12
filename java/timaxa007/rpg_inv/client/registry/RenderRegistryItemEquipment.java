package timaxa007.rpg_inv.client.registry;

import java.util.IdentityHashMap;

import com.google.common.collect.Maps;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import timaxa007.rpg_inv.api.IItemEquipmentRenderer;
import timaxa007.rpg_inv.registry.EnumEquipmentPart;

public class RenderRegistryItemEquipment {

	private static final IdentityHashMap<Item, IItemEquipmentRenderer> models = Maps.newIdentityHashMap();

	public static void registerItemEquipmentModel(Item item, IItemEquipmentRenderer renderer) {
		models.put(item, renderer);
	}

	public static IItemEquipmentRenderer getItemEquipmentModel(EntityPlayer entityPlayer, ItemStack itemStack, EnumEquipmentPart part) {
		IItemEquipmentRenderer renderer = models.get(itemStack.getItem());
		if (renderer != null && renderer.hasRender(entityPlayer, itemStack, part))
			return renderer;
		return null;
	}

}
