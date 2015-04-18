/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.jsb.robomagsine;

/**
 *
 * @author Laurent Cardon <laurent.cardon@jsb.be>
 */
public class PasAssezDArgentException extends Exception {

    public PasAssezDArgentException() {
        super("Il n'y a plus assez d'argent disponible pour effectuer cette action");
    }
   
}
