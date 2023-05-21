public class GuitarHero {
    private static final double CONCERT_A = 440.0;
    private static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);
    public static int totalNotes = 37;

    private static double ithConcert(int i){
        return CONCERT_A * Math.pow(2, (i - 24) / 12);
    }

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        synthesizer.GuitarString[] gs = new synthesizer.GuitarString[totalNotes];
        for (int i = 0; i < totalNotes; i += 1) {
            gs[i] = new synthesizer.GuitarString(ithConcert(i));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index < 0) {
                    System.out.println("wrong key typed");
                    continue;
                }
                gs[index].pluck();
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (synthesizer.GuitarString string : gs){
                sample += string.sample();
            }
            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (synthesizer.GuitarString string : gs) {
                string.tic();
        }
    }
}
}
