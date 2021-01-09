package AlphaCentauri;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotType;

public class EnlightenmentCenter extends RobotPlayer {

    static int buildsSinceMuck = 0;

    // New things
    static int dirIdx = 0;



    // Setup the enlightenment center
    static void setup() throws GameActionException {

    }

    // Run the enlightenment center
    static void run() throws GameActionException {

        if (rc.getCooldownTurns() < 1) {
            RobotType rt = RobotType.MUCKRAKER;
            Direction dir = directions[dirIdx % directions.length];
            if (ecRole == spawnTurtles) {
                rt = RobotType.MUCKRAKER;
            }
            if (rc.canBuildRobot(rt, dir, 1)) {
                rc.buildRobot(rt, dir, 1);
                dirIdx = (dirIdx + 1) % directions.length;
            }
            rc.setFlag(dirIdx * 10);

        }

        /*if (rc.getCooldownTurns() < 1) {
            RobotType t;
            int inf;
            if (rc.getInfluence() > slandererMinToBuild) { t = RobotType.SLANDERER; inf = slandererInf; }
            else if (buildsSinceMuck >= everyNShouldBeMuck) { t = RobotType.MUCKRAKER; inf = muckrakerInf; }
            else { t = RobotType.POLITICIAN; inf = politicianInf; }

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
        }*/

        // Bidding logic
        if (rc.canBid((int)(rc.getInfluence() * percentBid))) {
            rc.bid((int)(rc.getInfluence() * percentBid));
        }

    }
}
