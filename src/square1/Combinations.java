/*
 * Class to generate and test combinations of transformations
 * Created on Dec 25, 2005
 * By Kenneth Evans, Jr.
 */

package square1;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Class to implement calculating combinations of transforms
 * 
 * @author Kenneth Evans, Jr
 */
public class Combinations
{
    private static final int N_CYCLES = 2;
    private static final int N_MAX_DIFF = 4;
    private static final boolean OMIT_4_SIDES = false;
    private static final boolean START_WITH_R = true;

    private int nCycles = N_CYCLES;
    private int nMaxDiff = N_MAX_DIFF;
    private boolean omit4Sides = OMIT_4_SIDES;
    private boolean startWithR = START_WITH_R;

    private static final boolean SHOW_PROGRESS = true;
    // private long nErrs = 0;
    private long nErrs1 = 0;
    private static final String[] topCombos = {"T-5", "T-4", "T-3", "T-2",
        "T-1", "", "T1", "T2", "T3", "T4", "T5", "T6"};
    private static final String[] bottomCombos = {"B-5", "B-4", "B-3", "B-2",
        "B-1", "", "B1", "B2", "B3", "B4", "B5", "B6"};
    private static final String faces[] = {"b8", "b7", "b7", "b6", "b5", "b5",
        "b4", "b3", "b3", "b2", "b1", "b1", "err", "t1", "t1", "t2", "t3",
        "t3", "t4", "t5", "t5", "t6", "t7", "t7", "t8"};
    private static final int[] reducedIndex = {0, 2, 3, 5, 6, 8, 9, 11};
    private int baseLen = topCombos.length;
    private long nProcessed = 0;
    private long nPrinted = 0;
    private int[] topId = null;
    private int[] bottomId = null;
    private int[] changedBlocks = null;

    /**
     * Combinations constructor
     */
    public Combinations() {
        changedBlocks = new int[nMaxDiff];
    }

    /**
     * Handles the looping over cycles
     */
    public void doCycles() {
        nProcessed = 0;
        Configuration config = Configuration.defaultConfiguration();
        Block[] top = config.getTop();
        Block[] bottom = config.getBottom();
        // Fill in default id's
        topId = new int[12];
        bottomId = new int[12];
        for(int i = 0; i < 12; i++) {
            topId[i] = top[i].getId();
            bottomId[i] = bottom[i].getId();
        }
        // Start the first (zero'th) cycle
        cycle(0, null, config);
    }

    /**
     * Handles the nth cycle
     * 
     * @param n
     * @param tlist
     * @param config
     */
    public void cycle(int n, String[] tlist, Configuration config) {
        if(n == nCycles) {
            return;
        }
        // Save n-1 quantities
        String[] tlist0 = new String[3 * n];
        if(tlist != null) System.arraycopy(tlist, 0, tlist0, 0, 3 * n);
        if(config == null) config = Configuration.defaultConfiguration();
        Block[] top0 = new Block[12];
        System.arraycopy(config.getTop(), 0, top0, 0, 12);
        Block center0 = config.getCenter();
        Block[] bottom0 = new Block[12];
        System.arraycopy(config.getBottom(), 0, bottom0, 0, 12);

        String[] tlist1 = new String[3];
        tlist1[2] = "R";
        for(int i = 0; i < baseLen; i++) {
            tlist1[0] = topCombos[i];
            if(startWithR) {
                if(n == 0 && !(i == 5)) continue;
            }
            for(int j = 0; j < baseLen; j++) {
                tlist1[1] = bottomCombos[j];
                if(startWithR) {
                    if(n == 0 && !(j == 5)) continue;
                }
                Configuration config0 = new Configuration(top0, center0,
                    bottom0);
                if(false) {
                    System.out.println("i=" + i + " j=" + j + " config0");
                    config.printConfiguration();
                }
                Configuration config1 = null;
                // Do the transform
                try {
                    config1 = config0.transform(tlist1, false);
                } catch(InvalidOperationException ex) {
                    // nErrs++;
                    if(ex.isNotR()) {
                        if(ex.isTopBad()) {
                            // This choice for top will not work, independent of
                            // bottom
                            break;
                        } else {
                            // This choice for bottom will not work, try the
                            // next one
                            continue;
                        }
                    } else {
                        // Unexpected error
                        nErrs1++;
                        System.out.println(ex.getMessage());
                        continue;
                    }
                }
                // Check and process the transform
                String[] tlistFull = new String[3 * (n + 1)];
                System.arraycopy(tlist0, 0, tlistFull, 0, 3 * n);
                System.arraycopy(tlist1, 0, tlistFull, 3 * n, 3);
                boolean status = checkCombination(n, tlistFull, config1);
                // Abort if checkCombinations returned false
                // Means this combination is a dead end (results in no changes
                // or
                // is not of interest
                if(!status) continue;
                // Do the next cycle for this transform
                if(n < nCycles) cycle(n + 1, tlistFull, config1);
            }
            if(SHOW_PROGRESS && n == 0) {
                System.out.println("Finished set of top transforms number "
                    + (i + 1) + " for n=0 at " + Utilities.timeStamp());
                System.out.println("  Transforms processed: " + nProcessed);
            }
        }
    }

    /**
     * Checks a combination by adding all combinations of T^nB^m and checking
     * for only a few changed blocks
     * 
     * @return false to abort this combination
     */
    public boolean checkCombination(int n, String[] tlist, Configuration config) {
        nProcessed++;
        // Save n-1 quantities
        int n1 = n + 1;
        String[] tlist0 = new String[3 * n1];
        if(tlist != null) System.arraycopy(tlist, 0, tlist0, 0, 3 * n1);
        if(config == null) config = Configuration.defaultConfiguration();
        Block[] top0 = new Block[12];
        System.arraycopy(config.getTop(), 0, top0, 0, 12);
        Block center0 = config.getCenter();
        Block[] bottom0 = new Block[12];
        System.arraycopy(config.getBottom(), 0, bottom0, 0, 12);

        String[] tlist1 = new String[2];
        for(int i = 0; i < baseLen; i++) {
            tlist1[0] = topCombos[i];
            for(int j = 0; j < baseLen; j++) {
                tlist1[1] = bottomCombos[j];
                Configuration config0 = new Configuration(top0, center0,
                    bottom0);
                Configuration config1 = null;
                // Do the transform
                try {
                    config1 = config0.transform(tlist1, false);
                } catch(InvalidOperationException ex) {
                    // Unexpected error
                    nErrs1++;
                    System.out.println("checkCombination: " + ex.getMessage());
                    continue;
                }
                // Check for differences
                int nDiff = checkDifferences(config1);
                // Continue if there are no differences
                if(nDiff == 0) return false;
                if(nDiff <= nMaxDiff) {
                    // Check for id = 3, 6, 9, 12 (faces 2, 4, 6, 8) changes
                    if(omit4Sides && nDiff == 4) {
                        boolean omit = true;
                        for(int k = 0; k < n; k++) {
                            if((changedBlocks[k] % 3) != 0) {
                                omit = false;
                                break;
                            }
                            if(omit) return false;
                        }
                    }
                    // Make the full transformation list
                    String[] tlistFull = new String[3 * (n1 + 1) - 1];
                    System.arraycopy(tlist0, 0, tlistFull, 0, 3 * n1);
                    System.arraycopy(tlist1, 0, tlistFull, 3 * n1, 2);
                    printCombination(nDiff, tlistFull);
                }
            }
        }
        return true;
    }

    /**
     * Calculates the number of changed faces
     * 
     * @param config
     * @return number of changed faces
     */
    private int checkDifferences(Configuration config) {
        Block[] top = config.getTop();
        Block[] bottom = config.getBottom();
        int nDiff = 0;
        // for(int i=0; i < 12; i++) {
        // int ii = i;
        for(int i = 0; i < 8; i++) {
            // Only do the distinct 8 faces
            int ii = reducedIndex[i];
            if(top[ii].getId() != topId[ii]) {
                if(nDiff < nMaxDiff) changedBlocks[nDiff] = topId[ii];
                nDiff++;
            }
            if(bottom[ii].getId() != bottomId[ii]) {
                if(nDiff < nMaxDiff) changedBlocks[nDiff] = bottomId[ii];
                nDiff++;
            }
        }
        return nDiff;
    }

    /**
     * Prints the canonical form of the transformation
     * 
     * @param n
     * @param tlist
     */
    private void printCombination(int n, String[] tlist) {
        nPrinted++;
        // Print the transform
        System.out.print("@" + n);
        // Make an array and sort it
        String[] array = new String[n];
        for(int i = 0; i < n; i++) {
            int id = changedBlocks[i];
            String face = getFace(id);
            array[i] = face;
        }
        Arrays.sort(array);
        for(int i = 0; i < n; i++) {
            System.out.print("-" + array[i]);
        }
        System.out.print(" ");
        String stringTransform = Configuration.packTransform(tlist);
        System.out.print(stringTransform);
        System.out.println();
    }

    /**
     * Get the reduced face number fom the Id
     * 
     * @param id
     * @return
     */
    private static String getFace(int id) {
        return faces[id + 12];
    }

    /**
     * Main routine
     * 
     * @param args
     */
    public static void main(String[] args) {
        Date start = new Date();

        int digits = 2;
        NumberFormat fractFormat = NumberFormat.getNumberInstance();
        fractFormat.setMinimumFractionDigits(digits);
        fractFormat.setMaximumFractionDigits(digits);
        fractFormat.setMinimumIntegerDigits(2);
        fractFormat.setGroupingUsed(false);

        // Make an instance
        Combinations comb = new Combinations();

        // Parse the command line
        if(!comb.parseCommand(args)) System.exit(1);

        // Do the transformations
        System.out.println("Starting combinations at " + Utilities.timeStamp());
        System.out.println("Number of cycles: " + comb.nCycles);
        System.out.println("Maximum number of changed blocks allowed: "
            + comb.nMaxDiff);
        System.out.println("Omitting faces 2-4-6-8 only transforms: "
            + comb.omit4Sides);
        System.out.println("The first cycle is only R: " + comb.startWithR);
        comb.doCycles();

        // Print summary
        Date end = new Date();
        long millis = end.getTime() - start.getTime();
        double minutes = millis / 1000. / 60.;
        double hours = minutes / 60.;
        double days = hours / 24.;

        System.out.println();
        System.out.println("Number of cycles: " + comb.nCycles);
        System.out.println("Maximum number of changed blocks allowed: "
            + comb.nMaxDiff);
        System.out.println("Omitting 4 changes with only faces 2-4-6-8: "
            + comb.omit4Sides);
        System.out.println("The first cycle is only R: " + comb.startWithR);
        System.out.println("Transforms processed: " + comb.nProcessed);
        System.out.println("Transforms printed: " + comb.nPrinted);
        // System.out.println("InvalidOperation errors: " + comb.nErrs);
        System.out
            .println("Unexpected InvalidOperation errors: " + comb.nErrs1);
        System.out.println("Elapsed time: = " + fractFormat.format(days)
            + " days, " + fractFormat.format(hours) + " hours, "
            + fractFormat.format(minutes) + " minutes");
        System.out.println("All done");
    }

    /**
     * Parses the command line
     * 
     * @param args
     * @return success or failure
     */
    private boolean parseCommand(String[] args) {
        int i;

        try {
            for(i = 0; i < args.length; i++) {
                if(args[i].startsWith("-")) {
                    switch(args[i].charAt(1)) {
                    case 'c':
                        try {
                            nCycles = Integer.parseInt(args[++i]);
                        } catch(NumberFormatException ex) {
                            nCycles = 0;
                            System.err.println("Invalid number for -cycles");
                            return false;
                        }
                        break;
                    case 'h':
                        usage();
                        System.exit(0);
                    case 'm':
                        try {
                            nMaxDiff = Integer.parseInt(args[++i]);
                            changedBlocks = new int[nMaxDiff];
                        } catch(NumberFormatException ex) {
                            nCycles = 0;
                            System.err.println("Invalid number for -max");
                            return false;
                        }
                        break;
                    case 'o':
                        omit4Sides = true;
                        break;
                    case 'r':
                        startWithR = true;
                        break;
                    default:
                        System.err.println("\n\nInvalid option: " + args[i]);
                        usage();
                        return false;
                    }
                }
            }
        } catch(Exception ex) {
            System.err.println("\n\nError parsing command line\n +"
                + ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Prints usage
     */
    private void usage() {
        System.out
            .println("\nUsage: java "
                + this.getClass().getName()
                + " [Options]\n"
                + "  Calculates combinations of transformations and prints out simple ones\n"
                + "  as specified\n"
                + "\n"
                + "  Options (first letter is sufficient):\n"
                + "    -cycles <int>    Number of cycles (TnBmR) to do "
                + "(default = "
                + N_CYCLES
                + ")\n"
                + "    -maxDiff <int>   Maximum number of changed faces to print "
                + "(default = "
                + N_MAX_DIFF
                + ")\n"
                + "    -omit4Sides      Omit 4 changes with only faces 2-4-6-8\n"
                + "    -rStart          The first cycle is only R\n"
                + "    -h               Help (This message)\n");
    }
}
