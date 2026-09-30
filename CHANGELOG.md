Add compatibility for minecraft version 1.21.4, 1.21.5, 1.21.6, 1.21.7, 1.21.8, 1.21.9, 1.21.10

- Requires RecipesLibrary 4.0.1 or newer.
- The needle is now rendered by the vanilla compass item model. The compass stores its target additionally as vanilla `minecraft:lodestone_tracker` (not tracked, no lodestone needed).
- Compasses from worlds of older Minecraft versions get their target back when the player logs in with them in the inventory.
- `/giveDC` plays the pickup sound only for the receiving player.
