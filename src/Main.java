import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Achizitie> achizitii = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("achizitii.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Achizitie achizitie = new Achizitie(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]), Float.parseFloat(parts[5]));
                achizitii.add(achizitie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Afisarea achizitiilor din prima jumatate a lunii cu cantitate > 100
        achizitii.stream()
                .filter(a -> a.getZi() <= 15 && a.getCantitate() > 100)
                .forEach(System.out::println);

        // Agregarea datelor pe produse folosind Collectors
        Map<String, Double> produsValoareTotala = achizitii.stream()
                .collect(Collectors.groupingBy(
                        Achizitie::getCod,
                        Collectors.summingDouble(Achizitie::valoare)
                ));

        List<Map.Entry<String, Double>> sortedEntries = new ArrayList<>(produsValoareTotala.entrySet());
        sortedEntries.sort(Map.Entry.<String, Double>comparingByValue().reversed());


        // Afișare sortată
        sortedEntries.forEach(entry -> {
            long numAchizitii = achizitii.stream().filter(a -> a.getCod().equals(entry.getKey())).count();
            System.out.println("Produs " + entry.getKey() + " -> " + numAchizitii +
                    " achiziții, valoare totală " + entry.getValue() + " Lei");
        });

        // Serializarea produselor frecvente folosind un nou map cu numărul de achiziții
        Map<String, Long> produsNumarAchizitii = achizitii.stream()
                .collect(Collectors.groupingBy(
                        Achizitie::getCod,
                        Collectors.counting()
                ));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("produseFrecvente.dat"))) {
            for (Map.Entry<String, Long> entry : produsNumarAchizitii.entrySet()) {
                if (entry.getValue() > 3) {
                    oos.writeObject(entry.getKey());
                    oos.writeInt(entry.getValue().intValue());
                    oos.writeDouble(produsValoareTotala.get(entry.getKey()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("produseFrecvente.dat"))) {
            while (true) {
                try {
                    String cod = (String) ois.readObject();
                    int numTransactions = ois.readInt();
                    double totalValue = ois.readDouble();
                    System.out.println("Produs " + cod + " -> " + numTransactions + " achiziții, valoare totală " + totalValue + " Lei");
                } catch (EOFException e) {
                    break; // End of file
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
