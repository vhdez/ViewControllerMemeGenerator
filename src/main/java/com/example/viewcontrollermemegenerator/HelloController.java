package com.example.viewcontrollermemegenerator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class HelloController {
    // All of the Control's fields
    public ImageView photoView;
    public Button previousButton;
    public Button nextButton;
    public ComboBox choosePicture;
    public Button newButton;
    public TextField typeMemeText;
    public TextArea typeLongMemeText;
    public Label pictureNumberLabel;
    public Label memeTextDisplay;
    public ColorPicker memeTextColor;
    public ListView allTheTexts;

    // All of the App's data (its Model)
    ArrayList<Image> images;
    int currentImageNumber;

    // the setup code for your GUI
    public void initialize() throws Exception {
        restoreData();

        Image image1 = new Image(new FileInputStream(new File("images/cookie.png")));
        Image image2 = new Image(new FileInputStream(new File("images/BMO.jpg")));
        Image image3 = new Image(new FileInputStream(new File("images/CharlieBrown.png")));
        Image image4 = new Image(new FileInputStream(new File("images/Phineas.png")));

        images = new ArrayList<Image>();
        images.add(image1);
        choosePicture.getItems().add("Cookie Monster");
        images.add(image2);
        choosePicture.getItems().add("BMO");
        images.add(image3);
        choosePicture.getItems().add("Charlie Brown");
        images.add(image4);
        choosePicture.getItems().add("Phineas");

        currentImageNumber = 0;
        photoView.setImage(image1);
        choosePicture.getSelectionModel().select(currentImageNumber);
        pictureNumberLabel.setText("Picture #" + (currentImageNumber+1) + " of " + images.size());


    }

    // all of the Controls' onAction methods
    public void nextButtonPressed() {
        if (currentImageNumber < images.size()-1) {
            currentImageNumber = currentImageNumber + 1;
        } else {
            currentImageNumber = 0;
        }
        photoView.setImage(images.get(currentImageNumber));
        choosePicture.getSelectionModel().select(currentImageNumber);
        pictureNumberLabel.setText("Picture #" + (currentImageNumber+1) + " of " + images.size());
    }

    public void prevButtonPressed() {
        if (currentImageNumber == 0) {
            currentImageNumber = images.size() - 1;
        } else {
            currentImageNumber = currentImageNumber - 1;
        }
        photoView.setImage(images.get(currentImageNumber));
        choosePicture.getSelectionModel().select(currentImageNumber);
        pictureNumberLabel.setText("Picture #" + (currentImageNumber+1) + " of " + images.size());
    }

    public void newButtonPressed() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        Stage thisStage = (Stage) newButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(thisStage);
        choosePicture.getItems().add(selectedFile.getName().substring(0,selectedFile.getName().indexOf(".")));

        Image newImage = new Image(new FileInputStream(selectedFile));
        images.add(newImage);
        currentImageNumber = images.size()-1;
        photoView.setImage(images.get(currentImageNumber));
        choosePicture.getSelectionModel().select(currentImageNumber);
        pictureNumberLabel.setText("Picture #" + (currentImageNumber+1) + " of " + images.size());
    }

    public void deleteImage() {
        if (images.size() == 0) {
            return;
        }
        images.remove(currentImageNumber);
        choosePicture.getItems().remove(currentImageNumber);

        if (currentImageNumber > 0) {
            currentImageNumber = currentImageNumber - 1;
        } else {
            currentImageNumber = 0;
        }

        if (images.size() > currentImageNumber) {
            photoView.setImage(images.get(currentImageNumber));
            choosePicture.getSelectionModel().select(currentImageNumber);
            pictureNumberLabel.setText("Picture #" + (currentImageNumber + 1) + " of " + images.size());
        } else {
            photoView.setImage(null);
            choosePicture.getSelectionModel().clearSelection();
            pictureNumberLabel.setText("Picture #0 of " + images.size());
        }
    }

    public void photoChosen() {
        currentImageNumber = choosePicture.getSelectionModel().getSelectedIndex();
        pictureNumberLabel.setText("Picture #" + (currentImageNumber + 1) + " of " + images.size());
        photoView.setImage(images.get(currentImageNumber));
    }

    public void memeTyped() {
        memeTextDisplay.setFont(new Font("Comic Sans MS", 60));
        memeTextDisplay.setText("#" + typeMemeText.getText());

        allTheTexts.getItems().add(typeMemeText.getText());

        typeMemeText.clear();
    }

    public void longMemeTyped() {
        memeTextDisplay.setFont(new Font("Comic Sans MS", 18));
        memeTextDisplay.setText(typeLongMemeText.getText());
    }

    public void memeColorChanged() {
        memeTextDisplay.setTextFill(memeTextColor.getValue());
    }

    public void saveData() throws Exception {
        FileOutputStream fileOut = new FileOutputStream("SavedMemeList");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        // allTheTexts is my ListView. Save its ObservableList by turning it into an ArrayList.
        ArrayList<String> temporaryList = new ArrayList<>(allTheTexts.getItems());
        out.writeObject(temporaryList);

        out.close();
        fileOut.close();
    }

    public void restoreData() throws Exception {
        FileInputStream fileIn = new FileInputStream("SavedMemeList");
        ObjectInputStream in = new ObjectInputStream(fileIn);

        // Read the data into an ArrayList
        ArrayList<String> temporaryList = (ArrayList<String>) in.readObject();
        // Turn the ArrayList into an ObservableList
        ObservableList temporaryObservableList = FXCollections.observableArrayList(temporaryList);
        // Make that ObservableList the list for my ListView
        allTheTexts.setItems(temporaryObservableList);

        in.close();
        fileIn.close();
    }

}