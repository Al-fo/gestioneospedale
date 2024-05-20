package progetto.models;

public class ListaVuotaException extends Exception{
    String errore;
    ListaVuotaException(String s){
        errore = s;
    }
    public String getErrore(){
        return errore;
    }
}
