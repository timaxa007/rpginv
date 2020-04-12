package timaxa007.rpg_inv.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import timaxa007.rpg_inv.api.ArmorModel;

public class ArmorModelTest extends ArmorModel {

	public static final ResourceLocation texture_wood = new ResourceLocation("textures/blocks/planks_oak.png");

	public ArmorModelTest() {}

	@Override
	public void pre(int slot, EntityLivingBase entity) {
		//GL11.glEnable(GL11.GL_BLEND);
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture_wood);
	}

	@Override
	public void post(int slot, EntityLivingBase entity) {
		//GL11.glDisable(GL11.GL_BLEND);
	}

	@Override
	public void partHead(int slot, EntityLivingBase entity) {
		GL11.glTranslatef(0F, -1.5F, 0F);
		if (slot == 3) {
			GL11.glCallList(Proxy.getRenderPart("armor", "helmet"));
		}
	}

	@Override
	public void partBody(int slot, EntityLivingBase entity) {
		GL11.glTranslatef(0F, -1.5F, 0F);
		if (slot == 2) {
			GL11.glCallList(Proxy.getRenderPart("armor", "chest_up"));
			GL11.glCallList(Proxy.getRenderPart("armor", "chest_side_right"));
			GL11.glCallList(Proxy.getRenderPart("armor", "chest_side_left"));
			GL11.glCallList(Proxy.getRenderPart("armor", "chest_front"));
			GL11.glCallList(Proxy.getRenderPart("armor", "chest_back"));
		}
	}

	@Override
	public void partRightArm(int slot, EntityLivingBase entity) {
		GL11.glTranslatef(0.3125F, -1.375F, 0F);
		if (slot == 2) {
			GL11.glCallList(Proxy.getRenderPart("armor", "right_shoulders"));
		}
	}

	@Override
	public void partLeftArm(int slot, EntityLivingBase entity) {
		GL11.glTranslatef(-0.3125F, -1.375F, 0F);
		if (slot == 2) {
			GL11.glCallList(Proxy.getRenderPart("armor", "left_shoulders"));
		}
	}

	@Override
	public void partRightLeg(int slot, EntityLivingBase entity) {
		GL11.glTranslatef(0.125F, -0.75F, 0F);
		if (slot == 1) {
			GL11.glCallList(Proxy.getRenderPart("armor", "right_leggin"));
		}
		if (slot == 0) {
			GL11.glCallList(Proxy.getRenderPart("armor", "right_boot"));
		}
	}

	@Override
	public void partLeftLeg(int slot, EntityLivingBase entity) {
		GL11.glTranslatef(-0.125F, -0.75F, 0F);
		if (slot == 1) {
			GL11.glCallList(Proxy.getRenderPart("armor", "left_leggin"));
		}
		if (slot == 0) {
			GL11.glCallList(Proxy.getRenderPart("armor", "left_boot"));
		}
	}

}
