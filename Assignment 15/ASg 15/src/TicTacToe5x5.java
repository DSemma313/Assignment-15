import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TicTacToe5x5 extends Application {

    private static final int SIZE = 5; 
    private Button[][] buttons = new Button[SIZE][SIZE];
    private boolean isPlayerX = true; 

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Button button = new Button();
                button.setPrefSize(100, 100);
                button.setStyle("-fx-font-size: 24;");
                final int r = row, c = col;

                button.setOnAction(e -> handleMove(button, r, c));
                buttons[row][col] = button;
                grid.add(button, col, row);
            }
        }

        Scene scene = new Scene(grid, 500, 500);
        primaryStage.setTitle("Tic Tac Toe 5x5");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleMove(Button button, int row, int col) {
        if (!button.getText().isEmpty()) {
            return; 
        }

        button.setText(isPlayerX ? "X" : "O");

        if (checkWin(row, col)) {
            showAlert((isPlayerX ? "X" : "O") + " wins!");
            resetBoard();
        } else if (isBoardFull()) {
            showAlert("It's a draw!");
            resetBoard();
        }

        isPlayerX = !isPlayerX;
    }

    private boolean checkWin(int row, int col) {
        String symbol = isPlayerX ? "X" : "O";
        return checkDirection(row, col, 1, 0, symbol) 
                || checkDirection(row, col, 0, 1, symbol) 
                || checkDirection(row, col, 1, 1, symbol) 
                || checkDirection(row, col, 1, -1, symbol); 
    }

    private boolean checkDirection(int row, int col, int dr, int dc, String symbol) {
        int count = 1;

        count += countConsecutive(row, col, dr, dc, symbol);
        count += countConsecutive(row, col, -dr, -dc, symbol);

        return count >= 5;
    }

    private int countConsecutive(int row, int col, int dr, int dc, String symbol) {
        int count = 0;
        for (int i = 1; i < 5; i++) {
            int r = row + i * dr, c = col + i * dc;
            if (r < 0 || r >= SIZE || c < 0 || c >= SIZE || !buttons[r][c].getText().equals(symbol)) {
                break;
            }
            count++;
        }
        return count;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col].setText("");
            }
        }
        isPlayerX = true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
