package be.tbrx;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

// The unusual data (your puzzle input) consists of many reports, one report per line.
// Each report is a list of numbers called levels that are separated by spaces.

public class Day2 {

    // The engineers are trying to figure out which reports are safe. The Red-Nosed reactor safety systems can only tolerate levels that are either gradually increasing or gradually decreasing. So, a report only counts as safe if both of the following are true:
    //
    //The levels are either all increasing or all decreasing.
    //Any two adjacent levels differ by at least one and at most three.

    @Test
    void question1() throws IOException {
        // read input
        InputStream inputStream = getClass().getResourceAsStream("/day2/input.txt");
        String input = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        Integer totalSafeReports = 0;
        for (String report : input.split("\n")) {
            String[] reportNumbers = report.split("\\s+");
            List<Integer> list = Arrays.stream(reportNumbers).map(Integer::parseInt).toList();
            boolean reportSafe = isReportSafe(list);
            if(reportSafe) {
                totalSafeReports++;
            }
        }

        assertThat(totalSafeReports).isEqualTo(442);

    }

    @Test
    void question2() throws IOException {
        // read input
        InputStream inputStream = getClass().getResourceAsStream("/day2/input.txt");
        String input = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        Integer totalSafeReports = 0;
        for (String report : input.split("\n")) {
            String[] reportNumbers = report.split("\\s+");
            List<Integer> list = Arrays.stream(reportNumbers).map(Integer::parseInt).toList();
            boolean reportSafe = isReportSafeImproved(list);
            if(reportSafe) {
                totalSafeReports++;
            }
        }

        assertThat(totalSafeReports).isEqualTo(493);

    }

    public boolean isReportSafe(List<Integer> levels) {
        // Need at least 2 levels to check
        if (levels.size() < 2) {
            return true;
        }

        // First check if increasing or decreasing by looking at first two numbers
        boolean isIncreasing = levels.get(1) > levels.get(0);

        // Check each adjacent pair
        for (int i = 0; i < levels.size() - 1; i++) {
            int current = levels.get(i);
            int next = levels.get(i + 1);
            int difference = Math.abs(next - current);

            // Check if difference is within bounds (1-3)
            if (difference < 1 || difference > 3) {
                return false;
            }

            // Check if direction matches initial direction
            if (isIncreasing && next <= current) {
                return false;
            }
            if (!isIncreasing && next >= current) {
                return false;
            }
        }

        return true;
    }

    public boolean isReportSafeImproved(List<Integer> levels) {
        // If original report is safe, return true
        if (isDirectReportSafe(levels)) {
            return true;
        }

        // If not safe, try removing each level one at a time
        for (int i = 0; i < levels.size(); i++) {
            List<Integer> modifiedLevels = new ArrayList<>(levels);
            modifiedLevels.remove(i);

            if (isDirectReportSafe(modifiedLevels)) {
                return true;
            }
        }

        return false;
    }

    private boolean isDirectReportSafe(List<Integer> levels) {
        // Need at least 2 levels to check
        if (levels.size() < 2) {
            return true;
        }

        // First check if increasing or decreasing
        boolean isIncreasing = levels.get(1) > levels.get(0);

        // Check each adjacent pair
        for (int i = 0; i < levels.size() - 1; i++) {
            int current = levels.get(i);
            int next = levels.get(i + 1);
            int difference = Math.abs(next - current);

            // Check if difference is within bounds (1-3)
            if (difference < 1 || difference > 3) {
                return false;
            }

            // Check if direction matches initial direction
            if (isIncreasing && next <= current) {
                return false;
            }
            if (!isIncreasing && next >= current) {
                return false;
            }
        }

        return true;
    }
}
