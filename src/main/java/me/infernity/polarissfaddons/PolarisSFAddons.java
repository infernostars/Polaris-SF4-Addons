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

        ItemStack itemGroupItem = new CustomItemStack(Material.OAK_LOG, "&4Polaris Addons", "", "&a> Click to open");
        NamespacedKey itemGroupId = new NamespacedKey(this, "polaris_addons");
        ItemGroup itemGroup = new ItemGroup(itemGroupId, itemGroupItem);

        SlimefunItemStack polariumSlimefunItem = new SlimefunItemStack("POLARIUM", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDI5ZGE0ODVjZTU1MDRiOGI5OTY5NjEwYjRjMDdmNzVmZTYxODkyNGZlYWRhOWI0MDZlZmJlMWVlZWRkMzJjNSJ9fX0=", "&4Polarium", "", "&7A strong ore.");

//        ItemStack[] polariumRecipe = { new ItemStack(Material.EMERALD) , null                            , new ItemStack(Material.EMERALD),
//                                       null                            , new ItemStack(Material.DIAMOND) , null,
//                                       new ItemStack(Material.EMERALD) , null                            , new ItemStack(Material.EMERALD) };

        SlimefunItem polariumItem = new SlimefunItem(itemGroup, polariumSlimefunItem, RecipeType.GEO_MINER, new ItemStack[9]);
        polariumItem.register(this);

        PolariumResource polariumGEOResource = new PolariumResource(this, polariumSlimefunItem, 22, 0, 4, 0, 12);
        polariumGEOResource.register();

        ResearchPlus polariumResearch = new ResearchPlus(this, "polarium", 1972216001, "Polarium", 10);
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

    public static class ResearchPlus extends Research {

        public ResearchPlus(Plugin plugin, String key, int id, String name, int cost) {
            super(new NamespacedKey(plugin, key), id, name, cost);
            super.register();
        }
    }

    public static class PolariumResource implements GEOResource {

        private final NamespacedKey key;
        private final ItemStack item;
        private final int overworldSupply, hellSupply, endSupply, backupSupply, deviation;

        public PolariumResource(Plugin plugin, ItemStack item, int overworldSupply, int hellSupply, int endSupply, int backupSupply, int deviation){
            this.key = new NamespacedKey(plugin, "polarium");
            this.item = item;
            this.overworldSupply = overworldSupply;
            this.hellSupply = hellSupply;
            this.endSupply = endSupply;
            this.backupSupply = backupSupply;
            this.deviation = deviation;
        }

        @Override
        public int getDefaultSupply(World.Environment environment, Biome biome) {
            switch (environment) {
                case THE_END:
                    return endSupply;
                case NORMAL:
                    return overworldSupply;
                case NETHER:
                    return hellSupply;
            }
            return backupSupply; // just in case
        }

        @Override
        public int getMaxDeviation() {
            return deviation;
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
