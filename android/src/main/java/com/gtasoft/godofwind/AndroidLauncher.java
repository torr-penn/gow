package com.gtasoft.godofwind;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gtasoft.godofwind.GodOfWind;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
		AndroidPlatform ap=new AndroidPlatform();
		initialize(new GodOfWind(ap), configuration);
	}
}