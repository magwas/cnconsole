package cnconsole;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.LinkedTransferQueue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cnconsole.system.SystemServices;
import cnconsole.userinput.UserInput;

class UserInputTest {

	@Test
	@DisplayName("the user iinput from ncurses appears in the queue")
	void test() throws InterruptedException {
		SystemServices sys = mock(SystemServices.class);
		when(sys.getCh()).thenReturn((int) 'a');
		LinkedTransferQueue<Integer> inputQueue = new LinkedTransferQueue<Integer>();
		UserInput userInput = new UserInput(sys, inputQueue);
		userInput.start();
		Integer got = inputQueue.take();
		assertEquals('a', got);
		userInput.cease();
	}

}
