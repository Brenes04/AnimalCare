/*
 * ANIMAL CARE PROJECT
 * File: AnimalCareTheme.java
 * Description: Defines and applies the common visual style of the Animal Care system windows.
 *
 * Documentation added solely for academic and maintenance purposes.
 * The logic, instructions, and behavior of the original code were not modified.
 */

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import java.awt.*;

//Visual theme used by all Animal Care forms.
public final class AnimalCareTheme {

    private static final Color PRIMARY = new Color(24, 113, 106);      // veterinary teal
    private static final Color PRIMARY_DARK = new Color(16, 82, 78);
    private static final Color ACCENT = new Color(244, 156, 61);       // warm accent
    private static final Color BACKGROUND = new Color(245, 250, 249);
    private static final Color SURFACE = Color.WHITE;
    private static final Color TEXT = new Color(39, 55, 58);
    private static final Color MUTED = new Color(99, 115, 118);
    private static final Color DANGER = new Color(190, 74, 65);
    private static final Color INFO = new Color(59, 113, 165);

    private AnimalCareTheme() {
    }

    public static void apply(JFrame frame, JPanel root) {
        if (frame == null || root == null) return;

        frame.getContentPane().setBackground(BACKGROUND);
        root.setBackground(BACKGROUND);
        root.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(211, 228, 225), 1),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)
        ));

        styleTree(root);
        frame.setMinimumSize(new Dimension(560, 420));
    }

    private static void styleTree(Component component) {
        if (component instanceof JPanel panel) {
            panel.setBackground(BACKGROUND);
        }

        if (component instanceof JLabel label) {
            styleLabel(label);
        } else if (component instanceof JButton button) {
            styleButton(button);
        } else if (component instanceof JTextField field) {
            styleTextField(field);
        } else if (component instanceof JComboBox<?> combo) {
            combo.setBackground(SURFACE);
            combo.setForeground(TEXT);
            combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            combo.setBorder(fieldBorder());
        } else if (component instanceof JSpinner spinner) {
            spinner.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            spinner.setBorder(fieldBorder());
        } else if (component instanceof JRadioButton radio) {
            radio.setBackground(BACKGROUND);
            radio.setForeground(TEXT);
            radio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            radio.setFocusPainted(false);
        } else if (component instanceof JCheckBox checkBox) {
            checkBox.setBackground(BACKGROUND);
            checkBox.setForeground(TEXT);
            checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            checkBox.setFocusPainted(false);
        } else if (component instanceof JTable table) {
            styleTable(table);
        } else if (component instanceof JTextPane textPane) {
            textPane.setBackground(SURFACE);
            textPane.setForeground(TEXT);
            textPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            textPane.setBorder(fieldBorder());
        } else if (component instanceof JTabbedPane tabs) {
            tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
            tabs.setForeground(PRIMARY_DARK);
            tabs.setBackground(BACKGROUND);
        } else if (component instanceof JScrollPane scrollPane) {
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(205, 221, 218)));
            scrollPane.getViewport().setBackground(SURFACE);
        }

        if (component instanceof Container container) {
            for (Component child : container.getComponents()) {
                styleTree(child);
            }
        }
    }

    private static void styleLabel(JLabel label) {
        label.setForeground(TEXT);
        Font current = label.getFont();
        int size = current == null ? 13 : current.getSize();

        if (size >= 18) {
            label.setFont(new Font("Segoe UI", Font.BOLD, Math.max(size, 22)));
            label.setForeground(PRIMARY_DARK);
        } else {
            label.setFont(new Font("Segoe UI", current != null && current.isBold() ? Font.BOLD : Font.PLAIN, Math.max(13, size)));
        }
    }

    private static void styleTextField(JTextField field) {
        field.setBackground(SURFACE);
        field.setForeground(TEXT);
        field.setCaretColor(PRIMARY_DARK);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(fieldBorder());
        field.setPreferredSize(new Dimension(Math.max(140, field.getPreferredSize().width), 30));
    }

    private static Border fieldBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(185, 209, 205), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        );
    }

    private static void styleButton(JButton button) {
        String text = button.getText() == null ? "" : button.getText().toLowerCase();
        Color background = PRIMARY;

        if (text.contains("salir") || text.contains("cerrar")) {
            background = new Color(92, 105, 108);
        } else if (text.contains("desactivar") || text.contains("eliminar")) {
            background = DANGER;
        } else if (text.contains("modificar") || text.contains("buscar")) {
            background = INFO;
        } else if (text.contains("limpiar")) {
            background = ACCENT;
        }

        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(background);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(background.darker(), 1),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));
        button.setPreferredSize(new Dimension(Math.max(120, button.getPreferredSize().width), 36));
    }

    private static void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setForeground(TEXT);
        table.setBackground(SURFACE);
        table.setSelectionBackground(new Color(213, 236, 232));
        table.setSelectionForeground(TEXT);
        table.setRowHeight(26);
        table.setGridColor(new Color(224, 233, 231));
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setFont(new Font("Segoe UI", Font.BOLD, 12));
            header.setBackground(PRIMARY_DARK);
            header.setForeground(Color.WHITE);
            header.setReorderingAllowed(false);
            header.setPreferredSize(new Dimension(header.getPreferredSize().width, 30));
        }
    }
}
