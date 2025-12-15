# AGENTS.md - Dynamical Compass

## Projekt-Übersicht

**Dynamical Compass** ist ein NeoForge Minecraft Mod für Minecraft 1.21.1.
- **Mod ID**: `dynamical_compass`
- **Package**: `de.geheimagentnr1.dynamical_compass`
- **Java Version**: 21
- **NeoForge Version**: 21.1.x

Fügt einen Kompass hinzu, der auf eine benutzerdefinierte Position zeigt.

## Abhängigkeiten

- **Recipes Library** (`recipes_lib`) - Required

## Projektstruktur

```
src/main/java/de/geheimagentnr1/dynamical_compass/
└── DynamicalCompassMod.java    # Haupt-Mod-Klasse
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
