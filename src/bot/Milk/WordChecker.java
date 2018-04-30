package bot.Milk;

import java.util.ArrayList;
import java.util.List;

/*	WordChecker: used to scan messages for words that MilkBot will react to
 * 
 * 	Original Author: Millennium from ENG second year 2018 Discord
 * 	Modified by: margeobur
 */
public class WordChecker {
	private ArrayList<String> _words_to_check_for = new ArrayList<String>();

	public WordChecker(String... words_to_check_for) {
		for (String word: words_to_check_for) {
			_words_to_check_for.add(word);
		}
	}

	@Deprecated
	public String check(String sentence) {
		String word_found = "null";
		String[] words_string = sentence.split(" ");
		for (String word: words_string) {
			word_found = checkWord(word);
			if (word_found != "null") return word_found;
		}
		return word_found;
	}
	
	// Replacement for check()
	public List<String> checkForWords(String sentence) {
		List<String> tokensFound = new ArrayList<String>();
		String[] words = sentence.split(" ");

		List<String> tokensFromCurrentWord = new ArrayList<String>();
		for (String word : words) {
			tokensFromCurrentWord.addAll(checkWordMargsWay(word));
			for (String token : tokensFromCurrentWord) {
				if (!tokensFound.contains(token)) {
					tokensFound.add(token);
				}
			}
			tokensFromCurrentWord.clear();
		}
		return tokensFound;
	}

	public void addWord(String... words_to_add) {
		for (String word: words_to_add) {
			_words_to_check_for.add(word);
		}
	}

	public void removeWord(String... words_to_remove) {
		for (String word: words_to_remove) {
			_words_to_check_for.remove(word);
		}
	}
	
	@Deprecated
	private String checkWord(String word_being_checked) {
		char[] current_word = word_being_checked.toCharArray();
		int letter_to_check = 0; // the letter in current word

		int[] letter_number = new int[_words_to_check_for.size()]; // the letter in the checking word

		ArrayList<String> words_left = new ArrayList<String>();
		for (String word: _words_to_check_for) {
			words_left.add(word);
			letter_number[_words_to_check_for.indexOf(word)] = 0;
		}

		while ((words_left.size() > 0) && (letter_to_check <= current_word.length)) {
			for (String word_to_check: words_left) {

				int current_letter_number = letter_number[_words_to_check_for.indexOf(word_to_check)];
				if (current_word[letter_to_check] != word_to_check.charAt(current_letter_number)) {
					if (current_word[letter_to_check] != word_to_check.charAt(current_letter_number + 1)) letter_number[_words_to_check_for.indexOf(word_to_check)]++;
					else words_left.remove(word_to_check);
				}
			}
			letter_to_check++;
		}

		if (words_left.size() > 0) return (String)words_left.get(0);
		else return "null";
	}

	// Replacement for checkWord, for checking if an
	// individual word matches a token or multiple tokens
	private List<String> checkWordMargsWay(String word_to_check) {
		boolean currentTokenIsPresent;
		ArrayList<String> presentTokens = new ArrayList<String>();

		// Think of the word we're checking as the subject, hence
		// simply calling it "word":
		// Also, all checking is caps-insensitive
		char[] word = word_to_check.toLowerCase().toCharArray();
		int wordIndex;

		// This is an alias reference which fits in with my naming scheme
		// the words we're checking AGAINST are called "tokens"
		ArrayList<String> searchTokens = new ArrayList<String>(_words_to_check_for);
		int tokenIndex;
		boolean inToken;

		for (String token : searchTokens) {
			currentTokenIsPresent = false;
			inToken = false;
			tokenIndex = 0;
			wordIndex = 0;

			while (wordIndex < word.length) {
				if (word[wordIndex] == token.charAt(tokenIndex)) {
					inToken = true;
					wordIndex++;

					// AS SOON AS we reach the last character in the token, we know our
					// word matches that token, because if our word in fact does NOT match
					// the token, we would have reset the token index prior to getting
					// here upon finding some other character. In other words, if we reach
					// the last letter in the token, we have all letters in a row with no
					// other characters.
					if (tokenIndex == token.length() - 1) {
						currentTokenIsPresent = true;
						break;
					}

					if (token.charAt(tokenIndex) == token.charAt(tokenIndex + 1)) {
						tokenIndex++;
					}
				} else if (word[wordIndex] == token.charAt(tokenIndex + 1) && inToken) {
					tokenIndex++;
				} else {
					inToken = false;
					tokenIndex = 0;
					wordIndex++;
				}
			}

			if (currentTokenIsPresent) {
				presentTokens.add(token);
			}
		}
		return presentTokens;
	}
}