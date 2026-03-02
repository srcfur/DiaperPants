package com.srcfur.diaperpants.client.blockstates;

import com.srcfur.diaperpants.util.DiaperFamily;
import net.minecraft.state.property.EnumProperty;

public class ModProperties {
    public static final EnumProperty<DiaperFamily> DIAPER_FAMILY = EnumProperty.of("diaperfamily", DiaperFamily.class);
}
