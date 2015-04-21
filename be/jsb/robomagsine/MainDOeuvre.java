/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.jsb.robomagsine;

/**
 *
 * @author laurent
 */
abstract class MainDOeuvre implements Temporalisable, Comparable {

       
    /* Indicateurs */
    protected float bonheur; //note entre 0 et 10
    protected float compétences; //notre entre 0 et 10
    
    public MainDOeuvre(float bonheur, float compétences) {
        this.bonheur = bonheur;
        this.compétences = compétences;
    }
    
    public MainDOeuvre(){
        this.bonheur = 6;
        this.compétences = (float) Math.random()*10;
    }
        
    /**
     * 
     * @return Retourne la productivité sur 10 d'une main d'oeuvre 
     */
    public float getProductivite(){
        
        return this.bonheur/2 + this.compétences/2;
    }
    
    @Override
    public void passageALaProchainePeriode(){
        this.compétences *= 1.20;
        this.bonheur *= 0.985;
    }

    public float getBonheur() {
        return bonheur;
    }

    public void setBonheur(float bonheur) {
        this.bonheur = bonheur;
    }

    public float getCompétences() {
        return compétences;
    }

    public void setCompétences(float compétences) {
        this.compétences = compétences;
    }

    @Override
    public int compareTo(Object t) {
        float competenceT = ((MainDOeuvre) t).getCompétences();
        float competenceThis = this.getCompétences();
        
        if(competenceT<competenceThis) return -1;
        else if(competenceT==competenceThis) return 0;
        else return 1;
        
    }
    
}
