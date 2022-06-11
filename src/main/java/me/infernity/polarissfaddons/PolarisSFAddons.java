package me.infernity.polarissfaddons;

import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

/*credit to https://minecraft-heads.com/custom-heads/blocks/31893-ice-brick for the ice bricks, used as polarium.*/

public class PolarisSFAddons extends JavaPlugin implements SlimefunAddon {

    @Override
    public void onEnable() {
        // Read something from your config.yml
        Config cfg = new Config(this);

        /*if (cfg.getBoolean("options.auto-update")) {
            // You could start an Auto-Updater for example
        }*/

        /*
         * 1. Creating a new Category
         * This Category will use the following ItemStack
         */
        ItemStack itemGroupItem = new CustomItemStack(Material.OAK_LOG, "&4Polaris Addons", "", "&a> Click to open");

        // Give your Category a unique id.
        NamespacedKey itemGroupId = new NamespacedKey(this, "polaris_addons");
        ItemGroup itemGroup = new ItemGroup(itemGroupId, itemGroupItem);

        SlimefunItemStack polariumSlimefunItem = new SlimefunItemStack("POLARIUM", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDI5ZGE0ODVjZTU1MDRiOGI5OTY5NjEwYjRjMDdmNzVmZTYxODkyNGZlYWRhOWI0MDZlZmJlMWVlZWRkMzJjNSJ9fX0=", "&4Polarium", "", "&7A strong alloy, seemingly specific to this region...");

//        ItemStack[] polariumRecipe = { new ItemStack(Material.EMERALD) , null                            , new ItemStack(Material.EMERALD),
//                                       null                            , new ItemStack(Material.DIAMOND) , null,
//                                       new ItemStack(Material.EMERALD) , null                            , new ItemStack(Material.EMERALD) };

        SlimefunItem polariumItem = new SlimefunItem(itemGroup, polariumSlimefunItem, RecipeType.GEO_MINER, new ItemStack[9]);
        polariumItem.register(this);

        PolariumResource polariumResource = new PolariumResource(this, polariumSlimefunItem);
        polariumResource.register();

        NamespacedKey polariumResearchKey = new NamespacedKey(this, "polarium");
        Research polariumResearch = new Research(polariumResearchKey, 1972216001, "Polarium", 10);
        polariumResearch.register();
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public String getBugTrackerURL() {
        // You can return a link to your Bug Tracker instead of null here
        return "https://github.com/infernostars/PolarisSFAddons/issues";
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        /*
         * You will need to return a reference to your Plugin here.
         * If you are using your main class for this, simply return "this".
         */
        return this;
    }

    public static class PolariumWand extends SlimefunItem {

        public PolariumWand(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
            super(itemGroup, item, recipeType, recipe);
        }

    }

    public static class PolariumResource implements GEOResource {

        private final NamespacedKey key;
        private final ItemStack item;

        public PolariumResource(Plugin plugin, ItemStack item){
            this.key = new NamespacedKey(plugin, "polarium");
            this.item = item;
        }

        @Override
        public int getDefaultSupply(World.Environment environment, Biome biome) {
            switch (environment) {
                case THE_END:
                    return 1;
                case NORMAL:
                    return 12;
                case NETHER:
                    return 0;
            }
            return 0; // just in case
        }

        @Override
        public int getMaxDeviation() {
            return 15;
        }

        @Override
        public String getName() {
            return "Polarium";
        }

        @Override
        public ItemStack getItem() {
            return item.clone();
        }

        @Override
        public boolean isObtainableFromGEOMiner() {
            return true;
        }

        @Override
        public NamespacedKey getKey() {
            return key;
        }

    }

}
