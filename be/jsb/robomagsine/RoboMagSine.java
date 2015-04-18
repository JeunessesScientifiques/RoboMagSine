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
public class RoboMagSine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Usine usine = Usine.getInstance();
        
        try{
            usine.ajouterTresorerie(10000);
        }catch (PasAssezDArgentException e){
         
        }
        
        usine.setNom("Chez Lulu et Lulute :-)");
        usine.administratifs.add(new MainDOeuvreAdministrative(6,1));
        usine.administratifs.add(new MainDOeuvreAdministrative(6,5));
        
        DetailCommerceJFrame homePage = new DetailCommerceJFrame(); 
        homePage.setVisible(true);
    }
    
    
}
