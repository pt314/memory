package pt314.just4fun.games.memory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pt314.just4fun.games.memory.player.Player;

public class PlayerTest {

	private Player player;
	
	@Before
	public void setUp() {
		player = new Player(null);
	}

	@Test
	public void testIncrementWins() {
		assertEquals(0, player.getWins());
		player.incrementWins();
		assertEquals(1, player.getWins());
		player.incrementWins();
		assertEquals(2, player.getWins());
	}

}
