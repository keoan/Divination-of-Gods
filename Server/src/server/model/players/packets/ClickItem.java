package server.model.players.packets;

import server.Config;
import server.model.minigames.TreasureTrails;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.Player;
import server.model.players.content.ArmourSets;
import server.model.players.content.GabbesXPLamp;
import server.model.players.content.combat.BountyHunter.PvPHandler;
import server.util.Misc;

/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItem implements PacketType {
	public static int flower[] = { 2980, 2981, 2982, 2983, 2984, 2985, 2986,
			2987 };

	public static int flowerX = 0;

	public static int flowerY = 0;

	public static int flowerTime = -1;
	public static int flowers = 0;

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		@SuppressWarnings("unused")
		int junk = c.getInStream().readSignedWordBigEndianA();
		int itemSlot = c.getInStream().readUnsignedWordA();
		int itemId = c.getInStream().readUnsignedWordBigEndian();
		if (itemId != c.playerItems[itemSlot] - 1) {
			return;

		}
		if (itemId == 10832) {
			c.getItems().deleteItem(10832, 1);
			c.getItems().addItem(995, 500000000);
			c.sendMessage("You open the bag to find 500M stashed away!");

		}
		if (itemId == 10833) {
			c.getItems().deleteItem(10833, 1);
			c.getItems().addItem(995, 1000000000);
			c.sendMessage("You open the bag to find 1000M stashed away!");

		}
		if (itemId == 10834) {
			c.getItems().deleteItem(10834, 1);
			c.getItems().addItem(995, 2000000000);
			c.sendMessage("You open the bag to find 2000M stashed away!");

		}
		if (itemId == 17616) {
			if (c.inDung) {
				c.getItems().deleteItem(17616, 1);
				c.sendMessage("You drink the strong prayer potion.");
				c.startAnimation(829);
				c.playerLevel[5] += (c.getLevelForXP(c.playerXP[5]) * .40);
				if (c.playerLevel[5] > c.getLevelForXP(c.playerXP[5]))
					c.playerLevel[5] = c.getLevelForXP(c.playerXP[5]);
				c.getPA().refreshSkill(5);
			}
		}
		if (itemId == 17612) {
			if (c.inDung) {
				c.getItems().deleteItem(17612, 1);
				c.sendMessage("You drink the strong defence potion.");
				c.startAnimation(829);
				c.playerLevel[1] += (c.getLevelForXP(c.playerXP[1]) * .22);
				c.getPA().refreshSkill(1);
			}
		}
		if (itemId == 17610) {
			if (c.inDung) {
				c.getItems().deleteItem(17610, 1);
				c.sendMessage("You drink the strong melee potion.");
				c.startAnimation(829);
				c.playerLevel[2] += (c.getLevelForXP(c.playerXP[2]) * .22);
				c.playerLevel[0] += (c.getLevelForXP(c.playerXP[0]) * .22);
				/*
				 * if (c.playerLevel[2] > c.getLevelForXP(c.playerXP[2]))
				 * c.playerLevel[2] = c.getLevelForXP(c.playerXP[2]); if
				 * (c.playerLevel[0] > c.getLevelForXP(c.playerXP[0]))
				 * c.playerLevel[0] = c.getLevelForXP(c.playerXP[0]);
				 */
				c.getPA().refreshSkill(2);
				c.getPA().refreshSkill(0);
			}
		}
		if (itemId == 17608) {
			if (c.inDung) {
				c.getItems().deleteItem(17608, 1);
				c.sendMessage("You drink the strong ranged potion.");
				c.startAnimation(829);
				c.playerLevel[4] += (c.getLevelForXP(c.playerXP[4]) * .22);
				/*
				 * if (c.playerLevel[2] > c.getLevelForXP(c.playerXP[2]))
				 * c.playerLevel[2] = c.getLevelForXP(c.playerXP[2]); if
				 * (c.playerLevel[0] > c.getLevelForXP(c.playerXP[0]))
				 * c.playerLevel[0] = c.getLevelForXP(c.playerXP[0]);
				 */
				c.getPA().refreshSkill(4);
			}
		}
		if (itemId == 12855) {
			if (c.inFoGGame) {
				c.getPA().teleTabTeleport(1663, 5695, 0, "teleTab");
				c.getItems().deleteItem(12855, 1);
				c.sendMessage("You teleport into the middle..");
				return;
			} else {
				c.getItems().deleteItem(12855, 55);
			}
			return;
		}
		if (itemId == 15098) {
			if (c.isDonator != 5) {
				c.sendMessage("You need have dice rank to dice!");
				return;
			}
			if (c.playerRights == 0) {
				c.sendMessage("You need to have dice rank to dice!!");
				return;
			}
			c.useDice(itemId, false);
			return;
		}

		if (ArmourSets.isSet(itemId)) {
			ArmourSets.handleSet(c, itemId);
			return;
		}
		if (itemId == 15262) {// SUMMON SHARDS
			c.getItems().deleteItem(15262, 1);
			c.getItems().addItem(18016, 15000);
		}
		if (itemId == 11967) {// cannon
			if (c.getItems().freeSlots() < 5) {
				c.sendMessage("Get some inventory space first.");
			}
			int CANNON_BASE_ID = 6, CANNON_STAND_ID = 8, CANNON_BARRELS_ID = 10, CANNON_FURNACE_ID = 12;
			c.getItems().addItem(CANNON_BASE_ID, 1);
			c.getItems().addItem(CANNON_STAND_ID, 1);
			c.getItems().addItem(CANNON_BARRELS_ID, 1);
			c.getItems().addItem(CANNON_FURNACE_ID, 1);
			c.getItems().deleteItem(11967, 1);
			return;
		}
		if (itemId == 15246) { // BARRAGE RUNES
			c.getItems().deleteItem(15246, 1);
			c.getItems().addItem(560, 4000);
			c.getItems().addItem(555, 5000);
			c.getItems().addItem(565, 4000);
		}
		if (itemId == 15247) { // VENG RUNES
			c.getItems().deleteItem(15247, 1);
			c.getItems().addItem(560, 4000);
			c.getItems().addItem(557, 5000);
			c.getItems().addItem(9075, 4000);
		}
		if (itemId == 15084) {
			c.getDH().sendDialogues(106, 4289);
		}

		if (itemId == 11949) {
			c.makesnow();
			c.stopMovement();
		}
		if (itemId == 11137) {
			GabbesXPLamp.lampOpened = 11137;
			GabbesXPLamp.openInterface(c);
		}
		if (itemId == 7498) {
			GabbesXPLamp.lampOpened = 7498;
			GabbesXPLamp.openInterface(c);
		}

		if (itemId == 299 && c.seedtimer == 0) {
			if (!c.inVarrock()) {
				c.sendMessage("You must be in Varrock to plant seeds.");
				return;
			}
			flowers = randomflower();
			flowerX += c.absX;
			flowerY += c.absY;
			c.getPA().object(flowers, c.absX, c.absY, 0, 10);
			c.sendMessage("You plant the seed...");
			c.seedtimer += 5;
			c.getTimers().seedtimer(c);
			c.getItems().deleteItem(299, 1);
			c.getPA().walkTo(0, 1);
			c.getDH().sendDialogues(5672, -1);
			if (ClickItem.flowers == 2980) {
				c.floweritem = 2460;
			} else if (ClickItem.flowers == 2981) {
				c.floweritem = 2462;
			} else if (ClickItem.flowers == 2982) {
				c.floweritem = 2464;
			} else if (ClickItem.flowers == 2983) {
				c.floweritem = 2466;
			} else if (ClickItem.flowers == 2984) {
				c.floweritem = 2468;
			} else if (ClickItem.flowers == 2985) {
				c.floweritem = 2470;
			} else if (ClickItem.flowers == 2986) {
				c.floweritem = 2472;
			} else if (ClickItem.flowers == 2987) {
				c.floweritem = 2474;
			}
		}

		if (itemId == 9721) {
			c.getPA().showInterface(6965);
			c.getPA()
					.sendFrame126("@red@~ Overload Instruction Manual ~", 6968);
			c.getPA().sendFrame126("", 6969);
			c.getPA().sendFrame126(
					"@gre@Step 1: @cya@Get a Herblore level of 99.", 6970);
			c.getPA().sendFrame126(
					"@gre@Step 2: @cya@Have all Extreme Potions ", 6971);
			c.getPA()
					.sendFrame126("@cya@In your inventory, along with a", 6972);
			c.getPA().sendFrame126(
					"@gre@Step 3: @cya@Cleaned torstol ,use the", 6973);
			c.getPA().sendFrame126("@cya@ Torstol on an extreme potion.", 6974);
			c.getPA().sendFrame126(
					"@gre@Step 4: @cya@You now have an overload!", 6975);
			c.getPA().sendFrame126("", 6976);
			c.getPA().sendFrame126("", 6977);
			c.getPA().sendFrame126("", 6978);
			c.getPA().sendFrame126("", 6979);
			c.getPA().sendFrame126("", 6980);
		}

		if (itemId == 1856) {
			c.getPA().showInterface(6965);
			c.getPA().sendFrame126("@red@~ Duel Arena - Known Bugs ~", 6968);
			c.getPA().sendFrame126("", 6969);
			c.getPA().sendFrame126("@gre@1.@cya@Dueling is at your own risk.",
					6970);
			c.getPA().sendFrame126("@cya@If you loose items to players/bugs",
					6971);
			c.getPA().sendFrame126("@cya@ YOU WILL NOT GET YOUR ITEMS BACK.",
					6972);
			c.getPA().sendFrame126("@gre@2.@cya@FORFEITING DOES NOT WORK!!",
					6973);
			c.getPA()
					.sendFrame126("@gre@3.@cya@FUN WEAPONS ARE DISABLED", 6974);
			c.getPA().sendFrame126(
					"@gre@4.@cya@ROCKTAILS COUNTS AS FOOD! BE CAREFUL", 6975);
			c.getPA().sendFrame126("", 6976);
			c.getPA().sendFrame126("", 6977);
			c.getPA().sendFrame126("", 6978);
			c.getPA().sendFrame126("", 6979);
			c.getPA().sendFrame126("", 6980);
		}

		if (itemId == 9719) {
			c.getPA().showInterface(6965);
			c.getPA()
					.sendFrame126("@red@~ Extremes Instruction Manual ~", 6968);
			c.getPA().sendFrame126("", 6969);
			c.getPA().sendFrame126(
					"@gre@Step 1: @cya@Get a Herblore level of 80.", 6970);
			c.getPA().sendFrame126(
					"@gre@Step 2: @cya@Have a super potion (3) and ", 6971);
			c.getPA().sendFrame126("@cya@ the required cleaned herb.", 6972);
			c.getPA().sendFrame126(
					"@gre@Step 3: @cya@Use the herb on the super potion", 6973);
			c.getPA().sendFrame126(
					"@cya@ You will now get a extreme potion (3)", 6974);
			c.getPA().sendFrame126(
					"@gre@Step 4: @cya@Use RS Wiki for herbs req's", 6975);
			c.getPA().sendFrame126("", 6976);
			c.getPA().sendFrame126("", 6977);
			c.getPA().sendFrame126("", 6978);
			c.getPA().sendFrame126("", 6979);
			c.getPA().sendFrame126("", 6980);
		}

		if (itemId == 8013) {

			if (c.inCwWait) {
				c.sendMessage("To leave, use the portal!");
				return;
			}
			if (c.inWarriorG()) {
				return;
			}
			if (c.inPits) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.getPA().inPitsWait()) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.Jail == true) {
				c.sendMessage("You can't teleport out of Jail!");
			}
			if (c.inJail() && c.Jail == true) {
				c.sendMessage("You can't teleport out oaf Jail!");
			}
			if (c.duelStatus == 5) {
				c.sendMessage("You can't teleport during a duel!");
				return;
			}
			if (c.InDung) {
				c.sendMessage("You can't teleport from here moron");
				return;
			}
			if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
				c.sendMessage("You can't teleport above level "
						+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
				return;
			}
			if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
				c.sendMessage("You are teleblocked and can't teleport.");
				return;
			}
			if (c.inNomad()) {
				c.sendMessage("You can't teleport during Nomad Minigame");
				return;
			}
			if (c.inGoblin()) {
				c.sendMessage("You can't teleport during Goblin Minigame");
				return;
			}
			if (c.hasHousee == false) {
				c.sendMessage("You don't have a land!");
				return;
			}

			if (c.playerLevel[23] == 1) {
				c.sendMessage("Start of by building chairs!");
				c.getPA().teleTabTeleport(2058, 3146, 4, "house");
				c.getItems()
						.deleteItem(8013, c.getItems().getItemSlot(8013), 1);
				return;
			}
			c.getPA().teleTabTeleport(2059, 3143, 4, "house");
			c.sendMessage("You teleport to your land..");
			c.getItems().deleteItem(8013, c.getItems().getItemSlot(8013), 1);
		}
		if (itemId == 18814) {
			if (c.inWarriorG()) {
				return;
			}
			if (c.inPits) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.getPA().inPitsWait()) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.Jail == true) {
				c.sendMessage("You can't teleport out of Jail!");
			}
			if (c.inJail() && c.Jail == true) {
				c.sendMessage("You can't teleport out oaf Jail!");
			}
			if (c.duelStatus == 5) {
				c.sendMessage("You can't teleport during a duel!");
				return;
			}
			if (c.InDung) {
				c.sendMessage("You can't teleport from here moron");
				return;
			}
			if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
				c.sendMessage("You can't teleport above level "
						+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
				return;
			}
			if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
				c.sendMessage("You are teleblocked and can't teleport.");
				return;
			}
			if (c.inNomad()) {
				c.sendMessage("You can't teleport during Nomad Minigame");
				return;
			}
			if (c.inGoblin()) {
				c.sendMessage("You can't teleport during Goblin Minigame");
				return;
			}

			if (c.inCwWait) {
				c.sendMessage("To leave, use the portal!");
				return;
			}
			c.getPA().movePlayer(2602, 3088, 0);
			c.sendMessage("You teleport to Yanille..");
			c.getItems().deleteItem(18814, c.getItems().getItemSlot(18814), 1);
		}

		if (itemId == 15707) {

			if (c.inCwWait) {
				c.sendMessage("To leave, use the portal!");
				return;
			}
			if (c.inDuelArena()) {
				c.sendMessage("You cannot teleport right now.");
				return;
			}
			c.getPA().startTeleport(2416, 3526, 0, "dungtele");
			// c.sendMessage("Your Ring of Kinship takes you to Dungeoneering.");
			// c.sendMessage("Dungeoneering is currently Disabled! We're adding new dung atm.");
		}
		if (itemId == 18821) {

			if (c.inCwWait) {
				c.sendMessage("To leave, use the portal!");
				return;
			}
			if (c.inDuelArena()) {
				c.sendMessage("You cannot teleport right now.");
				return;
			}
			c.getPA().startTeleport(2416, 3526, 0, "dungtele");
			c.sendMessage("Your Ring of Kinship takes you to Dungeoneering.");
			// c.sendMessage("Dungeoneering is currently Disabled! We're adding new dung atm.");
		}
		if (itemId == 18817) {
			if (c.inDuelArena()) {
				c.sendMessage("You cannot teleport right now.");
				return;
			}

			if (c.inCwWait) {
				c.sendMessage("To leave, use the portal!");
				return;
			}
			c.getPA().startTeleport(2416, 3526, 0, "dungtele");
			// c.sendMessage("Your Ring of Kinship takes you to Dungeoneering.");
			// c.sendMessage("Dungeoneering is currently Disabled! We're adding new dung atm.");
		}
		if (itemId == 18823) {
			if (c.inDuelArena()) {
				c.sendMessage("You cannot teleport right now.");
				return;
			}
			c.getPA().startTeleport(2416, 3526, 0, "dungtele");
			// c.sendMessage("Your Ring of Kinship takes you to Dungeoneering.");
			// c.sendMessage("Dungeoneering is currently Disabled! We're adding new dung atm.");
		}

		if (itemId == 8007) {

			if (c.inCwWait) {
				c.sendMessage("To leave, use the portal!");
				return;
			}
			if (c.inPits) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.getPA().inPitsWait()) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.duelStatus == 5) {
				c.sendMessage("You can't teleport during a duel!");
				return;
			}
			if (c.Jail == true) {
				c.sendMessage("You can't teleport out of Jail!");
			}
			if (c.inJail() && c.Jail == true) {
				c.sendMessage("You can't teleport out oaf Jail!");
			}
			if (c.InDung) {
				c.sendMessage("You can't teleport from here moron");
				return;
			}
			if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
				c.sendMessage("You can't teleport above level "
						+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
				return;
			}
			if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
				c.sendMessage("You are teleblocked and can't teleport.");
				return;
			}
			if (c.inNomad()) {
				c.sendMessage("You can't teleport during Nomad Minigame");
				return;
			}
			if (c.inWarriorG()) {
				return;
			}
			if (c.inGoblin()) {
				c.sendMessage("You can't teleport during Goblin Minigame");
				return;
			}
			c.getPA().teleTabTeleport(3216, 3424, 0, "teleTab");
			c.getItems().deleteItem(8007, c.getItems().getItemSlot(8007), 1);
		}
		if (itemId == 8008) {
			if (c.inWarriorG()) {
				return;
			}
			if (c.inPits) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.getPA().inPitsWait()) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.Jail == true) {
				c.sendMessage("You can't teleport out of Jail!");
			}
			if (c.inJail() && c.Jail == true) {
				c.sendMessage("You can't teleport out oaf Jail!");
			}
			if (c.duelStatus == 5) {
				c.sendMessage("You can't teleport during a duel!");
				return;
			}
			if (c.InDung) {
				c.sendMessage("You can't teleport from here moron");
				return;
			}
			if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
				c.sendMessage("You can't teleport above level "
						+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
				return;
			}
			if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
				c.sendMessage("You are teleblocked and can't teleport.");
				return;
			}
			if (c.inNomad()) {
				c.sendMessage("You can't teleport during Nomad Minigame");
				return;
			}
			if (c.inGoblin()) {
				c.sendMessage("You can't teleport during Goblin Minigame");
				return;
			}

			if (c.inCwWait) {
				c.sendMessage("To leave, use the portal!");
				return;
			}
			c.getPA().teleTabTeleport(3221, 3217, 0, "teleTab");
			c.getItems().deleteItem(8008, c.getItems().getItemSlot(8008), 1);
		}
		if (itemId == 8009) {

			if (c.inCwWait) {
				c.sendMessage("To leave, use the portal!");
				return;
			}
			if (c.inWarriorG()) {
				return;
			}
			if (c.inPits) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.getPA().inPitsWait()) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.Jail == true) {
				c.sendMessage("You can't teleport out of Jail!");
			}
			if (c.inJail() && c.Jail == true) {
				c.sendMessage("You can't teleport out oaf Jail!");
			}
			if (c.duelStatus == 5) {
				c.sendMessage("You can't teleport during a duel!");
				return;
			}
			if (c.InDung) {
				c.sendMessage("You can't teleport from here moron");
				return;
			}
			if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
				c.sendMessage("You can't teleport above level "
						+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
				return;
			}
			if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
				c.sendMessage("You are teleblocked and can't teleport.");
				return;
			}
			if (c.inNomad()) {
				c.sendMessage("You can't teleport during Nomad Minigame");
				return;
			}
			if (c.inGoblin()) {
				c.sendMessage("You can't teleport during Goblin Minigame");
				return;
			}
			c.getPA().teleTabTeleport(2964, 3380, 0, "teleTab");
			c.getItems().deleteItem(8009, c.getItems().getItemSlot(8009), 1);
		}
		if (itemId == 10233) {
			TreasureTrails.addClueReward(c, 0);
		}

		if (itemId == 7318) {
			TreasureTrails.addClueReward(c, 1);
			return;
		}

		if (itemId == 10232) {
			c.getPA().showInterface(6965);
			c.getPA().sendFrame126("@red@Clue Scroll - Easy", 6968);
			c.getPA().sendFrame126("Clue (where to dig):", 6969);
			c.getPA().sendFrame126("I'd love to sleep near the hills..", 6970);
			c.getPA().sendFrame126("", 6971);
			c.getPA().sendFrame126("", 6972);
			c.getPA().sendFrame126("", 6973);
			c.getPA().sendFrame126("", 6974);
			c.getPA().sendFrame126("", 6975);
			c.getPA().sendFrame126("", 6976);
			c.getPA().sendFrame126("", 6977);
			c.getPA().sendFrame126("", 6978);
			c.getPA().sendFrame126("", 6979);
			c.getPA().sendFrame126("", 6980);
			c.sendMessage("You read the scroll...");
			return;
		}
		if (itemId == 13040) {
			c.getPA().showInterface(6965);
			c.getPA().sendFrame126("@red@Clue Scroll - Hard", 6968);
			c.getPA().sendFrame126("Clue (where to dig):", 6969);
			c.getPA().sendFrame126("Edgeville has 2 altars,", 6970);
			c.getPA().sendFrame126("One at home and one at .....", 6971);
			c.getPA().sendFrame126("Where is the other altar located?", 6972);
			c.getPA().sendFrame126("Dig next to the Font.", 6973);
			c.getPA().sendFrame126("", 6974);
			c.getPA().sendFrame126("", 6975);
			c.getPA().sendFrame126("", 6976);
			c.getPA().sendFrame126("", 6977);
			c.getPA().sendFrame126("", 6978);
			c.getPA().sendFrame126("", 6979);
			c.getPA().sendFrame126("", 6980);
			c.sendMessage("You read the scroll...");
			return;
		}
		if (itemId == 13078) {
			c.getPA().showInterface(6965);
			c.getPA().sendFrame126("@red@Clue Scroll - Medium", 6968);
			c.getPA().sendFrame126("Clue:", 6969);
			c.getPA().sendFrame126("Dig infront of the 'banging door'.", 6970);
			c.getPA().sendFrame126("", 6971);
			c.getPA().sendFrame126("", 6972);
			c.getPA().sendFrame126("", 6973);
			c.getPA().sendFrame126("", 6974);
			c.getPA().sendFrame126("", 6975);
			c.getPA().sendFrame126("", 6976);
			c.getPA().sendFrame126("", 6977);
			c.getPA().sendFrame126("", 6978);
			c.getPA().sendFrame126("", 6979);
			c.getPA().sendFrame126("", 6980);
			c.sendMessage("You read the scroll...");
			return;
		}
		if (itemId == 13080) {
			c.getPA().showInterface(6965);
			c.getPA().sendFrame126("@red@Clue Scroll - Medium", 6968);
			c.getPA().sendFrame126("Clue (where to dig):", 6969);
			c.getPA().sendFrame126("The floor is so hot, and the human", 6970);
			c.getPA().sendFrame126("Should be burning!", 6971);
			c.getPA().sendFrame126("", 6972);
			c.getPA().sendFrame126("", 6973);
			c.getPA().sendFrame126("", 6974);
			c.getPA().sendFrame126("", 6975);
			c.getPA().sendFrame126("", 6976);
			c.getPA().sendFrame126("", 6977);
			c.getPA().sendFrame126("", 6978);
			c.getPA().sendFrame126("", 6979);
			c.getPA().sendFrame126("", 6980);
			c.sendMessage("You read the scroll...");
			return;
		}
		if (itemId == 13079) {
			c.getPA().showInterface(17620);
			c.sendMessage("You read the scroll...");
			return;
		}
		if (itemId == 13041) {
			c.getPA().showInterface(9043);
			c.sendMessage("You read the scroll...");
			return;
		}
		if (itemId == 13042) {
			c.getPA().showInterface(9275);
			c.sendMessage("You read the scroll...");
			return;
		}
		if (itemId == 10224) {
			c.getPA().showInterface(17537);
			c.sendMessage("You read the scroll..");
			return;
		}
		c.setLastClicked(itemId);
		if (itemId == 18778 || itemId == 18779 || itemId == 18780
				|| itemId == 18781) {
			if (c.inVarrock()) { // (TOPLEFTX, TOPLEFTY, BOTTOMRIGHTX,
									// BOTTOMRIGHTY);
				c.getDH().sendDialogues(33, 0);
				return;
			} else {
				c.sendMessage("You must be standing in Varrock to Investigate this item.");
				return;
			}
		}

		if (itemId == 18782) {
			GabbesXPLamp.lampOpened = 18782;
			GabbesXPLamp.openInterface(c);
			return;
		}
		if (itemId == 405) {
			TreasureTrails.addClueReward(c, 2);
		}
		if (itemId == 8010) {

			if (c.inCwWait) {
				c.sendMessage("To leave, use the portal!");
				return;
			}
			if (c.inWarriorG()) {
				return;
			}
			if (c.inPits) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.getPA().inPitsWait()) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.Jail == true) {
				c.sendMessage("You can't teleport out of Jail!");
			}
			if (c.inJail() && c.Jail == true) {
				c.sendMessage("You can't teleport out oaf Jail!");
			}
			if (c.duelStatus == 5) {
				c.sendMessage("You can't teleport during a duel!");
				return;
			}
			if (c.InDung) {
				c.sendMessage("You can't teleport from here moron");
				return;
			}
			if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
				c.sendMessage("You can't teleport above level "
						+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
				return;
			}
			if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
				c.sendMessage("You are teleblocked and can't teleport.");
				return;
			}
			if (c.inNomad()) {
				c.sendMessage("You can't teleport during Nomad Minigame");
				return;
			}
			if (c.inGoblin()) {
				c.sendMessage("You can't teleport during Goblin Minigame");
				return;
			}
			c.getPA().teleTabTeleport(2756, 3479, 0, "teleTab");
			c.getItems().deleteItem(8010, c.getItems().getItemSlot(8010), 1);
		}
		if (itemId == 8011) {

			if (c.inCwWait) {
				c.sendMessage("To leave, use the portal!");
				return;
			}
			if (c.inWarriorG()) {
				return;
			}
			if (c.inPits) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.getPA().inPitsWait()) {
				c.sendMessage("You can't teleport during Fight Pits.");
				return;
			}
			if (c.Jail == true) {
				c.sendMessage("You can't teleport out of Jail!");
			}
			if (c.inJail() && c.Jail == true) {
				c.sendMessage("You can't teleport out oaf Jail!");
			}
			if (c.duelStatus == 5) {
				c.sendMessage("You can't teleport during a duel!");
				return;
			}
			if (c.InDung) {
				c.sendMessage("You can't teleport from here moron");
				return;
			}
			if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
				c.sendMessage("You can't teleport above level "
						+ Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
				return;
			}
			if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
				c.sendMessage("You are teleblocked and can't teleport.");
				return;
			}
			if (c.inNomad()) {
				c.sendMessage("You can't teleport during Nomad Minigame");
				return;
			}
			if (c.inGoblin()) {
				c.sendMessage("You can't teleport during Goblin Minigame");
				return;
			}
			c.getPA().teleTabTeleport(2661, 3306, 0, "teleTab");
			c.foodDelay = System.currentTimeMillis();
			c.getItems().deleteItem(8011, c.getItems().getItemSlot(8011), 1);
		}
		if (itemId == 8012) {
			PvPHandler.handleTeleTab(c, false);
		}
		if (itemId == 4447) {
			c.getPA().addSkillXP(3000, 24);
			c.sendMessage("You rub the lamp and feel yourself further in the arts of dungeoneering.");
			c.getItems().deleteItem(4447, 1);
		}
		if (itemId == 15262) {
			c.getItems().addItem(18016, 10000);
			c.getItems().deleteItem(15262, 1);
		}

		if (itemId == 6796) {
			c.playerLevel[0] = 99;
			c.playerLevel[2] = 99;
			c.playerLevel[3] = 99;
			c.playerLevel[4] = 99;
			c.playerLevel[6] = 99;
			c.playerXP[0] = c.getPA().getXPForLevel(100);
			c.playerXP[2] = c.getPA().getXPForLevel(100);
			c.playerXP[3] = c.getPA().getXPForLevel(100);
			c.playerXP[4] = c.getPA().getXPForLevel(100);
			c.playerXP[6] = c.getPA().getXPForLevel(100);
			c.getPA().refreshSkill(0);
			c.getPA().refreshSkill(2);
			c.getPA().refreshSkill(3);
			c.getPA().refreshSkill(4);
			c.getPA().refreshSkill(6);
			c.getItems().deleteItem(6796, 1);
			c.logout();
		}

		/*
		 * if (itemId == 15272) { if (System.currentTimeMillis() - c.foodDelay
		 * >= 1500 && c.playerLevel[3] > 0) { if(c.playerLevel[3] < 135 &&
		 * torva(c)) { if (torva(c) && c.playerLevel[3] < 135) {
		 * if(c.playerLevel[3] > 100 && c.playerLevel[3] < 120) {
		 * c.playerLevel[3] += 23; c.foodDelay = System.currentTimeMillis();
		 * c.getPA().refreshSkill(3); c.sendMessage("You eat the Rocktail.");
		 * c.getCombat().resetPlayerAttack(); c.attackTimer += 2;
		 * c.startAnimation(829); c.getItems().deleteItem(15272, 1); } } else {
		 * if (!torva(c)) { if (!torva(c) && c.playerLevel[3] >
		 * c.getLevelForXP(c.playerXP[3])) { c.playerLevel[3] =
		 * c.getLevelForXP(c.playerXP[3] + 10); return; } c.playerLevel[3] +=
		 * 23; c.foodDelay = System.currentTimeMillis();
		 * c.getPA().refreshSkill(3); c.sendMessage("You eat the Rocktail.");
		 * c.getCombat().resetPlayerAttack(); c.attackTimer += 2;
		 * c.startAnimation(829); c.getItems().deleteItem(15272, 1);
		 * c.sendMessage("3");
		 * 
		 * 
		 * // c.playerLevel[3] += 10; if (c.playerLevel[3] >
		 * (c.getLevelForXP(c.playerXP[3]) * 1.11 + 1) && !torva(c)) {
		 * c.playerLevel[3] = (int) (c.getLevelForXP(c.playerXP[3]) * 1.11);
		 * c.sendMessage("1"); } c.getPA().refreshSkill(3); return; } else {
		 * c.sendMessage("You alredy have full hp!"); } } /*} else {
		 * c.playerLevel[3] = c.getLevelForXP(c.playerXP[3] + 54);
		 * c.getPA().refreshSkill(3); return; }* } } }
		 */
		if (itemId == 2528) {
			c.getItems().deleteItem(2528, 1);
			c.getPA().showInterface(2808);
		}
		if (itemId == 11850) {
			c.getItems().deleteItem(11850, 1);
			c.getItems().addItem(4724, 1);
			c.getItems().addItem(4726, 1);
			c.getItems().addItem(4728, 1);
			c.getItems().addItem(4730, 1);
		}
		if (itemId == 11852) {
			c.getItems().deleteItem(11852, 1);
			c.getItems().addItem(4732, 1);
			c.getItems().addItem(4734, 1);
			c.getItems().addItem(4736, 1);
			c.getItems().addItem(4738, 1);
		}
		if (itemId == 11854) {
			c.getItems().deleteItem(11854, 1);
			c.getItems().addItem(4745, 1);
			c.getItems().addItem(4747, 1);
			c.getItems().addItem(4749, 1);
			c.getItems().addItem(4751, 1);
		}
		if (itemId == 11856) {
			c.getItems().deleteItem(11856, 1);
			c.getItems().addItem(4732, 1);
			c.getItems().addItem(4734, 1);
			c.getItems().addItem(4736, 1);
			c.getItems().addItem(4738, 1);
		}
		if (itemId == 11848) {
			c.getItems().deleteItem(11848, 1);
			c.getItems().addItem(4716, 1);
			c.getItems().addItem(4718, 1);
			c.getItems().addItem(4720, 1);
			c.getItems().addItem(4722, 1);
		}
		if (itemId == 11846) {
			c.getItems().deleteItem(11846, 1);
			c.getItems().addItem(4708, 1);
			c.getItems().addItem(4710, 1);
			c.getItems().addItem(4712, 1);
			c.getItems().addItem(4714, 1);
		}
		if (itemId == 5070) { // BIRD'S NEST
			int Lol = Misc.random(10);
			if (Lol > 0 && Lol < 2) {
				c.getItems().deleteItem(5070, 1);
				c.getItems().addItem(5304, 4);
				c.sendMessage("The nest contained some seeds.");
				return;
			}
			if (Lol > 2 && Lol < 4) {
				c.getItems().deleteItem(5070, 1);
				c.getItems().addItem(12163, 4);
				c.sendMessage("The nest contained some charms.");
				return;
			}
			if (Lol > 4 && Lol < 6) {
				c.getItems().deleteItem(5070, 1);
				c.getItems().addItem(7498, 1);
				c.sendMessage("The nest contained a lamp.");
				return;
			}
			if (Lol > 6 && Lol < 8) {
				c.getItems().deleteItem(5070, 1);
				c.getItems().addItem(13281, 1);
				c.sendMessage("The nest contained a ring.");
				return;
			}
			if (Lol > 8 && Lol < 11) {
				c.getItems().deleteItem(5070, 1);
				c.getItems().addItem(13281, 1);
				c.sendMessage("The nest contained a ring.");
				return;
			}
		}
		/*
		 * if (itemId >= 14876 && itemId <= 14892) { int a = itemId; String
		 * YEYAF = "<col=1532693>You Exchanged Your Artifact For</col> "; if (a
		 * == 14876){ c.getItems().deleteItem(a,1);
		 * c.getItems().addItem(995,10000000); c.sendMessage(YEYAF +
		 * "<col=1532693>10 million Coins!</col>"); } if (a == 14877){
		 * c.getItems().deleteItem(a,1); c.getItems().addItem(995,2000000);
		 * c.sendMessage(YEYAF + "<col=1532693>2 million Coins!</col>"); } if (a
		 * == 14878){ c.getItems().deleteItem(a,1);
		 * c.getItems().addItem(995,1500000); c.sendMessage(YEYAF +
		 * "<col=1532693>1.5 million Coins!</col>"); } if (a == 14879){
		 * c.getItems().deleteItem(a,1); c.getItems().addItem(995,1000000);
		 * c.sendMessage(YEYAF + "<col=1532693>1 million Coins!</col>"); } if (a
		 * == 14880){ c.getItems().deleteItem(a,1);
		 * c.getItems().addItem(995,800000); c.sendMessage(YEYAF +
		 * "<col=1532693>800,000 Coins!</col>"); } if (a == 14881){
		 * c.getItems().deleteItem(a,1); c.getItems().addItem(995,600000);
		 * c.sendMessage(YEYAF + "<col=1532693>600,000 Coins!</col>"); } if (a
		 * == 14882){ c.getItems().deleteItem(a,1);
		 * c.getItems().addItem(995,540000); c.sendMessage(YEYAF +
		 * "<col=1532693>540,000 Coins!</col>"); } if (a == 14883){
		 * c.getItems().deleteItem(a,1); c.getItems().addItem(995,400000);
		 * c.sendMessage(YEYAF + "<col=1532693>400,000 Coins!</col>"); } if (a
		 * == 14884){ c.getItems().deleteItem(a,1);
		 * c.getItems().addItem(995,300000); c.sendMessage(YEYAF +
		 * "<col=1532693>300,000 Coins!</col>"); } if (a == 14885){
		 * c.getItems().deleteItem(a,1); c.getItems().addItem(995,200000);
		 * c.sendMessage(YEYAF + "<col=1532693>200,000 Coins!</col>"); } if (a
		 * == 14886){ c.getItems().deleteItem(a,1);
		 * c.getItems().addItem(995,150000); c.sendMessage(YEYAF +
		 * "<col=1532693>150,000 Coins!</col>"); } if (a == 14887){
		 * c.getItems().deleteItem(a,1); c.getItems().addItem(995,100000);
		 * c.sendMessage(YEYAF + "<col=1532693>100,000 Coins!</col>"); } if (a
		 * == 14888){ c.getItems().deleteItem(a,1);
		 * c.getItems().addItem(995,80000); c.sendMessage(YEYAF +
		 * "<col=1532693>80,000 Coins!</col>"); } if (a == 14889){
		 * c.getItems().deleteItem(a,1); c.getItems().addItem(995,60000);
		 * c.sendMessage(YEYAF + "<col=1532693>60,000 Coins!</col>"); } if (a ==
		 * 14890){ c.getItems().deleteItem(a,1);
		 * c.getItems().addItem(995,40000); c.sendMessage(YEYAF +
		 * "<col=1532693>40,000 Coins!</col>"); } if (a == 14891){
		 * c.getItems().deleteItem(a,1); c.getItems().addItem(995,20000);
		 * c.sendMessage(YEYAF + "<col=1532693>20,000 Coins!</col>"); } if (a ==
		 * 14892){ c.getItems().deleteItem(a,1);
		 * c.getItems().addItem(995,10000); c.sendMessage(YEYAF +
		 * "<col=1532693>10,000 Coins!</col>"); }
		 * 
		 * }
		 */

		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			int a = itemId;
			if (a == 5509)
				pouch = 0;
			if (a == 5510)
				pouch = 1;
			if (a == 5512)
				pouch = 2;
			if (a == 5514)
				pouch = 3;
			c.getPA().fillPouch(pouch);
			return;
		}
		if (itemId == 6) {
			c.getCannon().setUpCannon();
			return;
		}
		if (itemId == 6199) {
			c.getItems().deleteItem(6199, 1);
			c.getItems().addItem(20087, 1);
			c.sendMessage("Happy Holidays from the staff!");
			return;
		}
		if (itemId == 3062) {
			c.getItems().deleteItem(3062, 1);
			c.getItems().addItem(20076, 1);
			c.sendMessage("Happy Holidays from the staff!");
			return;
		}
		if (itemId == 10025) {
			c.getItems().deleteItem(10025, 1);
			c.getItems().addItem(20077, 1);
			c.sendMessage("Happy Holidays from the staff!");
			return;
		}
		if (itemId == 2720) {
			c.getItems().deleteItem(2720, 1);
			c.getItems().addItem(20086, 1);
			c.sendMessage("Happy Holidays from the staff!");
			return;
		}
		if (itemId == 2717) {
			c.getItems().deleteItem(2717, 1);
			c.getItems().addItem(20085, 1);
			c.sendMessage("Happy Holidays from the staff!");
			return;
		}
		if (itemId == 2724) {
			c.getItems().deleteItem(2724, 1);
			c.getItems().addItem(20073, 1);
			c.sendMessage("Happy Holidays from the staff!");
			return;
		}
		if (itemId == 3565) {
			c.getItems().deleteItem(3565, 1);
			c.getItems().addItem(995, 5000000);
			c.sendMessage("Happy Holidays from the staff!");
			return;
		}
		if (itemId == 3567) {
			c.getItems().deleteItem(3567, 1);
			c.getItems().addItem(1419, 1);
			c.sendMessage("Happy Holidays from the staff!");
			return;
		}
		if (itemId == 3569) {
			c.getItems().deleteItem(3569, 1);
			c.getItems().addItem(1037, 1);
			c.sendMessage("Happy Holidays from the staff!");
			return;
		}
		if (itemId == 3571) {
			c.getItems().deleteItem(3571, 1);
			c.getItems().addItem(20080, 1);
			c.sendMessage("Congradulations Contest Winner!  -Divination Staff");
			return;
		}
		if (c.getHerblore().checkGrimy(itemId, 0))
			c.getHerblore().handleHerbClick(itemId);
		if (c.getFood().isFood(itemId))
			c.getFood().eat(itemId, itemSlot);
		// ScriptManager.callFunc("itemClick_"+itemId, c, itemId, itemSlot);
		if (c.getPotions().isPotion(itemId))
			c.getPotions().handlePotion(itemId, itemSlot);
		if (c.getPrayer().isBone(itemId))
			c.getPrayer().buryBone(itemId, itemSlot);
		if (itemId == 952) {
			if (c.inArea(3553, 3301, 3561, 3294)) {
				c.teleTimer = 3;
				c.newLocation = 1;
			} else if (c.inArea(3550, 3287, 3557, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 2;
			} else if (c.inArea(3561, 3292, 3568, 3285)) {
				c.teleTimer = 3;
				c.newLocation = 3;
			} else if (c.inArea(3570, 3302, 3579, 3293)) {
				c.teleTimer = 3;
				c.newLocation = 4;
			} else if (c.inArea(3571, 3285, 3582, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 5;
			} else if (c.inArea(3562, 3279, 3569, 3273)) {
				c.teleTimer = 3;
				c.newLocation = 6;
			} else if (c.inArea(2986, 3370, 3013, 3388)) {
				c.teleTimer = 3;
				c.newLocation = 7;

				// CLUE SCROLLS START BY GABBE
				// barros easy
			} else if (c.absX == 3570 && c.absY == 3310
					&& c.getItems().playerHasItem(10232, 1)) { // barrows bed
				c.sendMessage("You've found another clue scroll!");
				c.getItems().deleteItem(10232, 1);
				c.getItems().addItem(10224, 1);
				TreasureTrails.handleDig(c);
			} else if (c.absX == 3570 && c.absY == 3311
					&& c.getItems().playerHasItem(10232, 1)) { // barrows bed
				c.sendMessage("You've found another clue scroll!");
				c.getItems().deleteItem(10232, 1);
				c.getItems().addItem(10224, 1);
				TreasureTrails.handleDig(c);
				// Falador easy
			} else if (c.absX == 2973 && c.absY == 3415
					&& c.getItems().playerHasItem(10224, 1)) {
				c.sendMessage("You've found a casket!");
				c.getItems().deleteItem(10224, 1);
				c.getItems().addItem(10233, 1);
				TreasureTrails.handleDig(c);
			} else if (c.absX == 2974 && c.absY == 3415
					&& c.getItems().playerHasItem(10224, 1)) {
				c.sendMessage("You've found a casket!");
				c.getItems().deleteItem(10224, 1);
				c.getItems().addItem(10233, 1);
				TreasureTrails.handleDig(c);
				// Wilderness Medium
			} else if (c.absX == 3138 && c.absY == 3912
					&& c.getItems().playerHasItem(13079, 1)) {
				c.sendMessage("You've found another clue scroll!");
				c.getItems().deleteItem(13079, 1);
				c.getItems().addItem(13080, 1);
				TreasureTrails.handleDig(c);
			} else if (c.absX == 3138 && c.absY == 3911
					&& c.getItems().playerHasItem(13079, 1)) {
				c.sendMessage("You've found another clue scroll!");
				c.getItems().deleteItem(13079, 1);
				c.getItems().addItem(13080, 1);
				TreasureTrails.handleDig(c);
				// Fight caves banker medium
			} else if (c.absX == 2445 && c.absY == 5178
					&& c.getItems().playerHasItem(13080, 1)) {
				c.sendMessage("You've found a casket!");
				c.getItems().deleteItem(13080, 1);
				c.getItems().addItem(7318, 1);
				TreasureTrails.handleDig(c);
				// Bandos door, medium
			} else if (c.absX == 2851 && c.absY == 5333
					&& c.getItems().playerHasItem(13078, 1)) {
				c.sendMessage("You've found another clue!");
				c.getItems().deleteItem(13078, 1);
				c.getItems().addItem(13079, 1);
				TreasureTrails.handleDig(c);

			} else if (c.absX == 3053 && c.absY == 3499
					&& c.getItems().playerHasItem(13040, 1)
					&& c.heightLevel == 1) {
				c.sendMessage("You've found another clue!");
				c.getItems().deleteItem(13040, 1);
				c.getItems().addItem(13041, 1);
				TreasureTrails.handleDig(c);

			} else if (c.absX == 2616 && c.absY == 3077
					&& c.getItems().playerHasItem(13041, 1)) {
				c.sendMessage("You've found another clue!");
				c.getItems().deleteItem(13041, 1);
				c.getItems().addItem(13042, 1);
				TreasureTrails.handleDig(c);
			} else if (c.absX == 3109 && c.absY == 3153
					&& c.getItems().playerHasItem(13042, 1)) {
				c.sendMessage("You've found a casket!");
				c.getItems().deleteItem(13042, 1);
				c.getItems().addItem(405, 1);
				TreasureTrails.handleDig(c);
			} else if (c.absX == 3109 && c.absY == 3152
					&& c.getItems().playerHasItem(13042, 1)) {
				c.sendMessage("You've found a casket!");
				c.getItems().deleteItem(13042, 1);
				c.getItems().addItem(405, 1);
				TreasureTrails.handleDig(c);
			} else if (c.absX == 3109 && c.absY == 3151
					&& c.getItems().playerHasItem(13042, 1)) {
				c.sendMessage("You've found a casket!");
				c.getItems().deleteItem(13042, 1);
				c.getItems().addItem(405, 1);
				TreasureTrails.handleDig(c);
			}
		}

	}

	public int randomflower() {
		return flower[(int) (Math.random() * flower.length)];
	}

	public boolean torva(Client c) {
		if (c.playerEquipment[Player.playerHat] == 13362
				&& c.playerEquipment[Player.playerChest] == 13358
				&& c.playerEquipment[Player.playerLegs] == 13360) {
			return true;
		} else {
			return false;
		}
	}
}
