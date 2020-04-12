package timaxa007.rpg_inv.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.IIcon;
import timaxa007.rpg_inv.api.IItemEquipment;
import timaxa007.rpg_inv.registry.RpgEntityIEEP;

public class ContainerPlayer extends Container {

	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
	public IInventory craftResult = new InventoryCraftResult();
	private final EntityPlayer thePlayer;
	/*
	private final int
	CRAFT_MATRIX = 4,//4
	RESULT_CRAFT = 1,//5
	ARMOR = 4,//9
	HOTBAR = 9,//18
	MAIN_INV = 3 * 9,//27 -> 45
	EXD_INV = 8;//53
	 */
	public ContainerPlayer(final EntityPlayer player) {
		thePlayer = player;
		addSlotToContainer(new SlotCrafting(player, craftMatrix, craftResult, 0, 144, 36));
		int i;
		int j;

		for (i = 0; i < 2; ++i) {
			for (j = 0; j < 2; ++j) {
				addSlotToContainer(new Slot(craftMatrix, j + i * 2, 88 + j * 18, 26 + i * 18));
			}
		}

		for (i = 0; i < 4; ++i) {
			final int k = i;
			addSlotToContainer(new Slot(player.inventory, player.inventory.getSizeInventory() - 1 - i, 8, 8 + i * 18) {

				@Override
				public int getSlotStackLimit() {
					return 1;
				}

				@Override
				public boolean isItemValid(ItemStack itemStack) {
					if (itemStack == null) return false;
					return itemStack.getItem().isValidArmor(itemStack, k, thePlayer);
				}

				@SideOnly(Side.CLIENT)
				public IIcon getBackgroundIconIndex() {
					return ItemArmor.func_94602_b(k);
				}

			});
		}

		for (i = 0; i < 3; ++i) {
			for (j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(player.inventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
		}

		RpgEntityIEEP ieep = RpgEntityIEEP.get(player);
		if (ieep != null) {
			for (i = 0; i < ieep.getInventory().getSizeInventory(); ++i)
				addSlotToContainer(new SlotEquipment(ieep.getInventory(), i, 26 + (i / 4) * 18, 8 + ((i % 4) * 18), ieep.eep[i], player));
		}

		onCraftMatrixChanged(craftMatrix);
	}

	@Override
	public void onCraftMatrixChanged(IInventory p_75130_1_) {
		craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, thePlayer.worldObj));
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		for (int i = 0; i < 4; ++i) {
			ItemStack itemstack = craftMatrix.getStackInSlotOnClosing(i);

			if (itemstack != null)
				player.dropPlayerItemWithRandomChoice(itemstack, false);
		}

		craftResult.setInventorySlotContents(0, null);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		ItemStack itemstack = null;
		Slot slot = (Slot)inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotID == 0) {
				if (!mergeItemStack(itemstack1, 9, 45, true)) return null;

				slot.onSlotChange(itemstack1, itemstack);
			}

			else if (slotID >= 1 && slotID < 5) {
				if (!mergeItemStack(itemstack1, 9, 45, false)) return null;
			}

			else if (slotID >= 5 && slotID < 9) {
				if (!mergeItemStack(itemstack1, 9, 45, false)) return null;
			}

			else if (itemstack.getItem() instanceof ItemArmor && !((Slot)inventorySlots.get(5 + ((ItemArmor)itemstack.getItem()).armorType)).getHasStack()) {
				int j = 5 + ((ItemArmor)itemstack.getItem()).armorType;

				if (!mergeItemStack(itemstack1, j, j + 1, false)) return null;
			}

			else if (slotID >= 9 && slotID < 45 && itemstack.getItem() instanceof IItemEquipment) {
				boolean flag = false;

				for (int i = 0; i < RpgEntityIEEP.eep.length; ++i) {
					int j = 45 + i;
					Slot slot2 = (Slot)inventorySlots.get(j);
					if (!slot2.getHasStack() && slot2 instanceof SlotEquipment) {
						if (((SlotEquipment)slot2).equipmentPart == ((IItemEquipment)itemstack.getItem()).isEnumEquipmentPart(itemstack, ((SlotEquipment)slot2).equipmentPart, player)) {
							if (!mergeItemStack(itemstack1, j, j + 1, false)) return null;
							flag = true;
							break;
						}
					}
				}

				if (!flag) {
					if (slotID < 36) {
						if (!mergeItemStack(itemstack1, 36, 45, false)) return null;
					} else {
						if (!mergeItemStack(itemstack1, 9, 36, false)) return null;
					}
				}

			}

			else if (slotID >= 9 && slotID < 36) {
				if (!mergeItemStack(itemstack1, 36, 45, false)) return null;
			}

			else if (slotID >= 36 && slotID < 45) {
				if (!mergeItemStack(itemstack1, 9, 36, false)) return null;
			}

			else if (slotID >= 45 && slotID < 53) {
				if (!mergeItemStack(itemstack1, 9, 45, false)) return null;
			}

			else if (!mergeItemStack(itemstack1, 9, 45, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize) return null;

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean func_94530_a(ItemStack itemStack, Slot slot) {
		return slot.inventory != craftResult && super.func_94530_a(itemStack, slot);
	}

}
