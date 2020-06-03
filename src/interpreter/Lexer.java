package interpreter;

import java.util.ArrayList;
import java.util.Scanner;

import expressions.Utilities;

public class Lexer {
	// singleton
	private static class LexerHolder {
		public static final Lexer lexer = new Lexer();
	}

	private Lexer() {
	}

	public static Lexer getInstance() {
		return LexerHolder.lexer;
	}

	// TODO: make another lexer method which supports files(?)
	// TODO: use a regex to break expression tokens properly.
	// breaks the code into tokens, line by line.
	public String[] lexer(String code) {
		ArrayList<String> replacement = new ArrayList<String>();
		replacement.add("+");
		replacement.add("-");
		replacement.add("*");
		replacement.add("/");
		replacement.add("(");
		replacement.add(")");
		replacement.add("=");

		for (String delimiter : replacement)
			code = code.replaceAll(" *\\" + delimiter + " *", " " + delimiter + " ");

		code = code.replaceAll("= bind", "=bind");

		ArrayList<String> tokens = new ArrayList<String>();
		Scanner s = new Scanner(code);

		while (s.hasNext())
			tokens.add(s.next());
		s.close();

		ArrayList<String> fixed_code = new ArrayList<String>();
		ArrayList<String> operators = new ArrayList<String>();
		operators.add("+");
		operators.add("-");
		operators.add("*");
		operators.add("/");
		boolean ex_start = true;
		int i;
		for (i = 0; i < tokens.size() - 1; i++) {

			String token = tokens.get(i);
			String token_next = tokens.get(i + 1);
			boolean break1 = token_next.equals("(") && !operators.contains(token) && !token_next.equals(token);
			boolean break2 = token.equals(")") && !operators.contains(token_next) && !token_next.equals(token);
			boolean break3 = (Utilities.IsVarOrCmd(token) | Utilities.isDouble(token))
					&& (Utilities.isDouble(token_next) | Utilities.IsVarOrCmd(token_next));
			if (break1 | break2 | break3) {
				if (ex_start)// if we are at the beginning
				{
					fixed_code.add(token);// not to create duplications
				}
				ex_start = true;

			} else {

				String value = fixed_code.get(fixed_code.size() - 1);// last element
				if (ex_start)// in case we just started concreting
				{
					fixed_code.add(token + " " + token_next);
					ex_start = false;
				} else// in case we are already concreting
				{
					fixed_code.set(fixed_code.size() - 1, value + " " + token_next);
				}

			}

		}

		if (!fixed_code.get(fixed_code.size() - 1).endsWith(tokens.get(i)))
			fixed_code.add(tokens.get(i));

		String temp[] = fixed_code.toArray(new String[fixed_code.size()]);
		return temp;
	}

}
