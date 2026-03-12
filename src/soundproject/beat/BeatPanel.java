package soundproject.beat;

import soundproject.AudioData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BeatPanel extends JPanel {

    private AudioData audio;
    private BeatDetector detector;
    private List<Double> beats;

    // Calm medical-style colors
    private final Color bgColor   = new Color(12, 18, 28);
    private final Color waveColor = new Color(80, 200, 180);
    private final Color beatColor = new Color(230, 180, 80);
    private final Color textColor = new Color(180, 190, 200);

    public BeatPanel(AudioData audio) {
        this.audio = audio;

        // Initialize beat detector
        this.detector = new BeatDetector(audio.samples, audio.sampleRate);
        this.beats = detector.detectBeats(1024, 512, 1.5);

        setBackground(bgColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int midY = h / 2;

        // ===== Background =====
        g2.setColor(bgColor);
        g2.fillRect(0, 0, w, h);

        // ===== Draw waveform =====
        double[] samples = audio.samples;
        int total = samples.length;
        if (total <= 1) return;

        g2.setColor(waveColor);

        int step = Math.max(1, total / w);

        int prevX = 0;
        int prevY = midY;

        for (int i = 0; i < total; i += step) {
            int x = (int) ((i / (double) total) * w);
            int y = (int) (midY - samples[i] * (h * 0.35));

            g2.drawLine(prevX, prevY, x, y);

            prevX = x;
            prevY = y;
        }

        // ===== Draw beats as glowing circles =====
        if (beats != null && !beats.isEmpty()) {

            double duration = samples.length / (double) audio.sampleRate;

            for (double t : beats) {
                int x = (int) ((t / duration) * w);
                int r = 8;

                g2.setColor(new Color(
                        beatColor.getRed(),
                        beatColor.getGreen(),
                        beatColor.getBlue(),
                        180));

                g2.fillOval(x - r / 2, midY - r / 2, r, r);
            }
        }

        // ===== Text information =====
        g2.setColor(textColor);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        int beatCount = (beats == null ? 0 : beats.size());
        double bpm = calculateBPM();

        g2.drawString("Beats detected: " + beatCount, 15, 20);
        g2.drawString("Estimated BPM: " + String.format("%.1f", bpm), 15, 40);

        String rhythm;
        if (bpm < 60)
            rhythm = "Slow Rhythm";
        else if (bpm < 120)
            rhythm = "Normal Rhythm";
        else
            rhythm = "Fast Rhythm";

        g2.drawString("Rhythm: " + rhythm, 15, 60);

        // ===== Panel title =====
        g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        g2.drawString("Beat Detection & Rhythm Analysis",
                      w / 2 - 160, 22);
    }

    // ===== Estimate BPM from detected beats =====
    private double calculateBPM() {

        if (beats == null || beats.size() < 2)
            return 0.0;

        double totalTime = beats.get(beats.size() - 1) - beats.get(0);
        if (totalTime == 0)
            return 0.0;

        double avgInterval = totalTime / beats.size();
        return 60.0 / avgInterval;
    }
}
