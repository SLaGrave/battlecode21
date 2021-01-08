package ACen1;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;

public class Slanderer extends RobotPlayer {
    static int dirIdx;

    // Setup the slanderer
    static void setup() throws GameActionException {
        dirIdx = (int) (Math.random() * directions.length);

    }

    // Run the slanderer
    static void run() throws GameActionException {
        move();
    }

    static void move() throws GameActionException {
        int[] x = {0, 1, -1, 3, -3, 2, -2, 4, -4};
        for (int i: x) {
            if (rc.canMove(directions[myMod((dirIdx + i), directions.length)])) {
                rc.move(directions[myMod((dirIdx + i), directions.length)]);
                dirIdx += i;
                break;
            }
        }
    }
}
