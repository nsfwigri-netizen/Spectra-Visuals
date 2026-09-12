package com.spectra;

import com.spectra.dev.ContentLoader;
import com.spectra.menu.SpectraScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.resource.ResourceType;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpectraClient implements ClientModInitializer {
    public static final String MOD_ID = "spectra";
    public static final Logger LOGGER = LoggerFactory.getLogger("Spectra");

    private static KeyBinding openMenuKey;

    @Override
    public void onInitializeClient() {
        LOGGER.info("[Spectra] Инициализация визуалки...");

        // Загрузчик контента: JSON-анимации и косметика из assets/spectra.
        // Перезагружается через F3+T без рестарта игры.
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new ContentLoader());

        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.spectra.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                KeyBinding.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new SpectraScreen());
                }
            }
        });
    }
}
