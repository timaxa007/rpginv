package timaxa007.rpg_inv.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import timaxa007.rpg_inv.api.IItemEquipment;
import timaxa007.rpg_inv.registry.EnumEquipmentPart;

public class SlotEquipment extends Slot {

	public final EnumEquipmentPart equipmentPart;
	public final EntityPlayer player;

	public SlotEquipment(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition, EnumEquipmentPart equipmentPart, EntityPlayer player) {
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
		this.equipmentPart = equipmentPart;
		this.player = player;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack itemStack) {
		if (itemStack == null) return false;
		if (itemStack.getItem() instanceof IItemEquipment)
			return ((IItemEquipment)itemStack.getItem()).isEnumEquipmentPart(itemStack, equipmentPart, player) == equipmentPart;
		return false;
	}

}
