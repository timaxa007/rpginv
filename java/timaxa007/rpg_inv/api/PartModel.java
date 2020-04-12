package timaxa007.rpg_inv.api;

public class PartModel {

	public float
	rotationPointX,
	rotationPointY,
	rotationPointZ,
	rotateAngleX,
	rotateAngleY,
	rotateAngleZ,
	offsetX,
	offsetY,
	offsetZ;
	public boolean showModel;

	public void set(net.minecraft.client.model.ModelRenderer mr) {
		rotateAngleX = mr.rotateAngleX;
		rotateAngleY = mr.rotateAngleY;
		rotateAngleZ = mr.rotateAngleZ;
		rotationPointX = mr.rotationPointX;
		rotationPointY = mr.rotationPointY;
		rotationPointZ = mr.rotationPointZ;
		offsetX = mr.offsetX;
		offsetY = mr.offsetY;
		offsetZ = mr.offsetZ;
		showModel = mr.showModel;
	}

}
