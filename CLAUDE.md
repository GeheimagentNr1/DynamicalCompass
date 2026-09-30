# CLAUDE.md - Dynamical Compass

## Projekt-Übersicht

**Dynamical Compass** ist ein NeoForge Minecraft Mod. Fügt einen Kompass hinzu, der auf eine benutzerdefinierte Position zeigt (Shift-Rechtsklick auf einen Block, `/giveDC`; per Rezept mit Glasscheibe sperrbar).
- **Mod ID**: `dynamical_compass`
- **Package**: `de.geheimagentnr1.dynamical_compass`
- **Java Version**: 21

| Branch | MC | Range | NeoForge (kompiliert gegen) | Nadel-Rendering |
|---|---|---|---|---|
| `develop_1.21.1` | 1.21.1 | `[1.21.1,1.21.10]` (Release 1.21.1-4.0.1, lädt praktisch nur auf 1.21.1) | 21.1.x | `ItemProperties` + eigene `DynamicalCompassPropertyFunction` |
| `develop_1.21.2` | 1.21.2 - 1.21.3 | `[1.21.2,1.21.4)` | `21.2.1-beta` | wie 1.21.1, zusätzlich wird `minecraft:lodestone_tracker` gesetzt |
| `develop_1.21.4` | 1.21.4 - 1.21.10 | `[1.21.4,1.21.11)` | `21.4.158` | kein Client-Code: `assets/dynamical_compass/items/dynamical_compass.json` mit Vanilla `minecraft:compass`, Ziel `lodestone` |

**Ziel-Speicherung:** Eigene Komponenten `destination_dimension`, `destination_pos`, `locked` (Rezepte und Logik), ab `develop_1.21.2` zusätzlich gespiegelt in `minecraft:lodestone_tracker` mit `tracked=false` (`DynamicalCompassItemStackHelper.updateLodestoneTracker`). `tracked=false` = kein Lodestone an der Position nötig; `LodestoneTracker.tick` läuft ohnehin nur für `CompassItem`. Ab `develop_1.21.4` liest das Vanilla-Item-Model daraus die Nadelrichtung; Kompasse aus älteren Welten bekommen den Tracker beim Login (`DynamicalCompassEventHandler`, nur Spielerinventar).

**Cross-Version-Fallen (1.21.4 - 1.21.10):** `Item.appendHoverText` ändert sich in 1.21.5 → Tooltip über `ItemTooltipEvent`; `Level.playSound(Player, ...)` wird in 1.21.5 zu `playSound(Entity, ...)` (kompiliert, aber `NoSuchMethodError` zur Laufzeit) → `ServerPlayer.playNotifySound`. Siehe `../Docs/migrations/1.21.1-to-1.21.2.md`.

## Abhängigkeiten

- **Recipes Library** (`recipes_lib`) - Required, ab `develop_1.21.2` `[4.0.1,)`. Zur Laufzeit in der Dev-Umgebung: `RecipesLibrary-${rl_minecraft_version}:${rl_version}` (`custom.gradle`), der RecipesLibrary-1.21.2-Jar deckt 1.21.2 - 1.21.10 ab.

## Projektstruktur

```
src/main/java/de/geheimagentnr1/dynamical_compass/
├── DynamicalCompassMod.java                  # Haupt-Mod-Klasse
└── elements/
    ├── commands/                             # /giveDC
    ├── creative_mod_tabs/
    └── items/
        ├── ModItemsRegisterFactory.java      # Item (DeferredRegister.Items#registerItem) + Data Components
        └── dynamical_compass/                # Item, ItemStackHelper, (bis 1.21.3) PropertyFunction/Wobble, (ab 1.21.4) EventHandler
src/main/resources/data/dynamical_compass/recipe/item/   # Rezepte (recipes_lib-Typen, Format ab NeoForge 21.2)
```

## Code-Stil

- **Annotations**: `@NotNull` aus `org.jetbrains.annotations`
- **Lombok**: Projekt nutzt Lombok
- **Formatierung**: Leerzeichen nach `(` und vor `)` bei Methodenaufrufen

## Build & Test

```bash
./gradlew build
./gradlew runClient
./gradlew runServer
```

## Deployment

- **CurseForge**: `./gradlew curseforge`
- **Modrinth**: `./gradlew modrinth`

## Testing

### Java-Versionen

Verschiedene Java-Versionen sind unter `C:\Program Files\Eclipse Adoptium` installiert. Für einen Gradle-Build muss die passende Java-Version gewählt werden:

```powershell
# Java 21 für MC 1.20.5+ (NeoForge)
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.12.8-hotspot"
./gradlew build

# Kompatibilität gegen weitere Versionen im Bereich prüfen (baut kein zusätzliches Jar)
./gradlew compileJava compileTestJava --rerun-tasks -Pminecraft_version=1.21.10 -Pneo_forge_version=21.10.64 -Pmapping_version=1.21.10
```

Ein grüner Compile reicht nicht: zusätzlich die Bytecode-Referenzen gegen jede Zielversion vergleichen (siehe `../Docs/migrations/1.21.1-to-1.21.2.md`, Abschnitt Binärkompatibilität), sonst bleiben Fehler wie der `playSound`-`NoSuchMethodError` unentdeckt.

### Ingame-Test

Craften (4 Gold + Lapis), Ziel per Shift-Rechtsklick setzen (im Creative entsteht eine Kopie, wie beim Vanilla-Lodestone-Kompass), sperren (+ Glasscheibe, Gegenprobe ohne Ergebnis), Umbenennen (Namensschild), Schmelzen (→ Gold), Item Frame/fallengelassen, Nether (Nadel dreht sich), `/giveDC`. Ab 1.21.4 zusätzlich Welt-Upgrade: Welt aus 1.21.1 (Kompasse ohne Tracker) und aus 1.21.3 kopieren und prüfen, dass die Nadeln nach dem Login aufs Ziel zeigen.

### Unit Tests (JUnit 5)

Für reine Logik-Tests ohne Minecraft-Abhängigkeiten:

```bash
./gradlew test
```

Tests liegen unter `src/test/java/`. Ergebnisse: `build/reports/tests/test/index.html`

### NeoForge GameTest Framework

Ab `develop_1.21.2` gibt es keine GameTests: Das Annotations-Framework (`@GameTest`, `@GameTestHolder`) existiert ab 1.21.5 nicht mehr, der triviale Smoke-Test wurde samt `gameTestServer`-Run-Config und CI-Job entfernt (siehe `../Docs/migrations/1.21.10-to-1.21.11.md`). Die JUnit-Abhängigkeit ist seit `develop_1.21.2` in `build.gradle` eingetragen.

### CI/CD (GitHub Actions)

Der Workflow `.github/workflows/build-and-test.yml` führt automatisch aus:
1. **Build**: Kompiliert den Mod
2. **Unit Tests**: Führt JUnit Tests aus

### Was kann automatisiert getestet werden?

| Aspekt | Automatisiert? | Methode |
|--------|----------------|---------|
| Utility-Klassen | ✅ | JUnit |
| Config-Parsing | ✅ | JUnit |
| Commands | ✅ | GameTest |
| Block/Item-Verhalten | ✅ | GameTest |
| Multi-MC-Version | ⚠️ Pro Branch | CI Matrix |

## Referenzen

- [NeoForge Migration Primer](https://docs.neoforged.net/primer/docs/) — Dokumentiert API-Aenderungen zwischen Minecraft/NeoForge-Versionen; nuetzlich fuer die Pruefung von Breaking Changes beim Upgrade auf neue Versionen

---

## Wissensdatenbank

Versionsübergreifende Migrations- und Entwicklungs-Erkenntnisse (Breaking Changes, Fixes, Testumgebungs-Patterns) werden zentral in [`../Docs/`](../Docs/) gepflegt. Bei neuen relevanten Erkenntnissen dort ergänzen, nicht nur hier.
