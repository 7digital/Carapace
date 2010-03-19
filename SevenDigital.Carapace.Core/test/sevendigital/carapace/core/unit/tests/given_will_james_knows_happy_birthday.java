package sevendigital.carapace.core.unit.tests;

import org.junit.Ignore;
import org.junit.Test;
import org.jfugue.*;

public class given_will_james_knows_happy_birthday {
    @Test @Ignore("Takes a few seconds and is loud")
    public void then_I_can_play_it_with_jfugue() {
        Pattern song = new Pattern();

        song.add(new Pattern("F Fs G F Bb Ah"));
        song.add(new Pattern("F Fs G F C6 Bbh"));
        song.add(new Pattern("F Fs F6 D6 Bb A Gh"));
        song.add(new Pattern("d#6 d#6s D6 Bb5 C6 Bb5h"));
        
//        new Player().play(song);
    }
}