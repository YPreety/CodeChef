package Easy;

/*You are playing a game with a robot. The game starts with two integers: A and M. The robot makes exactly one move in the entire 
game, and it does so at the very beginning - it will remove exactly 1 digit from A and output it as the starting value (say X). 
Note that the value A remains intact. It is not changed by the robot. After the robot makes its moves, it is your turn. 
You can make an unlimited number of moves. In each move, you must remove exactly 1 digit from A and append it to X 
(to the right side of X). Again note that none of your moves change the value of A. You win if you can eventually make X a 
multiple of M (i.e. X mod M = 0). How many possible starting moves bot can make such that you are guaranteed to win assuming 
you play optimally?

Here is an example of a game: Suppose the numbers are A = 1003, M = 4. The robot can make four possible starting moves: 003 by 
removing 1st digit, 103 by removing 2nd or 3rd digit (both count as a separate possibilities even though the starting X will be 
same) or 100 by removing the 4th digit. Let us say the robot chooses 003 as the starting move. Then you can win by 
appending 100 (by removing the 4th digit) making X = 003100 which is divisible by 4.
*/
public class NumberGame {

}
