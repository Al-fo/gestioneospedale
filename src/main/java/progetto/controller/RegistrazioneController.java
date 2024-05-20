package progetto.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import progetto.App;
import progetto.models.Utente;

/**
 * class RegistraController
 * @author Lorenzo Gori
 * 15/03/2023
 */
public class RegistrazioneController {

    private ArrayList<Utente> listaUtenti = new ArrayList<Utente>();

    BufferedReader reader;
    BufferedWriter writer;
    File file = new File("src\\main\\java\\progetto\\file.txt");

    @FXML
    private Button bRegistra;

    @FXML
    private Button buttonAccedi;

    @FXML
    private Button buttonRegistra;

    @FXML
    private Label labelAccesso;

    @FXML
    private Label labelError;

    @FXML
    private Label labelErrorRegistra;

    @FXML
    private Label site;

    @FXML
    private PasswordField textPassword;

    @FXML
    private TextField textUsername;

    /**
     * method initialize
     * used for initialize the components before launching the frame
     */
    @FXML
    public void initialize(){
        labelAccesso.setVisible(false);
        labelErrorRegistra.setVisible(false);
        bRegistra.setVisible(false);
        labelError.setVisible(false);

        aggiornaLista();

        salvaLista();      
    }

    /**
     * method accedi
     * Checks if the inserted username exist in the file and if so, 
     * the user can log in into his account,
     * else it shows an error 
     * @throws IOException
     */
    @FXML
    public void accedi() throws IOException{
        site.setText("ACCEDI ALL'OSPEDALE MABOLS");
        labelError.setVisible(false);
        labelErrorRegistra.setVisible(false);
        labelAccesso.setVisible(false);

        buttonRegistra.setVisible(true);
        bRegistra.setVisible(false);
        
        if(checkForUsername(textUsername.getText())){
            if(checkUsernamesPassword(textUsername.getText()).equalsIgnoreCase(textPassword.getText())){
                App.nomeFile = textUsername.getText() + textPassword.getText();
                App.lista.deserializza(App.nomeFile);
                App.setRoot("View");
            }
            else{
                labelError.setVisible(true);
            }
        }
        else labelError.setVisible(true);

        salvaLista();
    }

    /**
     * method registra
     * switch view to a sign up view
     */
    @FXML
    public void registra(){
        labelError.setVisible(false);
        labelErrorRegistra.setVisible(false);
        labelAccesso.setVisible(false);
        
        site.setText("REGISTRATI ALL'OSPEDALE MABOLS");
        buttonRegistra.setVisible(false);
        bRegistra.setVisible(true);
    }

    /**
     * method registrati
     * used for signing up a new account
     * if the inserted username does already exist or it's empty, it shows an error
     */
    @FXML
    public void registrati(){

        labelError.setVisible(false);
        labelErrorRegistra.setText("Username gia esistente");
        labelErrorRegistra.setVisible(false);
        labelAccesso.setVisible(false);
        
        if(!(textUsername.getText().trim().equals(""))){
            if(checkForUsername(textUsername.getText())) labelErrorRegistra.setVisible(true);
            else listaUtenti.add(new Utente(textUsername.getText().trim(), textPassword.getText().trim()));
        }else{
            labelErrorRegistra.setText("Username non valido");
            labelErrorRegistra.setVisible(true);
        }

        salvaLista();

        buttonRegistra.setVisible(true);
        bRegistra.setVisible(false);
        site.setText("ACCEDI ALL' OSPEDALE MABOLS");
    }

    /**
     * method aggiornalista
     * copies the usernames and passwords in the file into the users list
     */
    private void aggiornaLista(){
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("Errore istanza");
        }

        listaUtenti = new ArrayList<Utente>();

        try {
            while(reader.ready()){
                try{
                    String letto = reader.readLine();
                    String[] arrayLetto = letto.split("-");
                    listaUtenti.add(new Utente(arrayLetto[0].trim(),arrayLetto[1].trim()));
                }catch(IOException e){
                    System.out.println("Errore interno al while");
                }
            }
        } catch (IOException e) {
            System.out.println("Errore While");
        }

        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("Errore chiusura");
        }
    }

    /**
     * method salvaLista
     * saves in the file the usernames and password contained in the users list
     */
    private void salvaLista(){
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            System.out.println("Errore istanza writer");
        }

        for(int i = 0; i < listaUtenti.size(); i++){
            try {
                writer.write(listaUtenti.get(i).getUsername() + " - " + listaUtenti.get(i).getPassword() + "\n");
            } catch (IOException e) {
                System.out.println("Errore nella stampa sul file");
            }
        }

        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Errore chiusura Writer");
        }
    }

    /**
     * method checkForUsername
     * checks if the inserted username does already exist into the users list
     * @param username the username to look for
     * @return true if the username does already exist, false otherwise
     */
    private boolean checkForUsername(String username){
        boolean trovato = false;
        if(listaUtenti.size() > 0)
            for(int i = 0; i < listaUtenti.size(); i++){
                if(listaUtenti.get(i).getUsername().trim().equalsIgnoreCase(username.trim())){
                    trovato = true;
                    break;
                }
            }
        return trovato;
    }

    /**
     * method checkUsernamesPassword
     * check the password of a specified user
     * @param username the username to check the password
     * @return a string containing the password of the given username
     */
    private String checkUsernamesPassword(String username){
        String password = "";
        if(listaUtenti.size() > 0)
            for(int i = 0; i < listaUtenti.size(); i++){
                if(listaUtenti.get(i).getUsername().trim().equalsIgnoreCase(username.trim())){
                    password = listaUtenti.get(i).getPassword().trim();
                    break;
                }
            }
        return password;
    }
}
