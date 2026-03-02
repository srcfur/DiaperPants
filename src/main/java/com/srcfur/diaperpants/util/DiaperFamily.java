package com.srcfur.diaperpants.util;

import net.minecraft.util.StringIdentifiable;

public enum DiaperFamily implements StringIdentifiable {

    NONE("generic"),
    GROCERY("cheap"),
    PULLUPS("pullup"),

    //All of our references will be down here :3
    ABU0("bunnyhopps"),
    ABU1("peekabu");

    public final String DiaperName;
    private DiaperFamily(String diapername){
        DiaperName = diapername;
    }

    @Override
    public String asString() {
        return DiaperName;
    }
}
