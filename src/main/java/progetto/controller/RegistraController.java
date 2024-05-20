package progetto.controller;

import java.io.IOException;
import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import progetto.App;
import progetto.models.Paziente;
import progetto.models.PazienteGiaRegistratoException;

public class RegistraController {

    @FXML
    TextField textFieldNome,textFieldCognome,textFieldCodiceFiscale,textFieldEta,textFieldMotivo;
    @FXML
    DatePicker datePickerData;
    @FXML
    Button buttonRegistra;
    @FXML
    Label labelNome,labelCognome,labelCodiceFiscale,labelData,labelMotivo,labelEsistente;

    @FXML
    public void initialize(){
        buttonRegistra.disableProperty().bind(textFieldNome.textProperty().isEmpty().or(textFieldCognome.textProperty().isEmpty().or(textFieldCodiceFiscale.textProperty().isEmpty().or(textFieldMotivo.textProperty().isEmpty()).or(used()))));
    }

    @FXML
    private void backToMain() throws IOException{
        App.setRoot("View");
    }

    @FXML
    private void registra() throws IOException{
        labelEsistente.setVisible(false);
        String nome = "";
        String cognome = "";
        String codiceFiscale = "";
        String motivo = textFieldMotivo.getText();
        LocalDate data = datePickerData.getValue();
        boolean giusto = true;
        if(MainController.isAnumeric(textFieldNome.getText())){
            char a = textFieldNome.getText().charAt(0);
            nome = Character.toString(a).toUpperCase() + textFieldNome.getText(1, textFieldNome.getText().length()).toLowerCase();
            labelNome.setVisible(false);
        }else{
            labelNome.setVisible(true);
            giusto = false;
        }

        if(MainController.isAnumeric(textFieldCognome.getText())){
            char a = textFieldCognome.getText().charAt(0);
            cognome = Character.toString(a).toUpperCase() + textFieldCognome.getText(1, textFieldCognome.getText().length()).toLowerCase();
            labelCognome.setVisible(false);
        }else{
            labelCognome.setVisible(true);
            giusto = false;
        }


        if(textFieldCodiceFiscale.getText().length() == 16){
            codiceFiscale = textFieldCodiceFiscale.getText().toUpperCase();
            labelCodiceFiscale.setVisible(false);
        }else{
            labelCodiceFiscale.setVisible(true);
            giusto = false;
        }

        if(data == null){
            giusto = false;
            labelData.setVisible(true);
        }else{
            labelData.setVisible(false);
        }

        if(giusto){
            try {
                App.lista.aggiungiPaziente(new Paziente(nome,cognome,codiceFiscale,motivo,data));
                App.lista.serializza(App.nomeFile);
                backToMain();
            } catch (PazienteGiaRegistratoException e) {
                labelEsistente.setVisible(true);
            }
        }
    }

    private BooleanProperty used(){
        BooleanProperty a = new SimpleBooleanProperty(datePickerUnused());
        return a;
    }

    private boolean datePickerUnused(){
        return datePickerData.getValue() != null;
    }
}
