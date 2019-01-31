import java.util.*;
import java.util.regex.*;
import java.io.*;

public class hangman {
	public static String url_pattern = ".+\\:\\/\\/.+";
	public static String char_pattern = ".+?";
	public static int MAX = 10;
	public static int score =0;
	
	public static String[] wordlist(String resource){
		ArrayList<String> words = new ArrayList<String>();
		
			Scanner wordReader;
			try{
				wordReader = new Scanner(new FileReader(resource));
			}catch(FileNotFoundException e){
				System.out.println("Cannot open source file "+resource);
				return new String[]{};
			}
			while (wordReader.hasNextLine()){
				words.add(wordReader.nextLine());
			}
		
		return words.toArray(new String[words.size()]);
	}
	public static String join(String[] str){
		return join(str, " ");
	}
	public static String join(String[] str, String del){
		String str_ = "";
		for (int i=0;i<str.length;i++) str_ += str[i]+del;
		return str_;
	}
	public static void update(ArrayList<String> chosen){
		String words = join(chosen.toArray(new String[chosen.size()]));
		status(words);
	}
	public static void status(String status){
		System.out.println(status);
	}
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("1-famous song");
		System.out.println("2-famous movie");
		System.out.print("Input Category : ");
		String[] words;
		while(true) {
		try {
		int num = Integer.parseInt(input.nextLine());
		if(num==1) {
			words = wordlist("/Users/johnpaul/eclipse-workspace/Internship/src/Famous Songs");
			break;
			}
		else if(num==2) {
			words = wordlist("/Users/johnpaul/eclipse-workspace/Internship/src/Movie");
			break;
		}
		else {
			System.out.println("Wrong input try again");
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Wrong input try again");
		}
		}
		
		Random rand = new Random();
		String word = words[rand.nextInt(words.length)].toLowerCase();
		String[] getWord = word.split("/");
		String realWord= getWord[0];
		String gameWord= realWord.replace(" ", "");
		String hint=getWord[1];
		System.out.println(hint);
		ArrayList<String> chosen = new ArrayList<String>();
		ArrayList<String> wrong = new ArrayList<String>();
		for (int i=0;i<gameWord.length();i++) { 
			if(!Character.isLetter(gameWord.charAt(i)))
				chosen.add(""+gameWord.charAt(i));
			else
				chosen.add("_");
			}
		update(chosen);
		int level = 0;
		while (true){
			System.out.print("Input: ");
			String char_ = input.nextLine();
			if (Pattern.matches(char_pattern, char_)){
				Matcher match = Pattern.compile(char_pattern).matcher(char_);
				while (match.find()) {
					String chr = match.group();
					chr = chr.toLowerCase();
					if (!gameWord.contains(chr)){
						System.out.println("Incorrect");
						if (!wrong.contains(chr)){
							score=score-5;
							System.out.println("your score "+score);
							System.out.println();
							wrong.add(chr);
							level++;
						}else{
							score=score-10;
							System.out.println("your score "+score);
							System.out.println(": You have already guessed "+chr);
						}
					}else{
						score=score+5;
						System.out.println("Correct");
						System.out.println("your score "+score);
						for (int i=0;i<gameWord.length();i++){
							if (gameWord.charAt(i)== chr.charAt(0)) chosen.set(i, chr);
						}
					}
					if (level >= MAX){
						//You Lost
						System.out.println("You lost, the correct word is "+realWord);
						System.out.println("Total score "+score);
						return;
					}
					update(chosen);
					System.out.println();
					if (!chosen.contains("_")){
						//You Won
						System.out.println("You won with "+level+" wrong guesses!");
						System.out.println("Total score "+score);
						return;
					}
				}
			}
		}
	}
}