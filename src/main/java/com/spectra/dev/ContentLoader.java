package com.spectra.dev;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.spectra.SpectraClient;
import com.spectra.animation.Animation;
import com.spectra.animation.AnimationRegistry;
import com.spectra.animation.AnimationTrack;
import com.spectra.animation.Easing;
import com.spectra.animation.Keyframe;
import com.spectra.cosmetics.Cosmetic;
import com.spectra.cosmetics.CosmeticRegistry;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Дев-плюшка номер один: весь контент — это JSON-файлы в assets/spectra.
 * Кинул файл в animations/ или cosmetics/ → нажал F3+T в игре → контент подхватился без рестарта.
 */
public class ContentLoader implements SimpleSynchronousResourceReloadListener {
    private static final Gson GSON = new Gson();
    private static final Identifier ID = Identifier.of(SpectraClient.MOD_ID, "content");

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public void reload(ResourceManager manager) {
        AnimationRegistry.clear();
        CosmeticRegistry.clear();
        loadAnimations(manager);
        loadCosmetics(manager);
        SpectraClient.LOGGER.info("[Spectra] Загружено анимаций: {}, косметики: {}",
                AnimationRegistry.size(), CosmeticRegistry.size());
    }

    private void loadAnimations(ResourceManager manager) {
        for (Map.Entry<Identifier, Resource> entry
                : manager.findResources("animations", id -> id.getPath().endsWith(".json")).entrySet()) {
            try (InputStreamReader reader = new InputStreamReader(entry.getValue().getInputStream(), StandardCharsets.UTF_8)) {
                JsonObject root = GSON.fromJson(reader, JsonObject.class);
                String id = root.get("id").getAsString();
                float duration = root.has("duration") ? root.get("duration").getAsFloat() : 20f;
                boolean loop = root.has("loop") && root.get("loop").getAsBoolean();

                Map<String, AnimationTrack> tracks = new HashMap<>();
                JsonObject tracksJson = root.getAsJsonObject("tracks");
                if (tracksJson != null) {
                    for (Map.Entry<String, JsonElement> trackEntry : tracksJson.entrySet()) {
                        List<Keyframe> keyframes = new ArrayList<>();
                        for (JsonElement element : trackEntry.getValue().getAsJsonArray()) {
                            JsonObject obj = element.getAsJsonObject();
                            keyframes.add(new Keyframe(
                                    obj.get("time").getAsFloat(),
                                    obj.get("value").getAsFloat(),
                                    Easing.byName(obj.has("easing") ? obj.get("easing").getAsString() : "LINEAR")
                            ));
                        }
                        keyframes.sort((a, b) -> Float.compare(a.time(), b.time()));
                        tracks.put(trackEntry.getKey(), new AnimationTrack(keyframes));
                    }
                }
                AnimationRegistry.register(new Animation(id, duration, loop, tracks));
            } catch (Exception e) {
                SpectraClient.LOGGER.error("[Spectra] Ошибка чтения анимации {}", entry.getKey(), e);
            }
        }
    }

    private void loadCosmetics(ResourceManager manager) {
        for (Map.Entry<Identifier, Resource> entry
                : manager.findResources("cosmetics", id -> id.getPath().endsWith(".json")).entrySet()) {
            try (InputStreamReader reader = new InputStreamReader(entry.getValue().getInputStream(), StandardCharsets.UTF_8)) {
                JsonObject root = GSON.fromJson(reader, JsonObject.class);
                String id = root.get("id").getAsString();
                Cosmetic.Slot slot = Cosmetic.Slot.valueOf(
                        root.has("slot") ? root.get("slot").getAsString().toUpperCase() : "HEAD");
                String model = root.has("model") ? root.get("model").getAsString() : "";
                String texture = root.has("texture") ? root.get("texture").getAsString() : "";
                List<String> animations = new ArrayList<>();
                if (root.has("animations")) {
                    JsonArray arr = root.getAsJsonArray("animations");
                    for (JsonElement element : arr) {
                        animations.add(element.getAsString());
                    }
                }
                CosmeticRegistry.register(new Cosmetic(id, slot, model, texture, animations));
            } catch (Exception e) {
                SpectraClient.LOGGER.error("[Spectra] Ошибка чтения косметики {}", entry.getKey(), e);
            }
        }
    }
}
