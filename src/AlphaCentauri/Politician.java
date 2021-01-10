package AlphaCentauri;

import battlecode.common.*;

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
        RobotInfo[] nearby2 = rc.senseNearbyRobots(rc.getType().actionRadiusSquared, Team.NEUTRAL);
        System.out.println("Nearby " + nearby2);
        if (nearby2.length != 0 && rc.canEmpower(rc.getType().actionRadiusSquared)) {
            System.out.println("Empowered");
            rc.empower(rc.getType().actionRadiusSquared);
            return;
        }
        move();

        int flag = Utils.genFlagNearestEC();
        System.out.println("My Flag is " + flag);
        if (rc.canSetFlag(flag)) { rc.setFlag(flag); }
        else { System.out.println("Can't set it to that!"); }
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
