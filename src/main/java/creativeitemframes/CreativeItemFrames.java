package creativeitemframes;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused") // referenced in plugin.yml
public class CreativeItemFrames extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Enabling Creative Item Frames...");

        registerListeners();

        getLogger().info("Creative Item Frames is now enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Creative Item Frames is now disabled");
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ItemFrameListener(this), this);
    }

}
