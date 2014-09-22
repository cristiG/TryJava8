/*
 * Copyright (c) Visma Software
 * 
 */
package maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author cristian.gherghinesc
 */
public class Maps {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        merging();
        bimaps();
    }

    private static void bimaps() {
        System.out.println(" -    --------------- Bimaps - --------------- ");
        List<Person> persons = new ArrayList<>();
        readPersons(persons);
        Map<Integer, List<Person>> map = mapByAge(persons);

        Map<Integer, Map<String, List<Person>>> bimap = new HashMap<>();

        persons.forEach(
                person -> bimap.computeIfAbsent(
                        person.getAge(),
                        HashMap::new)
                .merge(
                        person.getGender(),
                        new ArrayList<>(Arrays.asList(person)),
                        (l1, l2) -> {
                            l1.addAll(l2);
                            return l1;
                        }
                )
        );

        System.out.println("Bimap: ");
        bimap.forEach((age, m) -> System.out.println(age + " -> " + m));

    }

    private static void merging() {
        System.out.println(" -    --------------- Merging - --------------- ");
        List<Person> persons = new ArrayList<>();
        readPersons(persons);

        persons.forEach(System.out::println);

        List<Person> list1 = persons.subList(0, 10);
        List<Person> list2 = persons.subList(10, persons.size());

        Map<Integer, List<Person>> m1 = mapByAge(list1);
        System.out.println("M1: ");
        m1.forEach((age, l) -> System.out.println(age + " : " + l));

        Map<Integer, List<Person>> m2 = mapByAge(list2);
        System.out.println("M2: ");
        m2.forEach((age, l) -> System.out.println(age + " : " + l));

        // Merge the second map into the first one, adding to the existing lists from the map
        m2.entrySet().stream().forEach(
                entry -> {
                    m1.merge(entry.getKey(), entry.getValue(),
                            (l1, l2) -> {
                                l1.addAll(l2);
                                return l1;
                            }
                    );
                }
        );

        System.out.println("M1 merged: ");
        m1.forEach((age, l) -> System.out.println(age + " : " + l));
    }

    private static void readPersons(List<Person> persons) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Maps.class.getResourceAsStream("people.txt")));
                Stream<String> stream = reader.lines()) {
            stream.map(line -> {
                String[] s = line.split(" ");
                Person p = new Person(s[0].trim(), Integer.parseInt(s[1]), s[2].trim());
                persons.add(p);
                return p;
            })
                    .count();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private static Map<Integer, List<Person>> mapByAge(List<Person> list) {
        Map<Integer, List<Person>> map = list.stream().collect(
                Collectors.groupingBy(Person::getAge));
        return map;
    }

}
