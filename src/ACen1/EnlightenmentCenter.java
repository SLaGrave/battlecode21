package ACen1;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;

public class EnlightenmentCenter extends RobotPlayer {
    static boolean builtThisRound = false;
    static boolean builtLastRound = false;


    // Setup the enlightenment center
    static void setup() throws GameActionException {

    }

    // Run the enlightenment center
    static void run() throws GameActionException {
        builtLastRound = builtThisRound;
        builtThisRound = false;

        int idx = (int) (Math.random() * directions.length);
        for (int i = 0; i < directions.length; i++) {
            Direction dir = directions[myMod((idx + i), directions.length)];
            if (rc.canBuildRobot(RobotType.MUCKRAKER, dir, 25)) {
                rc.buildRobot(RobotType.MUCKRAKER, dir, 25);
                builtThisRound = true;
                break;
            }
        }

        // if (!builtThisRound && !builtLastRound) { rc.setFlag(0); }

        // Bidding logic
        if (rc.canBid((int)(rc.getInfluence() * percentBid))) {
            rc.bid((int)(rc.getInfluence() * percentBid));
        }

    }
}
