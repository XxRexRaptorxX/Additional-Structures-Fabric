package xxrexraptorxx.additionalstructures;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.launch.FabricLauncher;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Events {

    /**
     * @author XxRexRaptorxX (RexRaptor)
     *
     * When entering a new MC installation for the first time, a message appears informing about the risks of mod reposts.
     * Then generates a marker file in the configs folder with more details. Supports implementation in multiple mods.
     *
     * You are welcome to implement this method in your own mods!
     */
    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();

            Path configDir = FabricLoader.getInstance().getConfigDir();
            Path marker = configDir.resolve("#STOP_MOD_REPOSTS.txt");

            try {
                if (Files.notExists(marker)) {
                    String fileContent = """
                            Sites like 9minecraft.net, mc-mod.net, and many others, are known for reuploading mod files without permission from the authors. These sites will also contain a bunch of ads, to try to make money from mods they did not create.
                            
                            These sites will use various methods to appear higher in Google when you search for the mod name, so a lot of people will download mods from them instead of the proper place. If you were linked to this site, you're one of these people.
                            
                            FOR YOU, AS A PLAYER, THIS CAN MEAN ANY OF THE FOLLOWING:
                            > Getting versions of the mods advertised for the wrong Minecraft versions, which will 100% crash when you load them.
                            > Getting old, and broken, versions of the mods, possibly causing problems in your game.
                            > Getting modified versions of the mods, which may contain malware and viruses.
                            > Having your information stolen from malicious ads in the sites.
                            > Taking money and views away from the official authors, which may cause them to stop making new mods.
                            
                            WHAT DO I DO NOW?
                            The most important thing to do now is to make sure you stop visiting these sites, and get the mods from official sources. We also recommend you do the following:
                            
                            > Delete all the mods you've downloaded from these sites.
                            > Install the StopModReposts plugin, which makes sure you never visit them again.
                            > Run a virus/malware scan. We recommend MalwareBytes.
                            > Check out the #StopModReposts campaign, that tries to put an end to these sites. (https://stopmodreposts.org/)
                            > Spread the word. If you have any friends that use these sites, inform them to keep them safe.
                            
                            WHERE DO I GET MODS NOW?
                            Here's a bunch of links to places where you can download official versions of mods, hosted by their real authors:
                            
                            > CurseForge, where most modders host their mods. If it exists, it's probably there.
                            > Modrinth, a new hosting platform for mods that's also legit and more popular by the day.
                            > OptiFine.net, the official OptiFine site.
                            > Neoforged.net, which you need for any other Neoforge mods.
                            > FabricMC.net, which you need for any other Fabric mods.
                            > MinecraftForge Files, which you need for any other Forge mods.
                            
                            FAQ
                            Q: What if I've never had problems before?
                            > Just because you've never had problems with these sites before doesn't mean they're good. You should still avoid them for all the reasons listed above.
                            
                            Q: Is there a list of these sites I can check out?
                            > Yes, however, due to these showing up all the time, it's possible to be incomplete. (https://github.com/StopModReposts/Illegal-Mod-Sites/blob/master/SITES.md)
                            
                            Q: Why can't you just take these sites down?
                            > Unfortunately, these sites are often hosted in countries like Russia or Vietnam, where doing so isn't as feasible.
                            
                            Q: What if it says "Official Download" on the sites?
                            > Sometimes they'll do that to trick you. If you're uncertain, you should verify with the StopModReposts list linked above.
                            
                            
                            Credits: XxRexRaptorxX, Vazkii, StopModReposts campaign
                            """;

                    Files.writeString(marker, fileContent, StandardCharsets.UTF_8);
                    String launcher = FabricLauncherBase.getLauncher().getTargetNamespace().toLowerCase();

                    if (!launcher.contains("curseforge") || !launcher.contains("modrinth") || !launcher.contains("prism")) {
                        AdditionalStructures.LOGGER.info("Stop-mod-reposts info message is generated. Don't worry, this message should only appear the very first time after installation!");
                        player.sendMessage(Text.literal("Important Information about mod reposts:\n").styled(style -> style.withUnderline(true).withColor(Formatting.DARK_RED)), false);
                        player.sendMessage(Text.literal("Sites like 9minecraft.net, mc-mod.net, etc. are known for reuploading mod files without permissions.\nThese sites will also contain a bunch of ads, to try to make money from mods they did not create.\n").styled(style -> style.withColor(Formatting.RED)), false);
                        player.sendMessage(Text.literal("For you, this can mean any of the following:").styled(style -> style.withUnderline(true).withColor(Formatting.RED)), false);
                        player.sendMessage(Text.literal("- Modified versions of mods, which may contain malware & viruses").styled(style -> style.withColor(Formatting.RED)), false);
                        player.sendMessage(Text.literal("- Having your information stolen from malicious ads").styled(style -> style.withColor(Formatting.RED)), false);
                        player.sendMessage(Text.literal("- Old and broken mod versions that can corrupt your world").styled(style -> style.withColor(Formatting.RED)), false);
                        player.sendMessage(Text.literal("- Taking money and views away from the real authors, which may cause them to stop making mods").styled(style -> style.withColor(Formatting.RED)), false);

                        player.sendMessage(
                                Text.literal("* Click here for more information *")
                                        .styled(style -> style.withColor(Formatting.GOLD).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://vazkii.net/repost/"))),
                                false
                        );
                    }
                }
            } catch (IOException e) {
                AdditionalStructures.LOGGER.error("Failed to create marker file", e);
            }
        });
    }
}