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

    protected ArrayList<MainDOeuvreProduction> productions = new ArrayList();
    protected float paieHebdomadaireProduction = 1000;

    protected ArrayList<MainDOeuvreService> services = new ArrayList();
    protected float paieHebdomadaireService = 1600;

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

    abstract String getNomPourProduction();

    abstract String getNomPourService();

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

    public float getBonheurMoyenServices() {
        return this.getBonheurMoyen(this.services);
    }

    public float getBonheurMoyenProductions() {
        return this.getBonheurMoyen(this.productions);
    }

    private <T extends MainDOeuvre> float getBonheurMoyen(ArrayList<T> tab) {
        Iterator<T> it = tab.iterator();

        float bonheur = 0;

        while (it.hasNext()) {
            bonheur += it.next().getBonheur();
        }

        return Math.min(bonheur / (tab.size()), 10);
    }

    public float getProductivitéMoyenneAdministratifs() {
        return this.getProductivitéMoyenne(this.administratifs);
    }

    public float getProductivitéMoyenneProductions() {
        return this.getProductivitéMoyenne(this.productions);
    }

    public float getProductivitéMoyenneServices() {
        return this.getProductivitéMoyenne(this.services);
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

    public float getPaieHebdomadaireProduction() {
        return paieHebdomadaireProduction;
    }

    public float getPaieHebdomadaireService() {
        return paieHebdomadaireService;
    }

    public void augmenterPaieSalariésAdministratifs() {
        this.paieHebdomadaireAdministratif = this.deltaSalaire(this.paieHebdomadaireAdministratif, 1);
    }

    public void augmenterPaieSalariésServices() {
        this.paieHebdomadaireService = this.deltaSalaire(this.paieHebdomadaireService, 1);
    }

    public void augmenterPaieSalariésProductions() {
        this.paieHebdomadaireProduction = this.deltaSalaire(this.paieHebdomadaireProduction, 1);
    }

    public void diminuerPaieSalariésAdministratifs() {
        this.paieHebdomadaireAdministratif = this.deltaSalaire(this.paieHebdomadaireAdministratif, -1);
    }

    public void diminuerPaieSalariésServices() {
        this.paieHebdomadaireService = this.deltaSalaire(this.paieHebdomadaireService, -1);
    }

    public void diminuerPaieSalariésProductions() {
        this.paieHebdomadaireProduction = this.deltaSalaire(this.paieHebdomadaireProduction, -1);
    }

    private float deltaSalaire(float variableSalaire, int signe) {
        float deltaSalaire = this.augmentationSalaire;

        if (signe < 0) {
            deltaSalaire *= -1;
        }
        if (variableSalaire + deltaSalaire >= this.salaireMinimun) {
            variableSalaire += deltaSalaire;
        }

        return variableSalaire;
    }

    public ArrayList<MainDOeuvreAdministrative> getAdministratifs() {
        return administratifs;
    }

    public ArrayList<MainDOeuvreProduction> getProductions() {
        return productions;
    }

    public ArrayList<MainDOeuvreService> getServices() {
        return services;
    }

    public int getNombreAdministratifs() {
        return this.administratifs.size();
    }

    public int getNombreServices() {
        return this.services.size();
    }

    public int getNombreProductions() {
        return this.productions.size();
    }

    public void augmenterPersonnelAdministratif() {
        this.administratifs = this.augmenterSalariés(this.administratifs);
    }

    public void diminuerSalariésAdministratif() {
        this.administratifs = this.diminuerSalariées(this.administratifs);
    }

    public void augmenterPersonnelProduction() {
        this.productions = this.augmenterSalariés(this.productions);
    }

    public void diminuerSalariésProduction() {
        this.productions = this.diminuerSalariées(this.productions);
    }

    public void diminuerSalariésService() {
        this.services = this.diminuerSalariées(this.services);
    }

    public void augmenterPersonnelService() {
        this.services = this.augmenterSalariés(this.services);
    }

    private <T extends MainDOeuvre> ArrayList<T> augmenterSalariés(ArrayList<T> tab) {
        try {
            T test = (T) tab.get(0).getClass().newInstance();
            tab.add(test);
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Entreprise.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tab;
    }

    private <T extends MainDOeuvre> ArrayList<T> diminuerSalariées(ArrayList<T> tab) {
        if (tab.size() > 1) {
            Collections.sort(tab);
            tab.remove(tab.size() - 1);
        }
        return tab;
    }

}
