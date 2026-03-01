package com.srcfur.diaperpants.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.srcfur.diaperpants.DiaperPants;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class DiaperAssemblerScreen extends HandledScreen<DiaperAssemblerScreenHandler> {
    public DiaperAssemblerScreen(DiaperAssemblerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    private static final Identifier TEXTURE =
            new Identifier(DiaperPants.MOD_ID, "textures/gui/diaperassemblerui.png");

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - 172) / 2;
        int y = (height - 166) / 2;
        drawTexture(matrices, x, y, 0, 0, 172, 166);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
