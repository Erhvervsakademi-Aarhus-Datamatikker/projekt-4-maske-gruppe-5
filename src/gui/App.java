package gui;

import controller.Controller;
import model.PladsType;
import storage.Storage;

import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        initStorage();
        testPrint();
    }

    public static void initStorage() {
        Controller.lavForestilling("Evita", LocalDate.of(2023, 8, 10), LocalDate.of(2023, 8, 20));
        Controller.lavForestilling("Lykke Per", LocalDate.of(2023, 9, 1), LocalDate.of(2023, 9, 10));
        Controller.lavForestilling("Chess", LocalDate.of(2023, 8, 21), LocalDate.of(2023, 8, 30));

        Controller.lavKunde("Anders Hansen", "11223344");
        Controller.lavKunde("Peter Jensen", "12345678");
        Controller.lavKunde("Niels Madsen", "12341234");

        for (int row = 1; row <= 15; row++) {
            for (int seat = 1; seat <= 20; seat++) {

                if (row <= 5 && seat >= 3 && seat <= 18) { // _*_ Gule sæder _*_
                    Controller.lavPlads(row, seat, 500, PladsType.STANDARD);
                } else if (row <= 5 || ((row >= 6 && row <= 10) && seat >= 3 && seat <= 18)) { // _*_ Grønne sæder _*_
                    if (seat >= 8 && seat <= 12 && row == 10) { // Grønnne kørestol pladser
                        Controller.lavPlads(row, seat, 450, PladsType.KØRESTOL);
                    } else { // Grønne normale pladser
                        Controller.lavPlads(row, seat, 450, PladsType.STANDARD);
                    }
                } else { // _*_ Blå sæder _*_
                    if (seat >= 8 && seat <= 12 && row == 11) { // Blå ekstra benplads
                        Controller.lavPlads(row, seat, 400, PladsType.EKSTRABEN);
                    } else { // Blå normale pladser
                        Controller.lavPlads(row, seat, 400, PladsType.STANDARD);
                    }
                }
            }
        }
    }

    public static void testPrint(){
        System.out.println("Alle Forestillinger:");
        System.out.println(Storage.getForestillinger());

        System.out.println('\n');
        System.out.println("Alle Kunder: ");
        System.out.println(Storage.getKunder());

        System.out.println('\n');
        System.out.println("Alle Pladser: ");
        System.out.println(Storage.getPladser());
    }
}
