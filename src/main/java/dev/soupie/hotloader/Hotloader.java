package dev.soupie.hotloader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import dev.soupie.hotloader.command.CommandManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.launch.FabricLauncher;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class Hotloader implements ModInitializer {
    public static final String MOD_ID = "hotloader";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    private static Hotloader INSTANCE = null;

    public Hotloader() {
        INSTANCE = this;
    }

    public static Hotloader getInstance(){
        return INSTANCE;
    }

    @Override
    public void onInitialize() {
        LOGGER.error("WARNING:");
        LOGGER.warn("This instance contains Hotloader, created by justsoupie. This mod allows mods to be loaded PAST when they should be able to.");
        LOGGER.warn("Please have users remove this mod and load mods normally before reporting any bugs.");
        LOGGER.error("Hotloader is in beta. There will be many issues and crashes. Have patience.");
        CommandManager.registerCommands();
    }

    public void loadMod(String modId){
        File jarToLoad = findJar(modId);
        LOGGER.info("Jar with modId {} found at {}", modId, jarToLoad.toPath().toString());
    }

    File findJar(String modId){
        Path modsPath = FabricLoader.getInstance().getGameDir().resolve("mods");

        //breadth first search
        Queue<File> queue = new LinkedList<>();
        queue.add(modsPath.toFile());
        File retVal = null;

        while(!queue.isEmpty() || retVal != null){

            File current = queue.poll();
            File[] childFiles = current.listFiles();

            if(childFiles != null) {
                for(File ch :childFiles){
                    if(ch.isDirectory()){
                        queue.add(ch);
                    } else {
                        String chModId = getModId(ch);
                        if(chModId.equals(modId)){
                            retVal = ch;
                        }

                    }
                }
            }
        }

        return retVal;
    }

    String getModId(File file) {
        try {
            URL[] urls = {file.toURI().toURL()};
            URLClassLoader classLoader = new URLClassLoader(urls, this.getClass().getClassLoader());
            URL jsonURL = classLoader.findResource("fabric.mod.json");
            JsonObject json = (JsonObject) JsonParser.parseReader(new JsonReader(new InputStreamReader(jsonURL.openStream())));

            return json.get("id").getAsString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";

    }


}
