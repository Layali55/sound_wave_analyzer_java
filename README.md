\# Sound Wave Analyzer (Java)



\## Overview

A Java coursework project that analyzes audio signals through a simple GUI with multiple analysis modules (amplitude, FFT, filtering, power, and beat detection). The project is organized into modular packages and can be run using NetBeans (Ant).



\## Features

\- \*\*Amplitude\*\*: inspect/visualize amplitude over time

\- \*\*Beat Detection\*\*: detect beats/peaks and report beat-related metrics

\- \*\*FFT / Spectrum\*\*: frequency-domain analysis using FFT

\- \*\*Filtering\*\*: apply basic filtering to the signal

\- \*\*Power\*\*: compute simple power/energy-related measures



\## Project Structure

\- `src/` — source code organized by modules:

&nbsp; - `amplitude/`, `beat/`, `fft/`, `filter/`, `power/`, `main/`

\- `AudioLoader.java`, `AudioData.java` — shared utilities for loading and representing audio data

\- `figures/` — screenshots / example outputs

\- `build.xml`, `manifest.mf`, `nbproject/` — NetBeans/Ant project files



\## How to Run (NetBeans)

1\. Open NetBeans.

2\. \*\*File → Open Project\*\* and select this folder.

3\. Click \*\*Run\*\*.

4\. Load a local audio file using \*\*File → Open\*\* (WAV recommended).



\## Output Example

See `figures/beat\_detection\_output.png` for an example output.



\## Sample Audio

Sample audio files are not included in this repository.

To test the application, load any local `.wav` file via \*\*File → Open\*\*.



\## My Role / Contribution

\- Implemented the \*\*beat detection\*\* module (core logic + validation on test audio).

\- Worked on \*\*integration and main flow\*\*, connecting modules and ensuring the application runs end-to-end.



\## What I Learned

\- Structuring a modular Java application (analysis modules + shared utilities)

\- Basic signal-processing workflows (beat detection, FFT, filtering)

\- Integrating features into a runnable GUI project and documenting usage clearly

