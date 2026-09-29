# Headless tooltip regression (26.3 Fabric)

Requires Python 3 and the same JDK 25+ and Gradle dependencies as the mod build. From the repository root:

```sh
bash gradlew --no-configuration-cache -I qa/tooltip-regression/classpath.init.gradle.kts :26.3-fabric:writeTooltipRegressionClasspath
python3 qa/tooltip-regression/run.py /tmp/tiered-backpacks-regression.classpath
```

Set `JAVA_HOME` to your JDK. Gradle prints the classpath file location; substitute that path on systems whose temporary directory is not `/tmp`. Optionally pass a pre-fix Git revision as a second argument to `run.py` to verify that the original tag-only registration fails.

The harness uses real Minecraft, Shulker Box Tooltip, and built mod classes. A test-only `TieredBackpacks` shim uses the real config defaults while avoiding Fabric config-file registration. An eager capture registry implements the registration contract without starting a game client. Vanilla component initializers run before registration, with item tags deliberately absent; tags are applied afterward for preview checks.

Checks cover all six backpacks with chest equipment disabled, vanilla and modded chest armor, unrelated items, empty/full contents, unattached armor, dimensions, and stored dye. This tests the absent-tag case; it does not reproduce the reporter’s full game environment or verify rendered tooltips. See the adjacent manual checklist for in-game validation.
