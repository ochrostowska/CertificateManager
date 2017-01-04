package pl.oldzi.assecoTask.util;

import com.google.gson.GsonBuilder;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import okhttp3.OkHttpClient;
import pl.oldzi.assecoTask.network_calls.BaseNetworkManager;

public class UIEffectManager {

    private static UIEffectManager ourInstance;

    public static UIEffectManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new UIEffectManager();
        }
        return ourInstance;
    }

    public void addOnEnterGlowEffect(Node n, Color c) {
        DropShadow drop = new DropShadow(10, c);
        drop.setInput(new Glow());
        n.setOnMouseEntered(event -> n.setEffect(drop));
        n.setOnMouseExited(event -> n.setEffect(null));
    }

    public void addGlowEffect(Node n, Color c) {
        DropShadow drop = new DropShadow(10, c);
        drop.setInput(new Glow());
        n.setEffect(drop);
    }

    public void addSnowBackground(Pane pane) {
        Image image = new Image(getClass().getResource("/images/snow.png").toExternalForm());
        AnimatedImageView snowImageView = new AnimatedImageView(image, 20);
        pane.getChildren().add(snowImageView);
        snowImageView.getStyleClass().add("opaque");
        snowImageView.toBack();
    }
}
