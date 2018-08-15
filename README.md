# fp-sound - A simple general purpose sound library for Java.

This is a 'bare bones' audio library for basic audio play back.  This version of the API a prioritizes minimal code to play audio for those who just need to play audio.
Supported audio formats listed below.

## How to:

One line play audio:

```java
FpSoundEngine.PlaySound(Paths.get(System.getProperty("user.home"),"/fpAudio/test.wav").toFile());
```

Get an directory of audio to retrieve audio by name:

```java
AudioDirectory audioDir = new AudioDirectory(Paths.get(System.getProperty("user.home"), "/fpAudio"));
String key = "test.wav";
Optional<File> sound = audioDir.get(key);
```

Example of button (JavaFX) with sound.

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

A few basic diagnostic method can also be used to check the systems available audio devices (Mixers in Java).

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
