package com.srcfur.diaperpants.client;


import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import com.srcfur.diaperpants.DiaperPants;
import com.srcfur.diaperpants.util.IEntityDiapered;

public class BladderHudOverlay implements HudRenderCallback {
    private static final Identifier FILLED_BLADDER = new Identifier(DiaperPants.MOD_ID,
            "textures/bladder/filled_bladder.png");
    private static final Identifier EMPTY_BLADDER = new Identifier(DiaperPants.MOD_ID,
            "textures/bladder/empty_bladder.png");

    private static int actoffset = 52;

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if(client != null){
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();
            x = width / 2;
            y = height;
            if(client.player != null){
                if(client.player.isCreative()){
                    return;
                }
            }
            if(client.player != null){
                int offset = 52;
                if(client.player.getArmor() > 0 || client.player.getAir() < client.player.getMaxAir()){
                    offset = 64;
                }
                actoffset = (int)Math.round(actoffset + (0.3 * tickDelta * (offset - actoffset)));
            }

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1,1,1,1);
            RenderSystem.setShaderTexture(0, EMPTY_BLADDER);

            int continence = IEntityDiapered.getContinenceLevel(client.player);

            int contscale = continence / 4;

            for(int i = 0; i < contscale; i++){
                DrawableHelper.drawTexture(matrixStack, x - 2 + (i * 9) - (int)(contscale * 4.5), y - actoffset, 0, 0, 12, 12, 12, 12);
            }

            RenderSystem.setShaderTexture(0, FILLED_BLADDER);
            for(int i = 0; i < contscale; i++) {
                if(IEntityDiapered.getBladderLevel(client.player) > i * 4){
                    DrawableHelper.drawTexture(matrixStack, x - 2 + (i * 9) - (int)(contscale * 4.5), y - actoffset, 0, 0, 12, 12, 12, 12);
                }else {
                    break;
                }
            }
        }
    }
}
