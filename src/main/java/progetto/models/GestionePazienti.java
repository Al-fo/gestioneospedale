package progetto.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GestionePazienti implements Serializable{
    ArrayList<Paziente> listaDipendenti;

    public GestionePazienti(){
        listaDipendenti = new ArrayList<Paziente>();
    }
    GestionePazienti(Paziente[] d){
        listaDipendenti = new ArrayList<Paziente>();
        for(int i = 0; i < d.length; i++){
            listaDipendenti.add(new Paziente(d[i]));
        }
    }
    
    public ArrayList<Paziente> getLista(){
        return listaDipendenti;
    }

    public void aggiungiPaziente(Paziente d) throws PazienteGiaRegistratoException{
        if(listaDipendenti.size() == 0){
            try{
                listaDipendenti.add(new Paziente(d));
            }catch(NullPointerException e){

            }
        }else{
            for(Paziente Paziente : listaDipendenti){
                if(Paziente.isEquals(d)) throw new PazienteGiaRegistratoException("Paziente già registrato");
            }
            try{
                listaDipendenti.add(new Paziente(d));
            }catch(NullPointerException e){

            }
        }
    }

    public void modificaPaziente(Paziente PazienteDaModificare, Paziente nuovoPaziente) 
        throws PazienteGiaRegistratoException,PazienteNonRegistratoException,ListaVuotaException{
            if(listaDipendenti.size() == 0) throw new ListaVuotaException("La lista è vuota");
            if(!(PazienteDaModificare.isEquals(nuovoPaziente))){
                for(Paziente d: listaDipendenti){
                    if(d.isEquals(nuovoPaziente)) throw new PazienteGiaRegistratoException("Paziente già registrato");
                }
            }
            for(int i = 0; i < listaDipendenti.size(); i++){
                if(listaDipendenti.get(i).isEquals(PazienteDaModificare)){
                    listaDipendenti.set(i,new Paziente(nuovoPaziente));
                    return;
                }
            }
            throw new PazienteNonRegistratoException("Paziente da modificare non esistente");
    }

    public void modificaPaziente(int pos, Paziente nuovoPaziente) 
        throws PazienteGiaRegistratoException, IndexOutOfBoundsException, ListaVuotaException{
            if(listaDipendenti.size() == 0){
                throw new ListaVuotaException("La lista è vuota");
            }
            if(!(nuovoPaziente.isEquals(listaDipendenti.get(pos)))){
                for(Paziente d: listaDipendenti){
                    if(d.isEquals(nuovoPaziente)){
                        throw new PazienteGiaRegistratoException("Paziente già registrato");
                    }
                }
            }
            try{
                listaDipendenti.set(pos, new Paziente(nuovoPaziente));
            }catch(IndexOutOfBoundsException e){
                System.out.println("Errore 67");
                throw e;
            }
    }

    public void rimuoviPaziente(Paziente Paziente)
        throws ListaVuotaException, PazienteNonRegistratoException{
            if(listaDipendenti.size() == 0) throw new ListaVuotaException("La lista è vuota");
            for(int i = 0; i < listaDipendenti.size(); i++){
                if(listaDipendenti.get(i).isEquals(Paziente)){
                    listaDipendenti.remove(i);
                    return;
                }
            }
            throw new PazienteNonRegistratoException("Paziente non registrato");
    }

    public void rimuoviPaziente(int pos) throws IndexOutOfBoundsException{
        try{
            listaDipendenti.remove(pos);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Errore 88");
            throw e;
        }
    }

    public void stampa(){
        for(Paziente d: listaDipendenti){
            d.stampa();
            System.out.println("\n");
        }
    }

    public Paziente find(Paziente p){
        boolean trovato = false;
        Paziente p2 = new Paziente();
        for(Paziente p1 : listaDipendenti){
            if(p1.isEquals(p)){
                p2 = p1;
                trovato = true;
                break;
            }
        }
        if(trovato) return p2;
        return null;
    }   

    public void serializza(String nomeFile){
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(nomeFile + ".bin"));
            output.writeObject(listaDipendenti);
            output.close();
        } catch (FileNotFoundException e){
            File file = new File(nomeFile + ".bin");
            try {
                file.createNewFile();
            } catch (IOException e1) {

            }
        } catch (IOException e) {
            System.out.println("104 Error: [IOException]");
        }
    }

    public void deserializza(String nomeFile){
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(nomeFile + ".bin"));
            try {
                Object obj = input.readObject();
                if(obj instanceof ArrayList<?>){
                    ArrayList<?> a = (ArrayList<?>) obj;
                    if(a.size() > 0){
                        for(Object o : a){
                            if(o instanceof Paziente)
                                listaDipendenti.add((Paziente) o);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {

            }
            input.close();
        } catch (FileNotFoundException e) {
            
        } catch (IOException e) {
            System.out.println("104 Error: [IOException]");
        }
    }
}
