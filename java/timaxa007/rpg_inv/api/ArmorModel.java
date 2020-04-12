package timaxa007.rpg_inv.api;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;

@SideOnly(Side.CLIENT)
public abstract class ArmorModel {

	public final PartModel
	partHead = new PartModel(),
	partBody = new PartModel(),
	partRightArm = new PartModel(),
	partLeftArm = new PartModel(),
	partRightLeg = new PartModel(),
	partLeftLeg = new PartModel();

	public int color = -1;//Раскраска брони в цвет.

	public abstract void pre(int slot, EntityLivingBase entity);//До всех частей.
	public abstract void post(int slot, EntityLivingBase entity);//После всех частей.
	public abstract void partHead(int slot, EntityLivingBase entity);//Часть: Голова.
	public abstract void partBody(int slot, EntityLivingBase entity);//Часть: Тело.
	public abstract void partRightArm(int slot, EntityLivingBase entity);//Часть: Правая рука.
	public abstract void partLeftArm(int slot, EntityLivingBase entity);//Часть: Левая рука.
	public abstract void partRightLeg(int slot, EntityLivingBase entity);//Часть: Правая нога.
	public abstract void partLeftLeg(int slot, EntityLivingBase entity);//Часть: Левая нога.

	public void render(int slot, EntityLivingBase entity) {

		GL11.glPushMatrix();

		if (color != -1) {
			float red = (float)(color >> 16 & 255) / 255F;
			float blue = (float)(color >> 8 & 255) / 255F;
			float green = (float)(color & 255) / 255F;
			GL11.glColor3f(red, blue, green);
		}

		pre(slot, entity);

		float parTicks = 0.0625f;
		float f6 = 2.0F;

		if (partHead.showModel) {//partHead
			GL11.glPushMatrix();
			if (entity.isChild()) {
				GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
				GL11.glTranslatef(0.0F, 16.0F * parTicks, 0.0F);
			}
			blank(partHead, parTicks);
			partHead(slot, entity);
			//GL11.glTranslatef(-partHead.offsetX, -partHead.offsetY, -partHead.offsetZ);
			GL11.glPopMatrix();
		}

		if (entity.isChild()) {
			GL11.glPushMatrix();
			GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GL11.glTranslatef(0.0F, 24.0F * parTicks, 0.0F);
		}

		if (partBody.showModel) {//partBody
			GL11.glPushMatrix();
			blank(partBody, parTicks);
			partBody(slot, entity);
			//GL11.glTranslatef(-partBody.offsetX, -partBody.offsetY, -partBody.offsetZ);
			GL11.glPopMatrix();
		}

		if (partRightArm.showModel) {//partRightArm
			GL11.glPushMatrix();
			blank(partRightArm, parTicks);
			partRightArm(slot, entity);
			//GL11.glTranslatef(-partRightArm.offsetX, -partRightArm.offsetY, -partRightArm.offsetZ);
			GL11.glPopMatrix();
		}

		if (partLeftArm.showModel) {//partLeftArm
			GL11.glPushMatrix();
			blank(partLeftArm, parTicks);
			partLeftArm(slot, entity);
			//GL11.glTranslatef(-partLeftArm.offsetX, -partLeftArm.offsetY, -partLeftArm.offsetZ);
			GL11.glPopMatrix();
		}

		if (partRightLeg.showModel) {//partRightLeg
			GL11.glPushMatrix();
			blank(partRightLeg, parTicks);
			partRightLeg(slot, entity);
			//GL11.glTranslatef(-partRightLeg.offsetX, -partRightLeg.offsetY, -partRightLeg.offsetZ);
			GL11.glPopMatrix();
		}

		if (partLeftLeg.showModel) {//partLeftLeg
			GL11.glPushMatrix();
			blank(partLeftLeg, parTicks);
			partLeftLeg(slot, entity);
			//GL11.glTranslatef(-partLeftLeg.offsetX, -partLeftLeg.offsetY, -partLeftLeg.offsetZ);
			GL11.glPopMatrix();
		}

		if (entity.isChild()) {
			GL11.glPopMatrix();
		}

		post(slot, entity);

		GL11.glColor3f(1F, 1F, 1F);

		GL11.glPopMatrix();

	}

	private void blank(PartModel mr, float parTicks) {
		GL11.glTranslatef(mr.offsetX, mr.offsetY, mr.offsetZ);
		if (mr.rotationPointX != 0.0F || mr.rotationPointY != 0.0F || mr.rotationPointZ != 0.0F)
			GL11.glTranslatef(mr.rotationPointX * parTicks, mr.rotationPointY * parTicks, mr.rotationPointZ * parTicks);
		if (mr.rotateAngleZ != 0F) GL11.glRotatef(mr.rotateAngleZ * (180F / (float)Math.PI), 0F, 0F, 1F);
		if (mr.rotateAngleY != 0F) GL11.glRotatef(mr.rotateAngleY * (180F / (float)Math.PI), 0F, 1F, 0F);
		if (mr.rotateAngleX != 0F) GL11.glRotatef(mr.rotateAngleX * (180F / (float)Math.PI), 1F, 0F, 0F);
		GL11.glRotatef(180F, 1F, 0F, 0F);
		//GL11.glTranslatef(-mr.offsetX, -mr.offsetY, -mr.offsetZ);
	}

}

