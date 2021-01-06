package ACen1;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotType;

public class EnlightenmentCenter extends RobotPlayer {
    static boolean builtThisRound = false;
    static boolean builtLastRound = false;

    static int idx = 0;

    // Setup the enlightenment center
    static void setup() throws GameActionException {

    }

    // Run the enlightenment center
    static void run() throws GameActionException {
        builtLastRound = builtThisRound;
        builtThisRound = false;

        if (rc.canBuildRobot(RobotType.POLITICIAN, directions[idx%8], 15) && !builtLastRound) {
            rc.buildRobot(RobotType.POLITICIAN, directions[idx%8], 15);
            rc.setFlag(idx%8);
            builtThisRound = true;
        }
        idx++;

        if (!builtThisRound && !builtLastRound) { rc.setFlag(1000); }

        // Bidding logic
        if (rc.canBid((int)(rc.getInfluence() * percentBid))) {
            rc.bid((int)(rc.getInfluence() * percentBid));
        }

    }
}
