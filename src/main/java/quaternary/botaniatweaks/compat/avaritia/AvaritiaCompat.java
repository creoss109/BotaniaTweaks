package quaternary.botaniatweaks.compat.avaritia;

import morph.avaritia.Avaritia;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import quaternary.botaniatweaks.BotaniaTweaks;
import quaternary.botaniatweaks.block.BotaniaTweaksBlocks;
import quaternary.botaniatweaks.compat.shared.ModCompatUtil;
import quaternary.botaniatweaks.compat.shared.OptionalExtensions;
import quaternary.botaniatweaks.item.BotaniaTweaksItems;
import quaternary.botaniatweaks.lexi.DoubleCompatLexiconEntry;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.recipe.RecipeElvenTrade;
import vazkii.botania.common.lexicon.page.PageElvenRecipe;
import vazkii.botania.common.lexicon.page.PageText;

public class AvaritiaCompat {
	public static LexiconEntry direCrateEntry = null;
	
	public static void preinit() {		
		Block direCrate = new BlockDireCraftyCrate();
		
		OptionalExtensions.BLOCK_CALLBACKS.add(reg -> {
			reg.register(BotaniaTweaksBlocks.createBlock(direCrate, "dire_crafty_crate"));
			
			GameRegistry.registerTileEntity(TileDireCraftyCrate.class, BotaniaTweaks.MODID + ":dire_crafty_crate");
		});
		
		OptionalExtensions.ITEM_CALLBACKS.add(reg -> {
			reg.register(BotaniaTweaksItems.createItemBlock(new ItemBlock(direCrate)));
		});
		
		OptionalExtensions.MODEL_CALLBACKS.add(() -> {
			//Oh no it sucks
			BotaniaTweaksItems.Client.setModel(direCrate.getRegistryName().getResourcePath());
		});
		
		OptionalExtensions.LEXICON_CALLBACKS.add(() -> {
			ItemStack direCrateStack = ModCompatUtil.getStackFor(direCrate.getRegistryName());
			ItemStack extremeTableStack = ModCompatUtil.getStackFor(new ResourceLocation("avaritia", "extreme_crafting_table"));
			ItemStack craftyCrateStack = ModCompatUtil.getStackFor(new ResourceLocation("botania", "opencrate"), 1);
			
			RecipeElvenTrade direCrateRecipe = BotaniaAPI.registerElvenTradeRecipe(new ItemStack[]{direCrateStack}, extremeTableStack, craftyCrateStack);
			
			direCrateEntry = new DoubleCompatLexiconEntry("botania_tweaks.lexicon.category.direCrate", BotaniaAPI.categoryDevices, BotaniaTweaks.NAME, Avaritia.MOD_NAME);
			direCrateEntry.setKnowledgeType(BotaniaAPI.elvenKnowledge);
			direCrateEntry.setIcon(direCrateStack);
			direCrateEntry.addPage(new PageText("botania_tweaks.lexicon.direCrate.0"));
			direCrateEntry.addPage(new PageElvenRecipe("botania_tweaks.lexicon.direCrate.subtitle", direCrateRecipe));
		});
	}
}
