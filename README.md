Headless Tests with the Headless platform
===

This sample is forked from: https://github.com/digidol/Example-TestFX

See https://github.com/digidol/Example-TestFX/blob/master/README.md for detailed explanation of the test itself.

Build
---

<h3>Headless SDK</h3>

The headless platform is under public development in the OpenJFX sandbox repository: https://github.com/openjdk/jfx-sandbox/tree/johanvos-headless

A most advanced version can be found here: https://github.com/jperedadnr/jfx-sandbox/tree/1-headless

You can fork it and build it yourself, or just take the SDK from these links:

- Windows x86_64: https://download2.gluonhq.com/openjfx/forks/jperedadnr/jfx-sandbox/openjfx-23+398_null_windows-x86_64_bin-sdk.zip
- Linux x86_64: https://download2.gluonhq.com/openjfx/forks/jperedadnr/jfx-sandbox/openjfx-23+395_null_linux-x86_64_bin-sdk.zip
- macOS x86_64: https://download2.gluonhq.com/openjfx/forks/jperedadnr/jfx-sandbox/openjfx-23+396_null_mac-x86_64_bin-sdk.zip
- macOS aarch64: https://download2.gluonhq.com/openjfx/forks/jperedadnr/jfx-sandbox/openjfx-23+397_null_mac-aarch64_bin-sdk.zip

Download the SDK that best suits you, and extract it to `${user.home}/Downloads`.

<h3>TestFX</h3>

TestFX https://github.com/TestFX/TestFX has been forked to run with JDK 17+ and JavaFX 17+, and to use the headless platform.

You can see the changes here: https://github.com/jperedadnr/TestFX/tree/headless

You can fork it and build it yourself: 
```
 export _JAVA_OPTIONS=-Dtestfx.robot=glass -Dtestfx.headless=true
 .gradlew build publishToMavenLocal
 ``` 

Note that it will also run and pass headlessly all the unit tests (500+).

Or you can grab the artifacts with version 4.0.18-gluon-SNAPSHOT from https://nexus.gluonhq.com/nexus/content/repositories/public-snapshots

Run
---

Clone and run this sample, with JDK 17+ and JavaFX headless SDK:

```
git clone https://github.com/jperedadnr/Example-TestFX.git
git checkout headless
cd Example-TestFX
mvn -Psdk test
```
and now headlessly:

````
export _JAVA_OPTIONS=-Dtestfx.robot=glass -Dtestfx.headless=true
mvn -Psdk test
```

