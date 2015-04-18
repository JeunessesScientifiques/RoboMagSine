/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.jsb.robomagsine;

import java.util.ArrayList;

/**
 *
 * @author laurent
 */
public class Usine extends Entreprise {
  
    private static Usine instance = null;
    
    private void Usine(){
    }
    
    public static Usine getInstance(){
        if(instance==null){
            instance = new Usine();
        }
        
        return instance;
    }

    public ArrayList<MainDOeuvreAdministrative> getAdministratifs() {
        return administratifs;
    }

    public void addAdministratif(MainDOeuvreAdministrative administratif) {
        this.administratifs.add(administratif);
    }
    
    public void removeAdministratif(MainDOeuvreAdministrative administratif){
        this.administratifs.remove(administratif);
    }
    
    public int getNombreAdministratifs(){
        return this.administratifs.size();
    }
    
}
