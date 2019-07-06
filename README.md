# fp-sound version 0.1.1 - A 'bare bones' sound API for Java.

This is a 'bare bones' audio library for audio play back.  The API prioritizes minimal code to play audio for those who 'just need to play audio'.

This API provides three things:
 1. 32 track audio engine. Where each new play audio call starts on a new track. 
 2. AudioDirectory object that provides a key (string) value (File) interface to audio in a file system directory.
 3. Services for getting system sound Mixer and Line details.

## How to:

- Create a FpSoundEngine object:

```java
FpSoundEngine soundEngine = new FpSoundEngine();
```

- Play a sound:

```java
soundEngine.PlaySound(Paths.get(System.getProperty("user.home"),"/fpAudio/test.wav").toFile());
```

- Shutdown the sound engine (**important** otherwise application may not terminate as expected):

```java
soundEngine.shutDownEngine();
``` 

- Cache a directory of audio to retrieve audio by name:

```java
AudioDirectory audioDir = new AudioDirectory(Paths.get(System.getProperty("user.home"), "/fpAudio"));
String key = "test.wav";
Optional<File> sound = audioDir.get(key);
```

All supported files with a supported file extension will be cached as a File object, accessible with the get method by file name.

- Example of a button (JavaFX) with sound.

```java
AudioDirectory audioDir = new AudioDirectory(Paths.get(System.getProperty("user.home"), "/fpAudio"));
Button b = new Button("Test");
b.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
        String key = "test.wav";
        try {
            FpSoundEngine.PlaySound(audioDir.get(key).orElseThrow( () -> new NoSuchFileException("test.wav")));
        }
        catch (NoSuchFileException ex) {
            System.err.println("No such file test.wav");
        }
    }
});
```

- Diagnostic helpers can also be used to check the systems has audio devices (Mixers in Java) available.

```java
MixerService.MixerDetails(MixerService.SystemMixers()).forEach(System.out::println);
```

## Java Sampled API AudioFormat

Supported file formats are .wav, .au, and .aiff

Supported Audio Formats:
+ PMC , a-law, and mu-law encoded data in any of the supported audio file formats
+ Sample rate 8000, 11025, 16000, 22050, and 44100 samples per second.)
+ Sample size in bits 8 and 16 bits per sample.
+ Number of channels 1 channel for mono and 2 channels for stereo.
+ Signed or unsigned data.
+ Big-endian or little-endian order.

see: https://docs.oracle.com/javase/tutorial/sound/sampled-overview.html
