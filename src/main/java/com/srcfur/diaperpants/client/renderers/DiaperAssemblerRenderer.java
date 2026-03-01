package com.srcfur.diaperpants.client.renderers;

import com.srcfur.diaperpants.block.entity.DiaperAssemblerBlockEntity;
import com.srcfur.diaperpants.client.DiaperAssemblerBlockModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class DiaperAssemblerRenderer extends GeoBlockRenderer<DiaperAssemblerBlockEntity> {
    public DiaperAssemblerRenderer(BlockEntityRendererFactory.Context context) {
        super(new DiaperAssemblerBlockModel());
    }

    @Override
    public RenderLayer getRenderType(DiaperAssemblerBlockEntity animatable, float partialTick, MatrixStack poseStack, @Nullable VertexConsumerProvider bufferSource, @Nullable VertexConsumer buffer, int packedLight, Identifier texture) {
        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
    }
}
