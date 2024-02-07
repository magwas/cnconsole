package cnconsole;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cnconsole.data.Config;

public class AppMock {
	public static App behaviour() {
		App app = mock(App.class);
		ConfigParser parser = new ConfigParser();
		Config config = parser.parse(TestData.RC_DATA);
		when(app.getConfig()).thenReturn(config);
		return app;
	}

}
