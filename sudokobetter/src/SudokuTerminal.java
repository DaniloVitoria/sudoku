import java.util.Scanner;

public class SudokuTerminal {
    private static final int SIZE = 9;
    private int[][] board;
    private boolean[][] fixed; // marca as posições que já vêm preenchidas

    public SudokuTerminal(int[][] initialBoard) {
        board = new int[SIZE][SIZE];
        fixed = new boolean[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = initialBoard[i][j];
                fixed[i][j] = initialBoard[i][j] != 0; // zero indica posição vazia
            }
        }
    }

    public void printBoard() {
        System.out.println("   0 1 2   3 4 5   6 7 8");
        for (int i = 0; i < SIZE; i++) {
            if (i % 3 == 0) {
                System.out.println("  +-------+-------+-------+");
            }
            System.out.print(i + " | ");
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    System.out.print(". ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
                if ((j + 1) % 3 == 0) {
                    System.out.print("| ");
                }
            }
            System.out.println();
        }
        System.out.println("  +-------+-------+-------+");
    }

    public boolean setNumber(int row, int col, int num) {
        if (fixed[row][col]) {
            System.out.println("Posição fixa, não pode ser alterada.");
            return false;
        }
        if (num < 1 || num > 9) {
            System.out.println("Número inválido. Deve ser entre 1 e 9.");
            return false;
        }
        if (!isValidMove(row, col, num)) {
            System.out.println("Movimento inválido. Número repetido na linha, coluna ou bloco.");
            return false;
        }
        board[row][col] = num;
        return true;
    }

    private boolean isValidMove(int row, int col, int num) {
        // Verifica linha
        for (int j = 0; j < SIZE; j++) {
            if (board[row][j] == num && j != col) {
                return false;
            }
        }
        // Verifica coluna
        for (int i = 0; i < SIZE; i++) {
            if (board[i][col] == num && i != row) {
                return false;
            }
        }
        // Verifica bloco 3x3
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int i = boxRowStart; i < boxRowStart + 3; i++) {
            for (int j = boxColStart; j < boxColStart + 3; j++) {
                if (board[i][j] == num && (i != row || j != col)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isComplete() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int num = board[i][j];
                if (num == 0 || !isValidMove(i, j, num)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            System.out.println("Digite a linha (0-8), coluna (0-8) e número (1-9), separados por espaço. Ou digite 'exit' para sair:");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Jogo encerrado.");
                break;
            }
            String[] parts = input.split("\\s+");
            if (parts.length != 3) {
                System.out.println("Entrada inválida. Tente novamente.");
                continue;
            }
            try {
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                int num = Integer.parseInt(parts[2]);
                if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
                    System.out.println("Linha ou coluna fora do intervalo.");
                    continue;
                }
                if (setNumber(row, col, num)) {
                    if (isComplete()) {
                        printBoard();
                        System.out.println("Parabéns! Você completou o Sudoku!");
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, use números.");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        // Exemplo de tabuleiro inicial (0 = posição vazia)
        int[][] board = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        SudokuTerminal game = new SudokuTerminal(board);
        game.play();
    }
}
