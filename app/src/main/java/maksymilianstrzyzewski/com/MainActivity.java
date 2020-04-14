package maksymilianstrzyzewski.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    private List<Integer> gameState;
    private int activePlayer;
    private int movesCounter;
    private boolean isGameActive;

    private static final int YELLOW_PLAYER = -1;
    private static final int RED_PLAYER = 1;
    private static final int UNSET_PLAYER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        gameState = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            gameState.add(i, UNSET_PLAYER);
        }
        resetBoard();
    }

    public void move(View view) {

        if (!isGameActive) {
            return;
        }

        ImageView token = (ImageView) view;

        int tappedToken = Integer.parseInt(token.getTag().toString());

        if (activePlayer == YELLOW_PLAYER) {
            token.setImageResource(R.drawable.yellow);
            gameState.set(tappedToken, YELLOW_PLAYER);
        } else {
            token.setImageResource(R.drawable.red);
            gameState.set(tappedToken ,RED_PLAYER);
        }

        movesCounter++;

        if (movesCounter > 4) {
            checkIfGameOver();
        }

        activePlayer *= -1;
    }

    public void playAgain(View view) {
        resetBoard();
    }

    public void checkIfGameOver() {

        boolean isWinnerExists = false;
        for (int[] winningPosition : winningPositions) {

            if (gameState.get(winningPosition[0]) == UNSET_PLAYER) {
                continue;
            }

            if (gameState.get(winningPosition[0]).equals(gameState.get(winningPosition[1])) && gameState.get(winningPosition[1]).equals(gameState.get(winningPosition[2]))) {

                String message;
                if (activePlayer == RED_PLAYER) {
                    message = "Red won";
                } else {
                    message = "Yellow won";
                }

                viewResult(message);

                isWinnerExists = true;
            }
        }

        if (movesCounter == 9 && !isWinnerExists) {
            viewResult("Draw");
        }

    }

    public void viewResult(String message) {

        isGameActive = false;

        Button reset = findViewById(R.id.resetButton);

        reset.setVisibility(View.VISIBLE);

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void resetBoard() {
        movesCounter = 0;
        activePlayer = -1;
        isGameActive = true;

        GridLayout board = findViewById(R.id.board);
        Button reset = findViewById(R.id.resetButton);
        reset.setVisibility(View.INVISIBLE);

        for (int i = 0; i < 9; i++) {
            gameState.set(i, UNSET_PLAYER);
            ImageView token = (ImageView) board.getChildAt(i);
            token.setImageDrawable(null);
        }
    }
}
