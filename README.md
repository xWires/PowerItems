# PowerItems

[![Build Status](https://ci.tangledwires.xyz/job/PowerItems/badge/icon)](https://ci.tangledwires.xyz/job/PowerItems/)

PowerItems is a Minecraft Spigot plugin that can be used to make items with custom amounts of damage and lore

Information about the created items is saved in the plugin's "config.yml" file.

---

Download builds from here: https://ci.tangledwires.xyz/job/PowerItems/ 

A build is created on each commit.

---

## Using as a dependency

To use PowerItems as a dependency, add the following to your plugin.yml file:

```yaml
depend: [PowerItems]
```
And add the following to your pom.xml file if you are using Maven:

```xml
<repositories>
    <repository>
        <id>xwires</id>
        <url>https://nexus.tangledwires.xyz/repository/maven-releases/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>xyz.tangledwires.poweritems</groupId>
        <artifactId>PowerItems</artifactId>
        <version>60</version>
    </dependency>
</dependencies>
```
<br>
<p align="center">
    <img src="https://bstats.org/signatures/bukkit/PowerItems.svg">
</p>