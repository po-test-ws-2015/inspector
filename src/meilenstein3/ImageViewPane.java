package meilenstein3;

import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.layout.*;

/**
 * Klasse dessen Objekte skallierbare Bilder repraesentieren.
 *
 */
public class ImageViewPane extends Region {

    private ObjectProperty<ImageView> imageViewProperty = new SimpleObjectProperty<ImageView>();

    public ObjectProperty<ImageView> imageViewProperty() {
        return imageViewProperty;
    }

    public ImageView getImageView() {
        return imageViewProperty.get();
    }

    public void setImageView(ImageView imageView) {
        this.imageViewProperty.set(imageView);
    }

    public ImageViewPane() {
        this(new ImageView());
    }

    @Override
    protected void layoutChildren() {
        ImageView imageView = imageViewProperty.get();
        if (imageView != null) {
            imageView.setFitWidth(getWidth());
            imageView.setFitHeight(getHeight());
            layoutInArea(imageView, 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, VPos.CENTER);
        }
        super.layoutChildren();
    }

    public ImageViewPane(ImageView imageView) {
        imageViewProperty.addListener(new ChangeListener<ImageView>() {

            @Override
            public void changed(ObservableValue<? extends ImageView> arg0, ImageView oldIV, ImageView newIV) {
                if (oldIV != null) {
                    getChildren().remove(oldIV);
                }
                if (newIV != null) {
                    getChildren().add(newIV);
                }
            }
        });
        this.imageViewProperty.set(imageView);
    }

    public ObservableList<Node> mach() {
  	  return this.getChildren();
     }
}
