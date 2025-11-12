package controller;

import model.*;
import storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {
    public static void main(String[] args) {
        Forestilling forestilling1 = lavForestilling("Skønheden og hunden",
                LocalDate.of(2025, 11, 1),
                LocalDate.of(2026, 3, 19));
        System.out.println(Storage.getForestillinger());

    }

    public static Forestilling lavForestilling(String navn, LocalDate startDato, LocalDate slutDato) {
        Forestilling forestilling = new Forestilling(navn, startDato, slutDato);
        Storage.addForestilling(forestilling);
        return forestilling;
    }

    public static Kunde lavKunde(String navn, String mobil) {
        Kunde kunde = new Kunde(navn, mobil);
        Storage.addKunde(kunde);
        return kunde;
    }

    public static Plads lavPlads(int række, int nr, int pris, PladsType pladsType) {
        Plads plads = new Plads(række, nr, pris, pladsType);
        Storage.addPlads(plads);
        return plads;
    }

    public static Bestilling opretBestillingMedPladser(Forestilling forestilling, Kunde kunde, LocalDate dato,
                                                       ArrayList<Plads> pladser) {
        if (forestilling.getStartDato().isBefore(dato.plusDays(1))
                && forestilling.getSlutDato().isAfter(dato.minusDays(1))
                && erAllePladserLedige(forestilling, pladser, dato)) {
            Bestilling bestilling = forestilling.opretBestilling(dato, kunde);

            for (Plads plads : pladser) {
                bestilling.addPlads(plads);
            }

            return  bestilling;
        }

        return null;
    }

    public static boolean erAllePladserLedige(Forestilling forestilling, ArrayList<Plads> pladser, LocalDate dato) {
        for (Plads plads : pladser) {
            if (!forestilling.erPladsLedig(plads.getRække(), plads.getNr(), dato)) {
                // Hvis en given plads ikke er ledig skal vi return false
                return false;
            }
        }
        // ellers return'er vi true
        return true;
    }
}
