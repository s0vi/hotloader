package dev.soupie.hotloader.client;

import dev.soupie.hotloader.network.NetworkManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class HotloaderClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetworkManager.registerClientListeners();
    }
}
