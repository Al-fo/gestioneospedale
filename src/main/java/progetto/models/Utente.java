package progetto.models;

/**
 * Class Utente 
 * @author Lorenzo Gori
 * 15/03/2023
 */
public class Utente {
    private String username;
    private String password;

    /**
     * Constructor Utente
     */
    public Utente(){
        username = "";
        password = "";
    }
    /**
     * Contructor Utente
     * @param nome name to set as an attribute
     * @param password password to set as an attribute 
     */
    public Utente(String nome, String password){
        this.username = nome;
        this.password = password;
    }

    /**
     * method setNome
     * @param nome name to set as an attribute
     */
    public void setUsername(String nome){
        this.username = nome;
    }
    /**
     * method setPassword
     * @param password password to set as an attribute
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * method getNome
     * @return nome: the attribute nome
     */
    public String getUsername(){
        return username;
    }
    /**
     * method getPassword
     * @return password: the attribute password
     */
    public String getPassword(){
        return password;
    }

    /**
     * method toString
     * @return a string with name and password separated by a | 
     */
    public String toString(){
        return username + " | " + password;
    }
}
