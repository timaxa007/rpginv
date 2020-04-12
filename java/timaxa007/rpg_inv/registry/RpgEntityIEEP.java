package timaxa007.rpg_inv.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants.NBT;

public class RpgEntityIEEP implements IExtendedEntityProperties {

	private static final String ID = "RpgEntityIEEP";
	private EntityPlayer player;
	public static EnumEquipmentPart[] eep = new EnumEquipmentPart[]{
			EnumEquipmentPart.MASK,
			EnumEquipmentPart.NECK,
			EnumEquipmentPart.BELT,
			EnumEquipmentPart.GLOVES,
			EnumEquipmentPart.CLOAK,
			EnumEquipmentPart.SHOULDERS,
			EnumEquipmentPart.SLEEVES,
			EnumEquipmentPart.AMULET,
			EnumEquipmentPart.BACKPACK,
			EnumEquipmentPart.RING,
			EnumEquipmentPart.RING
	};
	private InventoryBasic inventory = new InventoryBasic(ID, false, eep.length);
	public ItemStack[] oldItemEquipment = new ItemStack[eep.length];
	public ItemStack[] oldItemArmor = new ItemStack[4];

	public static String reg(final EntityPlayer player) {
		return player.registerExtendedProperties(RpgEntityIEEP.ID, new RpgEntityIEEP());
	}

	public static RpgEntityIEEP get(final EntityPlayer player) {
		return (RpgEntityIEEP)player.getExtendedProperties(RpgEntityIEEP.ID);
	}

	public InventoryBasic getInventory() {
		return inventory;
	}

	@Override
	public void init(Entity entity, World world) {
		if (entity instanceof EntityPlayer) player = (EntityPlayer)entity;
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		if (compound.hasKey(ID, NBT.TAG_COMPOUND)) {

			NBTTagCompound nbt = compound.getCompoundTag(ID);
			//if (nbt.hasKey("special", NBT.TAG_INT)) special = nbt.getInteger("special");
			//if (nbt.hasKey("points", NBT.TAG_BYTE)) points = nbt.getByte("points");

			NBTTagList nbttaglist = nbt.getTagList("Items", 10);
			inventory = new InventoryBasic(ID, false, inventory.getSizeInventory());
			//this.chestContents = new ItemStack[this.getSizeInventory()];

			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				int j = nbttagcompound1.getByte("Slot") & 255;

				if (j >= 0 && j < inventory.getSizeInventory()) {
					inventory.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
					//this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				}
			}

		}
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		//nbt.setInteger("special", special);
		//nbt.setByte("points", points);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			if (inventory.getStackInSlot(i) != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				inventory.getStackInSlot(i).writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("Items", nbttaglist);

		compound.setTag(ID, nbt);
	}

}
