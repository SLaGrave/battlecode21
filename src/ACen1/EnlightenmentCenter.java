package ACen1;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotType;

public class EnlightenmentCenter extends RobotPlayer {
    static boolean builtThisRound = false;
    static boolean builtLastRound = false;

    static int buildsSinceMuck = 0;


    // Setup the enlightenment center
    static void setup() throws GameActionException {

    }

    // Run the enlightenment center
    static void run() throws GameActionException {
        builtLastRound = builtThisRound;
        builtThisRound = false;

        if (rc.getCooldownTurns() < 1) {
            RobotType t;
            int inf;
            if (rc.getInfluence() > slandererMinToBuild) { t = RobotType.SLANDERER; inf = slandererInf; }
            else if (buildsSinceMuck >= everyNShouldBeMuck) { t = RobotType.MUCKRAKER; inf = muckrakerInf; }
            else { t = RobotType.POLITICIAN; inf = politicanInf; }

            int idx = (int) (Math.random() * directions.length);
            for (int i = 0; i < directions.length; i++) {
                Direction dir = directions[myMod((idx + i), directions.length)];
                if (rc.canBuildRobot(t, dir, inf)) {
                    rc.buildRobot(t, dir, inf);
                    builtThisRound = true;
                    if (t != RobotType.MUCKRAKER) { buildsSinceMuck++; } else { buildsSinceMuck = 0; }
                    break;
                }
            }
        }

        // if (!builtThisRound && !builtLastRound) { rc.setFlag(0); }

        // Bidding logic
        if (rc.canBid((int)(rc.getInfluence() * percentBid))) {
            rc.bid((int)(rc.getInfluence() * percentBid));
        }

    }
}
