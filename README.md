## fp-sound - A simple general purpose sound library for Java.

### Java Sound API Notes:

##FILE IO

The AudioSystem class provides methods for reading and writing sounds in different file formats, and for converting between different data formats.

An AudioInputStream is a subclass of the InputStream class, which encapsulates a series of bytes that can be read sequentially. By reading a sound file as an AudioInputStream, you get immediate access to the samples, without having to worry about the sound file's structure (its header, chunks, etc.)

##Mixer:

In the Java Sound API, devices are represented by Mixer objects. The purpose of a mixer is to handle one or more streams of audio input and one or more streams of audio output. In the typical case, it actually mixes together multiple incoming streams into one outgoing stream.

In the case of audio output, the situation is reversed. The mixer's source for audio is one or more objects containing buffers into which one or more application programs write their sound data; and the mixer's target is one or more output ports.

##Line:

A line is a path for moving audio into or out of the system; Usually a path into or out of a mixer. They are analogous to the microphones and speakers connected to a physical mixing console.

In the Java Sound API, one difference between lines and those of a physical mixer is that the audio data flowing through a line in the Java Sound API can be multichannel. The number of channels in a line is specified by the AudioFormat of the data that is currently flowing through the line.

A clip is a mixer input (a kind of line) into which you can load audio data prior to playback.  An application program preloads audio data from a sound file into clips,
A source data line is a mixer input that accepts a real-time stream of audio data. An application program pushes audio data into SourceDataLines, a buffer at a time.

A mixer reads data from all it's lines, each of which may have its own reverberation, gain, and pan controls, and mixes the dry audio signals with the wet (reverberated) mix, then delivers its final output to one or more output ports.

Note that this is just one example of a possible mixer that is supported by the API. Not all audio configurations will have all the features illustrated. An individual source data line might not support panning, a mixer might not implement reverb, and so on.

-https://docs.oracle.com/javase/tutorial/sound/sampled-overview.html


