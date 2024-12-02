package be.tbrx;

// Throughout the Chief's office, the historically significant locations are listed not by name but by a unique number called the location ID.
// To make sure they don't miss anything, The Historians split into two groups, each searching the office and trying to create their own complete list of location IDs.
//
// There's just one problem: by holding the two lists up side by side (your puzzle input), it quickly becomes clear that the lists aren't very similar.
// Maybe you can help The Historians reconcile their lists?

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class Day1 {

    @Test
    void question1() throws IOException {
        // read input
        InputStream inputStream = getClass().getResourceAsStream("/day1/input.txt");
        String input = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);


        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (String line : input.split("\n")) {
            String[] split = line.split("\\s+");
            left.add(Integer.parseInt(split[0]));
            right.add(Integer.parseInt(split[1]));
        }

        // sort the lists
        left.sort(null);
        right.sort(null);

        Integer result = 0;
        for (int i = 0; i < left.size(); i++) {
            Integer minimumLeft = left.get(i);
            Integer minimumRight = right.get(i);

            result += Math.abs(minimumLeft - minimumRight);
        }

        // find the missing number
        assertThat(result).isEqualTo(2000468);
    }

    //a lot of location IDs appear in both lists! Maybe the other numbers aren't location IDs at all but rather misinterpreted handwriting.
    // Calculate a total similarity score by adding up each number in the left list after multiplying it by the number of times that number appears in the right list.

    @Test
    void question2() throws IOException {
        // read input
        InputStream inputStream = getClass().getResourceAsStream("/day1/input.txt");
        String input = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (String line : input.split("\n")) {
            String[] split = line.split("\\s+");
            left.add(Integer.parseInt(split[0]));
            right.add(Integer.parseInt(split[1]));
        }

        Map<Integer, Long> lookupCounts = right.stream()
                .collect(Collectors.groupingBy(
                        number -> number,
                        Collectors.counting()
                ));


        Long total = left.stream()
                .mapToLong(number -> number * lookupCounts.getOrDefault(number, 0L))
                .sum();

        assertThat(total).isEqualTo(31);

    }
}
