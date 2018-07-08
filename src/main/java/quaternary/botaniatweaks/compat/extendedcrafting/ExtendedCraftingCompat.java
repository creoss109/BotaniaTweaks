package quaternary.botaniatweaks.compat.extendedcrafting;

import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import quaternary.botaniatweaks.BotaniaTweaks;
import quaternary.botaniatweaks.block.BotaniaTweaksBlocks;
import quaternary.botaniatweaks.compat.shared.ModCompatUtil;
import quaternary.botaniatweaks.compat.shared.OptionalExtensions;
import quaternary.botaniatweaks.item.BotaniaTweaksItems;
import quaternary.botaniatweaks.lexi.DoubleCompatLexiconEntry;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.KnowledgeType;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.recipe.RecipeElvenTrade;
import vazkii.botania.common.lexicon.page.PageElvenRecipe;
import vazkii.botania.common.lexicon.page.PageText;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ExtendedCraftingCompat {
	public static LexiconEntry extCrateEntry;
	
	public static void preinit() {
		Block basicExtCrate = new BlockExtCraftCrate(TileBasicExtCraftCrate::new);
		Block advExtCrate = new BlockExtCraftCrate(TileAdvExtCraftCrate::new);
		Block eliteExtCrate = new BlockExtCraftCrate(TileEliteExtCraftCrate::new);
		Block ultExtCrate = new BlockExtCraftCrate(TileUltExtCraftCrate::new);
		
		OptionalExtensions.BLOCK_CALLBACKS.add(reg -> {
			reg.register(BotaniaTweaksBlocks.createBlock(basicExtCrate, "basic_extended_crafty_crate"));
			reg.register(BotaniaTweaksBlocks.createBlock(advExtCrate, "advanced_extended_crafty_crate"));
			reg.register(BotaniaTweaksBlocks.createBlock(eliteExtCrate, "elite_extended_crafty_crate"));
			reg.register(BotaniaTweaksBlocks.createBlock(ultExtCrate, "ultimate_extended_crafty_crate"));
			
			GameRegistry.registerTileEntity(TileBasicExtCraftCrate.class, BotaniaTweaks.MODID + ":basic_ext_crafty_crate");
			GameRegistry.registerTileEntity(TileAdvExtCraftCrate.class, BotaniaTweaks.MODID + ":adv_ext_crafty_crate");
			GameRegistry.registerTileEntity(TileEliteExtCraftCrate.class, BotaniaTweaks.MODID + ":elite_ext_crafty_crate");
			GameRegistry.registerTileEntity(TileUltExtCraftCrate.class, BotaniaTweaks.MODID + ":ult_ext_crafty_crate");
		});
		
		OptionalExtensions.ITEM_CALLBACKS.add(reg -> {
			reg.register(BotaniaTweaksItems.createItemBlock(new ItemBlock(basicExtCrate)));
			reg.register(BotaniaTweaksItems.createItemBlock(new ItemBlock(advExtCrate)));
			reg.register(BotaniaTweaksItems.createItemBlock(new ItemBlock(eliteExtCrate)));
			reg.register(BotaniaTweaksItems.createItemBlock(new ItemBlock(ultExtCrate)));
		});
		
		OptionalExtensions.MODEL_CALLBACKS.add(() -> {
			BotaniaTweaksItems.Client.setModel(basicExtCrate.getRegistryName().getResourcePath());
			BotaniaTweaksItems.Client.setModel(advExtCrate.getRegistryName().getResourcePath());
			BotaniaTweaksItems.Client.setModel(eliteExtCrate.getRegistryName().getResourcePath());
			BotaniaTweaksItems.Client.setModel(ultExtCrate.getRegistryName().getResourcePath());
		});
		
		OptionalExtensions.LEXICON_CALLBACKS.add(() -> {
			List<RecipeElvenTrade> elvenRecipes = new ArrayList<>();
			
			BiConsumer<Block, Block> elvenRecipeFunc = (extCrate, extTable) -> {
				ItemStack extCrateStack = new ItemStack(Item.getItemFromBlock(extCrate));
				ItemStack extTableStack = new ItemStack(Item.getItemFromBlock(extTable));
				ItemStack crateStack = ModCompatUtil.getStackFor(new ResourceLocation("botania", "opencrate"), 1);
				
				elvenRecipes.add(new RecipeElvenTrade(new ItemStack[]{extCrateStack}, extTableStack, crateStack));
			};
			
			elvenRecipeFunc.accept(basicExtCrate, ModBlocks.blockBasicTable);
			elvenRecipeFunc.accept(advExtCrate, ModBlocks.blockAdvancedTable);
			elvenRecipeFunc.accept(eliteExtCrate, ModBlocks.blockEliteTable);
			elvenRecipeFunc.accept(ultExtCrate, ModBlocks.blockUltimateTable);
			
			extCrateEntry = new DoubleCompatLexiconEntry("botania_tweaks.lexicon.category.extCrates", BotaniaAPI.categoryDevices, BotaniaTweaks.NAME, ExtendedCrafting.NAME);
			extCrateEntry.setKnowledgeType(BotaniaAPI.elvenKnowledge);
			extCrateEntry.setIcon(new ItemStack(Item.getItemFromBlock(ultExtCrate)));
			extCrateEntry.addPage(new PageText("botania_tweaks.lexicon.extCrates.0"));
			for(int i = 0; i < elvenRecipes.size(); i++) {
				extCrateEntry.addPage(new PageElvenRecipe("botania_tweaks.lexicon.extCrates.subtitle." + i, elvenRecipes.get(i)));
			}
		});
	}
}
