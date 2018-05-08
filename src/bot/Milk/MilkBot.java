package bot.Milk;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.AccountType;

import javax.security.auth.login.LoginException;

import bot.Milk.ReactListener;

/* 	
 * MilkBot discord bot for the ENG second year 2018 discord server.
 *  
 *  The pom.xml for this project was taken from https://www.youtube.com/watch?v=1VxfeK7nN6I
 *  
 * 	Author: margeobur from ENG second year 2018 discord
 * 	Version: 1.2
 */
public class MilkBot {
	private JDA myJDA;
	private final String myToken = "omitted";

	public static void main(String[] args) {
		MilkBot bot = new MilkBot();
		bot.initJDA();

		if(args.length > 0) {
			bot.addListeners(args[0]);
		} else {
			bot.addListeners("emojis.txt");
		}
	}

	private void initJDA() {
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		builder.setToken(myToken);
		builder.setAutoReconnect(true);
		builder.setStatus(OnlineStatus.ONLINE);

		try {
			myJDA = builder.buildBlocking();
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void addListeners(String emojiFilepath) {
		myJDA.addEventListener(new ReactListener(new EmojiMapping(emojiFilepath)));
	}
}
