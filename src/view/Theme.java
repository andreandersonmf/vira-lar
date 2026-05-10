package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class Theme {
    public static boolean darkMode = false;

    public static Color BACKGROUND = new Color(245, 247, 250);
    public static Color CARD = Color.WHITE;
    public static Color PRIMARY = new Color(37, 99, 235);
    public static Color PRIMARY_DARK = new Color(29, 78, 216);
    public static Color TEXT = new Color(31, 41, 55);
    public static Color MUTED = new Color(107, 114, 128);
    public static Color BORDER = new Color(229, 231, 235);

    public static Font TITLE = new Font("Segoe UI", Font.BOLD, 28);
    public static Font SUBTITLE = new Font("Segoe UI", Font.BOLD, 18);
    public static Font NORMAL = new Font("Segoe UI", Font.PLAIN, 14);

    public static void applyLightTheme() {
        darkMode = false;
        BACKGROUND = new Color(245, 247, 250);
        CARD = Color.WHITE;
        PRIMARY = new Color(37, 99, 235);
        PRIMARY_DARK = new Color(29, 78, 216);
        TEXT = new Color(31, 41, 55);
        MUTED = new Color(107, 114, 128);
        BORDER = new Color(229, 231, 235);
    }

    public static void applyDarkTheme() {
        darkMode = true;
        BACKGROUND = new Color(15, 23, 42);
        CARD = new Color(30, 41, 59);
        PRIMARY = new Color(96, 165, 250);
        PRIMARY_DARK = new Color(37, 99, 235);
        TEXT = new Color(248, 250, 252);
        MUTED = new Color(203, 213, 225);
        BORDER = new Color(51, 65, 85);
    }

    public static void primaryButton(JButton btn) {
        btn.setUI(new BasicButtonUI());

        btn.setBackground(PRIMARY_DARK);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
    }

    public static void secondaryButton(JButton btn) {
        btn.setUI(new BasicButtonUI());

        if (darkMode) {
            btn.setBackground(new Color(51, 65, 85));
            btn.setForeground(new Color(248, 250, 252));
        } else {
            btn.setBackground(Color.WHITE);
            btn.setForeground(PRIMARY);
        }

        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
    }

    public static void styleCombo(JComboBox<?> combo) {
        Color bg = darkMode ? new Color(51, 65, 85) : Color.WHITE;
        Color fg = darkMode ? new Color(248, 250, 252) : PRIMARY;

        combo.setEditable(false);
        combo.setBackground(bg);
        combo.setForeground(fg);
        combo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        combo.setFocusable(false);
        combo.setOpaque(true);
        combo.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));

        combo.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {

                JButton button = new JButton() {

                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        Graphics2D g2 = (Graphics2D) g.create();

                        g2.setRenderingHint(
                                RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

                        g2.setColor(new Color(190, 190, 190));

                        int w = getWidth();
                        int h = getHeight();

                        int size = 6;

                        Polygon arrow = new Polygon();

                        arrow.addPoint(w / 2 - size, h / 2 - 2);
                        arrow.addPoint(w / 2 + size, h / 2 - 2);
                        arrow.addPoint(w / 2, h / 2 + 4);

                        g2.fill(arrow);

                        g2.dispose();
                    }
                };

                button.setBackground(darkMode ? new Color(42, 52, 70) : new Color(245, 247, 250));

                button.setBorder(BorderFactory.createEmptyBorder());

                button.setFocusPainted(false);

                button.setContentAreaFilled(true);

                button.setOpaque(true);

                button.setCursor(new Cursor(Cursor.HAND_CURSOR));

                button.setPreferredSize(new Dimension(38, 38));

                return button;
            }
        });
    }
}