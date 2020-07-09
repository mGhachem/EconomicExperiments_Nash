package shared;

public interface NashGameScreenInterface {

	void notifyPlayerResult(int result);

	void notifyWaiting();

	void notifyUpdateTimer(int sec);

	void notifyNewRound(int round);

	void notifyWinner(int score);

	void notifyLoser(int score);

	void notifyOpponentDetails(String nickname, double averageScore, int ranking);

	void showGamePlatform();

}