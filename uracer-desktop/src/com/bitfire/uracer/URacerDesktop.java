package com.bitfire.uracer;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class URacerDesktop
{
	public static void main (String[] argv) {
		// (width!=height) && (width > height)

//		new JoglApplication(new URacer(), "uRacer: The King Of The Drift", 480, 320, true);
//		new JoglApplication(new URacer(), "uRacer: The King Of The Drift", 720, 480, true);
//		new JoglApplication(new URacer(), "uRacer: The King Of The Drift", 800, 480, true);
//		new JoglApplication(new URacer(), "uRacer: The King Of The Drift", 800, 800, true);
//		new JoglApplication(new URacer(), "uRacer: The King Of The Drift", 1280, 800, true);	// target
//		new JoglApplication(new URacer(), "uRacer: The King Of The Drift", 1280, 1024, true);

		// higher resolutions than the target can't be supported without Track artifacts of
		// some sort cropping out
		new JoglApplication(new URacer(), "uRacer: The King Of The Drift", 1920, 1050, true);
	}

}
