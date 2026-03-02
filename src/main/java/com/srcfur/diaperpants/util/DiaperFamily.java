package com.srcfur.diaperpants.util;

import net.minecraft.util.StringIdentifiable;

public enum DiaperFamily implements StringIdentifiable {

    NONE("generic"),
    PULLUPS("pullup"),
    PEEKABU("peekabu");

    public final String DiaperName;
    private DiaperFamily(String diapername){
        DiaperName = diapername;
    }

    @Override
    public String asString() {
        return DiaperName;
    }
}
