#!/usr/bin/env python3
"""Run with Python 3 after generating the classpath using classpath.init.gradle.kts.
Usage: run.py /tmp/tiered-backpacks-regression.classpath [baseline-git-ref]
JAVA_HOME must point to a JDK 25+ installation.
"""
from pathlib import Path
import os
import subprocess
import sys
import tempfile

here = Path(__file__).resolve().parent
classpath = Path(sys.argv[1]).read_text().strip()
bin_dir = Path(os.environ['JAVA_HOME']) / 'bin'
with tempfile.TemporaryDirectory(prefix='tooltip-regression-') as temporary:
    classes = Path(temporary) / 'classes'
    classes.mkdir()
    subprocess.run([str(bin_dir / 'javac'), '-proc:none', '-cp', classpath, '-d', str(classes),
                    str(here / 'me/pajic/tiered_backpacks/TieredBackpacks.java'),
                    str(here / 'TooltipRegression.java')], check=True)
    run_cp = str(classes) + os.pathsep + classpath
    subprocess.run([str(bin_dir / 'java'), '-cp', run_cp, 'TooltipRegression'], check=True, cwd=temporary)
    if len(sys.argv) > 2:
        original = subprocess.check_output(['git', 'show', sys.argv[2] + ':src/main/java/me/pajic/tiered_backpacks/tooltip/BackpackTooltipCompat.java'])
        source = Path(temporary) / 'BackpackTooltipCompat.java'
        source.write_bytes(original)
        baseline = Path(temporary) / 'baseline'
        baseline.mkdir()
        subprocess.run([str(bin_dir / 'javac'), '-proc:none', '-cp', run_cp, '-d', str(baseline), str(source)], check=True)
        result = subprocess.run([str(bin_dir / 'java'), '-cp', str(baseline) + os.pathsep + run_cp, 'TooltipRegression'], capture_output=True, text=True, cwd=temporary)
        expected = 'AssertionError: backpack registered before tags: tiered_backpacks:leather_backpack'
        if result.returncode == 0 or expected not in result.stderr:
            sys.exit('Unexpected baseline result:\n' + result.stdout + result.stderr)
        print('PASS: original registration fails the absent-tags regression as expected')
