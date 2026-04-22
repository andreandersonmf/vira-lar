import data.AppData;
import view.HomeView;

public class Main {
    public static void main(String[] args) {
        AppData.init();
        javax.swing.SwingUtilities.invokeLater(HomeView::new);
    }
}