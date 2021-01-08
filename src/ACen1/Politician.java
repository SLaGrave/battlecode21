package ACen1;

import battlecode.common.*;

import java.nio.file.DirectoryIteratorException;

public class Politician extends RobotPlayer {
    static int dirIdx;

    // Setup the politician
    static void setup() throws GameActionException {
        dirIdx = (int) (Math.random() * directions.length);

    }

    // Run the politician
    static void run() throws GameActionException {
        RobotInfo[] nearby = rc.senseNearbyRobots(rc.getType().actionRadiusSquared, rc.getTeam().opponent());
        System.out.println("Nearby " + nearby);
        if (nearby.length != 0 && rc.canEmpower(rc.getType().actionRadiusSquared)) {
            System.out.println("Empowered");
            rc.empower(rc.getType().actionRadiusSquared);
            return;
        }
        move();
    }

    // Random movement
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
