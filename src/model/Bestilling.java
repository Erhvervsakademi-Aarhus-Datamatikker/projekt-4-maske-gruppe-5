package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bestilling {
    private LocalDate dato;
    private Forestilling forestilling;
    private Kunde kunde;
    private ArrayList<Plads> pladser  = new ArrayList<>();

    protected Bestilling(LocalDate dato, Forestilling forestilling, Kunde kunde ) {
        this.dato = dato;
        this.forestilling = forestilling;
        this.kunde = kunde;
    }

    public void addPlads(Plads plads) {
        pladser.add(plads);
    }

    public LocalDate getDato() {
        return dato;
    }

    public Forestilling getForestilling() {
        return forestilling;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public ArrayList<Plads> getPladser() {
        return pladser;
    }
}
