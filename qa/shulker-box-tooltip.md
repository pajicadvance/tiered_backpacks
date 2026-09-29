# Shulker Box Tooltip regression checks

Install Tiered Backpacks and its required dependencies alongside the matching
Shulker Box Tooltip release. Start a fresh client for the first check: provider
registration only happens once per process, before item tags may be available.

1. With Shulker Box Tooltip's generic container preview disabled, put items in
   each of the six backpack tiers and request a tooltip preview. Each preview
   should show its contents using the configured row and column count.
2. Disable `canEquipInChestSlot`, restart, and repeat. Backpacks must still have
   previews even without a default equippable component.
3. Attach a filled backpack to a vanilla chestplate and, when available, a
   modded chestplate. Confirm the preview uses the backpack's size and dye.
4. Hover empty backpacks and chestplates without an attached backpack. Neither
   should display a backpack preview. An ordinary chestplate with a container
   component but no backpack tier must not display one either.
5. Confirm shulker boxes and other mods' containers retain their own previews.
6. Disconnect and join another world or server; repeat the backpack and attached
   chestplate checks without restarting the client.
7. Launch without Shulker Box Tooltip and confirm backpacks still work normally.

These are manual checks, not an assertion that an in-game test has been run.
