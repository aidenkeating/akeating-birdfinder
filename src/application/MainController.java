package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class MainController {
	private static FileChooser fileChooser = new FileChooser();
	private static BlackWhiteFilter bwFilter = new BlackWhiteFilter(127);

    @FXML
    private MenuItem openBtn;

    @FXML
    private MenuItem closeBtn;

    @FXML
    private ImageView originalImageView;

    @FXML
    private ImageView outlinedImageView;

    @FXML
    private ImageView blackWhiteImageView;

    @FXML
    void onOpen(ActionEvent event) {
    	File imageFile = fileChooser.showOpenDialog(null);
    	
    	if (imageFile == null) {
    		return;
    	}
    	try {
			BufferedImage selectedImage = ImageIO.read(imageFile);
			BufferedImage bwImage = bwFilter.applyToImage(selectedImage);
			
			BirdRecognition br = new BirdRecognition(bwImage);
			BufferedImage outlinedImage = br.outlineBirdsInImage(selectedImage);
			
			blackWhiteImageView.setImage(SwingFXUtils.toFXImage(bwImage, null));
			outlinedImageView.setImage(SwingFXUtils.toFXImage(outlinedImage, null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void onClose(ActionEvent event) {

    }

}
