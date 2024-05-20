package progetto.controller;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import progetto.models.Paziente;
import progetto.models.PazienteGiaRegistratoException;
import progetto.App;
import progetto.models.ListaVuotaException;

public class ModificaController {
    public static int indiceDaModificare;
    
    @FXML
    TextField textFieldNome,textFieldCognome,textFieldCodiceFiscale,textFieldEta,textFieldMotivo;
    @FXML
    DatePicker datePickerData;
    @FXML
    Button buttonModifica;
    @FXML
    Label labelNome, labelCognome, labelCodiceFiscale, labelData, labelEsistente;

    @FXML
    public void initialize(){
        textFieldNome.setText(App.lista.getLista().get(indiceDaModificare).getNome());
        textFieldCognome.setText(App.lista.getLista().get(indiceDaModificare).getCognome());
        textFieldCodiceFiscale.setText(App.lista.getLista().get(indiceDaModificare).getCodiceFiscale());
        textFieldMotivo.setText(App.lista.getLista().get(indiceDaModificare).getMotivoMalattia());

        buttonModifica.disableProperty().bind(textFieldNome.textProperty().isEmpty().or(textFieldCognome.textProperty().isEmpty().or(textFieldCodiceFiscale.textProperty().isEmpty().or(textFieldMotivo.textProperty().isEmpty()))));
    }

    @FXML
    private void backToMain() throws IOException{
        App.setRoot("View");
    }

    @FXML
    private void modifica() throws IOException{
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
            giusto = false;
            labelNome.setVisible(true);
        }

        if(MainController.isAnumeric(textFieldCognome.getText())){
            char a = textFieldCognome.getText().charAt(0);
            cognome = Character.toString(a).toUpperCase() + textFieldCognome.getText(1, textFieldCognome.getText().length()).toLowerCase();
            labelCognome.setVisible(false);
        }else{
            giusto = false;
            labelCognome.setVisible(true);
        }


        if(textFieldCodiceFiscale.getText().length() == 16){
            codiceFiscale = textFieldCodiceFiscale.getText().toUpperCase();
            labelCodiceFiscale.setVisible(false);
        }else{
            giusto = false;
            labelCodiceFiscale.setVisible(true);
        }

        if(data == null){ 
            giusto = false;
            labelData.setVisible(true);
        }else{
            labelData.setVisible(false);
        }

        if(giusto){
            try {
                App.lista.modificaPaziente(indiceDaModificare, new Paziente(nome,cognome,codiceFiscale,motivo,data));
                App.lista.serializza(App.nomeFile);
                backToMain();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("104 Error: [Indice]");
            } catch (PazienteGiaRegistratoException e) {
                labelEsistente.setVisible(true);
            } catch (ListaVuotaException e) {
                System.out.println("104 Error: [Lista vuota]");
            }
        }
    }
}
