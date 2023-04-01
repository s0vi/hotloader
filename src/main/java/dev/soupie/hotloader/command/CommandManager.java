package dev.soupie.hotloader.command;

import  static com.mojang.brigadier.arguments.StringArgumentType.*;

import com.mojang.brigadier.arguments.StringArgumentType;
import dev.soupie.hotloader.network.NetworkManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.*;

public class CommandManager {

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("loadmod")
                        .then(argument("modId", string()))
                .executes(context -> {

                })));
    }
}
