package com.srcfur.diaperpants.sounds;

import com.srcfur.diaperpants.DiaperPants;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static SoundEvent soiling_sound = registerSoundEvent("diaper_soiling");


    private static SoundEvent registerSoundEvent(String name){
        Identifier id = new Identifier(DiaperPants.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
    public static void Initialize(){

    }
}
