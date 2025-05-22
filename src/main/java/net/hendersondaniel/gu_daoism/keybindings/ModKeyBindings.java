package net.hendersondaniel.gu_daoism.keybindings;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static final String KEY_CATEGORY_GU_DAOISM = "key.category.gu_daoism.aperture";
    public static final String KEY_OPEN_APERTURE_SCREEN_LANG= "key.gu_daoism.open_aperture_screen";

    public static final KeyMapping KEY_OPEN_APERTURE_SCREEN = new KeyMapping(KEY_OPEN_APERTURE_SCREEN_LANG, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORY_GU_DAOISM);
}
