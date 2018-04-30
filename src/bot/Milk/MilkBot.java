package bot.Milk;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.AccountType;

import javax.security.auth.login.LoginException;

import bot.Milk.ReactListener;

/*  MilkBot discord bot for the ENG second year 2018 discord server.
 *  	This class is the entry point for execution of the bot
 *  
 *  The pom.xml for this project was taken from https://www.youtube.com/watch?v=1VxfeK7nN6I
 *  
 * 	Author: margeobur from ENG second year 2018 discord
 */
public class MilkBot
{
	private static JDA jda;
	private static final String token = 
			"ommited";

	public static void main( String[] args )
	{
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		builder.setToken(token);
		builder.setAutoReconnect(true);
		builder.setStatus(OnlineStatus.ONLINE);

		try
		{
			jda = builder.buildBlocking();
		}
		catch(LoginException e)
		{
			e.printStackTrace();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}

		jda.addEventListener(new ReactListener());
	}
}
