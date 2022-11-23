import java.io.*;
import java.util.*;

class SymTuple {
	String symbol;
	int value;

	SymTuple(String s1, int i1) {
		symbol = s1;
		value = i1;
	}
}

class pass2 {
	static List<SymTuple> symtable;
	static int lc;
	static PrintWriter out_pass2;

	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("sym.txt")));
		String str;
		System.out.println("Enter lines of text.");
		System.out.println("Enter 'stop' to quit.");

		symtable = new LinkedList<>();

		while ((str = br.readLine()) != null) {
			System.out.println(str);
			StringTokenizer st = new StringTokenizer(str, "	", false);
			String s_arr[] = new String[st.countTokens()];
			for (int i = 0; i < s_arr.length; i++) {
				s_arr[i] = st.nextToken();
			}
			symtable.add(new SymTuple(s_arr[0], Integer.parseInt(s_arr[1])));
		}
		pas();
	}

	static void pas() throws Exception {
		out_pass2 = new PrintWriter(new FileWriter("pass2_out.txt"), true);
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("ic.txt")));
		String s, output = "", temp;
		int i, code = 0, ope = 0;

		String[] s1;
		output = "Addr" + "	" + "Mcode" + "	" + "op1" + "	" + "op2";
		out_pass2.println(output);

		while ((s = input.readLine()) != null) {
			System.out.println(s);
			StringTokenizer st = new StringTokenizer(s, "	", false);
			String s_arr[] = new String[st.countTokens()];
			for (i = 0; i < s_arr.length; i++) {
				s_arr[i] = st.nextToken();
			}
			if (s_arr[1].startsWith("(DL,")) {
				temp = "00" + "	" + "0" + "	" + s_arr[2].substring(3, (s_arr[2].length() - 1));
				output = s_arr[0] + "	" + temp;
				System.out.println(output);
			} else if (s_arr[1].startsWith("(IS,")) {
				if (s_arr[1].equals("(IS,00)")) {
					temp = "00" + "	" + "0" + "	" + "00";
					output = s_arr[0] + "	" + temp;
					System.out.println(output);
				} else if (s_arr[1].equals("(IS,07)")) {
					System.out.println("BC st");
					StringTokenizer st1 = new StringTokenizer(s_arr[2], ",", false);
					String op1 = st1.nextToken();
					String op2 = st1.nextToken();
					System.out.println("op1" + " " + op1 + " " + "op2" + op2);
					if (op1.equalsIgnoreCase("lt"))
						code = 1;
					else if (op1.equalsIgnoreCase("le"))
						code = 2;
					else if (op1.equalsIgnoreCase("eq"))
						code = 3;
					else if (op1.equalsIgnoreCase("gt"))
						code = 4;
					else if (op1.equalsIgnoreCase("ge"))
						code = 5;
					else if (op1.equalsIgnoreCase("any"))
						code = 6;

					else {
						code = -1;
						System.out.println("Invalid first operand");
					}
					for (SymTuple x : symtable) {
						if (op2.equals(x.symbol)) {
							ope = x.value;
							break;
						}
					}
					output = s_arr[0] + "	" + "07" + "	" + code + "	" + ope;
					System.out.println(output);
				} else if (s_arr[1].equals("(IS,09)") || s_arr[1].equals("(IS,10)")) {
					for (SymTuple x : symtable) {
						if (s_arr[2].equals(x.symbol)) {
							ope = x.value;
							break;
						}
					}
					output = s_arr[0] + "	" + s_arr[1].substring(4, s_arr[1].length() - 1) + "	" + "0" + "	" + ope;
					System.out.println(output);
				} else {

					StringTokenizer st1 = new StringTokenizer(s_arr[2], ",", false);
					String op3 = st1.nextToken();
					String op4 = st1.nextToken();
					System.out.println("op1" + " " + op3 + " " + "op2" + op4);
					if (op3.equalsIgnoreCase("areg"))
						code = 1;
					else if (op3.equalsIgnoreCase("breg"))
						code = 2;
					else if (op3.equalsIgnoreCase("creg"))
						code = 3;
					else {
						code = -1;
						System.out.println("Invalid first operand");
					}
					for (SymTuple x : symtable) {
						if (op4.equals(x.symbol)) {
							ope = x.value;
							break;
						}
					}
					output = s_arr[0] + "	" + s_arr[1].substring(4, s_arr[1].length() - 1) + "	" + code + "	"
							+ ope;
					System.out.println(output);
				}

			}
			out_pass2.println(output);
		}
	}
}
