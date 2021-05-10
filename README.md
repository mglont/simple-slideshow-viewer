# Simplistic Slideshow Viewer

## TODO

## Future work 
* **implement slideshow pause and restart**
* Memory/Performance improvements: currently all folder images are stored in memory, but it would 
  make sense to switch to lazy loading and file system caching. 
* Use [WatchService](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/file/WatchService.html)
  to detect and react to file changes - new/modified/deleted photos
* Perform micro-benchmarking using [jmh](https://github.com/openjdk/jmh/) 
* Resize images when the window is modified
* Accessibility
* consistent look and feel across OSs

## Prerequisites

* Java 11+ (tested with AdoptOpenJDK 11.0.8)

## License
To the extent possible under law, Mihai Glont has waived all copyright and related or neighboring
rights to Simplistic Slideshow Viewer. This work is published from: United Kingdom.

See [CC0](https://creativecommons.org/publicdomain/zero/1.0/) for more details.

## Build

### Unix

    ./gradlew clean check

### Windows

    gradlew clean check

## Run

The recommended way of running the application is through the Gradle wrapper, which pulls all
project dependencies (including JavaFX).

### Unix

  ./gradlew run

### Windows

  gradlew run

### Profiling
The software automatically records [JFR](https://docs.oracle.com/en/java/java-components/jdk-mission-control/index.html)
events into `profileInfo.jfr`, which can be loaded into [JMC](https://oracle.com/missioncontrol). See
[JFR limitations](http://hirt.se/blog/?p=364).

## Photo Credits
I am grateful to the following artists, who have made their work freely-available through
[Unsplash](https://unsplash.com/):

* [Denise Jans](https://unsplash.com/@dmjdenise)
* [Garrett Patz](https://unsplash.com/@garrettpatz)
* [Jeremy Thomas](https://unsplash.com/@jeremythomasphoto)

Unsplash has helped me find with ease much-needed large images that I've used to develop and test
this software.

## Frequently asked questions
### Can I use an existing JavaFX SDK to run the software?

The project can run using a different version of the JavaFX SDK

    gradle shadowJar
    export PATH_TO_FX=~/Downloads/javafx-sdk-11.0.2/lib
    java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -jar build/libs/simple-slideshow-viewer-0.0.1-SNAPSHOT-all.jar

**Note for Mac OS Catalina/Big Sur users:** Recent versions of MacOS complain about JavaFX binaries
not being signed and prevent you from running the application. Java will also throw errors like
the one below as a result of being unable to load native JavaFX libraries. Follow 
[these instructions](https://www.macworld.co.uk/how-to/mac-app-unidentified-developer-3669596/) 
to solve the issue.

```
Graphics Device initialization failed for :  es2, sw
Error initializing QuantumRenderer: no suitable pipeline found
java.lang.RuntimeException: java.lang.RuntimeException: Error initializing QuantumRenderer: no suitable pipeline found
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumRenderer.getInstance(QuantumRenderer.java:280)
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumToolkit.init(QuantumToolkit.java:222)
	at javafx.graphics/com.sun.javafx.tk.Toolkit.getToolkit(Toolkit.java:260)
	at javafx.graphics/com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:267)
	at javafx.graphics/com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:158)
	at javafx.graphics/com.sun.javafx.application.LauncherImpl.startToolkit(LauncherImpl.java:658)
	at javafx.graphics/com.sun.javafx.application.LauncherImpl.launchApplicationWithArgs(LauncherImpl.java:409)
	at javafx.graphics/com.sun.javafx.application.LauncherImpl.launchApplication(LauncherImpl.java:363)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at java.base/sun.launcher.LauncherHelper$FXHelper.main(LauncherHelper.java:1051)
Caused by: java.lang.RuntimeException: Error initializing QuantumRenderer: no suitable pipeline found
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumRenderer$PipelineRunnable.init(QuantumRenderer.java:94)
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumRenderer$PipelineRunnable.run(QuantumRenderer.java:124)
	at java.base/java.lang.Thread.run(Thread.java:834)
Exception in thread "main" java.lang.reflect.InvocationTargetException
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at java.base/sun.launcher.LauncherHelper$FXHelper.main(LauncherHelper.java:1051)
Caused by: java.lang.RuntimeException: No toolkit found
	at javafx.graphics/com.sun.javafx.tk.Toolkit.getToolkit(Toolkit.java:272)
	at javafx.graphics/com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:267)
	at javafx.graphics/com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:158)
	at javafx.graphics/com.sun.javafx.application.LauncherImpl.startToolkit(LauncherImpl.java:658)
	at javafx.graphics/com.sun.javafx.application.LauncherImpl.launchApplicationWithArgs(LauncherImpl.java:409)
	at javafx.graphics/com.sun.javafx.application.LauncherImpl.launchApplication(LauncherImpl.java:363)
	... 5 more
```
