package pl.oldzi.assecoTask.util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class AnimatedImageView extends ImageView {
        private final Rectangle2D[] cellClips;
        private int cellsNumber;
        private final Duration FRAME_TIME = Duration.seconds(.1);

    public AnimatedImageView(Image animatedImage, int cellsNumber) {
        this.cellsNumber = cellsNumber;
        double cellWidth  = animatedImage.getWidth() / cellsNumber;
        double cellHeight = animatedImage.getHeight();

        cellClips = new Rectangle2D[cellsNumber];
        for (int i = 0; i < cellsNumber; i++) {
            cellClips[i] = new Rectangle2D(
                    i * cellWidth, 0,
                    cellWidth, cellHeight
            );
        }
        setImage(animatedImage);
        setViewport(cellClips[0]);
        animate();
    }

    private void animate() {
        final IntegerProperty frameCounter = new SimpleIntegerProperty(0);
        Timeline animationTimeline = new Timeline(
                new KeyFrame(FRAME_TIME, event -> {
                    frameCounter.set((frameCounter.get() + 1) % cellsNumber);
                    setViewport(cellClips[frameCounter.get()]);
                })
        );
        animationTimeline.setCycleCount(Animation.INDEFINITE);
        animationTimeline.play();
    }
}

