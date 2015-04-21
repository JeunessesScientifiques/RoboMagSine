/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.jsb.robomagsine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laurent
 */
abstract class Entreprise implements Temporalisable {

    /* Paramètrage */
    private float augmentationSalaire = 50;
    private float salaireMinimun = 500;

    /* Main d'oeuvre */
    protected ArrayList<MainDOeuvreAdministrative> administratifs = new ArrayList();
    protected float paieHebdomadaireAdministratif = 1350;

    /* Gestion du capital */
    protected float tresorerie = 0;

    /**
     * Indicateurs financiers
     */
    protected float beneficeDernièrePeriode;
    protected float salairesAdministratifs;

    /* Gestion des inputs/outputs */
    /* Configuration spaciale */
    protected int surfaceDisponible;

    /* Variables propres au jeu */
    protected int proprietaire;
    protected String nom;

    public void passageALaProchainePeriode() {

        this.calculsDePassage();
        this.passageALaProchainePerdiodeMainDOeuvre(this.administratifs);

    }

    private <T extends Temporalisable> void passageALaProchainePerdiodeMainDOeuvre(ArrayList<T> tab) {

        Iterator<T> it = tab.iterator();

        while (it.hasNext()) {
            T temp = it.next();
            temp.passageALaProchainePeriode();
        }
    }

    private void calculsDePassage() {
        int nbrAdministratif = this.administratifs.size();
        this.salairesAdministratifs = nbrAdministratif * this.paieHebdomadaireAdministratif;

    }

    public float getBonheurMoyenAdministratifs() {
        return this.getBonheurMoyen(this.administratifs);
    }

    private <T extends MainDOeuvre> float getBonheurMoyen(ArrayList<T> tab) {
        Iterator<T> it = tab.iterator();

        float bonheur = 0;

        while (it.hasNext()) {
            bonheur += it.next().getBonheur();
        }

        return Math.min(bonheur / (tab.size()), 10);
    }
    
    public float getProductivitéMoyenneAdministratifs(){
        return this.getProductivitéMoyenne(this.administratifs);
    }
    
    private <T extends MainDOeuvre> float getProductivitéMoyenne(ArrayList<T> tab) {
        Iterator<T> it = tab.iterator();

        float productivité = 0;

        while (it.hasNext()) {
            productivité += it.next().getProductivite();
        }

        return Math.min(productivité / (tab.size()), 10);
    }

    public float getTresorerie() {
        return tresorerie;
    }

    public void ajouterTresorerie(float montant) throws PasAssezDArgentException {

        if (montant < 0) {
            retirerTresorerie(Math.abs(montant));
        } else {
            this.tresorerie += montant;
        }

    }

    public void retirerTresorerie(float montant) throws PasAssezDArgentException {
        if (montant >= this.tresorerie) {
            throw new PasAssezDArgentException();
        } else {
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

    public void augmenterPaieHebdomadaireAdministratif() {
        this.paieHebdomadaireAdministratif = this.deltaSalaire(this.paieHebdomadaireAdministratif, this.getNombreAdministratifs(), 1);
    }

    public void diminuerPaieHebdomadaireAdministratif() {
        this.paieHebdomadaireAdministratif = this.deltaSalaire(this.paieHebdomadaireAdministratif, this.getNombreAdministratifs(), -1);
    }

    private float deltaSalaire(float variableSalaire, int nbrSalariés, int signe) {
        float deltaSalaire = this.augmentationSalaire;

        if (signe < 0) {
            deltaSalaire *= -1;
        }
        if (variableSalaire + deltaSalaire >= this.salaireMinimun) {
            try {
                this.retirerTresorerie(nbrSalariés * deltaSalaire);
                variableSalaire += deltaSalaire;
            } catch (PasAssezDArgentException e) {
                System.out.print(e.getMessage());
            }
        }

        return variableSalaire;
    }

    public ArrayList<MainDOeuvreAdministrative> getAdministratifs() {
        return administratifs;
    }

    public void addAdministratif(MainDOeuvreAdministrative administratif) {
        this.administratifs.add(administratif);
    }

    public void removeAdministratif(MainDOeuvreAdministrative administratif) {
        this.administratifs.remove(administratif);
    }

    public int getNombreAdministratifs() {
        return this.administratifs.size();
    }

    public void augmenterPersonnelAdministratif() {
        this.administratifs = this.augmenterSalariés(this.administratifs, this.paieHebdomadaireAdministratif);
    }
    
    public void diminuerSalariésAdministratif(){
        this.administratifs = this.diminuerSalariées(this.administratifs);
    }

    private <T extends MainDOeuvre> ArrayList<T> augmenterSalariés(ArrayList<T> tab, float salaire) {
        try {
            this.retirerTresorerie(salaire);
            T test = (T) tab.get(0).getClass().newInstance();
            tab.add(test);
        } catch (InstantiationException | IllegalAccessException | PasAssezDArgentException ex) {
            Logger.getLogger(Entreprise.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tab;
    }
    
    private <T extends MainDOeuvre> ArrayList<T> diminuerSalariées(ArrayList<T> tab){
        Collections.sort(tab);
        tab.remove(tab.size()-1);
        return tab;
    }

}
