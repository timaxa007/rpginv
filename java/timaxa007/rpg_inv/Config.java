package timaxa007.rpg_inv;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class Config {

	//public static Configuration config;

	public static final Set<Item> для_проверки = new HashSet<Item>();

	public static void init(File file) {
		Configuration config = new Configuration(file);
		config.load();


		String[] s1 = (config.get("main", "tools", "").getString()).split(",");

		Item item;
		for (int i = 0; i < s1.length; ++i) {

			String[] name_from_config = s1[i].trim().split(":");

			if (name_from_config.length > 1)
				item = GameRegistry.findItem(name_from_config[0], name_from_config[1]);
			else
				item = GameRegistry.findItem("minecraft", name_from_config[0]);

			if (item != null)
				для_проверки.add(item);

		}


		config.save();
	}

}
