# JiJer

A small tool to easily JiJ jars into a Fabric mod.

Took way longer to make than it would have taken to just add them manually, but it was totally worth it.

### Usage

Run the jar via your IDE, Gradle or with a built jar using `java -jar jijer-1.0.0.jar` and follow the instructions.

You will first be asked for the path to the mod you want to add jars to, and then you will be asked for what mods to add.

The mods to add can be either the path to a single jar, or a path to a directory.

You can input quoted strings.

### Setup

It's a regular Gradle project, just import it into your IDE like any other one.

### OSS

JiJer is open source software under the GPLv3 license.
It uses the [mjson](https://github.com/bolerio/mjson) library by bolerio to parse and add to the mod json, under Apache 2.0.