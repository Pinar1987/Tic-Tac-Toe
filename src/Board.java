public class Board {
    // A 3x3 array to represent the board. Initialized with spaces.
    private char[][] grid = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };

    // Size of the board (3x3)
    private static final int SIZE = 3;

    /**
     * Resets the board to an empty state.
     */
    public void reset() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    /**
     * Draws the current state of the board to the console.
     */
    public void draw() {
        // Clear screen (optional and platform-dependent, but good for cleanliness)
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("\n\t--- Tic Tac Toe ---\n");
        for (int i = 0; i < SIZE; i++) {
            // Print the cells
            System.out.print(" ");
            for (int j = 0; j < SIZE; j++) {
                // If cell is empty, print the corresponding number (1-9) for guidance
                // Otherwise, print 'X' or 'O'
                if (grid[i][j] == ' ') {
                    System.out.print(i * SIZE + j + 1);
                } else {
                    System.out.print(grid[i][j]);
                }

                if (j < SIZE - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();

            // Print the horizontal separators
            if (i < SIZE - 1) {
                System.out.println("---+---+---");
            }
        }
        System.out.println();
    }


    public boolean placeMove(int position, char symbol) {
        // Map 1-9 to 2D array indices (row, col)
        // position - 1 gives 0-8.
        // row = (position - 1) / SIZE
        // col = (position - 1) % SIZE
        int row = (position - 1) / SIZE;
        int col = (position - 1) % SIZE;

        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            // Should not happen if input validation is done in TicTacToe class
            return false;
        }

        if (grid[row][col] == ' ') {
            grid[row][col] = symbol;
            return true;
        }
        return false; // Position is already taken
    }

    /**
     * Checks if the given player symbol has won.
     * @param symbol The symbol ('X' or 'O') to check for a win.
     * @return true if the symbol has three in a row, false otherwise.
     */
    public boolean checkWin(char symbol) {
        // Check rows and columns
        for (int i = 0; i < SIZE; i++) {
            // Check row i
            if (grid[i][0] == symbol && grid[i][1] == symbol && grid[i][2] == symbol) {
                return true;
            }
            // Check column i
            if (grid[0][i] == symbol && grid[1][i] == symbol && grid[2][i] == symbol) {
                return true;
            }
        }

        // Check diagonals
        // Top-left to bottom-right
        if (grid[0][0] == symbol && grid[1][1] == symbol && grid[2][2] == symbol) {
            return true;
        }

        // Top-right to bottom-left
        if (grid[0][2] == symbol && grid[1][1] == symbol && grid[2][0] == symbol) {
            return true;
        }

        return false;
    }

    // check if board is full//
    public boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] == ' ') {
                    return false; // Found an empty spot
                }
            }
        }
        return true; // Board is full
    }
}