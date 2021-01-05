package ACen1;

import battlecode.common.GameActionException;

public class Slanderer extends RobotPlayer {
    // Run the slanderer
    static void run() throws GameActionException {
        if (tryMove(randomDirection()))
            System.out.println("I moved!");
    }
}
