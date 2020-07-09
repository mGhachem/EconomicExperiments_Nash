# Nash Demand Game - Online Economic Experiments

NashDemandGame is a Java Economic Game where two players play a game where they try to divide a `$10` among themselves. Each game consists of five rounds. In each round, each player, simultaneously, announces a demand `$x` from the set `{$1, $2,...,$8, $9}`. If the sum of the bids is smaller or equal to `$10`, then each player receives his own bid. If the sum is higher than `$10`, then both players receive `$0`. More about the game and its significance for economic research can be found here [Wikipedia - Bargaining Problem](https://en.wikipedia.org/wiki/Bargaining_problem)

The Nash Demand Game was used to explain the emergence for preferences for fairness and justice and "how individuals come to believe that proposing a 50–50 split is the only just solution". It is used widely in Economic experiments and this game allows researchers to make experiments remotely without the need of physical presence.


## Features

NashDemandGame has the following features:
* It relies on Server-Client technology and is built using Java RMI (Remote Method Invocation). It has a server to which many clients can connect and play at the same time.
* Each game requires two players. If a human player connects to the server and there is an open game by another human player, then he is automatically matched to the game and the game starts. If there are no open games, the human player can either choose to play with an AI player or wait for `30` seconds for a human player to join. At the end of the 30 seconds, the player is automatically matched with an AI player.
* The AI player, in the current form, makes a random demand from `{$1, $2,...,$8, $9}` in the first round. In the rounds 2 to 5, he registers the demand of his opponent in the previous round, call it `$x`. The AI player goes through the database and records all demands made by players in round `i+1` as a response to demand `$x` made by their opponents in round `i` and their results. It, then, picks the demand with the best results.
* Each player has `10`seconds to announce a demand. If a human player fails to announce a demand within `10` seconds, the server assigns the lowest possible demand to him, i.e., a demand of `$1`.
* At the end of each round, the players are not shown the demands of their opponents, only their result of the round.
* The game keeps two statistics for each player: their average score over all rounds they have played and their ranking based on that score. Each player is shown their own score and ranking as well as the score and ranking of their opponents.


## Downloads
Releases for Desktop are found on the [releases page](https://github.com/mingli1/Unlucky/releases). The latest release consist of two runnable .jar format files so you must have JRE installed. One JAR file for the server `NashDemandServer.jar` and one jar file for the client `NashDemandClient.jar`.


## Ongoing Improvements

NashDemandGame undergoes the following improvements:
* Java RMI is known to have problems with callback when the server does not have a public IP. We are working on using DiRMI instead.
* We are working on developing an Android Version for the game as it is nowadays much easier to run experiments on the phone.

## Credits
The NashDemandGame is designed and developed by Mohamed Ghachem using Java 8. Later versions will use DiRMI and Android.
The project has been done with cooperation with Timotheous Mavroupolos (Stockholm School of Economics).

## License
This project is licensed under the [MIT License](https://github.com/mingli1/Unlucky/blob/master/LICENSE).

## Screenshots
