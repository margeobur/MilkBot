package bot.Milk;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;

/*
 * This class encapsulates a mapping between search tokens and their corresponding emojis
 */
public class EmojiMapping {
	HashMap<String, String> myTokenEmojiMap;

	public EmojiMapping(String emojiFilepath) {
		myTokenEmojiMap = getEmojiMappingFromFile(emojiFilepath);
	}

	public String[] getTokens() {
		Set<String> tokenSet = myTokenEmojiMap.keySet();
		String[] tokens = new String[tokenSet.size()];
		tokenSet.toArray(tokens);
		return tokens;
	}

	/*
	 * Retrieve an emoji given its token
	 */
	public String get(String token) {
		return myTokenEmojiMap.get(token);
	}

	public boolean contains(String token) {
		return myTokenEmojiMap.containsKey(token);
	}

	/*
	 * Retrieves the mapping of search tokens to unicode emojis from a file.
	 * Each line of the file gives a mapping and has the format:
	 * "<token>\t<hex codepoint>"
	 */
	private HashMap<String, String> getEmojiMappingFromFile(String path) {
		HashMap<String, String> mapping = new HashMap<String, String>();

		String cwd = System.getProperty("user.dir");
		Path file = Paths.get(cwd, path);

		if(Files.notExists(file)) {
			System.out.println("File " + file.toString() + " does not exist.");
			return mapping;
		}
		if(!Files.isReadable(file)) {
			System.out.println("File " + file.toString() + " is not readable.");
			return mapping;
		}

		Charset charset = Charset.forName("UTF-8");
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			String line = null;
			String token;
			String emoji;
			int tabIndex;
			int lines = 0;

			while((line = reader.readLine()) != null) {
				lines++;
				if(line.contains("\t")) {
					tabIndex = line.indexOf('\t');
					token = line.substring(0, tabIndex);
					emoji = getEmoji(line.substring(tabIndex + 1));

					if(emoji == "1") {
						System.out.println("Problem with decoding emoji codepoint on line " + lines);
						continue;
					} else if(emoji == "2") {
						System.out.println("Invalid Unicode codepoint on line " + lines);
						continue;
					}

					if(mapping.containsKey(token)) {
						System.out.println("Duplicate token on " + lines);
					} else {
						mapping.put(token, emoji);
					}
				} else {
					System.out.println("No tab on line " + lines);
				}
			}
		} catch (IOException e) {
			System.out.println("Error processing file " + file.toString());
			e.printStackTrace();
		}

		return mapping;
	}

	/*
	 * This helper function converts a raw string containing a unicode codepoint
	 * into a string that contains the corresponding unicode character
	 */
	private String getEmoji(String rawText) {
		Integer codepoint;
		int[] codepoints = new int[1];
		String emoji;

		try {
			codepoint = Integer.decode(rawText);
		} catch (NumberFormatException e) {
			return "1";
		}

		codepoints[0] = codepoint.intValue();
		try {
			emoji = new String(codepoints, 0, 1);
		} catch (IllegalArgumentException e) {
			return "2";
		}

		return emoji;
	}
}
