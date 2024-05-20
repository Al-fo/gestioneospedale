package progetto.controller;

import java.io.IOException;
import java.time.LocalDate;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import progetto.models.Paziente;
import progetto.models.PazienteNonRegistratoException;
import progetto.App;
import progetto.models.ListaVuotaException;

public class MainController {

    @FXML
    ListView<String> listaPazienti;

    @FXML
    TableView<Paziente> listaVisualizzaPaziente;
    
    @FXML
    TableColumn<Paziente,String> colonnaNome,colonnaCognome,colonnaCodiceFiscale,colonnaMotivo;

    @FXML
    TableColumn<Paziente,LocalDate> colonnaData;

    @FXML
    Button buttonRegistra,buttonModifica,buttonRimuovi;

    @FXML
    Label labelErroreRimuovi,labelErroreModifica;

    @FXML
    void initialize(){
        colonnaNome.setCellValueFactory(new PropertyValueFactory<Paziente,String>("nome"));
        colonnaCognome.setCellValueFactory(new PropertyValueFactory<Paziente,String>("cognome"));
        colonnaCodiceFiscale.setCellValueFactory(new PropertyValueFactory<Paziente,String>("codiceFiscale"));
        colonnaData.setCellValueFactory(new PropertyValueFactory<Paziente,LocalDate>("dataRecovero"));
        colonnaMotivo.setCellValueFactory(new PropertyValueFactory<Paziente,String>("motivoMalattia"));
        listaVisualizzaPaziente.setVisible(false);
        refresh();
    }

    @FXML
    private void switchToRegistra() throws IOException {
        labelErroreRimuovi.setVisible(false);
        labelErroreModifica.setVisible(false);
        App.setRoot("RegistraView");
    }
    @FXML
    private void switchToModifica() throws IOException{
        labelErroreRimuovi.setVisible(true);
        if(listaPazienti.getSelectionModel().getSelectedItem() != null){
            labelErroreModifica.setVisible(false);
            ModificaController.indiceDaModificare = listaPazienti.getSelectionModel().getSelectedIndex();
            App.setRoot("ModificaView");
        }
        else{
            labelErroreModifica.setText("Seleziona un Paziente");
            labelErroreModifica.setVisible(true);
        }
    }
    @FXML
    private void backToMainView() throws IOException{
        App.setRoot("View");
    }

    @FXML
    private void rimuovi(){
        labelErroreModifica.setVisible(false);
        if(listaPazienti.getSelectionModel().getSelectedItem() != null){
            try {
                App.lista.rimuoviPaziente(new Paziente(null,null,listaPazienti.getSelectionModel().getSelectedItem(),null,null));
                labelErroreRimuovi.setVisible(false);
            } catch (ListaVuotaException e) {
                System.out.println("104 Error [Lista vuota]");
            } catch (PazienteNonRegistratoException e) {
                System.out.println("104 Error [Utente non esistente]");
            }
        }else{
            labelErroreRimuovi.setText("Seleziona un Paziente");
            labelErroreRimuovi.setVisible(true);
        }
        refresh();
        App.lista.serializza(App.nomeFile);
    }

    private void refresh(){
        listaPazienti.getItems().clear();
        ObservableList<Paziente> lista2 = FXCollections.observableArrayList(App.lista.getLista());

        buttonModifica.disableProperty().bind(new SimpleBooleanProperty(lista2.isEmpty()));
        buttonRimuovi.disableProperty().bind(new SimpleBooleanProperty(lista2.isEmpty()));

        if(lista2.isEmpty()) listaVisualizzaPaziente.setVisible(false);
        for(Paziente p : lista2){
            listaPazienti.getItems().add(p.getCodiceFiscale());
        }
    }

    @FXML
    private void visualizza(){
        if(listaPazienti.getSelectionModel().getSelectedItem() != null){
            listaVisualizzaPaziente.setVisible(true);
            ObservableList<Paziente> lista;
                lista = FXCollections.observableArrayList(App.lista.find(new Paziente(null,null,listaPazienti.getSelectionModel().getSelectedItem(),null,null)));
            listaVisualizzaPaziente.setItems(lista);
        }
    }

    public static boolean isNumeric(String s){
        for(char c: s.toCharArray()){
            if((c < '0' || c > '9')) return false;
        }
        return true;
    }

    public static boolean isAnumeric(String s){
        for(char c: s.toCharArray()){
            if(!(c < '0' || c > '9')) return false;
        }
        return true;
    }
}
