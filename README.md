Sound Wave Analyzer (Java)
==========================

Overview
--------
A modular Java coursework application for basic audio signal analysis through a simple GUI. It includes multiple modules such as amplitude, FFT, filtering, power, and beat detection.

Goal
----
- Provide a simple workflow to load an audio file and run different analysis modules.
- Demonstrate modular Java design for signal-processing tasks.

Modules
-------
- Amplitude
- Beat Detection
- FFT / Spectrum
- Filtering
- Power

Project Structure
----------------
- `src/` — source code organized by modules (`amplitude/`, `beat/`, `fft/`, `filter/`, `power/`, `main/`)
- `AudioLoader.java`, `AudioData.java` — shared utilities
- `figures/` — screenshots / example outputs
- `build.xml`, `manifest.mf`, `nbproject/` — NetBeans/Ant project files

How to Run (NetBeans)
---------------------
1. Open NetBeans → File → Open Project → Run
2. Load a local audio file via **File → Open** (WAV recommended)

Output Example
--------------
![Beat detection output](figures/beat_detection_output.png)

Sample Audio
------------
Sample audio files are not included in this repository.  
To test the app, load any local `.wav` file via **File → Open**.

My Contribution
---------------
- Implemented the beat detection module.
- Worked on integration and main flow to connect modules end-to-end.