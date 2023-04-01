package dev.soupie.hotloader.network;

import dev.soupie.hotloader.Hotloader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPacketListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class NetworkManager {
    public static final Identifier LOAD_MOD_ID = new Identifier(Hotloader.MOD_ID, "load_mod");
    public static final Identifier LOAD_MOD_FAILED = new Identifier(Hotloader.MOD_ID,  "load_mod_failed");

    @Environment(EnvType.CLIENT)
    public static void registerClientListeners() {
        ClientPlayNetworking.registerGlobalReceiver(LOAD_MOD_ID, (client, handler, buf, responseSender) -> {
            String modId = buf.readString();
            Hotloader.getInstance().loadMod(modId);
        });
    }

    @Environment(EnvType.SERVER)
    public static void sendModLoadPacket(ServerPlayerEntity player, String modId) {
        PacketByteBuf buf = PacketByteBufs.empty();
        buf.writeString(modId);
        ServerPlayNetworking.send(player, LOAD_MOD_ID, buf);
    }
}
