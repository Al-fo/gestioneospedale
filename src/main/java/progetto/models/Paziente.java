package progetto.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Paziente implements Serializable{
    private String nome,cognome,codiceFiscale,motivoMalattia;
    private LocalDate dataRecovero;

    public Paziente(){
        nome = "";
        cognome = "";
        codiceFiscale = "";
        motivoMalattia = "";
        dataRecovero = LocalDate.of(0,1,1);
    }

    public Paziente(String nome, String cognome, String codiceFiscale, String motivoMalattia, LocalDate dataRecovero){
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.motivoMalattia = motivoMalattia;
        if(dataRecovero != null)
        this.dataRecovero = LocalDate.of(dataRecovero.getYear(),dataRecovero.getMonth(),dataRecovero.getDayOfMonth());
        else this.dataRecovero = null;
    }

    public Paziente(Paziente altro){
     nome = altro.nome;
     cognome = altro.cognome;
     codiceFiscale = altro.codiceFiscale;
     motivoMalattia = altro.motivoMalattia;
     dataRecovero = LocalDate.of(altro.dataRecovero.getYear(),altro.dataRecovero.getMonth(),altro.dataRecovero.getDayOfMonth());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setMotivoMalattia(String motivoMalattia) {
        this.motivoMalattia = motivoMalattia;
    }

    public void setDataRecovero(LocalDate dataRecovero) {
        this.dataRecovero = dataRecovero;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getMotivoMalattia() {
        return motivoMalattia;
    }

    public LocalDate getDataRecovero() {
        return dataRecovero;
    }

    public boolean isEquals(Paziente altro){
        return codiceFiscale.equalsIgnoreCase(altro.codiceFiscale);
    }

    public void stampa(){
        System.out.println("nome: " + nome + " cognome: " + cognome +  "\ncodice fiscale: " + codiceFiscale + " motivo del recovero: " + motivoMalattia + "\ndata recovero: " + dataRecovero.toString());
    }

    public String toString(){
        return ("["+nome+cognome+codiceFiscale+motivoMalattia+dataRecovero.toString()+"]");
    }
    
}
