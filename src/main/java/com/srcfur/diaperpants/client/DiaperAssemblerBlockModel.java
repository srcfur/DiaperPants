package com.srcfur.diaperpants.client;

import com.srcfur.diaperpants.DiaperPants;
import com.srcfur.diaperpants.block.entity.DiaperAssemblerBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DiaperAssemblerBlockModel extends AnimatedGeoModel<DiaperAssemblerBlockEntity> {
    @Override
    public Identifier getModelLocation(DiaperAssemblerBlockEntity object) {
        return new Identifier(DiaperPants.MOD_ID, "geo/diaperassembler.json");
    }

    @Override
    public Identifier getTextureLocation(DiaperAssemblerBlockEntity object) {
        return new Identifier(DiaperPants.MOD_ID, "textures/block/diaperassembler.png");
    }

    @Override
    public Identifier getAnimationFileLocation(DiaperAssemblerBlockEntity animatable) {
        return new Identifier(DiaperPants.MOD_ID, "animations/diaperassembler.animation.json");
    }
}
