package timaxa007.rpg_inv.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import timaxa007.rpg_inv.inventory.ContainerPlayer;
import timaxa007.rpg_inv.registry.EnumEquipmentPart;
import timaxa007.rpg_inv.registry.RpgEntityIEEP;

@SideOnly(Side.CLIENT)
public class InventoryGui extends GuiContainer {

	private int mouseLastX;
	private boolean isMove;
	private float rotateX;

	public InventoryGui(EntityPlayer player) {
		super(new ContainerPlayer(player));
		allowUserInput = true;
	}

	@Override
	public void updateScreen() {
		if (mc.playerController.isInCreativeMode())
			mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
	}

	@Override
	public void initGui() {
		buttonList.clear();

		if (mc.playerController.isInCreativeMode())
			mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
		else
			super.initGui();

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float p_73863_3_) {
		super.drawScreen(mouseX, mouseY, p_73863_3_);

		RpgEntityIEEP ieep = RpgEntityIEEP.get(mc.thePlayer);
		if (ieep != null) {
			for (int i = 0; i < RpgEntityIEEP.eep.length; ++i) {
				if (ieep.getInventory().getStackInSlot(i) != null) continue;
				int offsetX = guiLeft + 25 + (i / 4) * 18;
				int offsetY = guiTop + 7 + ((i % 4) * 18);
				if (offsetX <= mouseX && offsetX + 18 > mouseX && offsetY <= mouseY && offsetY + 18 > mouseY) {
					List<String> list = new ArrayList<String>();
					//list.add("Slot for \"" + EnumEquipmentPart.names[RpgEntityIEEP.eep[i].ordinal()] + "\" type.");
					list.add(StatCollector.translateToLocalFormatted("slot_for.text",
							StatCollector.translateToLocal(EnumEquipmentPart.names[RpgEntityIEEP.eep[i].ordinal()] + ".text")));
					drawHoveringText(list, mouseX, mouseY, fontRendererObj);
				}
			}
		}

		func_147044_g();
	}

	@Override
	public void handleMouseInput() {
		super.handleMouseInput();
		int mouseX = Mouse.getEventX() * width / mc.displayWidth;
		int mouseY = height - Mouse.getEventY() * height / mc.displayHeight - 1;

		if (isMove) rotateX -= (mouseLastX - mouseX) * 0.75F;

		if (rotateX > 360F) rotateX -= 360F;
		if (rotateX < 0F) rotateX += 360F;

		if (Mouse.getEventButton() == 0) {
			if (isMove) {
				if (!Mouse.getEventButtonState()) isMove = false;

			} else {
				if (Mouse.getEventButtonState())
					if (mouseX >= 0 && mouseX <= guiLeft && mouseY >= 0 && mouseY <= height)
						isMove = true;
			}
		}

		mouseLastX = mouseX;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float parTick, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(field_147001_a);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		for (int i = 0; i < 3; ++i)
			drawTexturedModalRect(guiLeft + 25 + (18 * i), guiTop + 8, 7, 8, 18, 18 * (i == 2? 3 : 4));

		GL11.glPushMatrix();
		GL11.glTranslatef(guiLeft - 58, guiTop + 165, 0);
		GL11.glTranslatef(0, 0, 50.0F);
		GL11.glRotatef(rotateX, 0, 1, 0);
		GL11.glTranslatef(0, 0, -50.0F);
		GuiInventory.func_147046_a(0, 0, 90, (guiLeft - 58 - mouseX) / 4F, (guiTop + 120 - mouseY) / 10F, mc.thePlayer);
		GL11.glPopMatrix();
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch(button.id) {
		case 0:
			mc.displayGuiScreen(new GuiAchievements(this, mc.thePlayer.getStatFileWriter()));
			break;
		case 1:
			mc.displayGuiScreen(new GuiStats(this, mc.thePlayer.getStatFileWriter()));
			break;
		default:break;
		}

	}

	private void func_147044_g() {
		int i = guiLeft + xSize;
		int j = guiTop;
		boolean flag = true;
		Collection collection = mc.thePlayer.getActivePotionEffects();

		if (!collection.isEmpty()) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			int k = 33;

			if (collection.size() > 5) {
				k = 132 / (collection.size() - 1);
			}

			for (Iterator iterator = mc.thePlayer.getActivePotionEffects().iterator(); iterator.hasNext(); j += k) {
				PotionEffect potioneffect = (PotionEffect)iterator.next();
				Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.getTextureManager().bindTexture(field_147001_a);
				drawTexturedModalRect(i, j, 0, 166, 140, 32);

				if (potion.hasStatusIcon()) {
					int l = potion.getStatusIconIndex();
					drawTexturedModalRect(i + 6, j + 7, 0 + l % 8 * 18, 198 + l / 8 * 18, 18, 18);
				}

				potion.renderInventoryEffect(i, j, potioneffect, mc);
				if (!potion.shouldRenderInvText(potioneffect)) continue;
				String s1 = I18n.format(potion.getName(), new Object[0]);

				if (potioneffect.getAmplifier() == 1) {
					s1 = s1 + " " + I18n.format("enchantment.level.2", new Object[0]);
				}
				else if (potioneffect.getAmplifier() == 2) {
					s1 = s1 + " " + I18n.format("enchantment.level.3", new Object[0]);
				}
				else if (potioneffect.getAmplifier() == 3) {
					s1 = s1 + " " + I18n.format("enchantment.level.4", new Object[0]);
				}

				fontRendererObj.drawStringWithShadow(s1, i + 10 + 18, j + 6, 16777215);
				String s = Potion.getDurationString(potioneffect);
				fontRendererObj.drawStringWithShadow(s, i + 10 + 18, j + 6 + 10, 8355711);
			}
		}
	}

}
