import java.util.Scanner;

public class TicTacToe {
    private Board board;
    private Player playerX;
    private Player playerO;
    private Scanner scanner;
    private Player currentPlayer;

    public TicTacToe() {
        this.board = new Board();
        this.scanner = new Scanner(System.in);
    }


    private void setupPlayers() {
        System.out.println("Welcome to Tic Tac Toe!");

        // Get name for Player X
        String nameX = "";
        do {
            System.out.print("Player X, please enter your name: ");
            nameX = scanner.nextLine().trim();
            if (nameX.isEmpty()) {
                System.out.println("Name cannot be empty. Please try again.");
            }
        } while (nameX.isEmpty());
        this.playerX = new Player(nameX, 'X');

        // Get name for Player O
        String nameO = "";
        do {
            System.out.print("Player O, please enter your name: ");
            nameO = scanner.nextLine().trim();
            if (nameO.isEmpty()) {
                System.out.println("Name cannot be empty. Please try again.");
            } else if (nameO.equalsIgnoreCase(nameX)) {
                System.out.println("Name cannot be the same as Player X. Please choose another name.");
            }
        } while (nameO.isEmpty() || nameO.equalsIgnoreCase(nameX));
        this.playerO = new Player(nameO, 'O');

        System.out.printf("\n%s will be 'X' and %s will be 'O'. Let the game begin!\n", playerX.getName(), playerO.getName());
    }

    /**
     * Starts the main game loop.
     */
    public void start() {
        setupPlayers();
        currentPlayer = playerX; // X always starts the first game

        boolean keepPlaying = true;
        while (keepPlaying) {
            playGame();

            // Print current score
            System.out.println("\n--- Current Score ---");
            System.out.printf("%s (X) has %d wins.\n", playerX.getName(), playerX.getWins());
            System.out.printf("%s (O) has %d wins.\n", playerO.getName(), playerO.getWins());
            System.out.println("---------------------\n");

            // Ask to play another round
            String response;
            do {
                System.out.print("Do you want to play another game? (yes/no): ");
                response = scanner.nextLine().toLowerCase().trim();
            } while (!response.equals("yes") && !response.equals("no"));

            if (response.equals("no")) {
                keepPlaying = false;
            } else {
                // Alternate who starts the next game
                currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
                board.reset();
            }
        }

        System.out.println("\nThank you for playing! Final scores:");
        System.out.printf("%s: %d wins | %s: %d wins\n",
                playerX.getName(), playerX.getWins(),
                playerO.getName(), playerO.getWins());
        scanner.close();
    }

    private void playGame() {
        boolean gameFinished = false;

        while (!gameFinished) {
            board.draw();

            int move = getPlayerMove();

            // Place the move and check if successful
            boolean successfulMove = board.placeMove(move, currentPlayer.getSymbol());

            // Check if the cells taken //
            while (!successfulMove) {
                System.out.println("That position is already taken or invalid. Try again.");
                move = getPlayerMove();
                successfulMove = board.placeMove(move, currentPlayer.getSymbol());
            }

            // Check for Win
            if (board.checkWin(currentPlayer.getSymbol())) {
                board.draw();
                System.out.printf("\n*** Congratulations, %s! You won the game! ***\n", currentPlayer.getName());
                currentPlayer.incrementWins();
                gameFinished = true;
                break;
            }

            // Check for Draw
            if (board.isFull()) {
                board.draw();
                System.out.println("\n*** It's a draw! The board is full. ***");
                gameFinished = true;
                break;
            }

            // Switch player for the next turn
            currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
        }
    }


     //input validation error handling
     // @return The valid move position (1-9).
     //
    private int getPlayerMove() {
        int move = -1;
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.printf("%s (%c), enter a box number (1-9) to place your move: ",
                        currentPlayer.getName(), currentPlayer.getSymbol());

                // Check if the next input is an integer
                if (scanner.hasNextInt()) {
                    move = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline

                    if (move >= 1 && move <= 9) {
                        // Check if the spot is actually empty (Board method handles this internally,
                        isValid = true;
                    } else {
                        System.out.println("Invalid input. Please enter a number between 1 and 9.");
                    }
                } else {
                    // Handle non-integer input (check)
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // Consume the invalid input
                }
            } catch (Exception e) {
                // Catch any unexpected exceptions and clear scanner
                System.out.println("An unexpected error occurred. Try again.");
                scanner.nextLine();
            }
        }
        return move;
    }

    /**
     * Main method to run the application.
     */
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.start();
    }
}