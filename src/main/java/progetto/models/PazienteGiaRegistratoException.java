package progetto.models;

public class PazienteGiaRegistratoException extends Exception{
    String errore;
    PazienteGiaRegistratoException(String s){
        errore = s;
    }
    public String getErrore(){
        return errore;
    }
}
