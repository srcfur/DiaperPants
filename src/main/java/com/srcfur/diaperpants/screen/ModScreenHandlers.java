package com.srcfur.diaperpants.screen;

import com.srcfur.diaperpants.DiaperPants;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class ModScreenHandlers {
    public static ScreenHandlerType<DiaperAssemblerScreenHandler> DIAPER_ASSEMBLER_SCREEN_HANDLER =
            Registry.SCREEN_HANDLER.register(Registry.SCREEN_HANDLER, new Identifier(DiaperPants.MOD_ID, "diaperassembler_screen"),
                    new ScreenHandlerType<>(DiaperAssemblerScreenHandler::new));
    public static void initialize(){

    }
}
