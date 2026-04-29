import data.AppData;
import view.HomeView;

import javax.swing.UIManager;
import java.awt.Font;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
        } catch (Exception ignored) {}

        AppData.init();
        javax.swing.SwingUtilities.invokeLater(HomeView::new);
    }
}