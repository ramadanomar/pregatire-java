import java.io.*;
import java.util.*;

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

        HashMap<String, List<Achizitie>> map = new HashMap<>();
        for (Achizitie achizitie : achizitii) {
            map.computeIfAbsent(achizitie.getCod(), k -> new ArrayList<>()).add(achizitie);
        }

        map.entrySet().stream()
                .sorted(Map.Entry.<String, List<Achizitie>>comparingByValue(
                        Comparator.comparingDouble(list -> -list.stream().mapToDouble(Achizitie::valoare).sum())))
                .forEach(entry -> {
                    double totalValue = entry.getValue().stream().mapToDouble(Achizitie::valoare).sum();
                    System.out.println("Produs " + entry.getKey() + " -> " + entry.getValue().size() +
                            " achiziții, valoare totală " + totalValue + " Lei");
                });
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("produseFrecvente.dat"))) {
            for (Map.Entry<String, List<Achizitie>> entry : map.entrySet()) {
                if (entry.getValue().size() > 3) {
                    oos.writeObject(entry.getKey());
                    oos.writeInt(entry.getValue().size());
                    oos.writeDouble(entry.getValue().stream().mapToDouble(Achizitie::valoare).sum());
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
