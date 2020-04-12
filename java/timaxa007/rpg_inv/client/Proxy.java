package timaxa007.rpg_inv.client;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;
import timaxa007.rpg_inv.RpgInventoryMod;
import timaxa007.rpg_inv.api.ArmorModel;
import timaxa007.rpg_inv.api.EquipmentModel;
import timaxa007.rpg_inv.client.registry.RenderRegistryItemArmor;
import timaxa007.rpg_inv.client.registry.RenderRegistryItemEquipment;

public class Proxy extends timaxa007.rpg_inv.Proxy {

	public static final ModelBiped modelEpmty = new ModelBiped();
	static {modelEpmty.bipedHead.showModel = 
			modelEpmty.bipedHeadwear.showModel = 
			modelEpmty.bipedBody.showModel = 
			modelEpmty.bipedRightArm.showModel = 
			modelEpmty.bipedLeftArm.showModel = 
			modelEpmty.bipedRightLeg.showModel = 
			modelEpmty.bipedLeftLeg.showModel = false;
	}

	//public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(RpgInventoryMod.MODID, "models/equipment/test.obj"));

	public static final EquipmentModel model_test = new EquipmentModelTest();
	
	public static final ArmorModel model_armor_test = new ArmorModelTest();

	//--------------------------------------------------------------------------------------
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new Events());
		//FMLCommonHandler.instance().bus().register(new Events());
	}

	@Override
	public void init() {
		//getRenderPart("sword", "head");
		//getRenderPart("sword", "stick");
		RenderRegistryItemEquipment.registerItemEquipmentModel(RpgInventoryMod.itemUniEquipment, new ModelItemUniEquipment());
		
		RenderRegistryItemArmor.registerItemArmorModel(RpgInventoryMod.itemArmorUni, new ModelItemArmorUni());
	}
	//--------------------------------------------------------------------------------------
	private static GuiScreen getGui(final int id, final EntityPlayer player) {
		switch (id) {
		case 0:return new InventoryGui(player);
		default:return null;
		}
	}

	public void openGui(byte id, EntityPlayer player) {
		if (player instanceof EntityPlayerMP)
			super.openGui(id, player);
		else if (FMLCommonHandler.instance().getSide().equals(Side.CLIENT)) {
			GuiScreen gui = getGui(id, player);
			if (gui == null) return;
			Minecraft.getMinecraft().displayGuiScreen(gui);
		}
	}
	//--------------------------------------------------------------------------------------
	private static final HashMap<String, EquipmentModel> models = new HashMap<String, EquipmentModel>();


	public static EquipmentModel getModel(String name_model) {
		if (models.containsKey(name_model)) return models.get(name_model);
		EquipmentModel model = null;
		switch(name_model) {
		case "model_test":
			model = new EquipmentModelTest();
			break;
		}
		if (model != null) models.put(name_model, model);
		return model;
	}
	//--------------------------------------------------------------------------------------
	private static final HashMap<String, Integer> hash = new HashMap<String, Integer>();

	public static int getRenderAll(String model) {
		if (hash.containsKey(model)) return hash.get(model);
		int displayList = GLAllocation.generateDisplayLists(1);
		GL11.glNewList(displayList, GL11.GL_COMPILE);
		AdvancedModelLoader.loadModel(new ResourceLocation(RpgInventoryMod.MODID, "models/" + model + ".obj")).renderAll();
		GL11.glEndList();
		hash.put(model, displayList);
		return displayList;
	}

	public static int getRenderPart(String model, String partName) {
		if (hash.containsKey(model + "_" + partName)) return hash.get(model + "_" + partName);
		int displayList = GLAllocation.generateDisplayLists(1);
		GL11.glNewList(displayList, GL11.GL_COMPILE);
		AdvancedModelLoader.loadModel(new ResourceLocation(RpgInventoryMod.MODID, "models/" + model + ".obj")).renderPart(partName);
		GL11.glEndList();
		hash.put(model + "_" + partName, displayList);
		return displayList;
	}
	//--------------------------------------------------------------------------------------
}
