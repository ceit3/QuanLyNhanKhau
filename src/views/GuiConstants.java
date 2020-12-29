package views;

/**
 * Lop chua cac thuoc tinh hang
 * getWindowInitWidth() : lay chieu rong cua so
 * getWindowInitHeight() : lay chieu cao cua so
 */
public class GuiConstants {
    private static int WINDOW_INIT_HEIGHT = 900;
    private static int WINDOW_INIT_WIDTH = 1500;

    private static int PANE_INIT_HEIGHT = 900;
    private static int PANE_INIT_WIDTH = 1200;

    public static int getPaneInitHeight() {
        return PANE_INIT_HEIGHT;
    }

    public static void setPaneInitHeight(int paneInitHeight) {
        PANE_INIT_HEIGHT = paneInitHeight;
    }

    public static int getPaneInitWidth() {
        return PANE_INIT_WIDTH;
    }

    public static void setPaneInitWidth(int paneInitWidth) {
        PANE_INIT_WIDTH = paneInitWidth;
    }

    public static int getWindowInitWidth() {
        return WINDOW_INIT_WIDTH;
    }

    public static int getWindowInitHeight() {
        return WINDOW_INIT_HEIGHT;
    }
}
