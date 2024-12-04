package be.tbrx;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Day4 {

    @Test
    void question1() throws IOException {
        // read input
        InputStream inputStream = getClass().getResourceAsStream("/day4/input.txt");
        String input = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        //
        String[] lines = input.split("\n");
        char[][] array = Arrays.stream(lines).map(String::toCharArray).toArray(char[][]::new);

        int count = findWordCount(array, "XMAS");

        Assertions.assertThat(count).isEqualTo(2401);
    }

    @Test
    void question2() throws IOException {

        // read input
        InputStream inputStream = getClass().getResourceAsStream("/day4/input.txt");
        String input = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        String[] lines = input.split("\n");
        char[][] array = Arrays.stream(lines).map(String::toCharArray).toArray(char[][]::new);

        int xPatterns = findXPatterns(array);

        Assertions.assertThat(xPatterns).isEqualTo(1822);

    }

    // The eight possible directions to search
    private static final int[][] DIRECTIONS = {
            {-1, -1}, // northwest
            {-1, 0},  // north
            {-1, 1},  // northeast
            {0, -1},  // west
            {0, 1},   // east
            {1, -1},  // southwest
            {1, 0},   // south
            {1, 1}    // southeast
    };

    public static int findWordCount(char[][] grid, String word) {
        if (grid == null || grid.length == 0 || word == null || word.isEmpty()) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        // Try each cell as a starting point
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // For each direction
                for (int[] direction : DIRECTIONS) {
                    count += countWordsFromCell(grid, word, row, col, direction);
                }
            }
        }

        return count;
    }

    private static int countWordsFromCell(char[][] grid, String word,
                                          int startRow, int startCol,
                                          int[] direction) {
        int rows = grid.length;
        int cols = grid[0].length;
        int wordLength = word.length();

        // Check if the word can fit in this direction from the starting point
        int endRow = startRow + direction[0] * (wordLength - 1);
        int endCol = startCol + direction[1] * (wordLength - 1);

        if (endRow < 0 || endRow >= rows || endCol < 0 || endCol >= cols) {
            return 0;
        }

        // Check if the word matches in this direction
        for (int i = 0; i < wordLength; i++) {
            int currentRow = startRow + direction[0] * i;
            int currentCol = startCol + direction[1] * i;

            if (grid[currentRow][currentCol] != word.charAt(i)) {
                return 0;
            }
        }

        return 1;
    }

    public static int findXPatterns(char[][] grid) {
        if (grid == null || grid.length < 3 || grid[0].length < 3) {
            return 0;
        }

        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;

        // Check each possible center point for the X pattern
        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {
                // The center must be 'A'
                if (grid[row][col] != 'A') {
                    continue;
                }

                // Get the characters in each diagonal
                char topLeft = grid[row-1][col-1];
                char topRight = grid[row-1][col+1];
                char bottomLeft = grid[row+1][col-1];
                char bottomRight = grid[row+1][col+1];

                // Check all valid X patterns
                if (isValidXPattern(topLeft, topRight, bottomLeft, bottomRight)) {
                    count++;
                }
            }
        }

        return count;
    }

    private static boolean isValidXPattern(char topLeft, char topRight,
                                           char bottomLeft, char bottomRight) {
        // Check all possible valid combinations:
        // 1. MAS/MAS: M.S/.A./M.S
        // 2. SAM/SAM: S.M/.A./S.M
        // 3. MAS/SAM: M.M/.A./S.S
        // 4. SAM/MAS: S.S/.A./M.M

        // First diagonal (top-left to bottom-right)
        boolean firstDiagMAS = (topLeft == 'M' && bottomRight == 'S');
        boolean firstDiagSAM = (topLeft == 'S' && bottomRight == 'M');

        // Second diagonal (top-right to bottom-left)
        boolean secondDiagMAS = (topRight == 'M' && bottomLeft == 'S');
        boolean secondDiagSAM = (topRight == 'S' && bottomLeft == 'M');

        return (firstDiagMAS && secondDiagMAS) ||    // MAS/MAS
                (firstDiagSAM && secondDiagSAM) ||    // SAM/SAM
                (firstDiagMAS && secondDiagSAM) ||    // MAS/SAM
                (firstDiagSAM && secondDiagMAS);      // SAM/MAS
    }


}
