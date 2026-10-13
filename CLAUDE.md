# CLAUDE.md - Dynamical Compass

Fügt einen Kompass hinzu, der auf eine frei wählbare Position zeigt (Ziel per Shift-Rechtsklick auf einen Block oder per `/giveDC`, per Rezept sperrbar). Mod-ID `dynamical_compass`, Package `de.geheimagentnr1.dynamical_compass`. Client+Server (`displayTest=MATCH_VERSION`, keine Dist-Beschränkung); Item, Rezepte und Befehl leben auf dem Server, der Client braucht den Mod für Item-Modell und Nadel.

Diese Datei ist auf allen `develop_*`-Branches identisch; Quelle: `../Docs/mod-claude/DynamicalCompass.md` (Änderungen dort, dann `Docs/tools/sync_mod_claude_md.sh`). Versionsstand: [`../Docs/VERSIONEN.md`](../Docs/VERSIONEN.md).

## Versionen

- Forge: `develop_1.14.4` bis `develop_1.21`, dazu `develop_1.21.1_forge` (letzter Forge-Stand 1.21.1).
- NeoForge: `develop_1.21.1` (lädt wegen RecipesLibrary praktisch nur auf 1.21.1), `develop_1.21.2` (1.21.2 - 1.21.3), `develop_1.21.4` (1.21.4 - 1.21.10), `develop_1.21.11`, `develop_26.1` (26.1 - 26.2), `develop_26.3`.
- Jar-Grenzen: 1.21.4 (Item-Model-Umbau), 1.21.11 (`Identifier`, Rechtesystem, `playNotifySound` entfernt), 26.3 (`Player.drop(.., Prediction)`). Ranges und Tags: [`../Docs/VERSIONEN.md`](../Docs/VERSIONEN.md).

## Abhängigkeiten

- **RecipesLibrary** (`recipes_lib`, required, beide Seiten): eigene Rezepttypen für Sperren/Umbenennen. In der Dev-Umgebung über `custom.gradle` als `RecipesLibrary-${rl_minecraft_version}:${rl_version}` (ein RecipesLibrary-Jar deckt oft mehrere MC-Versionen ab, daher eigene Property `rl_minecraft_version`).
- Forge ab 1.20: zusätzlich MinecraftForgeAPI per `jarJar` (siehe [`../Docs/forge/README.md`](../Docs/forge/README.md)).
- Keine gebündelten Fremdbibliotheken.

## Architektur

- `DynamicalCompassMod` - Hauptklasse; registriert Befehle, Items, Creative-Tab (NeoForge: direkt im Konstruktor; Forge ab 1.20: `extends AbstractMod` aus MinecraftForgeAPI).
- `elements/commands/` - `GiveDCCommand` (`/giveDC`), `ModCommandsRegistryFactory`.
- `elements/items/ModItemsRegisterFactory` - Item und (NeoForge) Data Components `destination_dimension`, `destination_pos`, `locked`.
- `elements/items/dynamical_compass/` - `DynamicalCompass` (Item, Shift-Rechtsklick setzt das Ziel), `DynamicalCompassItemStackHelper` (Lesen/Schreiben des Ziels).
  - Bis 1.21.3: `DynamicalCompassPropertyFunction` + `DynamicalCompassWobble` (eigene `ItemProperties`, 32 Nadel-Modelle `models/item/dynamical_compass_00..31`).
  - Ab 1.21.2: Ziel zusätzlich in `minecraft:lodestone_tracker` mit `tracked=false` gespiegelt (`updateLodestoneTracker`).
  - Ab 1.21.4: kein Client-Code mehr - `assets/dynamical_compass/items/dynamical_compass.json` nutzt das Vanilla-Kompass-Modell `minecraft:compass` mit Ziel `lodestone`; `DynamicalCompassEventHandler` setzt den Tracker beim Login für Kompasse aus älteren Welten (nur Spielerinventar).
- Branches bis 1.20.4: Ziel als NBT-Compound `destination` am ItemStack; ab 1.20.6 (Forge und NeoForge) Data Components.
- `data/dynamical_compass/recipe/item/` - Herstellen, Sperren, Umbenennen, Schmelzen zu Gold.

## Funktionen, Befehle, Konfiguration

- Herstellen: 4 Gold + Lapis. Ziel setzen: Shift-Rechtsklick auf einen Block (im Creative entsteht eine Kopie, wie beim Vanilla-Lodestone-Kompass). Sperren: Kompass + Glasscheibe (gesperrte Kompasse ändern ihr Ziel nicht). Umbenennen mit Namensschild, Schmelzen ergibt Gold.
- `/giveDC <targets> <x z> <dimension> <locked>` - Rechtestufe 2 (ab 1.21.11 `Commands.LEVEL_GAMEMASTERS`).
- Keine Config-Datei, keine Welt-Daten außer den Item-Komponenten/NBT.

## Fallstricke

- 1.21.1 → 1.21.2: `Item.Properties` braucht eine ID (sonst Absturz beim Registrieren trotz grünem Compile) - Registrierung über `DeferredRegister.Items#registerItem`; Rezept-JSON-Format geändert (`neoforge:ingredient_type`, Ingredients als String).
- 1.21.4 → 1.21.5 (im Jar 1.21.4 - 1.21.10): `Item.appendHoverText` geändert → Tooltip über `ItemTooltipEvent`; `Level.playSound(Player, ..)` wird `playSound(Entity, ..)` - kompiliert, aber `NoSuchMethodError` zur Laufzeit → `ServerPlayer.playNotifySound` (ab 1.21.11 entfernt, dort wieder `Level.playSound(null, ..)`). Jeden Port per Bytecode-Check prüfen ([`../Docs/migrations/1.21.1-to-1.21.2.md`](../Docs/migrations/1.21.1-to-1.21.2.md) 4a/4b).
- 26.3: `Player.drop(stack, false, Prediction)` - `PREDICTED` bei der Creative-Kopie, `SERVER_ONLY` in `/giveDC`. Bewusst eigenes Jar statt Cross-fix `spawnAtLocation`, damit das Drop-Verhalten gleich bleibt.
- Nadel ab 1.21.4 hängt am `lodestone_tracker`: Kompasse aus Welten vor 1.21.2 haben ihn nicht; nachgetragen wird er nur beim Login für das Spielerinventar (Kompasse in Truhen/Item Frames erst, wenn sie bei einem Login im Inventar sind oder neu gesetzt werden).
- Das 1.21.1-Release lädt nur auf 1.21.1 (RecipesLibrary-Abhängigkeit), obwohl die Range weiter ist; Modrinth-Tags wurden reduziert.

## Testen

- Ingame: craften, Ziel setzen, sperren (Gegenprobe ohne Ergebnis), umbenennen, schmelzen, Item Frame/fallengelassen, Nether (Nadel dreht sich), `/giveDC`. Ab 1.21.4 zusätzlich Welt-Upgrade: Welt aus 1.21.1 und 1.21.3 kopieren, nach dem Login müssen die Nadeln aufs Ziel zeigen.
- Pro Jar niedrigste und höchste Version testen; Bytecode-Check mit [`../Docs/testing/tools/bincheck.ps1`](../Docs/testing/tools/bincheck.ps1).
- Server und Client brauchen dasselbe Jar plus RecipesLibrary. GameTests gibt es ab `develop_1.21.2` nicht mehr, nur JUnit.
- Allgemein: [`../Docs/testing/README.md`](../Docs/testing/README.md).

## Links

- Wiki: https://github.com/GeheimagentNr1/DynamicalCompass/wiki
- CurseForge: https://www.curseforge.com/minecraft/mc-mods/dynamical-compass (Projekt-ID 393636)
- Modrinth: https://modrinth.com/mod/iBSFWwzw
- Migration: [`../Docs/migrations/1.21.1-to-1.21.2.md`](../Docs/migrations/1.21.1-to-1.21.2.md) (4a), [`../Docs/migrations/1.21.11-to-26.1.md`](../Docs/migrations/1.21.11-to-26.1.md), [`../Docs/migrations/BREAKING_CHANGES.md`](../Docs/migrations/BREAKING_CHANGES.md)
