import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer extends Player {
    private Random random;

    public ComputerPlayer(char symbol) {
        super("Computer", symbol);
        this.random = new Random();
    }

    public int chooseMove(Board board) {
        List<Integer> availableMoves = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            if (board.isPositionEmpty(i)) {
                availableMoves.add(i);
            }
        }

        if (availableMoves.isEmpty()) {
            return -1;
        }


        return availableMoves.get(random.nextInt(availableMoves.size()));
    }
}
