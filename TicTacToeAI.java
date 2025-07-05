import java.util.Scanner;

public class TicTacToeAI {
    static char[][] board = {
        {' ', ' ', ' '},
        {' ', ' ', ' '},
        {' ', ' ', ' '}
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🎮 Welcome to Tic-Tac-Toe!");
        System.out.println("You are 'X', AI is 'O'. You play first.");

        printBoard();

        while (true) {
            // Human move
            playerMove(scanner);
            printBoard();
            if (isGameOver('X')) break;

            // AI move
            System.out.println("🤖 AI is making a move...");
            aiMove();
            printBoard();
            if (isGameOver('O')) break;
        }

        scanner.close();
    }

    static void printBoard() {
        System.out.println("-------------");
        for (char[] row : board) {
            System.out.print("| ");
            for (char cell : row) {
                System.out.print(cell + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    static void playerMove(Scanner scanner) {
        while (true) {
            System.out.print("Enter your move (row and column: 1 1): ");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;

            if (isValidMove(row, col)) {
                board[row][col] = 'X';
                break;
            } else {
                System.out.println("❌ Invalid move. Try again.");
            }
        }
    }

    static boolean isValidMove(int row, int col) {
        return row >= 0 && col >= 0 && row < 3 && col < 3 && board[row][col] == ' ';
    }

    static boolean isGameOver(char player) {
        if (hasWon(player)) {
            System.out.println((player == 'X' ? "🎉 You win!" : "💻 AI wins!"));
            return true;
        } else if (isDraw()) {
            System.out.println("🤝 It's a draw!");
            return true;
        }
        return false;
    }

    static boolean hasWon(char player) {
        for (int i = 0; i < 3; i++) {
            // Rows and Columns
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player))
                return true;
        }
        // Diagonals
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
               (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    static boolean isDraw() {
        for (char[] row : board)
            for (char cell : row)
                if (cell == ' ') return false;
        return true;
    }

    static void aiMove() {
        int bestScore = Integer.MIN_VALUE;
        int moveRow = -1;
        int moveCol = -1;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    board[row][col] = 'O';
                    int score = minimax(false);
                    board[row][col] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        moveRow = row;
                        moveCol = col;
                    }
                }
            }
        }

        board[moveRow][moveCol] = 'O';
    }

    static int minimax(boolean isMaximizing) {
        if (hasWon('O')) return 1;
        if (hasWon('X')) return -1;
        if (isDraw()) return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    board[row][col] = isMaximizing ? 'O' : 'X';
                    int score = minimax(!isMaximizing);
                    board[row][col] = ' ';
                    bestScore = isMaximizing ?
                        Math.max(score, bestScore) :
                        Math.min(score, bestScore);
                }
            }
        }

        return bestScore;
    }
}
