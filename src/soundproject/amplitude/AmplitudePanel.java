package soundproject.amplitude;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AmplitudePanel extends JPanel {

    private AmplitudeAnalyzer analyzer;

    private double zoom = 1.0;
    private double offsetSamples = 0.0;
    private int lastMouseX = 0;

    public AmplitudePanel(AmplitudeAnalyzer analyzer) {
        this.analyzer = analyzer;

        setPreferredSize(new Dimension(900, 350));
        setBackground(Color.BLACK);

        // ===== Zoom with Mouse Wheel =====
        addMouseWheelListener(e -> {
            if (e.getPreciseWheelRotation() < 0)
                zoom *= 1.2;
            else
                zoom /= 1.2;

            zoom = Math.max(0.2, Math.min(zoom, 20.0));
            repaint();
        });

        // ===== Start Drag =====
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMouseX = e.getX();
            }
        });

        // ===== Drag to Pan =====
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                int dx = e.getX() - lastMouseX;
                lastMouseX = e.getX();

                double[] samples = analyzer.getAmplitudeData();
                if (samples == null || samples.length == 0) return;

                double xScale = (getWidth() - 60) / (double) samples.length * zoom;
                if (xScale <= 0) return;

                offsetSamples -= dx / xScale;

                if (offsetSamples < 0) offsetSamples = 0;
                if (offsetSamples > samples.length - 1)
                    offsetSamples = samples.length - 1;

                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int midY = height / 2;

        double[] samples = analyzer.getAmplitudeData();
        if (samples == null || samples.length == 0) {
            g2.setColor(Color.WHITE);
            g2.drawString("No audio loaded...", 20, 20);
            return;
        }

        // ===== Grid =====
        g2.setColor(new Color(255, 255, 255, 30));
        for (int x = 50; x < width; x += 100)
            g2.drawLine(x, 0, x, height);

        for (int y = 0; y < height; y += 50)
            g2.drawLine(50, y, width - 10, y);

        // ===== Axes =====
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(50, 10, 50, height - 10);
        g2.drawLine(50, midY, width - 10, midY);

        // ===== Y Labels =====
        double[] yValues = { 1.0, 0.5, 0.0, -0.5, -1.0 };
        g2.setFont(new Font("Arial", Font.PLAIN, 12));

        for (double val : yValues) {
            int y = midY - (int) (val * (height / 2 - 20));

            g2.setColor(new Color(255, 255, 255, 60));
            g2.drawLine(50, y, width - 10, y);

            g2.setColor(Color.WHITE);
            g2.drawString(String.valueOf(val), 10, y + 5);
        }

        // ===== X Labels =====
        int sampleRate = analyzer.getSampleRate();
        int totalSamples = samples.length;
        double duration = totalSamples / (double) sampleRate;

        double step;
        if (duration < 1)           step = 0.1;
        else if (duration < 5)      step = 0.5;
        else if (duration < 30)     step = 1.0;
        else if (duration < 120)    step = 5.0;
        else                        step = 10.0;

        double xScale = (width - 60) / (double) totalSamples * zoom;

        g2.setColor(Color.WHITE);
        for (double t = 0; t <= duration; t += step) {
            int sampleIndex = (int) (t * sampleRate);
            int x = 50 + (int) ((sampleIndex - offsetSamples) * xScale);

            if (x >= 50 && x <= width - 10) {
                g2.drawString(String.format("%.2f s", t), x - 12, midY + 25);
                g2.drawLine(x, midY - 5, x, midY + 5);
            }
        }

        g2.drawString("Amplitude", 5, 20);
        g2.drawString("Time →", width - 70, midY - 5);

        // ===== Title =====
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Waveform (Time Domain)", width / 2 - 120, 25);
        g2.setFont(new Font("Arial", Font.PLAIN, 12));

        // ===== Draw Waveform =====
        g2.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(1.5f));

        if (xScale <= 0) return;

        int startIndex = (int) Math.max(0, offsetSamples);
        int endIndex = (int) Math.min(totalSamples - 1,
                                      offsetSamples + (width - 60) / xScale);

        int prevX = 50;
        int prevY = midY;

        for (int i = startIndex + 1; i <= endIndex; i++) {
            int x = 50 + (int) ((i - offsetSamples) * xScale);
            int y = midY - (int) (samples[i] * (height / 2 - 20));

            g2.drawLine(prevX, prevY, x, y);

            prevX = x;
            prevY = y;
        }
    }
}

