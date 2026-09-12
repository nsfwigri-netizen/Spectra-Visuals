package com.spectra.menu;

import com.spectra.animation.Animation;
import com.spectra.animation.AnimationRegistry;
import com.spectra.cosmetics.Cosmetic;
import com.spectra.cosmetics.CosmeticRegistry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/**
 * Меню визуалки — основа. Открывается на V.
 * Сейчас: панель-инспектор, показывает всё зарегистрированное в моде.
 * Дальше сюда лягут вкладки, превью моделей и настройки.
 */
public class SpectraScreen extends Screen {
    private static final int PANEL_WIDTH = 260;
    private static final int PANEL_HEIGHT = 200;

    private static final int COLOR_BG = 0xE0101018;
    private static final int COLOR_ACCENT = 0xFF8A5CF6;
    private static final int COLOR_TITLE = 0xFFFFFFFF;
    private static final int COLOR_SUB = 0xFF9B9BA8;
    private static final int COLOR_HEADER = 0xFFCFA8FF;
    private static final int COLOR_TEXT = 0xFFD8D8E0;

    public SpectraScreen() {
        super(Text.literal("Spectra"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        int x = (this.width - PANEL_WIDTH) / 2;
        int y = (this.height - PANEL_HEIGHT) / 2;

        context.fill(x, y, x + PANEL_WIDTH, y + PANEL_HEIGHT, COLOR_BG);
        context.fill(x, y, x + PANEL_WIDTH, y + 2, COLOR_ACCENT);

        context.drawText(this.textRenderer, "SPECTRA", x + 10, y + 10, COLOR_TITLE, true);
        context.drawText(this.textRenderer, "визуалка · меню-основа · v0.1", x + 10, y + 23, COLOR_SUB, false);

        int lineY = y + 44;
        context.drawText(this.textRenderer, "Анимации (" + AnimationRegistry.size() + "):", x + 10, lineY, COLOR_HEADER, false);
        lineY += 12;
        for (Animation animation : AnimationRegistry.all()) {
            if (lineY > y + PANEL_HEIGHT - 24) {
                break;
            }
            String meta = animation.duration() + "t" + (animation.loop() ? ", loop" : "") + ", дорожек: " + animation.tracks().size();
            context.drawText(this.textRenderer, "• " + animation.id() + "  [" + meta + "]", x + 16, lineY, COLOR_TEXT, false);
            lineY += 10;
        }

        lineY += 8;
        context.drawText(this.textRenderer, "Косметика (" + CosmeticRegistry.size() + "):", x + 10, lineY, COLOR_HEADER, false);
        lineY += 12;
        for (Cosmetic cosmetic : CosmeticRegistry.all()) {
            if (lineY > y + PANEL_HEIGHT - 24) {
                break;
            }
            context.drawText(this.textRenderer, "• " + cosmetic.id() + "  (" + cosmetic.slot() + ")", x + 16, lineY, COLOR_TEXT, false);
            lineY += 10;
        }

        context.drawText(this.textRenderer, "Esc — закрыть", x + 10, y + PANEL_HEIGHT - 14, COLOR_SUB, false);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
