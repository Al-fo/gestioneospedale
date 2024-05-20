package progetto.models;

public class PazienteNonRegistratoException extends Exception{
    String errore;
    PazienteNonRegistratoException(String s){
        errore = s;
    }
    public String getErrore(){
        return errore;
    }
}
