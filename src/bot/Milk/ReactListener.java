package bot.Milk;

import java.time.ZonedDateTime;
import java.util.List;

import bot.Milk.WordChecker;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReactListener extends ListenerAdapter {
	EmojiMapping myEmojiMapping;

	public ReactListener(EmojiMapping mapping) {
		myEmojiMapping = mapping;
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String[] tokens = myEmojiMapping.getTokens();
		WordChecker checker = new WordChecker(tokens);

		Message message = event.getMessage();
		String plainMsg = message.getContentDisplay();

		List<String> result = checker.checkForWords(plainMsg);
		for(String tokenFound: result) {
			System.out.println(ZonedDateTime.now().toString() + ": " + tokenFound);

			if(myEmojiMapping.contains(tokenFound)) {
				message.addReaction(myEmojiMapping.get(tokenFound)).queue();
			} else {
				System.out.println("Something went wrong getting the emoji for token " + tokenFound
						+ "\nscanning message by " + message.getAuthor() + " at " + message.getCreationTime().toString());
			}
		}
	}
}
