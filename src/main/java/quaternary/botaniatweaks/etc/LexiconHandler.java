package quaternary.botaniatweaks.etc;

import net.minecraft.util.ResourceLocation;
import quaternary.botaniatweaks.BotaniaTweaks;
import quaternary.botaniatweaks.config.BotaniaTweaksConfig;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.lexicon.LexiconPage;
import vazkii.botania.common.lexicon.LexiconData;
import vazkii.botania.common.lexicon.page.PageCraftingRecipe;

public class LexiconHandler {
	public static void fixKnowledgeTypes() {
		setBasicOrElvenKnowledge(BotaniaTweaksConfig.SPORK, LexiconData.corporea);
		setBasicOrElvenKnowledge(BotaniaTweaksConfig.SPORK, LexiconData.corporeaFunnel);
		setBasicOrElvenKnowledge(BotaniaTweaksConfig.SPORK, LexiconData.corporeaInterceptor);
		setBasicOrElvenKnowledge(BotaniaTweaksConfig.SPORK, LexiconData.corporeaRetainer);
		
		if(!BotaniaTweaks.PROXY.shouldAddLexiconPages()) return;
		
		if(BotaniaTweaksConfig.SPORK) {
			LexiconPage spork = new PageCraftingRecipe("botania_tweaks.lexicon.spork", new ResourceLocation(BotaniaTweaks.MODID, "spork"));
			LexiconPage regularCraft = new PageCraftingRecipe("botania_tweaks.lexicon.sporkspark", new ResourceLocation(BotaniaTweaks.MODID, "spork/corporeaspark_0_spork"));
			LexiconPage masterCraft = new PageCraftingRecipe("botania_tweaks.lexicon.masterspork", new ResourceLocation(BotaniaTweaks.MODID, "spork/corporeaspark_1_spork"));
			
			LexiconData.corporea.addPage(spork);
			LexiconData.corporea.addPage(regularCraft);
			LexiconData.corporea.addPage(masterCraft);
		}
		
		for(int i=1; i <= 8; i++) {
			PageCraftingRecipe potat = new PageCraftingRecipe("botania_tweaks.lexicon.potato." + i, new ResourceLocation(BotaniaTweaks.MODID, "compressed_tiny_potato_" + i));
			LexiconData.tinyPotato.addPage(potat);
		}
	}
	
	static void setBasicOrElvenKnowledge(boolean flag, LexiconEntry toFix) {
		toFix.setKnowledgeType(flag ? BotaniaAPI.basicKnowledge : BotaniaAPI.elvenKnowledge);
	}
}