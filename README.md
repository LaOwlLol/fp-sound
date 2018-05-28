# fp-sound - A simple general purpose sound library for Java.

No need to construct an object.  Play audio files with global accessible FpSoundEngine.PlaySound(File) method.
Note you must provide your own test audio file.  Java Sampled API supports formats listed below.


```java
FpSoundEngine.PlaySound(Paths.get(System.getProperty("user.home"),"/fpAudio/test.wav").toFile());
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
