/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.jsb.robomagsine;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author laurent
 */
abstract class Entreprise implements Temporalisable {
    
    /* Main d'oeuvre */
    protected ArrayList<MainDOeuvreAdministrative> administratifs = new ArrayList();
    protected float paieHebdomadaireAdministratif = 1350; 
    
    /* Gestion du capital */
    protected float tresorerie = 0;
    
    /** Indicateurs financiers */
    protected float beneficeDernièrePeriode;
    protected float salairesAdministratifs;
    
    /* Gestion des inputs/outputs */
    
    /* Configuration spaciale */
    protected int surfaceDisponible;
    
    /* Variables propres au jeu */
    protected int proprietaire;
    protected String nom;
    
    
    public void passageALaProchainePeriode(){
        
        this.calculsDePassage();
        this.passageALaProchainePerdiodeMainDOeuvre(this.administratifs);
               
    }
    
    private <T extends Temporalisable> void passageALaProchainePerdiodeMainDOeuvre(ArrayList<T> tab){
        
        
        Iterator<T> it = tab.iterator();
        
        while(it.hasNext()){
            T temp = it.next();
            temp.passageALaProchainePeriode();
        }     
    }
    
    private void calculsDePassage(){
        int nbrAdministratif = this.administratifs.size();
        this.salairesAdministratifs = nbrAdministratif * this.paieHebdomadaireAdministratif;
        
    }
    
    public float getBonheurMoyenAdministratifs(){
        return this.getBonheurMoyen(this.administratifs);
    }
    
    private <T extends MainDOeuvre> float getBonheurMoyen(ArrayList<T> tab){
        Iterator<T> it  = tab.iterator();
        
        float bonheur = 0;
        
        while(it.hasNext()){
            bonheur += it.next().getBonheur();
        }
        
        return Math.min(bonheur/(tab.size()),10);
    }

    public float getTresorerie() {
        return tresorerie;
    }

    public void ajouterTresorerie(float montant) throws PasAssezDArgentException{
        
        if(montant<0){
            retirerTresorerie(Math.abs(montant));
        }else{
            this.tresorerie += montant;
        }
        
    }
    
    public void retirerTresorerie(float montant) throws PasAssezDArgentException
    {    
        if(montant>=this.tresorerie){
            throw new PasAssezDArgentException();
        }else{
            this.tresorerie -= montant;
        }
        
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPaieHebdomadaireAdministratif() {
        return paieHebdomadaireAdministratif;
    }

    public void setPaieHebdomadaireAdministratif(float paieHebdomadaireAdministratif) {
        this.paieHebdomadaireAdministratif = paieHebdomadaireAdministratif;
    }
    
    
    
}
