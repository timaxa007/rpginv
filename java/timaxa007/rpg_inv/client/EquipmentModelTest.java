package timaxa007.rpg_inv.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import timaxa007.rpg_inv.api.EquipmentModel;
import timaxa007.rpg_inv.registry.EnumEquipmentPart;

public class EquipmentModelTest extends EquipmentModel {

	public static final ResourceLocation texture_wood = new ResourceLocation("textures/blocks/planks_oak.png");

	public EquipmentModelTest() {}

	@Override
	public void pre(EnumEquipmentPart equipmentPart, EntityLivingBase entity) {
		//GL11.glEnable(GL11.GL_BLEND);
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture_wood);
	}

	@Override
	public void post(EnumEquipmentPart equipmentPart, EntityLivingBase entity) {
		//GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public void partHead(EnumEquipmentPart equipmentPart, EntityLivingBase entity) {
		GL11.glTranslatef(0F, -1.5F, 0F);
		if (equipmentPart == EnumEquipmentPart.MASK) {
			GL11.glCallList(Proxy.getRenderPart("armor", "shield_up"));
			GL11.glCallList(Proxy.getRenderPart("armor", "shield_down"));
		}
	}

	@Override
	public void partBody(EnumEquipmentPart equipmentPart, EntityLivingBase entity) {
		GL11.glTranslatef(0F, -1.5F, 0F);
		if (equipmentPart == EnumEquipmentPart.BACKPACK) {
			if (entity.getEquipmentInSlot(3) != null) {
				GL11.glTranslatef(0F, 0F, -0.03125F);
			}
			GL11.glCallList(Proxy.getRenderAll("equipment/backpack"));
		}
	}

	@Override
	public void partRightArm(EnumEquipmentPart equipmentPart, EntityLivingBase entity) {
		GL11.glTranslatef(0.3125F, -1.375F, 0F);
		if (equipmentPart == EnumEquipmentPart.SHOULDERS) {
			GL11.glCallList(Proxy.getRenderPart("armor", "right_shoulders_big"));
		}
		if (equipmentPart == EnumEquipmentPart.SLEEVES) {
			GL11.glCallList(Proxy.getRenderPart("armor", "right_sleeves"));
		}
	}

	@Override
	public void partLeftArm(EnumEquipmentPart equipmentPart, EntityLivingBase entity) {
		GL11.glTranslatef(-0.3125F, -1.375F, 0F);
		if (equipmentPart == EnumEquipmentPart.SHOULDERS) {
			GL11.glCallList(Proxy.getRenderPart("armor", "left_shoulders_big"));
		}
		if (equipmentPart == EnumEquipmentPart.SLEEVES) {
			GL11.glCallList(Proxy.getRenderPart("armor", "left_sleeves"));
		}
	}

	@Override
	public void partRightLeg(EnumEquipmentPart equipmentPart, EntityLivingBase entity) {
		GL11.glTranslatef(0.125F, -0.75F, 0F);
	}

	@Override
	public void partLeftLeg(EnumEquipmentPart equipmentPart, EntityLivingBase entity) {
		GL11.glTranslatef(-0.125F, -0.75F, 0F);
	}

}
