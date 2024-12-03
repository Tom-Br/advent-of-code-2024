package be.tbrx;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {

    @Test
    void question1() throws IOException {
        // read input
        InputStream inputStream = getClass().getResourceAsStream("/day3/input.txt");
        String input = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        String regex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        List<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group());
        }

        Integer total = 0;

        for (String match : matches) {
            String[] split = match.split(",");

            Integer a = Integer.parseInt(split[0].substring(4));
            Integer b = Integer.parseInt(split[1].substring(0, split[1].length() - 1));
            total += a * b;
        }

        Assertions.assertThat(total).isEqualTo(166357705);

    }

    @Test
    void question2() throws IOException {

        // read input
        InputStream inputStream = getClass().getResourceAsStream("/day3/input.txt");
        String input = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        List<String> strings = extractValidMultiplications(input);

        Integer total = 0;
        for (String match : strings) {
            String[] split = match.split(",");

            Integer a = Integer.parseInt(split[0].substring(4));
            Integer b = Integer.parseInt(split[1].substring(0, split[1].length() - 1));
            total += a * b;
        }

        Assertions.assertThat(total).isEqualTo(88811886);

    }

    public List<String> extractValidMultiplications(String input) {
        List<String> matches = new ArrayList<>();
        boolean enabled = true;  // mul instructions start enabled

        // Regex for mul instructions and control instructions
        Pattern mulPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        Pattern controlPattern = Pattern.compile("(do|don't)\\(\\)");

        // Split input into segments at control instructions
        String[] segments = input.split("(do|don't)\\(\\)");
        int position = 0;

        // Process each segment
        for (int i = 0; i < segments.length; i++) {
            // Find mul instructions in this segment
            Matcher mulMatcher = mulPattern.matcher(segments[i]);
            while (mulMatcher.find()) {
                if (enabled) {
                    matches.add(mulMatcher.group());
                }
            }

            // Update position to find control instruction
            position += segments[i].length();

            // Find next control instruction if not at end
            if (i < segments.length - 1) {
                Matcher controlMatcher = controlPattern.matcher(input);
                controlMatcher.find(position);
                String control = controlMatcher.group();
                enabled = control.equals("do()");
                position += control.length();
            }
        }

        return matches;
    }
}
