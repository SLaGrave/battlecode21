package AlphaCentauri;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;

import java.util.ArrayList;
import java.util.Collections;

public class Utils extends RobotPlayer{
    static int genFlagNearestEC() throws GameActionException {
        RobotInfo[] all = rc.senseNearbyRobots();
        ArrayList<MapLocation> nearby = new ArrayList<>();
        for (RobotInfo robot: all) {
            if (robot.getType() == RobotType.ENLIGHTENMENT_CENTER && robot.getTeam() == rc.getTeam().opponent()) {
                nearby.add(robot.getLocation());
            }
            else if (robot.getTeam() == rc.getTeam()) {
                MapLocation q = parseFlagNearestEC(robot);
                if (q != null) { nearby.add(q); }
            }
        }

        if (nearby.isEmpty()) {
            return 0;
        }
        else {
            Collections.sort(nearby, (ml1, ml2) -> (rc.getLocation().distanceSquaredTo(ml1) - rc.getLocation().distanceSquaredTo(ml2)));
            MapLocation nearest = nearby.get(0);
            // format: NXXYY
            // N = 0 (+, +), 1 (-, +), 2 (+, -), 3 (-, -)
            int i = 0;
            int x = rc.getLocation().x; int y = rc.getLocation().y;
            if (nearest.x - x <= 0 && nearest.y - y <= 0) { i = 3; }
            else if (nearest.x - x <= 0) { i = 1; }
            else if (nearest.y - y <= 0) { i = 2; }

            return i * 10000 + Math.abs(nearest.x - x) * 100 + Math.abs(nearest.y - y);
        }
    }

    static MapLocation parseFlagNearestEC(RobotInfo robot) throws GameActionException {
        if (rc.canGetFlag(robot.getID())) {
            int flag = rc.getFlag(robot.getID());
            if (flag != 0) {
                int xModifier = 1; int yModifier = 1;
                if (flag >= 30000) { xModifier = -1; yModifier = -1; flag -= 30000; }
                else if (flag >= 20000) { yModifier = -1; flag -= 20000; }
                else if (flag >= 10000) { xModifier = -1; flag -= 10000; }


                int ones = flag % 10;
                int tens = (int)((flag % 100) / 10);
                int hundreds = (int)((flag % 1000) / 100);
                int thousands = (int)((flag % 10000) / 1000);

                int x = robot.getLocation().x + ((thousands * 10 + hundreds) * xModifier);
                int y = robot.getLocation().y + ((tens * 10 + ones) * yModifier);

                return new MapLocation(x, y);
            }
        }
        return null;
    }
}
