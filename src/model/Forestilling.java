package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Forestilling {
    private String navn;
    private LocalDate startDato;
    private LocalDate slutDato;
    private ArrayList<Bestilling> bestillinger = new ArrayList<>();

    public Forestilling(String navn, LocalDate startDato, LocalDate slutDato) {
        this.navn = navn;
        this.startDato = startDato;
        this.slutDato = slutDato;
    }

    public Bestilling opretBestilling(LocalDate dato, Kunde kunde) {
        Bestilling bestilling = new Bestilling(dato, this, kunde);
        bestillinger.add(bestilling);
        return bestilling;
    }

    public Boolean erPladsLedig(int række, int nummer, LocalDate dato) {

        for (Bestilling bestilling : bestillinger) { // Tjekker alle bestillinger
            if (bestilling.getDato().equals(dato)){ // Tjekker om datoen er den rigtige, hvis ja så videre
                for (Plads plads : bestilling.getPladser()) { // Tjekker alle pladser i den givne bestilling
                    if (række == plads.getRække() && nummer == plads.getNr()) { // Tjekker om pladserne er taget
                        // Hvis en af pladserne er taget return false for at sige at pladserne ikke er ledige
                        return false;
                    }
                }
            }
        }
        // return'er true hvis pladserne ikke er taget
        return true;
    }

    public String getNavn() {
        return navn;
    }

    public LocalDate getStartDato() {
        return startDato;
    }

    public LocalDate getSlutDato() {
        return slutDato;
    }

    public ArrayList<Bestilling> getBestillinger() {
        return bestillinger;
    }

    @Override
    public String toString() {
        return navn + " (fra " + startDato + " til " + slutDato + ")";
    }
}
