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

        String gameMode = "";
        do {
            System.out.print("Do you want to play against another player or the computer? (player/computer): ");
            gameMode = scanner.nextLine().trim().toLowerCase();
        } while (!gameMode.equals("player") && !gameMode.equals("computer"));

        // Player X
        String nameX;
        do {
            System.out.print("Player X, please enter your name: ");
            nameX = scanner.nextLine().trim();
        } while (nameX.isEmpty());
        this.playerX = new Player(nameX, 'X');

        if (gameMode.equals("player")) {
            // Player O
            String nameO;
            do {
                System.out.print("Player O, please enter your name: ");
                nameO = scanner.nextLine().trim();
            } while (nameO.isEmpty() || nameO.equalsIgnoreCase(nameX));
            this.playerO = new Player(nameO, 'O');
        } else {
            // Computer O
            this.playerO = new ComputerPlayer('O');
        }

        System.out.printf("\n%s will be 'X' and %s will be 'O'. Let the game begin!\n", playerX.getName(), playerO.getName());



    }

    //gameloop
    public void start() {
        boolean keepPlaying = true;

        setupPlayers();

        while (keepPlaying) {
            currentPlayer = playerX;
            playGame();

            System.out.println("\n--- Current Score ---");
            System.out.printf("%s (X) has %d wins.\n", playerX.getName(), playerX.getWins());
            System.out.printf("%s (O) has %d wins.\n", playerO.getName(), playerO.getWins());
            System.out.println("---------------------\n");


            String response;
            do {
                System.out.print("Do you want to play another game? (yes/no): ");
                response = scanner.nextLine().toLowerCase().trim();
            } while (!response.equals("yes") && !response.equals("no"));

            if (response.equals("no")) {
                keepPlaying = false;
            } else {
                board.reset();
            }
        }

        System.out.println("\nThank you for playing! Final scores:");
        System.out.printf("%s: %d wins | %s: %d wins\n", playerX.getName(), playerX.getWins(), playerO.getName(), playerO.getWins());

        scanner.close();
    }


    private void playGame() {
        boolean gameFinished = false;

        while (!gameFinished) {
            board.draw();

            int move;

            if (currentPlayer instanceof ComputerPlayer) {
                System.out.printf("%s (%c) is making a move...\n", currentPlayer.getName(), currentPlayer.getSymbol());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                move = ((ComputerPlayer) currentPlayer).chooseMove(board);
                System.out.printf("Computer chooses position %d\n", move);
            } else {
                move = getPlayerMove();
            }

            boolean successfulMove = board.placeMove(move, currentPlayer.getSymbol());

            while (!successfulMove) {
                if (currentPlayer instanceof ComputerPlayer) {
                    move = ((ComputerPlayer) currentPlayer).chooseMove(board);
                } else {
                    System.out.println("That position is already taken or invalid. Try again.");
                    move = getPlayerMove();
                }
                successfulMove = board.placeMove(move, currentPlayer.getSymbol());
            }

            if (board.checkWin(currentPlayer.getSymbol())) {
                board.draw();
                System.out.printf("\n*** Congratulations, %s! You won the game! ***\n", currentPlayer.getName());
                currentPlayer.incrementWins();
                gameFinished = true;
                break;
            }

            if (board.isFull()) {
                board.draw();
                System.out.println("\n*** It's a draw! The board is full. ***");
                gameFinished = true;
                break;
            }

            currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
        }
    }


    private int getPlayerMove() {
        int move = -1;
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.printf("%s (%c), enter a box number (1-9) to place your move: ", currentPlayer.getName(), currentPlayer.getSymbol());

                // Check if the next input is an integer
                if (scanner.hasNextInt()) {
                    move = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline

                    if (move >= 1 && move <= 9) {

                        isValid = true;
                    } else {
                        System.out.println("Invalid input. Please enter a number between 1 and 9.");
                    }
                } else {
                    // Handle non-integer input (check)
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine();
                }
            } catch (Exception e) {
                // Catch any unexpected exceptions and clear scanner
                System.out.println("An unexpected error comes upp. Try again.");
                scanner.nextLine();
            }
        }
        return move;
    }

    // main method to run programmet//
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.start();
    }
}