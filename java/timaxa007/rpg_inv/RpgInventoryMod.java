package timaxa007.rpg_inv;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.MinecraftForge;
import timaxa007.rpg_inv.item.ItemArmorTest;
import timaxa007.rpg_inv.item.ItemArmorUni;
import timaxa007.rpg_inv.item.ItemEquipment;
import timaxa007.rpg_inv.item.ItemUniEquipment;
import timaxa007.rpg_inv.network.OpenGuiMessage;
import timaxa007.rpg_inv.network.SyncItemEquipmentMessage;
import timaxa007.rpg_inv.network.SyncRpgEntityMessage;
import timaxa007.rpg_inv.registry.EnumEquipmentPart;

@Mod(modid = RpgInventoryMod.MODID, name = RpgInventoryMod.NAME, version = RpgInventoryMod.VERSION)
public class RpgInventoryMod {

	public static final String
	MODID = "rpginv",
	NAME = "RPG Inventory Mod",
	VERSION = "0.3";

	@Mod.Instance(MODID)
	public static RpgInventoryMod instance;

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	@SidedProxy(modId=MODID,
			clientSide="timaxa007.rpg_inv.client.Proxy",
			serverSide="timaxa007.rpg_inv.Proxy")
	public static Proxy proxy;

	public static Item
	itemUniEquipment,

	itemArmorHelmet,
	itemArmorVest,
	itemArmorPants,
	itemArmorBoots,

	itemArmorUni;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		network.registerMessage(OpenGuiMessage.Handler.class, OpenGuiMessage.class, 0, Side.CLIENT);
		network.registerMessage(OpenGuiMessage.Handler.class, OpenGuiMessage.class, 0, Side.SERVER);

		network.registerMessage(SyncRpgEntityMessage.Handler.class, SyncRpgEntityMessage.class, 1, Side.CLIENT);
		//network.registerMessage(SyncRpgEntityMessage.Handler.class, SyncRpgEntityMessage.class, 1, Side.SERVER);

		network.registerMessage(SyncItemEquipmentMessage.Handler.class, SyncItemEquipmentMessage.class, 2, Side.CLIENT);
		//network.registerMessage(SyncItemEquipmentMessage.Handler.class, SyncItemEquipmentMessage.class, 2, Side.SERVER);

		//Config.init(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new Events());
		//FMLCommonHandler.instance().bus().register(new EventFML());

		int i = 0;
		for (Item itemEquipment; i < EnumEquipmentPart.names.length; ++i) {//is the Magic
			itemEquipment = new ItemEquipment(EnumEquipmentPart.values()[i])
					.setUnlocalizedName("equipment_" + EnumEquipmentPart.names[i])
					.setTextureName(MODID + ":"  + EnumEquipmentPart.names[i])
					.setMaxStackSize(1)
					.setCreativeTab(CreativeTabs.tabMisc);
			GameRegistry.registerItem(itemEquipment, "item_equipment_" + EnumEquipmentPart.names[i]);
		}

		itemUniEquipment = new ItemUniEquipment()
				.setMaxStackSize(1)
				.setUnlocalizedName("uni_equipment")
				.setTextureName(MODID + ":uni_equipment")
				.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(itemUniEquipment, "item_uni_equipment");


		itemArmorHelmet = new ItemArmorTest(ArmorMaterial.IRON, 0)
				.setUnlocalizedName("armor_helmet")
				.setTextureName(MODID + ":armor_helmet")
				.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(itemArmorHelmet, "item_armor_helmet");

		itemArmorVest = new ItemArmorTest(ArmorMaterial.IRON, 1)
				.setUnlocalizedName("armor_vest")
				.setTextureName(MODID + ":armor_vest")
				.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(itemArmorVest, "item_armor_vest");

		itemArmorPants = new ItemArmorTest(ArmorMaterial.IRON, 2)
				.setUnlocalizedName("armor_pants")
				.setTextureName(MODID + ":armor_pants")
				.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(itemArmorPants, "item_armor_pants");

		itemArmorBoots = new ItemArmorTest(ArmorMaterial.IRON, 3)
				.setUnlocalizedName("armor_boots")
				.setTextureName(MODID + ":armor_boots")
				.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(itemArmorBoots, "item_armor_boots");


		itemArmorUni = new ItemArmorUni()
				.setMaxStackSize(1)
				.setMaxDamage(0)
				.setUnlocalizedName("armor_uni")
				.setTextureName(MODID + ":armor_uni")
				.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(itemArmorUni, "item_armor_uni");


		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}

}
