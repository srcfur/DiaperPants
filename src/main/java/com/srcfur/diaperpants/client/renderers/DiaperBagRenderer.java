package com.srcfur.diaperpants.client.renderers;

import com.srcfur.diaperpants.block.entity.DiaperBagEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

import java.util.List;

public class DiaperBagRenderer implements BlockEntityRenderer<DiaperBagEntity> {
    private TextRenderer queue;
    public DiaperBagRenderer(BlockEntityRendererFactory.Context context){
        queue = context.getTextRenderer();

    }
    @Override
    public void render(DiaperBagEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        float[] text_scalor = new float[3];
        float wide_scalor = 128;
        float diaper_offset = 0;
        text_scalor[0] = 1f / wide_scalor;
        text_scalor[1] = 1f / 64;
        text_scalor[2] = 1f / wide_scalor;
        if(MinecraftClient.getInstance().world == null){return;}
        //This means we've broke it and this shit sucsks anyways :P
        if(MinecraftClient.getInstance().world.getBlockEntity(entity.getPos()) != entity){return;}

        String diaperbag_display_text = entity.getDiaperBagName();
        float diaper_bag_display_offset = queue.getWidth(diaperbag_display_text) / wide_scalor;
        BlockState bs = MinecraftClient.getInstance().world.getBlockState(entity.getPos());
        Direction prop = bs.get(Properties.HORIZONTAL_FACING);

        //Start out rotated context
        matrices.translate(0.5f, 0.5f, 0.5f);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(prop.asRotation() + 180));

        //We handle drawing the diaper bag's name here!
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
        matrices.translate(-diaper_bag_display_offset / 2, 0.1f, -0.32);

        //Draw Front Text
        matrices.scale(text_scalor[0], text_scalor[1], text_scalor[2]);
        queue.draw(matrices, diaperbag_display_text, 0, 0, 0xffffffff);
        matrices.pop();

        //Draw back text
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        matrices.translate(-diaper_bag_display_offset / 2, 0.1f, -0.32);

        matrices.scale(text_scalor[0], text_scalor[1], text_scalor[2]);
        queue.draw(matrices, diaperbag_display_text, 0, 0, 0xffffffff);
        matrices.pop();

        //Now we start drawing the diaper's peeking from the top
        //We should also have a distance check for this as well :P
        List<ItemStack> alldiapers = entity.getAllDiapers();
        ItemRenderer rend = MinecraftClient.getInstance().getItemRenderer();
        matrices.push();
        matrices.multiply((Vec3f.POSITIVE_Y.getDegreesQuaternion(90)));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));
        matrices.translate(0, 0f, 0.45f);
        for(int i = 0; i < alldiapers.size(); i++){
            matrices.push();
            matrices.translate(0, diaper_offset, -0.1f * i);
            matrices.scale(0.5f,0.5f,0.5f);
            rend.renderItem(null, alldiapers.get(i), ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, MinecraftClient.getInstance().world, 255, overlay, 0);
            matrices.pop();
        }
        matrices.pop();
    }
}
