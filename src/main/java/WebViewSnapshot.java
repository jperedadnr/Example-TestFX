import javafx.scene.Scene;
import javafx.scene.web.WebView;

public class WebViewSnapshot extends Scene {

    static final String URL = "https://gluonhq.com";

    public WebViewSnapshot() {
        super(new WebView());
    }
}
