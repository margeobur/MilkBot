package bot.Milk;

import java.util.List;

import bot.Milk.WordChecker;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReactListener extends ListenerAdapter
{
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		String[] tokens = {"milk", "chocolate", "pizza", "cat", "meow", "snek", "snake"};
		
		WordChecker checker = new WordChecker(tokens);

		Message message = event.getMessage();
		String plainMsg = message.getContentDisplay();
		
		List<String> result = checker.checkForWords(plainMsg);
		
		for(String token: result)
		{
			System.out.println(token);

			switch(token)
			{
			case "milk":
				message.addReaction("🥛").queue();	// unicode emojis directly in the source :LUL:
				break;
			case "pizza":
				message.addReaction("🍕").queue();
				break;
			case "chocolate":
				message.addReaction("🍫").queue();
				break;
			case "cat":
				message.addReaction("🐱").queue();
				break;
			case "meow":
				message.addReaction("🐱").queue();
				break;
			case "snek":
				message.addReaction("🐍").queue();
				break;
			case "snake":
				message.addReaction("🐍").queue();
				break;
			}
		}
	}
}
