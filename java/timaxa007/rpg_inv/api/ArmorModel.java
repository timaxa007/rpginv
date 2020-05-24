package timaxa007.rpg_inv.api;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

@SideOnly(Side.CLIENT)
public abstract class ArmorModel {

	public abstract void pre(int slot, ItemStack itemStack, EntityLivingBase entity);//До всех частей.
	public abstract void post(int slot, ItemStack itemStack, EntityLivingBase entity);//После всех частей.
	public abstract void partHead(int slot, ItemStack itemStack, EntityLivingBase entity);//Часть: Голова.
	public abstract void partBody(int slot, ItemStack itemStack, EntityLivingBase entity);//Часть: Тело.
	public abstract void partRightArm(int slot, ItemStack itemStack, EntityLivingBase entity);//Часть: Правая рука.
	public abstract void partLeftArm(int slot, ItemStack itemStack, EntityLivingBase entity);//Часть: Левая рука.
	public abstract void partRightLeg(int slot, ItemStack itemStack, EntityLivingBase entity);//Часть: Правая нога.
	public abstract void partLeftLeg(int slot, ItemStack itemStack, EntityLivingBase entity);//Часть: Левая нога.

	public void render(ModelBiped modelBipedMain, int slot, ItemStack itemStack, EntityLivingBase entity, float partialRenderTick) {

		GL11.glPushMatrix();
		/*
		if (color != -1) {
			float red = (float)(color >> 16 & 255) / 255F;
			float blue = (float)(color >> 8 & 255) / 255F;
			float green = (float)(color & 255) / 255F;
			GL11.glColor3f(red, blue, green);
		}
		 */
		pre(slot, itemStack, entity);

		float parTicks = 0.0625F;
		float f6 = 2.0F;

		if (modelBipedMain.bipedHead.showModel) {//partHead
			GL11.glPushMatrix();
			if (entity.isChild()) {
				GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
				GL11.glTranslatef(0.0F, 16.0F * parTicks, 0.0F);
			}
			modelBipedMain.bipedHead.postRender(parTicks);
			GL11.glRotatef(180F, 1F, 0F, 0F);
			partHead(slot, itemStack, entity);
			GL11.glPopMatrix();
		}

		if (entity.isChild()) {
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * parTicks, 0.0F);
		}

		if (modelBipedMain.bipedBody.showModel) {//partBody
			GL11.glPushMatrix();
			modelBipedMain.bipedBody.postRender(parTicks);
			GL11.glRotatef(180F, 1F, 0F, 0F);
			partBody(slot, itemStack, entity);
			GL11.glPopMatrix();
		}

		if (modelBipedMain.bipedRightArm.showModel) {//partRightArm
			GL11.glPushMatrix();
			modelBipedMain.bipedRightArm.postRender(parTicks);
			GL11.glRotatef(180F, 1F, 0F, 0F);
			partRightArm(slot, itemStack, entity);
			GL11.glPopMatrix();
		}

		if (modelBipedMain.bipedLeftArm.showModel) {//partLeftArm
			GL11.glPushMatrix();
			modelBipedMain.bipedLeftArm.postRender(parTicks);
			GL11.glRotatef(180F, 1F, 0F, 0F);
			partLeftArm(slot, itemStack, entity);
			GL11.glPopMatrix();
		}

		if (modelBipedMain.bipedRightLeg.showModel) {//partRightLeg
			GL11.glPushMatrix();
			modelBipedMain.bipedRightLeg.postRender(parTicks);
			GL11.glRotatef(180F, 1F, 0F, 0F);
			partRightLeg(slot, itemStack, entity);
			GL11.glPopMatrix();
		}

		if (modelBipedMain.bipedLeftLeg.showModel) {//partLeftLeg
			GL11.glPushMatrix();
			modelBipedMain.bipedLeftLeg.postRender(parTicks);
			GL11.glRotatef(180F, 1F, 0F, 0F);
			partLeftLeg(slot, itemStack, entity);
			GL11.glPopMatrix();
		}

		if (entity.isChild()) {
			GL11.glPopMatrix();
		}

		post(slot, itemStack, entity);

		GL11.glColor4f(1F, 1F, 1F, 1F);

		GL11.glPopMatrix();

	}

}

